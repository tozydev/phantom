package vn.id.tozydev.phantom.gradle.paper.features.paperweight

import io.papermc.paperweight.userdev.PaperweightUser
import io.papermc.paperweight.userdev.PaperweightUserDependenciesExtension
import io.papermc.paperweight.userdev.PaperweightUserExtension
import io.papermc.paperweight.userdev.ReobfArtifactConfiguration
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object PaperweightUserdevConfigurer : FeatureConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            apply<PaperweightUser>()
            dependencies {
                configure<PaperweightUserDependenciesExtension> {
                    paperDevBundle(extension.minecraftVersion.map { "$it-R0.1-SNAPSHOT" })
                }
            }

            extensions.configure<PaperweightUserExtension> {
                reobfArtifactConfiguration.convention(ReobfArtifactConfiguration.Companion.MOJANG_PRODUCTION)
            }
        }
}
