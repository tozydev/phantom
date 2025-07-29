package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.polymorphicDomainObjectContainer
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.plugin.DebugServerConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.extensions.DependenciesExtensionConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.plugin.DynamicLibraryLoaderConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.jvm.JavaConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.jvm.KotlinConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.plugin.PaperPluginYmlConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.paperweight.PaperweightUserdevConfigurer
import xyz.jpenilla.runtask.pluginsapi.DownloadPluginsSpec
import xyz.jpenilla.runtask.pluginsapi.PluginApi

abstract class PaperPluginProjectPlugin : BasePaperProjectPlugin<PaperPluginProjectExtension>() {
    override val configurers: List<FeatureConfigurer<in PaperPluginProjectExtension>> =
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
