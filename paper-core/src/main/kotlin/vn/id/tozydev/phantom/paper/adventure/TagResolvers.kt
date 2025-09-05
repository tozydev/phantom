@file:Suppress("unused")

package vn.id.tozydev.phantom.paper.adventure

import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.StyleBuilderApplicable
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

/** Creates a [TagResolver] with the specified block configuration. */
fun TagResolver(block: TagResolver.Builder.() -> Unit): TagResolver {
    val builder = TagResolver.builder()
    builder.block()
    return builder.build()
}

/**
 * Creates a placeholder that inserts a MiniMessage string.
 * The inserted string will impact the rest of the parse process.
 */
fun TagResolver.Builder.placeholder(
    key: String,
    value: String,
): TagResolver.Builder = this.resolver(Placeholder.parsed(key, value))

/**
 * Creates a replacement that inserts a component.
 * This replacement is auto-closing, so its style will not influence the style of following components.
 */
fun TagResolver.Builder.placeholder(
    key: String,
    value: ComponentLike,
): TagResolver.Builder = this.resolver(Placeholder.component(key, value))

/**
 * Creates a placeholder that inserts a literal string, without attempting to parse any contained tags.
 */
fun TagResolver.Builder.unparsedPlaceholder(
    key: String,
    value: String,
): TagResolver.Builder = this.resolver(Placeholder.unparsed(key, value))

/**
 * Creates a style tag which will modify the style of the component.
 * This style can be used like other styles.
 */
fun TagResolver.Builder.styling(
    key: String,
    vararg style: StyleBuilderApplicable,
): TagResolver.Builder = this.resolver(Placeholder.styling(key, *style))
