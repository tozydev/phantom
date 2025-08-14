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

fun TagResolver.Builder.placeholder(
    key: String,
    value: String,
): TagResolver.Builder = this.resolver(Placeholder.parsed(key, value))

fun TagResolver.Builder.placeholder(
    key: String,
    value: ComponentLike,
): TagResolver.Builder = this.resolver(Placeholder.component(key, value))

fun TagResolver.Builder.unparsedPlaceholder(
    key: String,
    value: String,
): TagResolver.Builder = this.resolver(Placeholder.unparsed(key, value))

fun TagResolver.Builder.styling(
    key: String,
    vararg style: StyleBuilderApplicable,
): TagResolver.Builder = this.resolver(Placeholder.styling(key, *style))
