package vn.id.tozydev.phantom.gradle

import org.gradle.api.Project

interface FeatureConfigurer<E> {
    operator fun invoke(
        project: Project,
        extension: E,
    )
}
