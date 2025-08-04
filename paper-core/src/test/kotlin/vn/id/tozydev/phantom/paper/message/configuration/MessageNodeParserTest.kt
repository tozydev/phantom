package vn.id.tozydev.phantom.paper.message.configuration

import org.spongepowered.configurate.kotlin.node
import vn.id.tozydev.phantom.paper.message.ActionbarMessage
import vn.id.tozydev.phantom.paper.message.CompositeMessage
import vn.id.tozydev.phantom.paper.message.EmptyMessage
import vn.id.tozydev.phantom.paper.message.SoundMessage
import vn.id.tozydev.phantom.paper.message.TextMessage
import vn.id.tozydev.phantom.paper.message.TitleMessage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.seconds

class MessageNodeParserTest {
    @Test
    fun `parse should parse text node`() {
        val node =
            node {
                set("Hello, world!")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TextMessage>(message)
        assertEquals("Hello, world!", message.text)
    }

    @Test
    fun `parse should return empty message for empty text node`() {
        val node =
            node {
                set("")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<EmptyMessage>(message)
    }

    @Test
    fun `parse should return empty message for null node`() {
        val node =
            node {
                set(null)
            }

        val message = MessageNodeParser.parse(node)

        assertIs<EmptyMessage>(message)
    }

    @Test
    fun `parse should parse text message from map node`() {
        val node =
            node {
                node("text").set("This is a text message")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TextMessage>(message)
        assertEquals("This is a text message", message.text)
    }

    @Test
    fun `parse should parse text message with empty string`() {
        val node =
            node {
                node("text").set("")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<EmptyMessage>(message)
    }

    @Test
    fun `parse should parse text message with null value`() {
        val node =
            node {
                node("text").set(null)
            }

        val message = MessageNodeParser.parse(node)

        assertIs<EmptyMessage>(message)
    }

    @Test
    fun `parse should parse actionbar message from map node`() {
        val node =
            node {
                node("actionbar").set("This is an action bar message")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<ActionbarMessage>(message)
        assertEquals("This is an action bar message", message.actionbar)
    }

    @Test
    fun `parse should parse actionbar message with empty string`() {
        val node =
            node {
                node("actionbar").set("")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<ActionbarMessage>(message)
        assertEquals("", message.actionbar)
    }

    @Test
    fun `parse should return empty actionbar message for null node`() {
        val node =
            node {
                node("actionbar").set(null)
            }

        val message = MessageNodeParser.parse(node)

        assertIs<EmptyMessage>(message)
    }

    @Test
    fun `parse should parse title message with all properties`() {
        val node =
            node {
                node("title").set("Welcome!")
                node("subtitle").set("Enjoy your stay")
                node("fade-in").set("1s")
                node("stay").set("3s")
                node("fade-out").set("1s")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals("Welcome!", message.title)
        assertEquals("Enjoy your stay", message.subtitle)
        assertEquals(1.seconds, message.fadeIn)
        assertEquals(3.seconds, message.stay)
        assertEquals(1.seconds, message.fadeOut)
    }

    @Test
    fun `parse should parse title message with only title`() {
        val node =
            node {
                node("title").set("Just a title")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals("Just a title", message.title)
        assertNull(message.subtitle)
        assertNull(message.fadeIn)
        assertNull(message.stay)
        assertNull(message.fadeOut)
    }

    @Test
    fun `parse should parse title message with fadein instead of fade-in`() {
        val node =
            node {
                node("fadein").set("2s")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals(2.seconds, message.fadeIn)
    }

    @Test
    fun `parse should parse title message with fadeout instead of fade-out`() {
        val node =
            node {
                node("fadeout").set("2s")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals(2.seconds, message.fadeOut)
    }

    @Test
    fun `parse should prefer fade-in over fadein when both present`() {
        val node =
            node {
                node("fade-in").set("1s")
                node("fadein").set("2s")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals(1.seconds, message.fadeIn)
    }

    @Test
    fun `parse should prefer fade-out over fadeout when both present`() {
        val node =
            node {
                node("fade-out").set("1s")
                node("fadeout").set("2s")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<TitleMessage>(message)
        assertEquals(1.seconds, message.fadeOut)
    }

    @Test
    fun `parse should parse sound message with all properties`() {
        val node =
            node {
                node("sound").set("minecraft:entity.player.levelup")
                node("source").set("master")
                node("volume").set(0.8f)
                node("pitch").set(1.2f)
                node("seed").set(12345L)
            }

        val message = MessageNodeParser.parse(node)

        assertIs<SoundMessage>(message)
        assertEquals("minecraft:entity.player.levelup", message.type)
        assertEquals("master", message.source)
        assertEquals(0.8f, message.volume)
        assertEquals(1.2f, message.pitch)
        assertEquals(12345L, message.seed)
    }

    @Test
    fun `parse should parse sound message with minimal properties`() {
        val node =
            node {
                node("sound").set("minecraft:block.note_block.harp")
            }

        val message = MessageNodeParser.parse(node)

        assertIs<SoundMessage>(message)
        assertEquals("minecraft:block.note_block.harp", message.type)
        assertNull(message.source)
        assertEquals(1f, message.volume)
        assertEquals(1f, message.pitch)
        assertNull(message.seed)
    }

    @Test
    fun `parse should throw exception for sound message without sound type`() {
        val node =
            node {
                node("source").set("master")
                node("volume").set(0.8f)
            }

        assertFailsWith<IllegalArgumentException> {
            MessageNodeParser.parse(node)
        }
    }

    @Test
    fun `parse should parse composite message from list`() {
        val node =
            node {
                appendListNode().set("Hello, world!")
                appendListNode().apply {
                    node("text").set("This is a text message")
                }
                appendListNode().apply {
                    node("actionbar").set("Action bar message")
                }
                appendListNode().apply {
                    node("sound").set("minecraft:block.note_block.harp")
                }
                appendListNode().apply {
                    node("title").set("Welcome!")
                }
            }

        val message = MessageNodeParser.parse(node)
        assertIs<CompositeMessage>(message)
        assertEquals(5, message.messages.size)
        assertIs<TextMessage>(message.messages[0])
        assertEquals("Hello, world!", (message.messages[0] as TextMessage).text)
        assertIs<TextMessage>(message.messages[1])
        assertEquals("This is a text message", (message.messages[1] as TextMessage).text)
        assertIs<ActionbarMessage>(message.messages[2])
        assertEquals("Action bar message", (message.messages[2] as ActionbarMessage).actionbar)
        assertIs<SoundMessage>(message.messages[3])
        assertEquals("minecraft:block.note_block.harp", (message.messages[3] as SoundMessage).type)
        assertIs<TitleMessage>(message.messages[4])
        assertEquals("Welcome!", (message.messages[4] as TitleMessage).title)
    }

    @Test
    fun `parse should handle empty list as composite message`() {
        val node =
            node {
                setList(String::class.java, emptyList<String>())
            }
        val message = MessageNodeParser.parse(node)
        assertIs<CompositeMessage>(message)
        assertEquals(0, message.messages.size)
    }

    @Test
    fun `parse should handle composite message with single element`() {
        val node =
            node {
                appendListNode().set("Single message")
            }
        val message = MessageNodeParser.parse(node)
        assertIs<CompositeMessage>(message)
        assertEquals(1, message.messages.size)
        assertIs<TextMessage>(message.messages[0])
        assertEquals("Single message", (message.messages[0] as TextMessage).text)
    }

    @Test
    fun `parse should handle composite message with empty and null elements`() {
        val node =
            node {
                appendListNode().set("")
                appendListNode().set(null)
                appendListNode().set("Valid text")
            }
        val message = MessageNodeParser.parse(node)
        assertIs<CompositeMessage>(message)
        assertEquals(2, message.messages.size)
        assertIs<EmptyMessage>(message.messages[0])
        assertIs<TextMessage>(message.messages[1])
    }

    @Test
    fun `parse should handle deeply nested composite messages`() {
        val node =
            node {
                appendListNode().apply {
                    appendListNode().apply {
                        appendListNode().set("Level 3 text")
                    }
                    appendListNode().set("Level 2 text")
                }
            }
        val message = MessageNodeParser.parse(node)
        assertIs<CompositeMessage>(message)
        assertEquals(1, message.messages.size)
        val level1 = message.messages[0] as CompositeMessage
        assertEquals(2, level1.messages.size)
        val level2 = level1.messages[0] as CompositeMessage
        assertEquals(1, level2.messages.size)
        assertIs<TextMessage>(level2.messages[0])
    }
}
