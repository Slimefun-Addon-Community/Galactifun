package io.github.addoncommunity.galactifun.api.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

// TODO implement
public class SpaceSuit extends SlimefunItem implements ProtectiveArmor {

    public SpaceSuit(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public ProtectionType[] getProtectionTypes() {
        return new ProtectionType[] {
                ProtectionType.RADIATION,
                ProtectionType.BEES
        };
    }

    @Override
    public boolean isFullSetRequired() {
        return true;
    }

    @Nullable
    @Override
    public NamespacedKey getArmorSetId() {
        return Galactifun.instance().getKey("galactifun_space_suit");
    }

}
