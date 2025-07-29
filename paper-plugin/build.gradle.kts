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
        name = "PhantomPaper"
        main = "vn.id.tozydev.phatom.paper.PhantomCorePaperPlugin"
        apiVersion = "1.21"
        author = "tozydev"
        website = "https://tozydev.id.vn/"
        foliaSupported = true
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
