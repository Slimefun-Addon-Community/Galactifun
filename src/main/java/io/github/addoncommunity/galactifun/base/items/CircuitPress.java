package io.github.addoncommunity.galactifun.base.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;

public final class CircuitPress extends AContainer {

    private static final List<MachineRecipe> RECIPES = new ArrayList<>();
    public static final RecipeType TYPE = new RecipeType(
            new NamespacedKey(Galactifun.instance(), BaseItems.CIRCUIT_PRESS.getItemId().toLowerCase(Locale.ROOT)),
            BaseItems.CIRCUIT_PRESS,
            (stacks, itemStack) -> RECIPES.add(new MachineRecipe(10, Arrays.copyOf(stacks, 2), new ItemStack[] { itemStack }))
    );

    public CircuitPress(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        RECIPES.forEach(this::registerRecipe);
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
