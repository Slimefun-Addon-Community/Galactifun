package io.github.addoncommunity.galactifun.base.items;

import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import io.github.addoncommunity.galactifun.base.GalactifunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class CircuitPress extends AContainer {

    public CircuitPress() {
        super(CoreCategories.MACHINES, GalactifunItems.CIRCUIT_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL,
            SlimefunItems.STEEL_PLATE, null, SlimefunItems.STEEL_PLATE,
            SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL
        });
    }

    @Override
    protected void registerDefaultRecipes() {
        for (Circuits circuit : Circuits.values()) {
            registerRecipe(10, new ItemStack[]{SlimefunItems.SILICON, circuit.getOtherItem()}, new ItemStack[]{circuit.getItem()});
        }
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.PISTON);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "CIRCUIT_PRESS";
    }
}
