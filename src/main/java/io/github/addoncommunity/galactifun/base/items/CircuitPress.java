package io.github.addoncommunity.galactifun.base.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.mooy1.infinitylib.slimefun.utils.DelayedRecipeType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class CircuitPress extends AContainer {
    
    public static final DelayedRecipeType TYPE = new DelayedRecipeType(Galactifun.inst(), BaseItems.CIRCUIT_PRESS);

    public CircuitPress(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        TYPE.accept(((stacks, itemStack) -> registerRecipe(10, Arrays.copyOf(stacks, 2), new ItemStack[] {itemStack})));
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
