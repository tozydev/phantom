package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.paper.config.DependenciesExtensionConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.JavaLibraryConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.KotlinConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.MavenPublishConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.PaperweightUserdevConfigurer
import vn.id.tozydev.phantom.gradle.paper.config.ProjectConfigurer

abstract class PaperLibraryProjectPlugin : BasePaperProjectPlugin<PaperLibraryProjectExtension>() {
    override fun createExtension(project: Project): PaperLibraryProjectExtension =
        project.extensions.create<PaperLibraryProjectExtension>("paperLibrary").apply {
            javaVersion.convention(BuildConfig.JAVA_VERSION)
            minecraftVersion.convention(BuildConfig.MINECRAFT_VERSION)
        }

    override val configurers: List<ProjectConfigurer<PaperLibraryProjectExtension>> =
        listOf(
            JavaLibraryConfigurer,
            KotlinConfigurer,
            PaperweightUserdevConfigurer,
            MavenPublishConfigurer,
            DependenciesExtensionConfigurer,
        )
}
