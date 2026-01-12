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

configurations.shadowRuntimeElements {
    attributes {
        attribute(GradlePluginApiVersion.GRADLE_PLUGIN_API_VERSION_ATTRIBUTE, objects.named("9.0.0"))
    }
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

    @Suppress("unused")
    plugins {
        val phantomPlugin by creating {
            id = "vn.id.tozydev.phantom"
            displayName = "Phantom Gradle Plugin"
            description = "A Gradle plugin that simplifies the Paper plugin development for Minecraft servers."
            implementationClass = "vn.id.tozydev.phantom.gradle.PhantomSettingsPlugin"
        }
        val phantomPaperLibraryPlugin by creating {
            id = "vn.id.tozydev.phantom.paper-library"
            displayName = "Phantom Paper Library Plugin"
            description = "A Gradle plugin that simplifies the Paper library development for Minecraft servers."
            implementationClass = "vn.id.tozydev.phantom.gradle.paper.PaperLibraryProjectPlugin"
        }
        val phantomPaperPlugin by creating {
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
        enableAutoRelocation = true
        archiveClassifier = ""
        relocationPrefix = "phantom.libs"
        configurations = listOf(shade)
    }
}

val mavenSnapshotsUrl = findProperty("tozydev.maven.snapshots.url").toString()
val mavenReleasesUrl = findProperty("tozydev.maven.releases.url").toString()
val mavenPublicUrl = findProperty("tozydev.maven.public.url").toString()

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
        "\"${mavenSnapshotsUrl}\"",
    )
    buildConfigField(
        "String",
        "VELA_RELEASES_URL",
        "\"${mavenReleasesUrl}\"",
    )
    buildConfigField(
        "String",
        "TOZYDEV_MAVEN_RELEASES_URL",
        "\"${mavenReleasesUrl}\"",
    )
    buildConfigField(
        "String",
        "TOZYDEV_MAVEN_SNAPSHOTS_URL",
        "\"${mavenSnapshotsUrl}\"",
    )
    buildConfigField(
        "String",
        "TOZYDEV_MAVEN_PUBLIC_URL",
        "\"${mavenPublicUrl}\"",
    )
    buildConfigField(
        "TOZYDEV_MAVEN_REPO_NAME",
        "tozydevRepository",
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
                name = "tozydevSnapshots"
                url = uri(mavenSnapshotsUrl)
            } else {
                name = "tozydevReleases"
                url = uri(mavenReleasesUrl)
            }
            credentials {
                username = providers.gradleProperty("tozydev-maven.username").orNull
                    ?: providers.environmentVariable("TOZYDEV_MAVEN_USERNAME").orNull
                password = providers.gradleProperty("tozydev-maven.password").orNull
                    ?: providers.environmentVariable("TOZYDEV_MAVEN_PASSWORD").orNull
            }
        }
        maven {
            name = "Local"
            url = uri(file("../build/artifacts/maven"))
        }
    }
}

@Suppress("UnusedReceiverParameter")
fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>): Provider<String> =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
