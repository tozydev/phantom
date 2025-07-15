package vn.id.tozydev.phantom.paper.message.configuration

import org.spongepowered.configurate.ConfigurationNode
import vn.id.tozydev.phantom.paper.message.ActionbarMessage
import vn.id.tozydev.phantom.paper.message.EmptyMessage
import vn.id.tozydev.phantom.paper.message.Message
import vn.id.tozydev.phantom.paper.message.SoundMessage
import vn.id.tozydev.phantom.paper.message.TextMessage
import vn.id.tozydev.phantom.paper.message.TitleMessage
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.ACTIONBAR
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.FADEIN
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.FADEOUT
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.FADE_IN
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.FADE_OUT
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.PITCH
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.SEED
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.SOUND
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.SOURCE
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.STAY
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.SUBTITLE
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.TEXT
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.TITLE
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields.VOLUME
import kotlin.time.Duration

internal object MessageNodeParser {
    /**
     * Parses a [ConfigurationNode] into a [Message].
     *
     * Example:
     * ```yaml
     * test-message: Hello, world!
     * test-actionbar:
     *  actionbar: This is an action bar message.
     * test-title:
     *  title: Welcome to the server!
     *  subtitle: Enjoy your stay.
     * test-composite:
     *  - text: This is a composite message.
     *  - actionbar: This is an action bar message.
     *  - sound: minecraft:entity.player.levelup
     *    source: master
     * ```
     */
    fun parse(node: ConfigurationNode): Message =
        when {
            node.isList -> node.childrenList().map { parse(it) }.let(::Message)
            node.isMap -> parseMessageNode(node)
            else -> parseTextNode(node)
        }

    private fun parseTextNode(node: ConfigurationNode) = if (node.empty()) EmptyMessage else TextMessage(node.string.toString())

    private fun parseMessageNode(node: ConfigurationNode): Message {
        var contentNode = node.node(TEXT)
        if (contentNode.empty()) {
            // Scalar value: `text: "Hello, world!"`
            return parseTextNode(contentNode)
        }

        contentNode = node.node(ACTIONBAR)
        if (contentNode.empty()) {
            // Scalar value: `actionbar: "This is an action bar message."`
            return parseActionbarNode(contentNode)
        }

        contentNode = node.node(SOUND)
        if (contentNode.empty()) {
            return parseSoundNode(node)
        }

        return parseTitleNode(node) ?: throw IllegalArgumentException("Cannot parse node: $node")
    }

    private fun parseTitleNode(node: ConfigurationNode): TitleMessage? {
        val title = node.node(TITLE)
        val subtitle = node.node(SUBTITLE)
        var fadeIn = node.node(FADE_IN)
        fadeIn = if (fadeIn.empty()) node.node(FADEIN) else fadeIn
        val stay = node.node(STAY)
        var fadeOut = node.node(FADE_OUT)
        fadeOut = if (fadeOut.empty()) node.node(FADEOUT) else fadeOut
        if (title.isNull && subtitle.isNull && fadeIn.isNull && stay.isNull && fadeOut.isNull) {
            return null
        }
        return TitleMessage(
            title = title.string,
            subtitle = subtitle.string,
            fadeIn = fadeIn.string?.let(Duration::parse),
            stay = stay.string?.let(Duration::parse),
            fadeOut = fadeOut.string?.let(Duration::parse),
        )
    }

    private fun parseActionbarNode(node: ConfigurationNode) = ActionbarMessage(node.getString(""))

    private fun parseSoundNode(node: ConfigurationNode): SoundMessage {
        val sound = node.node(SOUND)
        val source = node.node(SOURCE)
        val volume = node.node(VOLUME)
        val pitch = node.node(PITCH)
        val seed = node.node(SEED)
        return SoundMessage(
            type = checkNotNull(sound.string) { "Sound type is required" },
            source = source.string,
            volume = volume.getFloat(1f),
            pitch = pitch.getFloat(1f),
            seed = seed.long,
        )
    }

    object Fields {
        const val TEXT = "text"

        const val ACTIONBAR = "actionbar"

        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val FADEIN = "fadein"
        const val FADE_IN = "fade-in"
        const val STAY = "stay"
        const val FADEOUT = "fadeout"
        const val FADE_OUT = "fade-out"

        const val SOUND = "sound"
        const val SOURCE = "source"
        const val VOLUME = "volume"
        const val PITCH = "pitch"
        const val SEED = "seed"
    }
}
