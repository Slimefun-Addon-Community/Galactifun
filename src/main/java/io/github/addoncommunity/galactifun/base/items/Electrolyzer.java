package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.BasicGas;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public class Electrolyzer extends AContainer {

    public Electrolyzer(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        setCapacity(512);
        setEnergyConsumption(128);
        setProcessingSpeed(1);
    }

    @Override
    protected void registerDefaultRecipes() {
        registerRecipe(new ItemStack(Material.WATER_BUCKET), BasicGas.WATER.item(), new ItemStack(Material.BUCKET));
        registerRecipe(BasicGas.WATER.item(), BasicGas.HYDROGEN.item().asQuantity(2), BasicGas.OXYGEN.item());

        registerRecipe(SlimefunItems.OIL_BUCKET, BasicGas.HYDROCARBONS.item(), new ItemStack(Material.BUCKET));
        registerRecipe(BasicGas.HYDROCARBONS.item(), BasicGas.METHANE.item().asQuantity(6));
        registerRecipe(BasicGas.METHANE.item(), BasicGas.HYDROGEN.item().asQuantity(4), SlimefunItems.CARBON);
        registerRecipe(BasicGas.CARBON_DIOXIDE.item(), BasicGas.OXYGEN.item().asQuantity(2), SlimefunItems.CARBON);

        registerRecipe(BasicGas.AMMONIA.item(), BasicGas.HYDROGEN.item().asQuantity(3), BasicGas.NITROGEN.item());

        registerRecipe(BaseMats.MARS_DUST, SlimefunItems.IRON_DUST.asQuantity(2), BasicGas.OXYGEN.item().asQuantity(3));
        registerRecipe(BaseMats.MOON_DUST, BasicGas.HELIUM.item().asQuantity(3));
    }

    private void registerRecipe(ItemStack in, ItemStack... out) {
        registerRecipe(10, new ItemStack[]{in}, out);
    }

    @Override
    public ItemStack getProgressBar() {
        return MenuBlock.PROCESSING_ITEM;
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "ELECTROLYZER";
    }

}
