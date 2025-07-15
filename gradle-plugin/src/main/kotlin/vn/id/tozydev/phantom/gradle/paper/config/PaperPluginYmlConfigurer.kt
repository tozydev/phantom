package vn.id.tozydev.phantom.gradle.paper.config

import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import vn.id.tozydev.phantom.gradle.paper.PaperPluginProjectExtension
import xyz.jpenilla.resourcefactory.ResourceFactoryExtension
import xyz.jpenilla.resourcefactory.ResourceFactoryPlugin

object PaperPluginYmlConfigurer : ProjectConfigurer<PaperPluginProjectExtension> {
    override fun invoke(
        project: org.gradle.api.Project,
        extension: PaperPluginProjectExtension,
    ): Unit =
        with(project) {
            apply<ResourceFactoryPlugin>()
            extensions.getByType<SourceSetContainer>().named(SourceSet.MAIN_SOURCE_SET_NAME) {
                extensions.configure<ResourceFactoryExtension> {
                    factory(extension.metadata.resourceFactory())
                }
            }
        }
}
