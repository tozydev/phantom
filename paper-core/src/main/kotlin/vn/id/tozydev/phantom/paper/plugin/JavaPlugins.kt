package vn.id.tozydev.phantom.paper.plugin

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import vn.id.tozydev.phantom.paper.Server

/**
 * Retrieves the instance of a specified plugin class.
 *
 * @see JavaPlugin.getPlugin
 */
inline fun <reified T : JavaPlugin> javaPlugin() = JavaPlugin.getPlugin(T::class.java)

/** Retrieves the plugin with the specified [name], if it is of the specified type [T]. */
inline fun <reified T : Plugin> pluginOrNull(name: String) = Server.pluginManager.getPlugin(name) as? T

/**
 * Retrieves the plugin with the specified [name], if it is of the specified type.
 *
 * @throws IllegalArgumentException if the plugin with the given name is not found.
 * @throws ClassCastException if the plugin is not of the specified type.
 */
inline fun <reified T : Plugin> plugin(name: String) = requireNotNull(pluginOrNull(name)) { "Plugin $name not found" } as T
