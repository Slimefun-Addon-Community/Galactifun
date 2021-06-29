package io.github.addoncommunity.galactifun.api.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class Extension extends UnplaceableBlock {

    @Nonnull
    private final AtmosphericEffect workFor;
    private final int extraRange;
    private final int extraProtection;

    @ParametersAreNonnullByDefault
    public Extension(String name, AtmosphericEffect workFor, int extraRange, int extraProtection, ItemStack[] recipe) {
        super(CoreCategory.ITEMS, createItem(name, workFor, extraRange, extraProtection), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.workFor = workFor;
        this.extraRange = extraRange;
        this.extraProtection = extraProtection;
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    private static SlimefunItemStack createItem(String name, AtmosphericEffect workFor, int extraRange, int extraProtection) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Works on blocks that negate " + workFor + " in an area");
        if (extraRange != 0) {
            lore.add("&7Extra Blocks Affected: " + extraRange);
        }
        if (extraProtection != 0) {
            lore.add("&7Extra Protection Levels: " + extraProtection);
        }

        return new SlimefunItemStack(
                ChatUtils.removeColorCodes(name).toUpperCase(Locale.ROOT).replace(' ', '_'),
                Material.ACTIVATOR_RAIL,
                "&fEffect Extension: " + name,
                lore.toArray(new String[0])
        );
    }
}
