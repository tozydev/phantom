package vn.id.tozydev.phantom.paper.event

import com.github.shynixn.mccoroutine.folia.registerSuspendingEvents
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import vn.id.tozydev.phantom.paper.Server
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass

/**
 * Registers the listener to receive events from the Bukkit plugin manager.
 *
 * @param plugin the plugin to register the listener with (defaults to the calling plugin)
 * @see org.bukkit.plugin.PluginManager.registerEvents
 */
fun Listener.registerAll(plugin: Plugin) {
    Server.pluginManager.registerEvents(this, plugin)
}

/**
 * Unregisters this listener from all event handler lists.
 *
 * @see HandlerList.unregisterAll
 */
fun Listener.unregisterAll() = HandlerList.unregisterAll(this)

/**
 * Registers this [Listener] to receive suspending events from the specified [plugin].
 *
 * @param plugin the plugin to register the listener with (defaults to the calling plugin)
 * @param eventDispatchers A mapping for each used event type in the given listener because Folia uses different
 *                         schedulers for different event types.
 * @see to
 */
@Suppress("UNCHECKED_CAST")
fun Listener.registerAllSuspending(
    plugin: Plugin,
    eventDispatchers: Map<KClass<out Event>, (Nothing) -> CoroutineContext>,
) = Server.pluginManager.registerSuspendingEvents(
    listener = this,
    plugin = plugin,
    eventDispatcher = eventDispatchers.mapKeys { it.key.java } as Map<Class<out Event>, (Event) -> CoroutineContext>,
)

/** Creates a pair of [KClass] and dispatcher function for use in [registerAllSuspending]. */
infix fun <T : Event> KClass<T>.toDispatcher(dispatcher: (T) -> CoroutineContext) = Pair(this, dispatcher)
