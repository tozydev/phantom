import vn.id.tozydev.phantom.gradle.paper.features.plugin.excludePaperweightInternalRepositories
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml
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
        load = BukkitPluginYaml.PluginLoadOrder.STARTUP
        dependencies {
            server("eco", PaperPluginYaml.Load.BEFORE, false, joinClasspath = false)
        }
    }
    runServer {
        acceptEula = true
    }
}

tasks {
    jar {
        archiveClassifier = "plain"
    }

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
