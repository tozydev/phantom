@file:Suppress("unused")

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.kotlin.dsl.maven
import vn.id.tozydev.phantom.gradle.BuildConfig

fun RepositoryHandler.xenondevsReleases(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://repo.xenondevs.xyz/releases") {
        name = "XenonDevsReleases"
        action()
    }

fun RepositoryHandler.engineHub(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://maven.enginehub.org/repo/") {
        name = "EngineHub"
        action()
    }

fun RepositoryHandler.codeMc(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://repo.codemc.io/repository/maven-public/") {
        name = "CodeMCPublic"
        action()
    }

fun RepositoryHandler.jitpack(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://jitpack.io") {
        name = "JitPack"
        action()
    }

fun RepositoryHandler.extendedclip(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://repo.extendedclip.com/releases/") {
        name = "helpchatRepoReleases"
        action()
    }

fun RepositoryHandler.dmulloy2(action: MavenArtifactRepository.() -> Unit = {}) =
    maven("https://repo.dmulloy2.net/repository/public/") {
        name = "Dmulloy2Public"
        action()
    }

@Deprecated("Use tozydevReleases instead.", ReplaceWith("tozydevReleases(action)"))
fun RepositoryHandler.velaReleases(action: MavenArtifactRepository.() -> Unit = {}) =
    maven(BuildConfig.VELA_RELEASES_URL) {
        name = "VelaReleases"
        action()
    }

@Deprecated("Use tozydevSnapshots instead.", ReplaceWith("tozydevSnapshots(action)"))
fun RepositoryHandler.velaSnapshots(action: MavenArtifactRepository.() -> Unit = {}) =
    maven(BuildConfig.VELA_SNAPSHOTS_URL) {
        name = "VelaSnapshots"
        action()
    }

fun RepositoryHandler.tozydevMaven(action: MavenArtifactRepository.() -> Unit = {}) =
    maven(BuildConfig.TOZYDEV_MAVEN_PUBLIC_URL) {
        name = "tozydevMaven"
        action()
    }

fun RepositoryHandler.tozydevReleases(action: MavenArtifactRepository.() -> Unit = {}) =
    maven(BuildConfig.TOZYDEV_MAVEN_RELEASES_URL) {
        name = "tozydevReleases"
        action()
    }

fun RepositoryHandler.tozydevSnapshots(action: MavenArtifactRepository.() -> Unit = {}) =
    maven(BuildConfig.TOZYDEV_MAVEN_SNAPSHOTS_URL) {
        name = "tozydevSnapshots"
        action()
    }
