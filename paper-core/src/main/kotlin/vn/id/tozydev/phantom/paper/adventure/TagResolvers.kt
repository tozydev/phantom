@file:Suppress("unused")

package vn.id.tozydev.phantom.paper.adventure

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.StyleBuilderApplicable
import net.kyori.adventure.text.minimessage.Context
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.TagPattern
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue
import net.kyori.adventure.text.minimessage.tag.resolver.Formatter
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import java.time.temporal.Temporal
import kotlin.time.Instant

/** Creates a [TagResolver] with the specified block configuration. */
fun TagResolver(block: TagResolver.Builder.() -> Unit): TagResolver {
    val builder = TagResolver.builder()
    builder.block()
    return builder.build()
}

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.parsed(value: String) = builder.resolver(Placeholder.parsed(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.parsed(value: () -> Any) = parsed(value().toString())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.unparsed(value: String) = builder.resolver(Placeholder.unparsed(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.unparsed(value: () -> Any) = unparsed(value().toString())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.component(value: Component) = builder.resolver(Placeholder.component(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.component(value: () -> Component) = component(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.styling(style: List<StyleBuilderApplicable>) =
    builder.resolver(Placeholder.styling(this, *style.toTypedArray()))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.styling(style: StyleBuilderApplicable) = builder.resolver(Placeholder.styling(this, style))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.styling(style: () -> StyleBuilderApplicable) = styling(style())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.number(value: Number) = builder.resolver(Formatter.number(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.number(value: () -> Number) = number(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.date(value: Temporal) = builder.resolver(Formatter.date(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.date(value: () -> Temporal) = date(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.instant(instant: Instant) =
    date(instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.instant(instant: () -> Instant) = instant(instant())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.dateTime(value: LocalDateTime) = date(value.toJavaLocalDateTime())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.dateTime(value: () -> LocalDateTime) = dateTime(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.time(value: LocalTime) = date(value.toJavaLocalTime())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.time(value: () -> LocalTime) = time(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.choice(value: Int) = builder.resolver(Formatter.choice(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.choice(value: () -> Int) = choice(value())

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.booleanChoice(value: Boolean) = builder.resolver(Formatter.booleanChoice(this, value))

context(builder: TagResolver.Builder)
infix fun @receiver:TagPattern String.booleanChoice(value: () -> Boolean) = booleanChoice(value())

context(builder: TagResolver.Builder)
operator fun String.invoke(handler: (ArgumentQueue, Context) -> Tag) = builder.resolver(TagResolver.resolver(this, handler))

// Old style functions - kept for compatibility

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
