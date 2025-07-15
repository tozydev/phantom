@file:Suppress("ObjectPropertyName", "unused")

import org.gradle.plugin.use.PluginDependenciesSpec

val PluginDependenciesSpec.`paper-library`
    get() =
        id("vn.id.tozydev.phantom.paper-library")

val PluginDependenciesSpec.`paper-plugin`
    get() =
        id("vn.id.tozydev.phantom.paper-plugin")

val PluginDependenciesSpec.shadow
    get() =
        id("com.gradleup.shadow")
