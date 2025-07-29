package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.provider.Property

interface PaperProjectExtension : ExtensionAware {
    val javaVersion: Property<Int>
    val minecraftVersion: Property<String>
}
