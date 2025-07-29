package vn.id.tozydev.phantom.gradle.paper.features.extensions

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.paper.features.plugin.DynamicLibraryLoaderConfigurer

@Suppress("unused")
abstract class PhantomDependenciesExtension(
    private val dependencies: DependencyHandler,
) {
    fun paperCore(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-paper-core:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun databaseJdbc(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-database-jdbc:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun databaseExposed(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-database-exposed:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}
