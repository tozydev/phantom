package vn.id.tozydev.phantom.paper.gui

import org.bukkit.event.inventory.InventoryType
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import vn.id.tozydev.phantom.paper.inventory.ItemStackBuilder
import java.lang.reflect.Type

/**
 * Configuration for a GUI.
 */
@ConfigSerializable
data class GuiConfig(
    val type: InventoryType = InventoryType.CHEST,
    val title: String,
    val structure: List<String>,
    val actions: Map<String, Char> = emptyMap(),
    val ingredients: Map<String, GuiItem> = emptyMap(),
) {
    /** Get the ingredient character and corresponding [GuiItem] for the given [action], or null if not found. */
    operator fun get(action: String): Pair<Char, GuiItem>? {
        val ingredient = actions[action] ?: return null
        val item = ingredients[ingredient.toString()] ?: return null
        return ingredient to item
    }

    /** Execute [block] if the [action] exists, providing the corresponding ingredient and item. */
    fun actionItem(
        action: String,
        block: (ingredient: Char, item: GuiItem) -> Unit,
    ) {
        val ingredient = actions[action] ?: return
        val item = ingredients[ingredient.toString()] ?: return
        block(ingredient, item)
    }

    /** Execute [block] for each ingredient that does not have an associated action. */
    fun noActionsItem(block: (ingredient: Char, item: GuiItem) -> Unit) {
        val actionIngredients = actions.values.toSet()
        ingredients.forEach { (key, item) ->
            val ingredient = key.first()
            if (ingredient !in actionIngredients) {
                block(ingredient, item)
            }
        }
    }
}

fun GuiConfig.d() {
}

/** Represents an item in a GUI, with a [default] item and optional [states]. */
data class GuiItem(
    val default: ItemStackBuilder,
    val states: Map<String, ItemStackBuilder> = emptyMap(),
) {
    /** Get the item for the given [state], or the [default] item if the state does not exist. */
    operator fun get(state: String): ItemStackBuilder = states[state] ?: default

    internal object Serializer : TypeSerializer<GuiItem> {
        override fun deserialize(
            type: Type,
            node: ConfigurationNode,
        ): GuiItem {
            val defaultNode = node.node("default")
            if (defaultNode.isNull) {
                val item =
                    node.get<ItemStackBuilder>()
                        ?: throw SerializationException("GuiItem have wrong format or missing 'default' item")
                return GuiItem(item)
            }
            val default =
                defaultNode.get<ItemStackBuilder>()
                    ?: throw SerializationException("GuiItem 'default' is not a valid ItemStackBuilder")
            return GuiItem(
                default = default,
                states =
                    node
                        .childrenMap()
                        .filter { it.key != "default" }
                        .mapValues { (key, value) ->
                            value.get<ItemStackBuilder>()
                                ?: throw SerializationException("GuiItem state '$key' is not a valid ItemStackBuilder")
                        }.mapKeys { it.key.toString() },
            )
        }

        override fun serialize(
            type: Type,
            obj: GuiItem?,
            node: ConfigurationNode,
        ) {
            if (obj == null) {
                node.raw(null)
                return
            }
            node.node("default").set(obj.default)
            obj.states.forEach { (key, value) ->
                node.node(key).set(value)
            }
            if (obj.states.isEmpty()) {
                node.raw(null) // If no state, we can remove the node
            } else {
                node.raw(obj.states) // Otherwise, we keep the state
            }
        }
    }
}
