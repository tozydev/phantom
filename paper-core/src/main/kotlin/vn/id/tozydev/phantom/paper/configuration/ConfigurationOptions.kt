package vn.id.tozydev.phantom.paper.configuration

import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.serialize.TypeSerializerCollection

operator fun ConfigurationOptions.plus(serializers: TypeSerializerCollection): ConfigurationOptions = this + { registerAll(serializers) }

operator fun ConfigurationOptions.plus(serializer: TypeSerializerCollection.Builder.() -> Unit): ConfigurationOptions =
    this.serializers(serializer)
