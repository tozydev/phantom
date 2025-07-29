package vn.id.tozydev.phantom.gradle.paper.features.plugin

import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.add
import vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectExtension

typealias LibraryRepositoriesFilter = (MavenArtifactRepository) -> Boolean

val EXCLUDED_HOSTS = listOf("maven.neoforged.net", "maven.fabricmc.net", "maven.parchmentmc.org")

val DEFAULT_FILTER: LibraryRepositoriesFilter = {
    it.url.host !in EXCLUDED_HOSTS
}

fun PaperPluginProjectExtension.libraryRepositoriesFilter(filter: LibraryRepositoriesFilter = DEFAULT_FILTER): PaperPluginProjectExtension =
    apply {
        extensions.add<LibraryRepositoriesFilter>("libraryRepositoriesFilter", filter)
    }
