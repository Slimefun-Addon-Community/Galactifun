package io.github.addoncommunity.galactifun.base.items;

import java.util.Arrays;
import java.util.Locale;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.mooy1.infinitylib.machines.MachineRecipeType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;

public final class DiamondAnvil extends AContainer {

    public static final MachineRecipeType TYPE = new MachineRecipeType(
            BaseItems.DIAMOND_ANVIL.getItemId().toLowerCase(Locale.ROOT),
            BaseItems.DIAMOND_ANVIL
    );

    public DiamondAnvil(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        TYPE.sendRecipesTo((ing, res) ->{
            ItemStack[] itemRecipe = Arrays.copyOf(ing, 2);
            if (itemRecipe[1] == null){
                itemRecipe = Arrays.copyOf(itemRecipe, 1);
            }
            this.registerRecipe(10, itemRecipe, new ItemStack[] { res });
        });
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.ANVIL);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return BaseItems.DIAMOND_ANVIL.getItemId().toLowerCase(Locale.ROOT);
    }

}
