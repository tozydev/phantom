package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object JavaConfigurer : ProjectConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: org.gradle.api.Project,
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
