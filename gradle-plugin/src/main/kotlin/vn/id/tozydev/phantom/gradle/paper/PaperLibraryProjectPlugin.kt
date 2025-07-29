package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.extensions.DependenciesExtensionConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.library.JavaLibraryConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.jvm.KotlinConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.library.MavenPublishConfigurer
import vn.id.tozydev.phantom.gradle.paper.features.paperweight.PaperweightUserdevConfigurer

abstract class PaperLibraryProjectPlugin : BasePaperProjectPlugin<PaperLibraryProjectExtension>() {
    override fun createExtension(project: Project): PaperLibraryProjectExtension =
        project.extensions
            .create(
                PaperLibraryProjectExtension::class,
                "paperLibrary",
                DefaultPaperLibraryProjectExtension::class,
            ).apply {
                javaVersion.convention(BuildConfig.JAVA_VERSION)
                minecraftVersion.convention(BuildConfig.MINECRAFT_VERSION)
            }

    override val configurers: List<FeatureConfigurer<in PaperLibraryProjectExtension>> =
        listOf(
            JavaLibraryConfigurer,
            KotlinConfigurer,
            PaperweightUserdevConfigurer,
            MavenPublishConfigurer,
            DependenciesExtensionConfigurer,
        )
}
