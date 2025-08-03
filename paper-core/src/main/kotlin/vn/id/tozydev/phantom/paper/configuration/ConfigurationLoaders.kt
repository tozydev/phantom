package vn.id.tozydev.phantom.paper.configuration

import org.bukkit.plugin.Plugin
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import vn.id.tozydev.phantom.paper.configuration.serializers.ConfigurationSerializers
import kotlin.io.path.notExists

/**
 * Create a [YamlConfigurationLoader] for the plugin.
 *
 * If the configuration file does not exist, it will be saved from the plugin resources.
 */
fun Plugin.yamlConfigurationLoader(
    name: String = "config",
    options: ConfigurationOptions.() -> ConfigurationOptions = { this },
): YamlConfigurationLoader {
    val configPathName = "$name.yml"
    val path = dataPath.resolve(configPathName)
    if (path.notExists()) {
        saveResource(configPathName, false)
    }
    return YamlConfigurationLoader
        .builder()
        .path(dataPath.resolve(configPathName))
        .defaultOptions { (it + ConfigurationSerializers.Defaults).options() }
        .build()
}
