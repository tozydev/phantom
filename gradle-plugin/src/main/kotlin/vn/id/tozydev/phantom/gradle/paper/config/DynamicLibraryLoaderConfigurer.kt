package vn.id.tozydev.phantom.gradle.paper.config

import com.palantir.javapoet.AnnotationSpec
import com.palantir.javapoet.ClassName
import com.palantir.javapoet.CodeBlock
import com.palantir.javapoet.FieldSpec
import com.palantir.javapoet.JavaFile
import com.palantir.javapoet.MethodSpec
import com.palantir.javapoet.ParameterSpec
import com.palantir.javapoet.ParameterizedTypeName
import com.palantir.javapoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.artifacts.ArtifactRepositoryContainer
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.result.ResolvedComponentResult
import org.gradle.api.artifacts.result.ResolvedDependencyResult
import org.gradle.api.attributes.Category
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.annotations.NotNull
import org.slf4j.LoggerFactory
import vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectExtension
import java.nio.file.Path
import java.time.LocalDateTime
import javax.annotation.processing.Generated
import javax.inject.Inject
import javax.lang.model.element.Modifier
import kotlin.io.path.createDirectories

object DynamicLibraryLoaderConfigurer : ProjectConfigurer<PaperPluginProjectExtension> {
    private val logger = LoggerFactory.getLogger(DynamicLibraryLoaderConfigurer::class.java)

    private const val DEFAULT_LOADER_PACKAGE = "vn.id.tozydev.phantom.paper.loader"
    private const val DEFAULT_LOADER_CLASS_NAME = "DynamicLibrariesPluginLoader"

    private val MAVEN_CENTRAL_HOSTS =
        listOf(
            "repo1.maven.org",
            "repo.maven.apache.org",
        )
    private const val MAVEN_CENTRAL_MIRROR = "https://maven-central-asia.storage-download.googleapis.com/maven2/"

    const val LIBRARY_CONFIGURATION_NAME = "library"

    override fun invoke(
        project: Project,
        extension: PaperPluginProjectExtension,
    ): Unit =
        with(project) {
            val library =
                configurations.register(LIBRARY_CONFIGURATION_NAME) {
                    configurations[JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME].extendsFrom(this)
                }

            val task =
                tasks.register(
                    "generateDynamicLibrariesLoaderClass",
                    GenerateDynamicLibrariesLoaderClassTask::class.java,
                ) {
                    description = "Generates a dynamic libraries PluginLoader class for Paper plugin."
                    packageName.convention(DEFAULT_LOADER_PACKAGE)
                    className.convention(DEFAULT_LOADER_CLASS_NAME)
                }

            extension.metadata {
                loader.convention("$DEFAULT_LOADER_PACKAGE.$DEFAULT_LOADER_CLASS_NAME")
            }

            afterEvaluate {
                task.configure {
                    outputDir.convention(layout.buildDirectory.dir("generated/sources/phantom/java"))
                    repositories.set(
                        project.repositories
                            .collectMavenRepositories()
                            .excludeMavenCentral()
                            .associate { it.name to it.url.toASCIIString() },
                    )
                    repositories.put(
                        ArtifactRepositoryContainer.DEFAULT_MAVEN_CENTRAL_REPO_NAME,
                        MAVEN_CENTRAL_MIRROR,
                    )
                    dependencies.set(
                        library
                            .flatMap { it.incoming.resolutionResult.rootComponent }
                            .map { result ->
                                result.collectModuleVersions().map { it.toString() }.toList()
                            },
                    )
                }

                tasks.named(JavaPlugin.COMPILE_JAVA_TASK_NAME) {
                    dependsOn(task)
                }

                extensions.getByType<SourceSetContainer>().named(SourceSet.MAIN_SOURCE_SET_NAME) {
                    java {
                        srcDir(task.flatMap { it.outputDir })
                    }
                }
            }
        }

    fun RepositoryHandler.collectMavenRepositories() =
        filterIsInstance<MavenArtifactRepository>()
            .distinctBy { it.url.toASCIIString() }

    private fun List<MavenArtifactRepository>.excludeMavenCentral() = filterNot { MAVEN_CENTRAL_HOSTS.contains(it.url.host) }

    fun ResolvedDependencyResult.containsPlatformVariant() =
        selected.variants.any {
            it.displayName.contains(Category.REGULAR_PLATFORM) || it.displayName.contains(Category.ENFORCED_PLATFORM)
        }

    fun ResolvedComponentResult.collectModuleVersions() =
        dependencies
            .asSequence()
            .filterIsInstance<ResolvedDependencyResult>()
            .filterNot {
                if (it.containsPlatformVariant()) {
                    logger.info("Ignoring platform dependency: ${it.selected.moduleVersion}")
                    true
                } else {
                    false
                }
            }.mapNotNull { it.selected.moduleVersion }
}

@CacheableTask
abstract class GenerateDynamicLibrariesLoaderClassTask : DefaultTask() {
    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val className: Property<String>

    @get:Input
    abstract val repositories: MapProperty<String, String>

