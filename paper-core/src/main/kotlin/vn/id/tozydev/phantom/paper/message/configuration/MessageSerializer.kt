package vn.id.tozydev.phantom.paper.message.configuration

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.TypeSerializer
import vn.id.tozydev.phantom.paper.message.ActionbarMessage
import vn.id.tozydev.phantom.paper.message.CompositeMessage
import vn.id.tozydev.phantom.paper.message.EmptyMessage
import vn.id.tozydev.phantom.paper.message.Message
import vn.id.tozydev.phantom.paper.message.SoundMessage
import vn.id.tozydev.phantom.paper.message.TextMessage
import vn.id.tozydev.phantom.paper.message.TitleMessage
import vn.id.tozydev.phantom.paper.message.configuration.MessageNodeParser.Fields
import java.lang.reflect.Type

object MessageSerializer : TypeSerializer<Message> {
    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): Message = MessageNodeParser.parse(node)

    override fun serialize(
        type: Type,
        obj: Message?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        serialize(obj, node)
    }

    private fun serialize(
        message: Message,
        node: ConfigurationNode,
    ) {
        when (message) {
            is TextMessage -> node.set(message.text)
            is ActionbarMessage -> node.node(Fields.ACTIONBAR).set(message.actionbar)
            is TitleMessage -> {
                node.node(Fields.TITLE).set(message.title)
                node.node(Fields.SUBTITLE).set(message.subtitle)
                node.node(Fields.FADE_IN).set(message.fadeIn)
                node.node(Fields.STAY).set(message.stay)
                node.node(Fields.FADE_OUT).set(message.fadeOut)
            }

            is SoundMessage -> {
                node.node(Fields.SOUND).set(message.type)
                node.node(Fields.SOURCE).set(message.source)
                node.node(Fields.VOLUME).set(message.volume)
                node.node(Fields.PITCH).set(message.pitch)
            }

            is CompositeMessage -> {
                for (msg in message.messages) {
                    serialize(msg, node.appendListNode())
                }
            }

            EmptyMessage -> node.raw(null)
        }
    }
}
