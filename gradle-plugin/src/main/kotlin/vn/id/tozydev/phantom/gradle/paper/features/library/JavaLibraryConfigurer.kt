package vn.id.tozydev.phantom.gradle.paper.features.library

import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.apply
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension
import vn.id.tozydev.phantom.gradle.paper.features.jvm.JavaConfigurer

object JavaLibraryConfigurer : FeatureConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            apply<JavaLibraryPlugin>()
            JavaConfigurer(project, extension)
        }
}
