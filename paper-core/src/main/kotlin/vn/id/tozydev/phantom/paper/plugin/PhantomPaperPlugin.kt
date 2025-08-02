package vn.id.tozydev.phantom.paper.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger
import vn.id.tozydev.phantom.paper.chat.ChatInputService
import xyz.xenondevs.invui.InvUI

/**
 * Base class for Phantom plugins that use the Paper API.
 */
abstract class PhantomPaperPlugin : SuspendingJavaPlugin() {
    protected open val configureCommandApi: (CommandAPIBukkitConfig.() -> Unit)? = null

    protected val isCommandApiEnabled: Boolean
        get() = configureCommandApi != null

    protected open val isInvUIEnabled: Boolean
        get() = false

    override fun onLoad() {
        if (isCommandApiEnabled) {
            CommandAPI.setLogger(CommandAPILogger.fromSlf4jLogger(slF4JLogger))
            CommandAPI.onLoad(
                CommandAPIBukkitConfig(this).apply {
                    configureCommandApi?.invoke(this)
                },
            )
        }
        super.onLoad()
    }

    override fun onEnable() {
        if (isCommandApiEnabled) {
            CommandAPI.onEnable()
        }
        if (isInvUIEnabled) {
            InvUI.getInstance().setPlugin(this)
        }
        ChatInputService.initialize(this)
        super.onEnable()
    }

    override fun onDisable() {
        ChatInputService.uninitialize()
        if (isCommandApiEnabled) {
            CommandAPI.onDisable()
        }
        super.onDisable()
    }
}
