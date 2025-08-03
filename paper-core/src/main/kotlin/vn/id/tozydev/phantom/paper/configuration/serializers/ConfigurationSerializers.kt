package vn.id.tozydev.phantom.paper.configuration.serializers

import org.spongepowered.configurate.kotlin.objectMapperFactory
import org.spongepowered.configurate.serialize.TypeSerializer
import org.spongepowered.configurate.serialize.TypeSerializerCollection
import vn.id.tozydev.phantom.paper.gui.GuiItem
import vn.id.tozydev.phantom.paper.inventory.CustomModelDataComponentBuilder
import vn.id.tozydev.phantom.paper.inventory.DamageableBuilder
import vn.id.tozydev.phantom.paper.inventory.LeatherArmorBuilder
import vn.id.tozydev.phantom.paper.inventory.SkullBuilder
import vn.id.tozydev.phantom.paper.message.configuration.MessageSerializer

/**
 * Collection of serializers used in Phantom's configuration system.
 */
object ConfigurationSerializers {
    val BukkitColor = BukkitColorSerializer
    val Defaults: TypeSerializerCollection =
        TypeSerializerCollection(TypeSerializerCollection.defaults()) {
            registerAnnotatedObjects(objectMapperFactory())
            registerExact(KotlinDurationSerializer)
            register(KeySerializer)
            register(MessageSerializer)
        }
    val ItemStackBuilders: TypeSerializerCollection =
        TypeSerializerCollection {
            registerExact(CustomModelDataComponentBuilder.Serializer)
            registerExact(DamageableBuilder.Serializer)
            registerExact(SkullBuilder.Serializer)
            registerExact(LeatherArmorBuilder.Serializer)
            registerExact(BukkitColorSerializer)
        }
    val GuiItems: TypeSerializerCollection =
        TypeSerializerCollection {
            registerExact(GuiItem.Serializer)
            registerAll(ItemStackBuilders)
        }

    fun withDefaults(builder: TypeSerializerCollection.Builder.() -> Unit): TypeSerializerCollection =
        Defaults.childBuilder().apply(builder).build()
}

inline fun <reified T> TypeSerializerCollection.Builder.register(serializer: TypeSerializer<T>) {
    register(T::class.java, serializer)
}

inline fun <reified T> TypeSerializerCollection.Builder.registerExact(serializer: TypeSerializer<T>) {
    registerExact(T::class.java, serializer)
}

fun TypeSerializerCollection(builder: TypeSerializerCollection.Builder.() -> Unit): TypeSerializerCollection =
    TypeSerializerCollection.builder().apply(builder).build()

fun TypeSerializerCollection(
    parent: TypeSerializerCollection,
    builder: TypeSerializerCollection.Builder.() -> Unit,
): TypeSerializerCollection = parent.childBuilder().apply(builder).build()
