package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;

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
        registerRecipe(new ItemStack(Material.WATER_BUCKET), Gas.WATER.item().item(), new ItemStack(Material.BUCKET));
        registerRecipe(Gas.WATER.item().item(), Gas.HYDROGEN.item().item().asQuantity(2), Gas.OXYGEN.item().item());

        registerRecipe(SlimefunItems.OIL_BUCKET.item(), Gas.HYDROCARBONS.item().item(), new ItemStack(Material.BUCKET));
        registerRecipe(Gas.HYDROCARBONS.item().item(), Gas.METHANE.item().item().asQuantity(6));
        registerRecipe(Gas.METHANE.item().item(), Gas.HYDROGEN.item().item().asQuantity(4), SlimefunItems.CARBON.item());
        registerRecipe(Gas.CARBON_DIOXIDE.item().item(), Gas.OXYGEN.item().item().asQuantity(2), SlimefunItems.CARBON.item());

        registerRecipe(Gas.AMMONIA.item().item(), Gas.HYDROGEN.item().item().asQuantity(3), Gas.NITROGEN.item().item());

        registerRecipe(BaseMats.MARS_DUST.item(), SlimefunItems.IRON_DUST.asQuantity(2), Gas.OXYGEN.item().item().asQuantity(3));
        registerRecipe(BaseMats.MOON_DUST.item(), Gas.HELIUM.item().asQuantity(3));
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
