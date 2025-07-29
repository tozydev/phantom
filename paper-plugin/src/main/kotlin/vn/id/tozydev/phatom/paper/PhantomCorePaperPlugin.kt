package vn.id.tozydev.phatom.paper

import dev.jorel.commandapi.CommandAPIBukkitConfig
import vn.id.tozydev.phantom.paper.plugin.PhantomPaperPlugin

class PhantomCorePaperPlugin : PhantomPaperPlugin() {
    override val configureCommandApi: (CommandAPIBukkitConfig.() -> Unit)? = {
        shouldHookPaperReload(true)
    }

    override val isInvUIEnabled: Boolean = true
}
