package vn.id.tozydev.phantom.paper.message

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.ComponentDecoder

/**
 * Sends a [Message] to the [Audience] using the provided [MessageDecoder] for deserialization.
 *
 * By default, [decoder] will decode text to plain text component.
 *
 * This function handles different message types:
 * - [ActionbarMessage]: Sends the message to the action bar.
 * - [CompositeMessage]: Sends each individual message within the composite.
 * - [EmptyMessage]: Does nothing.
 * - [SoundMessage]: Plays the specified sound.
 * - [TextMessage]: Sends the message as chat text.
 * - [TitleMessage]: Displays the title or resets the existing one if empty.
 */
fun Audience.sendMessage(
    message: Message,
    decoder: MessageDecoder = PlainTextComponentDecoder,
) = when (message) {
    is ActionbarMessage -> sendActionBar(message.toComponent(decoder))
    is CompositeMessage -> sendMessages(message, decoder)
    EmptyMessage -> Unit
    is SoundMessage -> playSound(message.toAdventureSound())
    is TextMessage -> sendMessage(message.toComponent(decoder))
    is TitleMessage -> sendOrResetTitle(message, decoder)
}

/**
 * Sends a rich [Message] to the [Audience] using MiniMessage for deserialization.
 *
 * This function utilizes the provided [miniMessage] instance and [tagResolver] to deserialize
 * the message content before sending it to the audience.
 *
 * @see sendMessage
 */
fun Audience.sendRichMessage(
    message: Message,
    tagResolver: TagResolver = TagResolver.empty(),
    miniMessage: MiniMessage = MiniMessage.miniMessage(),
) = sendMessage(message) { input -> miniMessage.deserialize(input, tagResolver) }

internal object PlainTextComponentDecoder : ComponentDecoder<String, Component> {
    override fun deserialize(input: String): Component = Component.text(input)
}

private fun Audience.sendMessages(
    message: CompositeMessage,
    decoder: MessageDecoder,
) {
    for (msg in message.messages) {
        sendMessage(msg, decoder)
    }
}

private fun Audience.sendOrResetTitle(
    title: TitleMessage,
    decoder: MessageDecoder,
) {
    val adventureTitle = title.toAdventureTitle(decoder)
    if (adventureTitle == ResetTitle) {
        resetTitle()
    } else {
        showTitle(adventureTitle)
    }
}
