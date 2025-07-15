package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.apply
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object JavaLibraryConfigurer : ProjectConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            apply<JavaLibraryPlugin>()
            JavaConfigurer(project, extension)
        }
}
