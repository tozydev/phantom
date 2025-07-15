package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import vn.id.tozydev.phantom.gradle.paper.PaperProjectExtension

object KotlinConfigurer : ProjectConfigurer<PaperProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperProjectExtension,
    ): Unit =
        with(project) {
            apply<KotlinPluginWrapper>()
            // Configure Kotlin compiler options for configurate-extra-kotlin
            extensions.configure<KotlinJvmExtension> {
                compilerOptions {
                    freeCompilerArgs.add("-Xemit-jvm-type-annotations")
                }
            }
        }
}
