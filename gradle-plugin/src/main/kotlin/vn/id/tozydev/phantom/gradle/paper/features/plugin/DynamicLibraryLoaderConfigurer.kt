package vn.id.tozydev.phantom.gradle.paper.features.plugin

import org.gradle.api.Project
import org.gradle.api.artifacts.ArtifactRepositoryContainer
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.artifacts.result.ResolvedComponentResult
import org.gradle.api.artifacts.result.ResolvedDependencyResult
import org.gradle.api.attributes.Category
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.slf4j.LoggerFactory
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectExtension
import java.net.URI

object DynamicLibraryLoaderConfigurer : FeatureConfigurer<PaperPluginProjectExtension> {
    private val logger = LoggerFactory.getLogger(DynamicLibraryLoaderConfigurer::class.java)

    private const val DEFAULT_LOADER_PACKAGE = "vn.id.tozydev.phantom.paper.loader"
    private const val DEFAULT_LOADER_CLASS_NAME = "DynamicLibrariesPluginLoader"

    private val MAVEN_CENTRAL_HOSTS =
        listOf(
            "repo1.maven.org",
            "repo.maven.apache.org",
        )
    private val MAVEN_CENTRAL_MIRROR = URI.create("https://maven-central-asia.storage-download.googleapis.com/maven2/")

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
                val libraryRepositoriesFilter = extension.extensions.findByType<LibraryRepositoriesFilter>()

                task.configure {
                    outputDir.convention(layout.buildDirectory.dir("generated/sources/phantom/java"))
                    repositories.set(
                        project.repositories
                            .collectMavenRepositories()
                            .excludeMavenCentral()
                            .filter(libraryRepositoriesFilter ?: { true })
                            .associate { it.name to it.url },
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
