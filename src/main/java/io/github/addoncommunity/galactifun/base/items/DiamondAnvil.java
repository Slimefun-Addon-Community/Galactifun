package io.github.addoncommunity.galactifun.base.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class DiamondAnvil extends AContainer {

    private static final List<MachineRecipe> RECIPES = new ArrayList<>();
    public static final RecipeType TYPE = new RecipeType(
            Galactifun.instance().getKey(BaseItems.DIAMOND_ANVIL.getItemId().toLowerCase(Locale.ROOT)),
            BaseItems.DIAMOND_ANVIL,
            (stacks, itemStack) -> RECIPES.add(new MachineRecipe(30, Arrays.copyOf(stacks, 2), new ItemStack[] { itemStack }))
    );

    public DiamondAnvil(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreCategory.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        RECIPES.forEach(this::registerRecipe);
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
