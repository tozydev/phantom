@file:Suppress("UnstableApiUsage")

package vn.id.tozydev.phantom.paper.inventory

import com.destroystokyo.paper.profile.ProfileProperty
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ResolvableProfile
import org.bukkit.inventory.ItemStack
import java.util.*

fun ItemStack.setProfile(
    name: String? = null,
    uuid: UUID? = null,
    texture: String? = null,
): ItemStack {
    require(texture != null || uuid != null || name != null) {
        "At least one of 'name', 'uuid', or 'texture' must be provided to set a profile."
    }
    val builder = ResolvableProfile.resolvableProfile()
    if (name != null) {
        builder.name(name)
    }
    if (uuid != null) {
        builder.uuid(uuid)
    }
    if (texture != null) {
        builder.addProperty(ProfileProperty("textures", texture))
    }
    setData(DataComponentTypes.PROFILE, builder)
    return this
}
