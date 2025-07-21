package vn.id.tozydev.phantom.paper.configuration.serializers

import io.papermc.paper.registry.RegistryAccess.registryAccess
import io.papermc.paper.registry.RegistryKey
import net.kyori.adventure.key.Key
import org.bukkit.Keyed
import org.spongepowered.configurate.serialize.ScalarSerializer
import vn.id.tozydev.phantom.paper.key.toKey
import java.lang.reflect.Type
import java.util.function.Predicate
import kotlin.reflect.KClass

/** Predefined serializers for common Paper registries. */
object PaperRegistrySerializers {
    val BLOCK = PaperRegistrySerializer(RegistryKey.BLOCK)
    val ITEM = PaperRegistrySerializer(RegistryKey.ITEM)
    val ATTRIBUTE = PaperRegistrySerializer(RegistryKey.ATTRIBUTE)
    val SOUND_EVENT = PaperRegistrySerializer(RegistryKey.SOUND_EVENT)
    val BIOME = PaperRegistrySerializer(RegistryKey.BIOME)
    val ENCHANTMENT = PaperRegistrySerializer(RegistryKey.ENCHANTMENT)
    val ENTITY_TYPE = PaperRegistrySerializer(RegistryKey.ENTITY_TYPE)
    val PARTICLE_TYPE = PaperRegistrySerializer(RegistryKey.PARTICLE_TYPE)
    val POTION = PaperRegistrySerializer(RegistryKey.POTION)
}

/**
 * A serializer for Paper registries that can handle both [Key] and [String] representations.
 *
 * @param T The type of the keyed object.
 * @param registryKey The registry key for the type.
 * @see RegistryKey
 */
class PaperRegistrySerializer<T : Keyed>(
    type: KClass<T>,
    registryKey: RegistryKey<T>,
) : ScalarSerializer<T>(type.java) {
    private val registry = registryAccess().getRegistry(registryKey)

    override fun deserialize(
        type: Type,
        obj: Any,
    ): T =
        when (obj) {
            is Key -> registry.getOrThrow(obj)
            is net.kyori.adventure.key.Keyed -> registry.getOrThrow(obj.key())
            else -> registry.getOrThrow(obj.toString().lowercase().toKey())
        }

    override fun serialize(
        item: T,
        typeSupported: Predicate<Class<*>>,
    ): Any =
        if (typeSupported.test(Key::class.java)) {
            item.key()
        } else {
            item.key().asMinimalString()
        }
}

inline fun <reified T : Keyed> PaperRegistrySerializer(registryKey: RegistryKey<T>) = PaperRegistrySerializer(T::class, registryKey)
