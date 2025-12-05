import vn.id.tozydev.phantom.gradle.paper.features.plugin.excludePaperweightInternalRepositories
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml

plugins {
    `paper-plugin`
    shadow
}

repositories {
    xenondevsReleases()
}

dependencies {
    implementation(projects.phantomPaperCore)
    implementation(projects.phantomDatabaseJdbc) {
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
    implementation(projects.phantomDatabaseExposed) {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
        exclude(group = "org.slf4j", module = "slf4j-api")
    }
}

paperPlugin {
    metadata {
        name = "PhantomPaper"
        main = "vn.id.tozydev.phatom.paper.PhantomCorePaperPlugin"
        apiVersion = "1.21"
        author = "tozydev"
        website = "https://tozydev.id.vn/"
        foliaSupported = true
        dependencies {
            server("eco", PaperPluginYaml.Load.BEFORE, required = true)
        }
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
