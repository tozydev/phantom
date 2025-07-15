plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/gradle-plugin-libs.versions.toml"))
        }
    }
}

rootProject.name = "phantom-gradle-plugin"
