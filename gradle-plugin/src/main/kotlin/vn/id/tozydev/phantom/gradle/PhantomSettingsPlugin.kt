package vn.id.tozydev.phantom.gradle

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.apply
import org.gradle.toolchains.foojay.FoojayToolchainsConventionPlugin

abstract class PhantomSettingsPlugin : Plugin<Settings> {
    override fun apply(target: Settings): Unit =
        with(target) {
            apply<FoojayToolchainsConventionPlugin>()
        }
}
