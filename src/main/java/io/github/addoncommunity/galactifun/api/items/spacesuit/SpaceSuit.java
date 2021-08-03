package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import lombok.Getter;
import lombok.NonNull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@Getter
public final class SpaceSuit extends SlimefunItem implements ProtectiveArmor {

    private final boolean isChestPlate;
    private final int invSize;

    public SpaceSuit(SlimefunItemStack item, ItemStack[] recipe, boolean isChestPlate, int invSize) {
        super(CoreCategory.EQUIPMENT, item, RecipeType.ARMOR_FORGE, recipe);
        this.isChestPlate = isChestPlate;
        this.invSize = invSize;
    }

    @Nonnull
    public static Map<AtmosphericEffect, Integer> getProtections(@NonNull Player p) {
        Optional<SpaceSuitProfile> optionalProfile = SpaceSuitProfile.getOrCreate(p);
        if (optionalProfile.isEmpty()) return new HashMap<>();
        Inventory inventory = optionalProfile.get().inventory();

        Map<AtmosphericEffect, Integer> zeMap = new HashMap<>();
        for (ItemStack stack : inventory.getContents()) {
            if (SlimefunItem.getByItem(stack) instanceof SpaceSuitModule module) {
                zeMap.merge(module.effect(), module.protectionLevel(), Integer::sum);
            }
        }

        return zeMap;
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

    @Nonnull
    @Override
    public NamespacedKey getArmorSetId() {
        return Galactifun.instance().getKey("space_suit");
    }

}
