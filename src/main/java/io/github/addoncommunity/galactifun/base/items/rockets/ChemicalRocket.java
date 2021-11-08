package io.github.addoncommunity.galactifun.base.items.rockets;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import io.github.addoncommunity.galactifun.api.items.Rocket;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;

public final class ChemicalRocket extends Rocket {

    public ChemicalRocket(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int fuelCapacity, int storageCapacity) {
        super(category, item, recipeType, recipe, fuelCapacity, storageCapacity);
    }

    @Override
    protected List<ItemStack> getAllowedFuels() {
        return Lists.newArrayList(SlimefunItems.OIL_BUCKET, SlimefunItems.FUEL_BUCKET, Gas.METHANE.item());
    }

}
