package vn.id.tozydev.phantom.paper.message

import kotlin.time.Duration

/**
 * Represents a message that can be sent to entities.
 *
 * @see EmptyMessage
 * @see TextMessage
 * @see ActionbarMessage
 * @see TitleMessage
 * @see SoundMessage
 * @see CompositeMessage
 */
sealed interface Message

/** An empty message, representing no action. */
data object EmptyMessage : Message

/**
 * A text message that can be sent to entities.
 *
 * @property text The text of the message.
 */
@JvmInline
value class TextMessage(
    val text: String,
) : Message

/**
 * An action bar message that can be sent to entities.
 *
 * @property actionbar The text to display in the action bar.
 */
data class ActionbarMessage(
    val actionbar: String,
) : Message

/**
 * A title message that can be sent to entities.
 *
 * If all properties are null, the reset title message will be sent.
 *
 * @property title The title text to display. Can be null.
 * @property subtitle The subtitle text to display. Can be null.
 * @property fadeIn The duration of the fade-in effect. Can be null.
 * @property stay The duration to display the title. Can be null.
 * @property fadeOut The duration of the fade-out effect. Can be null.
 */
data class TitleMessage(
    val title: String? = null,
    val subtitle: String? = null,
    val fadeIn: Duration? = null,
    val stay: Duration? = null,
    val fadeOut: Duration? = null,
) : Message

/**
 * A sound message that can be sent to entities.
 *
 * @property type The name of the sound to play.
 * @property source The source of the sound. Can be null.
 * @property volume The volume of the sound.
 * @property pitch The pitch of the sound.
 * @property seed The seed for the sound. Can be null.
 */
data class SoundMessage(
    val type: String,
    val source: String? = null,
    val volume: Float = 1f,
    val pitch: Float = 1f,
    val seed: Long? = null,
) : Message

/**
 * A composite message that contains a list of other messages.
 *
 * @property messages The list of messages to send.
 */
data class CompositeMessage(
    val messages: List<Message>,
) : Message

/** Returns this message if it's not null, otherwise returns [EmptyMessage]. */
fun Message?.orEmpty() = this ?: EmptyMessage

/**
 * Creates a [CompositeMessage] from a list of messages.
 *
 * @param messages The list of messages to include in the composite message.
 */
fun Message(messages: List<Message>): Message = CompositeMessage(messages)
