package io.github.addoncommunity.galactifun.implementation.machines;

import io.github.addoncommunity.galactifun.implementation.items.Circuits;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class CircuitPress extends AContainer {

    public CircuitPress() {
        super(Categories.MACHINES, GalactifunItems.CIRCUIT_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
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
    public int getCapacity() {
        return 1024;
    }

    @Override
    public int getEnergyConsumption() {
        return 25;
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
