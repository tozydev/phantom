package vn.id.tozydev.phantom.gradle.paper.features.library

import com.vanniktech.maven.publish.MavenPublishPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import tozydevReleases
import tozydevSnapshots
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperLibraryProjectExtension

object MavenPublishConfigurer : FeatureConfigurer<PaperLibraryProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperLibraryProjectExtension,
    ): Unit =
        with(project) {
            apply<MavenPublishPlugin>()
            extensions.configure<PublishingExtension> {
                repositories {
                    val isSnapshot = version.toString().endsWith("-SNAPSHOT")
                    val credentialsConfigure: PasswordCredentials.() -> Unit = {
                        username = providers.gradleProperty("tozydev-maven.username").orNull
                            ?: providers.environmentVariable("TOZYDEV_MAVEN_USERNAME").orNull
                        password = providers.gradleProperty("tozydev-maven.password").orNull
                            ?: providers.environmentVariable("TOZYDEV_MAVEN_PASSWORD").orNull
                    }
                    if (isSnapshot) {
                        tozydevSnapshots {
                            credentials {
                                credentialsConfigure()
                            }
                        }
                    } else {
                        tozydevReleases {
                            credentials {
                                credentialsConfigure()
                            }
                        }
                    }
                }
            }
        }
}
