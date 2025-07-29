import vn.id.tozydev.phantom.gradle.paper.features.plugin.excludePaperweightInternalRepositories

plugins {
    `paper-plugin`
    shadow
}

repositories {
    xenondevsReleases()
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

    afterEvaluate {
        generateDynamicLibrariesLoaderClass {
            excludePaperweightInternalRepositories()
        }
    }
}
