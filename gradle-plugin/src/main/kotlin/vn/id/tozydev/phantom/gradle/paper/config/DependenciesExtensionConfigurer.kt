package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

// TODO manage versions in a central place

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

    fun databaseCore(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-paper-database-core:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun databaseExposed(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-paper-database-exposed:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}

@Suppress("unused")
abstract class PluginDependenciesExtension(
    private val dependencies: DependencyHandler,
) {
    fun placeholderApi(
        version: String = "2.11.6",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("me.clip:placeholderapi:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun itemNbtApi(
        version: String = "2.15.0",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("de.tr7zw:item-nbt-api:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun protocolLib(
        version: String = "5.3.0",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("com.comphenix.protocol:ProtocolLib:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun vault(
        version: String = "1.7.1",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("com.github.MilkBowl:VaultAPI:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun luckPerms(
        version: String = "5.5",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("net.luckperms:api:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun worldEdit(
        version: String = "7.3.15-SNAPSHOT",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep =
            dependencies.create("com.sk89q.worldedit:worldedit-bukkit:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun worldGuard(
        version: String = "7.1.0-SNAPSHOT",
        configurationName: String = JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep =
            dependencies.create("com.sk89q.worldguard:worldguard-bukkit:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}

@Suppress("unused")
abstract class DatabaseDriversDependenciesExtension(
    private val dependencies: DependencyHandler,
) {
    fun mysql(
        version: String = "9.3.0",
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("com.mysql:mysql-connector-j:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun mariadb(
        version: String = "3.5.4",
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep =
            dependencies.create("org.mariadb.jdbc:mariadb-java-client:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun postgresql(
        version: String = "42.7.7",
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("org.postgresql:postgresql:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun sqlite(
        version: String = "3.50.2.0",
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("org.xerial:sqlite-jdbc:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }

    fun h2(
        version: String = "2.3.232",
        configurationName: String = JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("com.h2database:h2:$version", action)
        dependencies.add(configurationName, dep)
        return dep
    }
}
