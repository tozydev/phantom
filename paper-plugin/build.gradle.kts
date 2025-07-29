plugins {
    `paper-plugin`
    shadow
}

dependencies {
    implementation(projects.phantomPaperCore)
    implementation(projects.phantomDatabaseJdbc)
    implementation(projects.phantomDatabaseExposed)
}

paperPlugin {
    metadata {
        main = "vn.id.tozydev.phatom.paper.PhantomCorePaperPlugin"
        author = "tozydev"
    }
    runServer {
        acceptEula = true
    }
}

tasks {
    shadowJar {
        archiveClassifier = ""
    }

    assemble {
        dependsOn(shadowJar)
    }
}
