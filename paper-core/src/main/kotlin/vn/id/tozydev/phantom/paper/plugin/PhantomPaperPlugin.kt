package vn.id.tozydev.phantom.paper.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger

/**
 * Base class for Phantom plugins that use the Paper API.
 */
abstract class PhantomPaperPlugin : SuspendingJavaPlugin() {
    protected open val configureCommandApi: (CommandAPIBukkitConfig.() -> Unit)? = {
        shouldHookPaperReload(true)
        setNamespace(pluginMeta.name)
    }

    protected val isCommandApiEnabled: Boolean
        get() = configureCommandApi != null

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
        super.onEnable()
    }

    override fun onDisable() {
        if (isCommandApiEnabled) {
            CommandAPI.onDisable()
        }
        super.onDisable()
    }
}
