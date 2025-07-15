package vn.id.tozydev.phantom.paper.adventure

import net.kyori.adventure.text.Component

/** Returns this [Component] if it's not null, otherwise returns an empty [Component]. */
fun Component?.orEmpty() = this ?: Component.empty()
