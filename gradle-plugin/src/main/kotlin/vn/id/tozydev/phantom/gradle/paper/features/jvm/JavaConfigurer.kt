package vn.id.tozydev.phantom.gradle.paper.features.jvm

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import vn.id.tozydev.phantom.gradle.FeatureConfigurer
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object JavaConfigurer : FeatureConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            apply<JavaPlugin>()
            extensions.configure<JavaPluginExtension> {
                toolchain {
                    languageVersion.convention(extension.javaVersion.map { JavaLanguageVersion.of(it) })
                }
            }

            afterEvaluate {
                tasks.named<JavaCompile>("compileJava") {
                    options.release.convention(extension.javaVersion)
                    options.encoding = Charsets.UTF_8.name()
                }
            }
        }
}
