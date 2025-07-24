@file:Suppress("UnstableApiUsage")

import com.github.jengelman.gradle.plugins.shadow.ShadowBasePlugin.Companion.shadow
import org.jetbrains.kotlin.gradle.utils.extendsFrom


plugins {
    `kotlin-dsl`
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.buildconfig)
    alias(libs.plugins.pluginPublish)
    alias(libs.plugins.shadow)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

val shade: Configuration by configurations.creating

configurations.compileOnly {
    extendsFrom(shade)
}

configurations.implementation.extendsFrom(configurations.shadow)

dependencies {
    shadow(plugin(libs.plugins.foojay.resolver.convention))
    shadow(plugin(libs.plugins.kotlin.jvm))
    shadow(plugin(libs.plugins.paperweight.userdev))
    shadow(plugin(libs.plugins.run.paper))
    shadow(plugin(libs.plugins.resource.factory))
    shadow(plugin(libs.plugins.maven.publish))
    shadow(plugin(libs.plugins.shadow))

    shade(libs.javapoet)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)
        }

        @Suppress("unused")
        val functionalTest by registering(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)

            dependencies {
                implementation(project())
            }

            targets {
                all {
                    testTask.configure { shouldRunAfter(test) }
                }
            }
        }
    }
}

gradlePlugin {
    testSourceSets.add(sourceSets["functionalTest"])

    plugins {
        create("phantomPlugin") {
            id = "vn.id.tozydev.phantom"
            displayName = "Phantom Gradle Plugin"
            description = "A Gradle plugin that simplifies the Paper plugin development for Minecraft servers."
            implementationClass = "vn.id.tozydev.phantom.gradle.PhantomSettingsPlugin"
        }
        create("phantomPaperLibraryPlugin") {
            id = "vn.id.tozydev.phantom.paper-library"
            displayName = "Phantom Paper Library Plugin"
            description = "A Gradle plugin that simplifies the Paper library development for Minecraft servers."
            implementationClass = "vn.id.tozydev.phantom.gradle.paper.PaperLibraryProjectPlugin"
        }
        create("phantomPaperPlugin") {
            id = "vn.id.tozydev.phantom.paper-plugin"
            displayName = "Phantom Paper Plugin"
            description = "A Gradle plugin that simplifies the Paper plugin development for Minecraft servers."
            implementationClass = "vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectPlugin"
        }
    }
}

tasks {
    check {
        dependsOn(testing.suites.named("functionalTest"))
    }

    validatePlugins {
        enableStricterValidation = true
    }

    shadowJar {
        enableRelocation = true
        archiveClassifier = ""
        relocationPrefix = "phantom.libs"
        configurations = listOf(shade)
    }
}

val velaSnapshotsUrl = findProperty("vela.snapshots.url").toString()
val velaReleasesUrl = findProperty("vela.releases.url").toString()

buildConfig {
    packageName("vn.id.tozydev.phantom.gradle")
    className("BuildConfig")
    useKotlinOutput()
    buildConfigField(
        "String",
        "PHANTOM_GROUP",
        "\"$group\"",
    )
    buildConfigField(
        "String",
        "PHANTOM_VERSION",
        "\"${version}\"",
    )
    buildConfigField(
        "Int",
        "JAVA_VERSION",
        providers
            .gradleProperty("java.version")
            .get()
            .toInt(),
    )
    buildConfigField(
        "String",
        "MINECRAFT_VERSION",
        "\"${libs.versions.minecraft.get()}\"",
    )
    buildConfigField(
        "String",
        "VELA_SNAPSHOTS_URL",
        "\"${velaSnapshotsUrl}\"",
    )
    buildConfigField(
        "String",
        "VELA_RELEASES_URL",
        "\"${velaReleasesUrl}\"",
    )

    forClass("DependenciesRes") {
        val depLibs = versionCatalogs.named("depLibs")
        depLibs.libraryAliases.forEach { alias ->
            depLibs.findLibrary(alias).map { it.orNull }.ifPresent {
                val name = alias.replace(".", "_").uppercase()
                buildConfigField("${name}_MODULE", it.module.toString())
                buildConfigField("${name}_VERSION", it.version.toString())
            }
        }
    }
}

publishing {
    repositories {
        maven {
            val isSnapshot = version.toString().endsWith("-SNAPSHOT")
            if (isSnapshot) {
                name = "VelaSnapshots"
                url = uri(velaSnapshotsUrl)
            } else {
                name = "VelaReleases"
                url = uri(velaReleasesUrl)
            }
            credentials {
                username = providers.gradleProperty("vela.username").orNull
                    ?: providers.environmentVariable("VELA_USERNAME").orNull
                password = providers.gradleProperty("vela.password").orNull
                    ?: providers.environmentVariable("VELA_PASSWORD").orNull
            }
        }
        maven {
            name = "Local"
            url = uri(file("../build/artifacts/maven"))
        }
    }
}

@Suppress("UnusedReceiverParameter")
fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
