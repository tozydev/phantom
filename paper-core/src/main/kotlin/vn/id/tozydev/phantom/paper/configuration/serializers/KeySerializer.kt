package vn.id.tozydev.phantom.paper.configuration.serializers

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Keyed
import org.spongepowered.configurate.serialize.ScalarSerializer
import java.lang.reflect.Type
import java.util.function.Predicate

/** Serializer for [Key] objects, which can handle both [Key] and [Keyed] types. */
object KeySerializer : ScalarSerializer<Key>(Key::class.java) {
    override fun deserialize(
        type: Type,
        obj: Any,
    ): Key =
        when (obj) {
            is Key -> obj
            is Keyed -> obj.key()
            else -> Key.key(obj.toString())
        }

    override fun serialize(
        item: Key,
        typeSupported: Predicate<Class<*>>,
    ): Any = item.asMinimalString()
}
