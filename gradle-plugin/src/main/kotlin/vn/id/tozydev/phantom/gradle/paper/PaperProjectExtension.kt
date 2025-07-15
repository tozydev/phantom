package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.provider.Property

interface PaperProjectExtension {
    val javaVersion: Property<Int>
    val minecraftVersion: Property<String>
}
