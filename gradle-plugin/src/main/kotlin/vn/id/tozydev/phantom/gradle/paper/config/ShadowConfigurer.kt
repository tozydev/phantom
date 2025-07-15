package vn.id.tozydev.phantom.gradle.paper.config

import com.github.jengelman.gradle.plugins.shadow.ShadowJavaPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import vn.id.tozydev.phantom.gradle.paper.PaperLibraryProjectExtension

object ShadowConfigurer : ProjectConfigurer<PaperLibraryProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperLibraryProjectExtension,
    ): Unit =
        with(project) {
            plugins.withType<ShadowJavaPlugin> {
                val shade = configurations.create("shade")
                configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME) {
                    extendsFrom(shade)
                }
                tasks.named<ShadowJar>(ShadowJavaPlugin.SHADOW_JAR_TASK_NAME) {
                    archiveClassifier.set("")
                    configurations.set(listOf(shade))
                }
                tasks.named(BasePlugin.ASSEMBLE_TASK_NAME) {
                    dependsOn(ShadowJavaPlugin.SHADOW_JAR_TASK_NAME)
                }
            }
        }
}
