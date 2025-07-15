package vn.id.tozydev.phantom.paper.message

import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.ComponentDecoder
import net.kyori.adventure.title.Title
import vn.id.tozydev.phantom.paper.adventure.orEmpty
import vn.id.tozydev.phantom.paper.key.toKey
import kotlin.time.toJavaDuration

/** Represents a function that decodes a [String] into a [Component]. */
typealias MessageDecoder = ComponentDecoder<String, in Component>

internal val ResetTitle = Title.title(Component.empty(), Component.empty(), null)

/** Converts this [TextMessage] to a [Component] using the provided [decoder]. */
fun TextMessage.toComponent(decoder: MessageDecoder) = decoder.deserialize(text)

/** Converts this [ActionbarMessage] to a [Component] using the provided [decoder]. */
fun ActionbarMessage.toComponent(decoder: MessageDecoder) = decoder.deserialize(actionbar)

/** Converts this [TitleMessage] to a [Title] using the provided [decoder]. */
fun TitleMessage.toAdventureTitle(decoder: MessageDecoder): Title {
    if (title == null && subtitle == null && fadeIn == null && stay == null && fadeOut == null) {
        return ResetTitle
    }
    return Title.title(
        decoder.deserializeOrNull(title).orEmpty(),
        decoder.deserializeOrNull(subtitle).orEmpty(),
        Title.Times.times(
            fadeIn?.toJavaDuration() ?: Title.DEFAULT_TIMES.fadeIn(),
            stay?.toJavaDuration() ?: Title.DEFAULT_TIMES.stay(),
            fadeOut?.toJavaDuration() ?: Title.DEFAULT_TIMES.fadeOut(),
        ),
    )
}

/** Converts this [SoundMessage] to an Adventure [Sound] object. */
fun SoundMessage.toAdventureSound() =
    Sound.sound { builder ->
        builder.type(type.toKey())
        if (source != null) {
            builder.source(Sound.Source.NAMES.valueOrThrow(source.lowercase()))
        }
        builder.volume(volume).pitch(pitch)
        if (seed != null) {
            builder.seed(seed)
        }
    }
