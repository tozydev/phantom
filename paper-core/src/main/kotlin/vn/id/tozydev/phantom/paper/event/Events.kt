package vn.id.tozydev.phantom.paper.event

import com.github.shynixn.mccoroutine.folia.EventExecutionType
import com.github.shynixn.mccoroutine.folia.callSuspendingEvent
import org.bukkit.event.Cancellable
import org.bukkit.event.Event
import org.bukkit.plugin.Plugin
import vn.id.tozydev.phantom.paper.Server

/**
 * Calls an event with the given details.
 * Allows awaiting the completion of suspending event listeners.
 *
 * @param plugin Plugin plugin.
 * @param executionType Allows specifying how suspend receivers are executed.
 * @return Collection of await-able jobs. This job list may be empty if no suspending listener
 * was called. Each job instance represents an await-able job for each method being called in each suspending listener.
 * For awaiting use callSuspendingEvent(...).joinAll().
 *
 * @see org.bukkit.plugin.PluginManager.callSuspendingEvent
 */
fun Event.callSuspending(
    plugin: Plugin,
    executionType: EventExecutionType = EventExecutionType.Concurrent,
) = Server.pluginManager.callSuspendingEvent(this, plugin, executionType)

/**
 * Sets the cancellation state of this event.
 */
fun Cancellable.cancel(cancel: Boolean = true) {
    isCancelled = cancel
}
