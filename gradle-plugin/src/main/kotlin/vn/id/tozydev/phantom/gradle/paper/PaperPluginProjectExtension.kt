package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.runtask.pluginsapi.DownloadPluginsSpec
import javax.inject.Inject

interface PaperPluginProjectExtension : PaperProjectExtension {
    @get:Nested
    val metadata: PaperPluginYaml

    @get:Nested
    val runServer: RunServerOptions

    fun metadata(configure: PaperPluginYaml.() -> Unit) {
        metadata.configure()
    }

    fun runServer(configure: RunServerOptions.() -> Unit) {
        runServer.configure()
    }
}

interface RunServerOptions {
    val acceptEula: Property<Boolean>

    val directory: DirectoryProperty

    val hotswap: Property<Boolean>

    val downloadPluginsSpec: DownloadPluginsSpec

    fun downloadPlugins(configure: DownloadPluginsSpec.() -> Unit) {
        downloadPluginsSpec.configure()
    }
}

internal abstract class PaperPluginProjectExtensionImpl
    @Inject
    constructor(
        override val runServer: RunServerOptions,
    ) : PaperPluginProjectExtension

internal abstract class RunServerOptionsImpl
    @Inject
    constructor(
        override val downloadPluginsSpec: DownloadPluginsSpec,
    ) : RunServerOptions
