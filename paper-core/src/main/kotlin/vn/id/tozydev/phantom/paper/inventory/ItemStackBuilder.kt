@file:Suppress("UnstableApiUsage")

package vn.id.tozydev.phantom.paper.inventory

import com.destroystokyo.paper.profile.ProfileProperty
import de.tr7zw.nbtapi.NBT
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.inventory.meta.components.CustomModelDataComponent
import org.slf4j.LoggerFactory
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.extensions.getList
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import vn.id.tozydev.phantom.paper.Server
import vn.id.tozydev.phantom.paper.nbt.NbtTag
import vn.id.tozydev.phantom.paper.nbt.set
import java.lang.reflect.Type
import java.util.UUID

/**
 * Quick and easy way to build an [ItemStack] with various properties.
 *
 * This builder supports serialization and deserialization using SpongePowered Configurate.
 *
 * @see toItemStack
 * @see CustomModelDataComponentBuilder
 * @see DamageableBuilder
 * @see SkullBuilder
 * @see LeatherArmorBuilder
 */
@ConfigSerializable
data class ItemStackBuilder(
    val type: String,
    val amount: Int = 1,
    val displayName: String? = null,
    val lore: List<String> = emptyList(),
    val customModelData: CustomModelDataComponentBuilder? = null,
    val enchantments: Map<Enchantment, Int> = emptyMap(),
    val flags: Set<ItemFlag> = emptySet(),
    val unbreakable: Boolean = false,
    val glowing: Boolean? = null,
    val hideTooltip: Boolean = false,
    val glider: Boolean = false,
    val maxStackSize: Int? = null,
    val nbt: List<NbtTag> = emptyList(),
    val damageable: DamageableBuilder? = null,
    val skull: SkullBuilder? = null,
    val leatherArmor: LeatherArmorBuilder? = null,
)

data class CustomModelDataComponentBuilder(
    val floats: List<Float> = emptyList(),
    val strings: List<String> = emptyList(),
    val flags: List<Boolean> = emptyList(),
    val colors: List<Color> = emptyList(),
) {
    fun apply(target: CustomModelDataComponent): CustomModelDataComponent {
        target.floats = floats
        target.strings = strings
        target.flags = flags
        target.colors = colors
        return target
    }

    internal object Serializer : TypeSerializer<CustomModelDataComponentBuilder> {
        override fun deserialize(
            type: Type,
            node: ConfigurationNode,
        ): CustomModelDataComponentBuilder {
            if (!node.isMap) {
                val customDataModel =
                    node.string?.toFloatOrNull() ?: throw SerializationException("Invalid custom model data format")
                return CustomModelDataComponentBuilder(floats = listOf(customDataModel))
            }

            val floats = node.node("floats").getList(Float::class) ?: emptyList()
            val strings = node.node("strings").getList(String::class) ?: emptyList()
            val flags = node.node("flags").getList(Boolean::class) ?: emptyList()
            val colors = node.node("colors").getList(Color::class) ?: emptyList()
            return CustomModelDataComponentBuilder(floats, strings, flags, colors)
        }

        override fun serialize(
            type: Type,
            obj: CustomModelDataComponentBuilder?,
            node: ConfigurationNode,
        ) {
            if (obj == null) {
                node.raw(null)
                return
            }

            if (obj.floats.isEmpty() && obj.strings.isEmpty() && obj.flags.isEmpty() && obj.colors.isEmpty()) {
                node.raw(null)
                return
            }

            if (obj.floats.isNotEmpty()) {
                node.node("floats").set(obj.floats)
            }
            if (obj.strings.isNotEmpty()) {
                node.node("strings").set(obj.strings)
            }
            if (obj.flags.isNotEmpty()) {
                node.node("flags").set(obj.flags)
            }
            if (obj.colors.isNotEmpty()) {
                node.node("colors").set(obj.colors)
            }
        }
    }
}

data class DamageableBuilder(
    val damage: Int? = null,
    val maxDamage: Int? = null,
) {
    fun apply(target: Damageable): Damageable {
        damage?.let { target.damage = it }
        target.setMaxDamage(maxDamage)
        return target
    }

    internal object Serializer : TypeSerializer<DamageableBuilder> {
        override fun deserialize(
            type: Type,
            node: ConfigurationNode,
        ): DamageableBuilder {
            if (!node.isMap) {
                val damage = node.int
                return DamageableBuilder(damage = damage, maxDamage = null)
            }

            val damage = node.node("damage")
            val maxDamage = node.node("max-damage")
            return DamageableBuilder(damage.int, if (maxDamage.isNull) null else maxDamage.int)
        }

        override fun serialize(
            type: Type,
            obj: DamageableBuilder?,
            node: ConfigurationNode,
        ) {
            if (obj == null) {
                node.raw(null)
                return
            }
            if (obj.damage != null) {
                node.node("damage").set(obj.damage)
            }
            if (obj.maxDamage != null) {
                node.node("max-damage").set(obj.maxDamage)
            }
        }
    }
}

