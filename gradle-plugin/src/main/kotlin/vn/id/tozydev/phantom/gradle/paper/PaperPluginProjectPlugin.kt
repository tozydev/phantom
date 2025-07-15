package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.polymorphicDomainObjectContainer
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.paper.config.DebugServerConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.DependenciesExtensionConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.DynamicLibraryLoaderConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.JavaConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.KotlinConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.PaperPluginYmlConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.PaperweightUserdevConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.ProjectConfigurer
import xyz.jpenilla.runtask.pluginsapi.DownloadPluginsSpec
import xyz.jpenilla.runtask.pluginsapi.PluginApi

abstract class PaperPluginProjectPlugin : BasePaperProjectPlugin<PaperPluginProjectExtension>() {
    override val configurers: List<ProjectConfigurer<PaperPluginProjectExtension>> =
        listOf(
            JavaConfigurer,
            KotlinConfigurer,
            PaperweightUserdevConfigurer,
            DebugServerConfigurer,
            PaperPluginYmlConfigurer,
            DynamicLibraryLoaderConfigurer,
            DependenciesExtensionConfigurer,
        )

    override fun createExtension(project: Project): PaperPluginProjectExtension {
        val runServerOptions =
            project.objects.newInstance<RunServerOptionsImpl>(
                project.objects.newInstance(
                    DownloadPluginsSpec::class,
                    project.objects.polymorphicDomainObjectContainer(
                        PluginApi::class,
                    ),
                ),
            )
        return project.extensions
            .create(
                PaperPluginProjectExtension::class,
                "paperPlugin",
                PaperPluginProjectExtensionImpl::class,
                runServerOptions,
            ).apply {
                javaVersion.convention(BuildConfig.JAVA_VERSION)
                minecraftVersion.convention(BuildConfig.MINECRAFT_VERSION)
                metadata.setConventionsFromProjectMeta(project)
                metadata.apiVersion.convention(minecraftVersion)
                runServerOptions.acceptEula.convention(false)
                runServerOptions.hotswap.convention(true)
                runServerOptions.directory.convention(project.layout.buildDirectory.dir("run-server"))
            }
    }
}
