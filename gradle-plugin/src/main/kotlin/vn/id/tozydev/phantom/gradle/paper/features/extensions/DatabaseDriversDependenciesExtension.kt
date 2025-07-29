package vn.id.tozydev.phantom.gradle.paper.features.extensions

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.DependenciesRes

@Suppress("unused")
abstract class DatabaseDriversDependenciesExtension(
    private val dependencies: DependencyHandler,
) {
    fun mysql(
        version: String = DependenciesRes.MYSQL_VERSION,
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.MYSQL_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun mariadb(
        version: String = DependenciesRes.MARIADB_VERSION,
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.MARIADB_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun postgresql(
        version: String = DependenciesRes.POSTGRESQL_VERSION,
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.POSTGRESQL_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun sqlite(
        version: String = DependenciesRes.SQLITE_VERSION,
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.SQLITE_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun h2(
        version: String = DependenciesRes.H2_VERSION,
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.H2_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}
