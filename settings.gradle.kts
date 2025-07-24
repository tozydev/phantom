pluginManagement {
    includeBuild("gradle-plugin")
}

plugins {
    id("vn.id.tozydev.phantom")
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "phantom"

subproject("paper-core")
subproject("database-jdbc")
subproject("database-exposed")

val enableExamples =
    providers
        .gradleProperty("enable.examples")
        .getOrElse(providers.environmentVariable("ENABLE_EXAMPLES").getOrElse("false"))
        .toBoolean()

if (enableExamples) {
    includeBuild("examples")
}
