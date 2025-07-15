package vn.id.tozydev.phantom.gradle.paper.config

import com.vanniktech.maven.publish.MavenPublishPlugin
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import velaReleases
import velaSnapshots
import vn.id.tozydev.phantom.gradle.paper.PaperLibraryProjectExtension

object MavenPublishConfigurer : ProjectConfigurer<PaperLibraryProjectExtension> {
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
                        username = providers.gradleProperty("vela.username").orNull
                            ?: providers.environmentVariable("VELA_USERNAME").orNull
                        password = providers.gradleProperty("vela.password").orNull
                            ?: providers.environmentVariable("VELA_PASSWORD").orNull
                    }
                    if (isSnapshot) {
                        velaSnapshots {
                            credentials {
                                credentialsConfigure()
                            }
                        }
                    } else {
                        velaReleases {
                            credentials {
                                credentialsConfigure()
                            }
                        }
                    }
                }
            }
        }
}
