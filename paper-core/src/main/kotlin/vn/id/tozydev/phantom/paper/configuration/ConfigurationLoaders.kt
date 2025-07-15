package vn.id.tozydev.phantom.paper.configuration

import org.bukkit.plugin.Plugin
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import kotlin.io.path.notExists

/**
 * Create a [YamlConfigurationLoader] for the plugin.
 *
 * If the configuration file does not exist, it will be saved from the plugin resources.
 */
fun Plugin.yamlConfigurationLoader(
    name: String = "config",
    options: (ConfigurationOptions) -> ConfigurationOptions = { it },
): YamlConfigurationLoader {
    val configPathName = "$name.yml"
    val path = dataPath.resolve(configPathName)
    if (path.notExists()) {
        saveResource(configPathName, false)
    }
    return YamlConfigurationLoader
        .builder()
        .path(dataPath.resolve(configPathName))
        .defaultOptions { defaultOptions ->
            options(defaultOptions.serializers { it.registerAnnotatedObjects(objectMapperFactory()) })
        }.build()
}