data class SkullBuilder(
    val owner: String? = null,
    val texture: String? = null,
) {
    fun apply(target: SkullMeta): SkullMeta {
        owner?.let { target.owningPlayer = Server.getOfflinePlayer(it) }
        texture?.let {
            target.playerProfile =
                Server.createProfile(UUID.randomUUID()).apply {
                    setProperty(ProfileProperty("textures", it))
                }
        }
        return target
    }

    internal object Serializer : TypeSerializer<SkullBuilder> {
        override fun deserialize(
            type: Type,
            node: ConfigurationNode,
        ): SkullBuilder {
            if (!node.isMap) {
                return SkullBuilder(texture = node.string)
            }

            val owner = node.node("owner").string
            val texture = node.node("texture").string
            return SkullBuilder(owner, texture)
        }

        override fun serialize(
            type: Type,
            obj: SkullBuilder?,
            node: ConfigurationNode,
        ) {
            if (obj == null) {
                node.raw(null)
                return
            }
            if (obj.owner != null) {
                node.node("owner").set(obj.owner)
            }
            if (obj.texture != null) {
                node.node("texture").set(obj.texture)
            }
        }
    }
}

data class LeatherArmorBuilder(
    val color: Color? = null,
) {
    fun apply(target: LeatherArmorMeta): LeatherArmorMeta {
        color?.let { target.setColor(it) }
        return target
    }

    internal object Serializer : TypeSerializer<LeatherArmorBuilder> {
        override fun deserialize(
            type: Type,
            node: ConfigurationNode,
        ): LeatherArmorBuilder {
            if (!node.isMap) {
                return LeatherArmorBuilder(color = node.get())
            }

            val color = node.node("color").get<Color>()
            return LeatherArmorBuilder(color)
        }

        override fun serialize(
            type: Type,
            obj: LeatherArmorBuilder?,
            node: ConfigurationNode,
        ) {
            if (obj == null) {
                node.raw(null)
                return
            }
            if (obj.color != null) {
                node.node("color").set(obj.color)
            }
        }
    }
}

/**
 * Converts this [ItemStackBuilder] to an [ItemStack].
 *
 * @param miniMessage The MiniMessage instance used for parsing display names and lore.
 * @param tagResolver The TagResolver used for resolving tags in the display name and lore.
 * @return The constructed [ItemStack].
 */
fun ItemStackBuilder.toItemStack(
    miniMessage: MiniMessage = MiniMessage.miniMessage(),
    tagResolver: TagResolver = TagResolver.empty(),
): ItemStack {
    val itemStack = ItemStack.of(Material.matchMaterial(type) ?: error("Invalid material type: $type"), amount)

    itemStack.editMeta { meta ->
        displayName?.let {
            meta.displayName(
                miniMessage.deserialize(it, tagResolver).decoration(TextDecoration.ITALIC, false),
            )
        }
        if (lore.isNotEmpty()) {
            meta.lore(lore.map { miniMessage.deserialize(it, tagResolver).decoration(TextDecoration.ITALIC, false) })
        }
        customModelData?.let { meta.setCustomModelDataComponent(it.apply(meta.customModelDataComponent)) }
        if (enchantments.isNotEmpty()) {
            for ((enchantment, level) in enchantments) {
                meta.addEnchant(enchantment, level, true)
            }
        }
        if (flags.isNotEmpty()) {
            meta.addItemFlags(*flags.toTypedArray())
        }
        meta.isUnbreakable = unbreakable
        meta.setEnchantmentGlintOverride(glowing)
        meta.isHideTooltip = hideTooltip
        meta.isGlider = glider
        meta.setMaxStackSize(maxStackSize)
        damageable?.apply(meta as? Damageable ?: error("`damageable` is only applicable to Damageable items"))
        skull?.apply(meta as? SkullMeta ?: error("`skull` is only applicable to Skull items"))
        leatherArmor?.apply(
            meta as? LeatherArmorMeta ?: error("`leatherArmor` is only applicable to Leather Armor items"),
        )
    }
    itemStack.applyNbt(nbt)
    return itemStack
}

private val logger = LoggerFactory.getLogger(ItemStackBuilder::class.java)

private fun ItemStack.applyNbt(tags: List<NbtTag>) {
    try {
        Class.forName("de.tr7zw.nbtapi.NBT")
        NBT.modify(this) { nbt ->
            tags.forEach { nbt.set(it) }
        }
    } catch (_: ClassNotFoundException) {
        logger.debug("NBT API not found, skipping NBT application for ItemStack.")
        return
    }
}
