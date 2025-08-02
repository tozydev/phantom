package vn.id.tozydev.phantom.paper.chat

import com.github.shynixn.mccoroutine.folia.asyncDispatcher
import io.papermc.paper.event.player.AsyncChatEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin
import vn.id.tozydev.phantom.paper.event.registerAll
import vn.id.tozydev.phantom.paper.event.unregisterAll
import vn.id.tozydev.phantom.paper.message.Message
import vn.id.tozydev.phantom.paper.message.sendRichMessage
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Prompts the player for chat input and returns the input as a [Component].
 *
 * @param prompt The message to send to the player before waiting for input.
 * @param timeout The maximum duration to wait for input. Defaults to 30 seconds.
 * @return The player's input as a [Component], or null if the timeout is reached.
 */
suspend fun Player.promptChatInput(
    prompt: Message? = null,
    timeout: Duration = 30.seconds,
): Component? {
    check(ChatInputService.plugin != null) { "ChatInputService is not initialized." }

    return withContext(ChatInputService.plugin!!.asyncDispatcher) {
        val channel = Channel<Component>(Channel.RENDEZVOUS)
        ChatInputService.registerChannel(this@promptChatInput, channel)
        prompt?.let { sendRichMessage(it) }

        try {
            withTimeoutOrNull(timeout) { channel.receive() }
        } finally {
            ChatInputService.unregisterChannel(this@promptChatInput)
        }
    }
}

/**
 * Prompts the player for chat input with validation and cancellation options.
 *
 * @param prompt The message to send to the player before waiting for input.
 * @param timeout The maximum duration to wait for input. Defaults to 30 seconds.
 * @param maxRetries The maximum number of retries for input validation. Defaults to 3.
 * @param cancel A lambda function that determines if the input should be canceled. Defaults are always false.
 * @param validate A lambda function that validates the input. Defaults are always true.
 * @return The player's input as a [Component], or null if the timeout is reached or all retries are exhausted.
 */
suspend fun Player.promptChatInput(
    prompt: Message? = null,
    timeout: Duration = 30.seconds,
    maxRetries: Int = 3,
    cancel: Player.(Component) -> Boolean = { false },
    validate: Player.(Component) -> Boolean = { true },
): Component? {
    check(ChatInputService.plugin != null) { "ChatInputService is not initialized." }

    return withContext(ChatInputService.plugin!!.asyncDispatcher) {
        val channel = Channel<Component>(maxRetries)
        ChatInputService.registerChannel(this@promptChatInput, channel)
        prompt?.let { sendRichMessage(it) }

        try {
            withTimeoutOrNull(timeout) {
                var counter = maxRetries
                for (input in channel) {
                    if (counter-- <= 0) {
                        break
                    }
                    if (cancel(input)) {
                        break
                    }
                    if (validate(input)) {
                        return@withTimeoutOrNull input
                    }
                }
                null
            }
        } finally {
            ChatInputService.unregisterChannel(this@promptChatInput)
        }
    }
}

internal object ChatInputService {
    var plugin: Plugin? = null

    private val inputChannels = ConcurrentHashMap<UUID, SendChannel<Component>>()

    fun initialize(plugin: Plugin) {
        if (this.plugin != null) {
            return
        }
        this.plugin = plugin
        ChatListener.registerAll(plugin)
    }

    fun uninitialize() {
        if (plugin == null) {
            return
        }
        ChatListener.unregisterAll()
        inputChannels.values.forEach { it.close() }
        inputChannels.clear()
        plugin = null
    }

    fun registerChannel(
        player: Player,
        channel: SendChannel<Component>,
    ) {
        inputChannels.remove(player.uniqueId)?.close()
        inputChannels[player.uniqueId] = channel
    }

    fun unregisterChannel(player: Player) {
        inputChannels.remove(player.uniqueId)?.close()
    }

    object ChatListener : Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        fun onChat(event: AsyncChatEvent) {
            val player = event.player
            val channel = inputChannels[player.uniqueId] ?: return

            event.isCancelled = true
            channel.trySend(event.message())
        }

        @EventHandler
        fun onQuit(event: PlayerQuitEvent) {
            unregisterChannel(event.player)
        }
    }
}
