package vn.id.tozydev.phantom.gradle.paper.features.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object DependenciesExtensionConfigurer : FeatureConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            dependencies.extensions.create<PhantomDependenciesExtension>("phantomDependencies", this)
            dependencies.extensions.create<PluginDependenciesExtension>("pluginDependencies", dependencies)
            dependencies.extensions.create<DatabaseDriversDependenciesExtension>("databaseDrivers", dependencies)
        }
}
