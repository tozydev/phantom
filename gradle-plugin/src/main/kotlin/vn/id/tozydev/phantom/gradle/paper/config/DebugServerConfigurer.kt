@file:Suppress("unused")

package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.jvm.toolchain.JvmVendorSpec
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectExtension
import xyz.jpenilla.runpaper.RunPaperPlugin
import xyz.jpenilla.runpaper.task.RunServer

object DebugServerConfigurer : ProjectConfigurer<PaperPluginProjectExtension> {
    override fun invoke(
        project: Project,
        extension: PaperPluginProjectExtension,
    ): Unit =
        with(project) {
            apply<RunPaperPlugin>()
            afterEvaluate {
                val javaToolchains = extensions.getByType<JavaToolchainService>()
                tasks {
                    val runServerOptions = extension.runServer
                    named<RunServer>("runServer") {
                        version.convention(extension.minecraftVersion)
                    }

                    withType<RunServer> {
                        runDirectory.convention(runServerOptions.directory)
                        systemProperties["com.mojang.eula.agree"] = runServerOptions.acceptEula.get()

                        downloadPlugins {
                            from(runServerOptions.downloadPluginsSpec)
                        }

                        if (runServerOptions.hotswap.get()) {
                            javaLauncher.convention(
                                javaToolchains.launcherFor {
                                    languageVersion.set(extension.javaVersion.map { JavaLanguageVersion.of(it) })
                                    @Suppress("UnstableApiUsage")
                                    vendor.set(JvmVendorSpec.JETBRAINS)
                                },
                            )
                            jvmArgs("-XX:+AllowEnhancedClassRedefinition")
                        }
                    }
                }
            }
        }
}
