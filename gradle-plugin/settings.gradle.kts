plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/gradle-plugin-libs.versions.toml"))
        }
        create("depLibs") {
            from(files("../gradle/dep-libs.versions.toml"))
        }
    }
}

rootProject.name = "phantom-gradle-plugin"
