import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

plugins {
    `paper-library`
}

dependencies {
    compileOnly(libs.kotlin.stdlib)

    api(libs.hikari.cp)
}

paperLibrary {
    localPublishingRepository {
        url = uri(rootProject.layout.buildDirectory.dir("artifacts/maven"))
    }
}

testing {
    @Suppress("UnstableApiUsage")
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(getKotlinPluginVersion())
        }
    }
}
