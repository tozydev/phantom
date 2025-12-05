import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    `paper-library`
}

repositories {
    xenondevsReleases()
    codeMc()
    maven("https://repo.auxilor.io/repository/maven-public/") {
        mavenContent {
            includeGroup("com.willfp")
        }
    }
}

dependencies {
    compileOnly(libs.eco)

    compileOnlyApi(platform(libs.kotlin.bom)) {
        version {
            prefer(getKotlinPluginVersion())
        }
    }
    compileOnlyApi(libs.kotlin.stdlib)
    compileOnlyApi(libs.kotlin.reflect)
    api(libs.kotlinx.datetime.jvm) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
    }

    compileOnly(platform(libs.kotlinx.coroutines.bom))
    compileOnly(libs.kotlinx.coroutines.core)
    api(libs.mccoroutine.folia.api) {
        exclude(group = "org.jetbrains.kotlin")
    }
    api(libs.mccoroutine.folia.core) {
        exclude(group = "org.jetbrains.kotlin")
    }

    compileOnlyApi(libs.configurate.core)
    compileOnlyApi(libs.configurate.yaml)
    api(libs.configurate.extra.kotlin) {
        exclude(group = "org.spongepowered")
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "org.jetbrains.kotlinx")
        exclude(group = "com.google.errorprone")
    }

    api(libs.commandapi.bukkit.shade.mojmap)
    api(libs.commandapi.bukkit.kotlin)

    api(libs.invui)
    api(libs.invuiKotlin) {
        exclude(group = "org.jetbrains.kotlin")
    }

    pluginDependencies.itemNbtApi()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xcontext-receivers")
        optIn.addAll("kotlin.time.ExperimentalTime")
    }
}

paperLibrary {
    localPublishingRepository {
        url = uri(rootProject.layout.buildDirectory.dir("artifacts/maven"))
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(getKotlinPluginVersion())
        }
    }
}
