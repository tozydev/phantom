package vn.id.tozydev.phantom.paper.adventure

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

/** Returns this [Component] if it's not null, otherwise returns an empty [Component]. */
fun Component?.orEmpty() = this ?: Component.empty()

/** Serializes this [Component] to a plain text string. */
fun Component.toPlainText(): String = PlainTextComponentSerializer.plainText().serialize(this)

/** Serializes this [Component] to a legacy text string using the section sign (§) for color codes. */
fun Component.toLegacyText(): String = LegacyComponentSerializer.legacySection().serialize(this)

/** Serializes this [Component] to a legacy text string using the ampersand (&) for color codes. */
fun Component.toLegacyAmpersandText(): String = LegacyComponentSerializer.legacyAmpersand().serialize(this)
