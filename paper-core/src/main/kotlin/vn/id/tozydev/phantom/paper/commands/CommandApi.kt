package vn.id.tozydev.phantom.paper.commands

import dev.jorel.commandapi.CommandAPICommand
import org.bukkit.plugin.java.JavaPlugin

/** Registers a command using CommandAPI with plugin as namespace. */
fun JavaPlugin.pluginCommand(
    name: String,
    block: CommandAPICommand.() -> Unit,
) = CommandAPICommand(name).apply(block).also {
    it.register(this)
}
