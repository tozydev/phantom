package vn.id.tozydev.phantom.paper.key

import net.kyori.adventure.key.Key
import org.bukkit.NamespacedKey

/** Converts a [Key] to a [NamespacedKey]. */
fun Key.toBukkitKey(): NamespacedKey = this as? NamespacedKey ?: NamespacedKey(namespace(), value())

/** Creates a [Key] from a [String]. */
fun String.toKey(): Key = Key.key(this)
