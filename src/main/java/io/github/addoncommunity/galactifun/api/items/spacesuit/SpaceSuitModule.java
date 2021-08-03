package io.github.addoncommunity.galactifun.api.items.spacesuit;

import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@Getter
public class SpaceSuitModule extends UnplaceableBlock {

    private final AtmosphericEffect effect;
    private final int protectionLevel;

    @ParametersAreNonnullByDefault
    public SpaceSuitModule(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, AtmosphericEffect effect, int protectionLevel) {
        super(category, item, recipeType, recipe);
        this.effect = effect;
        this.protectionLevel = protectionLevel;
    }
}
