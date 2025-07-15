plugins {
    `paper-library`
}

dependencies {
    api(projects.phantomDatabaseJdbc)
    api(platform(libs.exposed.bom))
    api(libs.bundles.exposed)
}
