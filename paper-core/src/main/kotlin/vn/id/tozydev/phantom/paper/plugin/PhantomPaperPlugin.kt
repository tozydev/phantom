package vn.id.tozydev.phantom.paper.plugin

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger

abstract class PhantomPaperPlugin : SuspendingJavaPlugin() {
    protected open val configureCommandApi: (CommandAPIBukkitConfig.() -> Unit)? = {
        shouldHookPaperReload(true)
        setNamespace(pluginMeta.name)
    }

    protected val isCommandApiEnabled: Boolean
        get() = configureCommandApi != null

    abstract override suspend fun onLoadAsync()

    abstract override suspend fun onEnableAsync()

    abstract override suspend fun onDisableAsync()

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
