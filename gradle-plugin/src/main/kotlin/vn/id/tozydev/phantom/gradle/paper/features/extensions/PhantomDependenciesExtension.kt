package vn.id.tozydev.phantom.gradle.paper.features.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.kotlin.dsl.create
import vn.id.tozydev.phantom.gradle.BuildConfig
import vn.id.tozydev.phantom.gradle.paper.features.plugin.DynamicLibraryLoaderConfigurer
import xenondevsReleases

@Suppress("unused")
abstract class PhantomDependenciesExtension(
    private val project: Project,
) {
    private val dependencies = project.dependencies

    fun paperCore(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-paper-core:$version", action)
        dependencies.add(configurationName, dep)
        project.injectPhantomRepositoryIfNeeded()
        return dep
    }

    fun databaseJdbc(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-database-jdbc:$version", action)
        dependencies.add(configurationName, dep)
        project.injectPhantomRepositoryIfNeeded()
        return dep
    }

    fun databaseExposed(
        version: String = BuildConfig.PHANTOM_VERSION,
        configurationName: String = DynamicLibraryLoaderConfigurer.LIBRARY_CONFIGURATION_NAME,
        action: ExternalModuleDependency.() -> Unit = {},
    ): ExternalModuleDependency {
        val dep = dependencies.create("${BuildConfig.PHANTOM_GROUP}:phantom-database-exposed:$version", action)
        dependencies.add(configurationName, dep)
        project.injectPhantomRepositoryIfNeeded()
        return dep
    }

    private fun Project.injectPhantomRepositoryIfNeeded() {
        if (!shouldInjectPhantomRepository) {
            return
        }

        if (phantomRepositoryInjected) {
            return
        }

        logger.debug("Injecting Phantom repository...")

        val repoUrl =
            when (phantomRepositoryInjectType) {
                PHANTOM_REPOSITORY_TYPE_RELEASES -> BuildConfig.VELA_RELEASES_URL
                PHANTOM_REPOSITORY_TYPE_SNAPSHOTS -> BuildConfig.VELA_SNAPSHOTS_URL
                else -> throw IllegalArgumentException(
                    "Unknown Phantom repository type: $phantomRepositoryInjectType, expected one of: " +
                        "$PHANTOM_REPOSITORY_TYPE_RELEASES, $PHANTOM_REPOSITORY_TYPE_SNAPSHOTS",
                )
            }
        repositories.maven {
            name = BuildConfig.VELA_REPO_NAME
            url = uri(repoUrl)
        }
        // XenonDevs repository is required for InvUI dependencies
        repositories.xenondevsReleases()
    }

    private val Project.phantomRepositoryInjected
        get() = repositories.findByName(BuildConfig.VELA_REPO_NAME) != null

    private val Project.shouldInjectPhantomRepository
        get() = findProperty(PHANTOM_REPOSITORY_INJECT)?.toString()?.toBoolean() ?: PHANTOM_REPOSITORY_INJECT_DEFAULT

    private val Project.phantomRepositoryInjectType
        get() = findProperty(PHANTOM_REPOSITORY_TYPE)?.toString() ?: PHANTOM_REPOSITORY_TYPE_DEFAULT

    companion object {
        /** Property to control whether the Phantom repository should be injected, default is true. */
        const val PHANTOM_REPOSITORY_INJECT = "phantom.repository.inject"
        const val PHANTOM_REPOSITORY_INJECT_DEFAULT = true

        /** Property to specify the type of Phantom repository, default is "releases". */
        const val PHANTOM_REPOSITORY_TYPE = "phantom.repository.type"
        const val PHANTOM_REPOSITORY_TYPE_RELEASES = "releases"
        const val PHANTOM_REPOSITORY_TYPE_SNAPSHOTS = "snapshots"
        const val PHANTOM_REPOSITORY_TYPE_DEFAULT = PHANTOM_REPOSITORY_TYPE_RELEASES
    }
}
