package vn.id.tozydev.phantom.paper.nbt

import de.tr7zw.nbtapi.iface.ReadWriteNBT
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type
import java.util.UUID

/** Represents a single NBT tag with its type, key, and value. It usually uses in configuration. */
data class NbtTag(
    val type: NbtType? = null,
    val key: String,
    val value: Any,
)

/** Represents the type of NBT tag. */
enum class NbtType {
    BYTE,
    SHORT,
    INT,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    BYTE_ARRAY,
    INT_ARRAY,
    LONG_ARRAY,
    UUID,
}

/** Serializer for [NbtTag] that allows it to be stored in a configuration node. */
object NbtTagSerializer : TypeSerializer<NbtTag> {
    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): NbtTag {
        val typeNode = node.node("type")
        val keyNode = node.node("key")
        val valueNode = node.node("value")

        val nbtType = typeNode.get<NbtType>()
        val key = keyNode.get<String>() ?: throw SerializationException("NbtTag 'key' cannot be null")
        val value = valueNode.raw() ?: throw SerializationException("NbtTag 'value' cannot be null")

        return NbtTag(nbtType, key, value)
    }

    override fun serialize(
        type: Type,
        obj: NbtTag?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node("type").set(obj.type)
        node.node("key").set(obj.key)
        node.node("value").set(obj.value)
    }
}

/** Extension function to set an NbtTag in a ReadWriteNBT object. */
fun ReadWriteNBT.set(tag: NbtTag) {
    when (tag.type) {
        NbtType.BYTE -> this.setByte(tag.key, tag.value as Byte)
        NbtType.SHORT -> this.setShort(tag.key, tag.value as Short)
        NbtType.INT -> this.setInteger(tag.key, tag.value as Int)
        NbtType.LONG -> this.setLong(tag.key, tag.value as Long)
        NbtType.FLOAT -> this.setFloat(tag.key, tag.value as Float)
        NbtType.DOUBLE -> this.setDouble(tag.key, tag.value as Double)
        NbtType.STRING -> this.setString(tag.key, tag.value as String)
        NbtType.BYTE_ARRAY -> this.setByteArray(tag.key, tag.value as ByteArray)
        NbtType.INT_ARRAY -> this.setIntArray(tag.key, tag.value as IntArray)
        NbtType.LONG_ARRAY -> this.setLongArray(tag.key, tag.value as LongArray)
        NbtType.UUID -> this.setUUID(tag.key, tag.value as UUID)
        null -> {
            when (tag.value) {
                is Byte -> this.setByte(tag.key, tag.value)
                is Short -> this.setShort(tag.key, tag.value)
                is Int -> this.setInteger(tag.key, tag.value)
                is Long -> this.setLong(tag.key, tag.value)
                is Float -> this.setFloat(tag.key, tag.value)
                is Double -> this.setDouble(tag.key, tag.value)
                is String -> this.setString(tag.key, tag.value)
                is ByteArray -> this.setByteArray(tag.key, tag.value)
                is IntArray -> this.setIntArray(tag.key, tag.value)
                is LongArray -> this.setLongArray(tag.key, tag.value)
                is UUID -> this.setUUID(tag.key, tag.value)
                else -> throw IllegalArgumentException("Unsupported NBT value type: ${tag.value::class.qualifiedName}")
            }
        }
    }
}
