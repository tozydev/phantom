package vn.id.tozydev.phantom.paper.persistence

import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType
import java.nio.ByteBuffer
import java.util.UUID

/**
 * A persistent data type for representing UUID values as byte arrays.
 *
 * This class implements the `PersistentDataType<ByteArray, UUID>` interface and provides methods for converting
 * between byte arrays and UUID values.
 *
 * @see PersistentDataType
 */
object UuidTagType : PersistentDataType<ByteArray, UUID> {
    override fun getPrimitiveType() = ByteArray::class.java

    override fun getComplexType() = UUID::class.java

    override fun fromPrimitive(
        primitive: ByteArray,
        context: PersistentDataAdapterContext,
    ): UUID {
        val buffer = ByteBuffer.wrap(primitive)
        return UUID(buffer.long, buffer.long)
    }

    override fun toPrimitive(
        complex: UUID,
        context: PersistentDataAdapterContext,
    ): ByteArray {
        val buffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(complex.mostSignificantBits)
        buffer.putLong(complex.leastSignificantBits)
        return buffer.array()
    }
}
