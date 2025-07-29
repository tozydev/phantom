package vn.id.tozydev.phantom.gradle.paper

import org.gradle.api.Plugin
import org.gradle.api.Project
import vn.id.tozydev.phantom.gradle.FeatureConfigurer

abstract class BasePaperProjectPlugin<E : PaperProjectExtension> : Plugin<Project> {
    protected abstract fun createExtension(project: Project): E

    protected abstract val configurers: List<FeatureConfigurer<in E>>

    final override fun apply(target: Project) {
        val extension = createExtension(target)
        for (configurer in configurers) {
            configurer(target, extension)
        }
    }
}
