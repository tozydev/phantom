package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import javax.inject.Inject

interface PaperLibraryProjectExtension : PaperProjectExtension {
    fun localPublishingRepository(action: MavenArtifactRepository.() -> Unit = {})
}

internal abstract class DefaultPaperLibraryProjectExtension : PaperLibraryProjectExtension {
    @get:Inject
    abstract val project: Project

    override fun localPublishingRepository(action: MavenArtifactRepository.() -> Unit) {
        project.extensions.configure<PublishingExtension> {
            repositories {
                maven {
                    name = "Local"
                    url = project.uri(project.layout.buildDirectory.dir("artifacts/maven"))
                    action(this)
                }
            }
        }
    }
}
