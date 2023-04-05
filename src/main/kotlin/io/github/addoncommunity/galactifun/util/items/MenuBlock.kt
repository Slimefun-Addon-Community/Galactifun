package io.github.addoncommunity.galactifun.util.items

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import org.bukkit.inventory.ItemStack

abstract class MenuBlock(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<ItemStack>
) : SlimefunItem(itemGroup, item, recipeType, recipe) {

    abstract val menu: MenuBuilder

    override fun preRegister() {
        menu.applyOn(this)
    }
}