    @get:Input
    abstract val dependencies: ListProperty<String>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @get:Inject
    abstract val fsOps: FileSystemOperations

    @TaskAction
    fun run() {
        val output = outputDir.get().asFile.toPath()
        fsOps.delete { delete(output) }

        output.createDirectories()

        val builder = DynamicLibrariesLoaderClassBuilder()
        builder.packageName = packageName.get()
        builder.className = className.get()
        builder.repositories = repositories.get()
        builder.dependencies = dependencies.get()

        builder.writeToPath(output)
    }
}

internal class DynamicLibrariesLoaderClassBuilder {
    var packageName: String = "vn.id.tozydev.paperinfra.paper"
    var className: String = "DynamicLibrariesPluginLoader"
    var repositories: Map<String, String> = mapOf()
    var dependencies: List<String> = listOf()

    fun writeToPath(directory: Path) {
        createJavaFile().writeToPath(directory)
    }

    private fun createJavaFile(): JavaFile =
        JavaFile
            .builder(packageName, createTypeSpec())
            .skipJavaLangImports(true)
            .build()

    private fun createTypeSpec(): TypeSpec =
        TypeSpec
            .classBuilder(className)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(suppressUnstableApiUsageAnnotation)
            .addAnnotation(createGeneratedAnnotationSpec())
            .addSuperinterface(PluginLoader)
            .addField(repositoriesField)
            .addField(dependenciesField)
            .addStaticBlock(createStaticInitializer())
            .addMethod(classloaderMethod)
            .build()

    private val suppressUnstableApiUsageAnnotation =
        AnnotationSpec
            .builder(SuppressWarnings::class.java)
            .addMember("value", $$"$S", "UnstableApiUsage")
            .build()

    private fun createGeneratedAnnotationSpec() =
        AnnotationSpec
            .builder(Generated::class.java)
            .addMember("value", $$"$S", DynamicLibrariesLoaderClassBuilder::class.qualifiedName)
            .addMember("date", $$"$S", LocalDateTime.now().toString())
            .addMember("comments", $$"$S", "Generated by vn.id.tozydev.paperinfra.paper Gradle plugin")
            .build()

    private fun createStaticInitializer(): CodeBlock =
        CodeBlock
            .builder()
            .apply {
                for ((key, value) in repositories) {
                    addStatement($$"repositories.put($S, $S)", key, value)
                }
                for (dependency in dependencies) {
                    addStatement($$"dependencies.add($S)", dependency)
                }
            }.build()

    private val repositoriesField: FieldSpec
        get() {
            val mapOfStringString =
                ParameterizedTypeName.get(
                    ClassName.get(MutableMap::class.java),
                    ClassName.get(String::class.java),
                    ClassName.get(String::class.java),
                )

            return FieldSpec
                .builder(mapOfStringString, "repositories")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer($$"new $T<>()", ClassName.get(LinkedHashMap::class.java))
                .build()
        }

    private val dependenciesField: FieldSpec
        get() {
            val listOfString =
                ParameterizedTypeName.get(
                    ClassName.get(MutableList::class.java),
                    ClassName.get(String::class.java),
                )

            return FieldSpec
                .builder(listOfString, "dependencies")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer($$"new $T<>()", ClassName.get(ArrayList::class.java))
                .build()
        }

    private val classloaderMethod: MethodSpec
        get() {
            return MethodSpec
                .methodBuilder("classloader")
                .addAnnotation(Override::class.java)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(
                    ParameterSpec
                        .builder(PluginClasspathBuilder, "pluginClasspathBuilder")
                        .addAnnotation(NotNull::class.java)
                        .build(),
                ).addCode(
                    CodeBlock
                        .builder()
                        .addStatement($$"var resolver = new $T()", MavenLibraryResolver)
                        .beginControlFlow("for (var entry : repositories.entrySet())")
                        .addStatement(
                            $$"resolver.addRepository(new $T.Builder(entry.getKey(), $S, entry.getValue()).build())",
                            RemoteRepository,
                            "default",
                        ).endControlFlow()
                        .beginControlFlow("for (String dependency : dependencies)")
                        .addStatement(
                            $$"resolver.addDependency(new $T(new $T(dependency), null))",
                            Dependency,
                            DefaultArtifact,
                        ).endControlFlow()
                        .addStatement("pluginClasspathBuilder.addLibrary(resolver)")
                        .build(),
                ).build()
        }

    companion object {
        private val PluginLoader = ClassName.get("io.papermc.paper.plugin.loader", "PluginLoader")
        private val PluginClasspathBuilder =
            ClassName.get("io.papermc.paper.plugin.loader", "PluginClasspathBuilder")
        private val MavenLibraryResolver =
            ClassName.get("io.papermc.paper.plugin.loader.library.impl", "MavenLibraryResolver")
        private val RemoteRepository = ClassName.get("org.eclipse.aether.repository", "RemoteRepository")
        private val Dependency = ClassName.get("org.eclipse.aether.graph", "Dependency")
        private val DefaultArtifact = ClassName.get("org.eclipse.aether.artifact", "DefaultArtifact")
    }
}
