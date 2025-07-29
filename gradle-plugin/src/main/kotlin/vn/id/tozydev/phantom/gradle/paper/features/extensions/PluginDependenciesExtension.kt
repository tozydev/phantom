package vn.id.tozydev.phantom.gradle.paper.features.extensions

import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.DependenciesRes

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
