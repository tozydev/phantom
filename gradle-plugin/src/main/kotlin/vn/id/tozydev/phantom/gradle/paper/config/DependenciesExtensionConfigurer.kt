package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.DependenciesRes
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object DependenciesExtensionConfigurer : ProjectConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            dependencies.extensions.create<PhantomDependenciesExtension>("phantomDependencies", dependencies)
            dependencies.extensions.create<PluginDependenciesExtension>("pluginDependencies", dependencies)
            dependencies.extensions.create<DatabaseDriversDependenciesExtension>("databaseDrivers", dependencies)
        }
}

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

@Suppress("unused")
abstract class PluginDependenciesExtension(
    private val dependencies: DependencyHandler,
) {
    fun placeholderApi(
        version: String = DependenciesRes.PLACEHOLDERAPI_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.PLACEHOLDERAPI_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun itemNbtApi(
        version: String = DependenciesRes.ITEM_NBT_API_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.ITEM_NBT_API_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun protocolLib(
        version: String = DependenciesRes.PROTOCOL_LIB_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.PROTOCOL_LIB_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun vault(
        version: String = DependenciesRes.VAULT_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.VAULT_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun luckPerms(
        version: String = DependenciesRes.LUCKPERMS_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.LUCKPERMS_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun worldEdit(
        version: String = DependenciesRes.WORLD_EDIT_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.WORLD_EDIT_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun worldGuard(
        version: String = DependenciesRes.WORLD_GUARD_VERSION,
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${DependenciesRes.WORLD_GUARD_MODULE}:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}

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
