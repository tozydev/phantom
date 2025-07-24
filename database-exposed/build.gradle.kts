plugins {
    `paper-library`
}

dependencies {
    api(projects.phantomDatabaseJdbc)
    api(platform(libs.exposed.bom))
    api(libs.bundles.exposed)
}

paperLibrary {
    localPublishingRepository {
        url = uri(rootProject.layout.buildDirectory.dir("artifacts/maven"))
    }
}
