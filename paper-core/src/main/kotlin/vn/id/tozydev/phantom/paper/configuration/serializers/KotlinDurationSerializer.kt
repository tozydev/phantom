package vn.id.tozydev.phantom.paper.configuration.serializers

import org.spongepowered.configurate.serialize.ScalarSerializer
import java.lang.reflect.Type
import java.util.function.Predicate
import kotlin.time.Duration

/** Serializer for Kotlin's [Duration] type. */
object KotlinDurationSerializer : ScalarSerializer<Duration>(Duration::class.java) {
    override fun deserialize(
        type: Type,
        obj: Any,
    ): Duration = Duration.parse(obj.toString())

    override fun serialize(
        item: Duration,
        typeSupported: Predicate<Class<*>>,
    ): Any = item.toString()
}
