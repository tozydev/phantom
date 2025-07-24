plugins {
    `paper-library`
}

dependencies {
    api(libs.hikari.cp)
}

paperLibrary {
    localPublishingRepository {
        url = uri(rootProject.layout.buildDirectory.dir("artifacts/maven"))
    }
}
