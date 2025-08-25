package vn.id.tozydev.phantom.paper.configuration.serializers

import org.bukkit.Color
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

/**
 * A serializer for Bukkit's [Color] class.
 *
 * This serializer supports deserializing colors from various formats including RGB integers, ARGB integers,
 * hexadecimal strings, and maps with red, green, blue, and optional alpha components.
 */
object BukkitColorSerializer : TypeSerializer<Color> {
    private const val RGB_MAX = 0xFFFFFF

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): Color {
        if (!node.isMap) {
            return when (val raw = node.raw()) {
                is Color -> raw
                is Int -> if (raw > RGB_MAX) Color.fromARGB(raw) else Color.fromRGB(raw)
                is Long -> if (raw > RGB_MAX) Color.fromARGB(raw.toInt()) else Color.fromRGB(raw.toInt())
                is String -> {
                    val hex =
                        raw.removePrefix("#").toIntOrNull(16)
                            ?: throw SerializationException("Invalid color format: $raw")
                    if (hex > RGB_MAX) Color.fromARGB(hex) else Color.fromRGB(hex)
                }

                else -> throw SerializationException("Cannot deserialize Color from $raw")
            }
        }

        val red = node.node("red").get<Int>() ?: node.node("r").get<Int>()
        val green = node.node("green").get<Int>() ?: node.node("g").get<Int>()
        val blue = node.node("blue").get<Int>() ?: node.node("b").get<Int>()
        val alpha = node.node("alpha").get<Int>() ?: node.node("a").get<Int>()
        if (red == null || green == null || blue == null) {
            throw SerializationException("Missing color components in node: $node")
        }
        return if (alpha != null) {
            Color.fromARGB(alpha, red, green, blue)
        } else {
            Color.fromRGB(red, green, blue)
        }
    }

    override fun serialize(
        type: Type,
        obj: Color?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }
        node.node("red").set(obj.red)
        node.node("green").set(obj.green)
        node.node("blue").set(obj.blue)
        if (obj.alpha != 255) {
            node.node("alpha").set(obj.alpha)
        }
    }
}
