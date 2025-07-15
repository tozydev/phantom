@file:Suppress("unused")

import org.gradle.api.initialization.Settings

fun Settings.subproject(
    module: String,
    dir: String = module,
    prefix: String = "${rootProject.name}-",
) {
    val name = "$prefix$module"
    include(name)
    project(":$name").projectDir = settingsDir.resolve(dir)
}
