package io.github.addoncommunity.galactifun.api.items.spacesuit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class SpaceSuit extends SlimefunItem implements ProtectiveArmor {

    private static final NamespacedKey SPACE_SUIT_KEY = Galactifun.instance().getKey("space_suit");

    @Getter
    private final int maxUpgrades;

    public SpaceSuit(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxUpgrades) {
        super(category, item, recipeType, recipe);
        this.maxUpgrades = maxUpgrades;
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
        return SPACE_SUIT_KEY;
    }

}
