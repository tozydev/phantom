package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

interface ProjectConfigurer<in E : PaperProjectExtension> {
    operator fun invoke(
        project: Project,
        extension: E,
    )
}
