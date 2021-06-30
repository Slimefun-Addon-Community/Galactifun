package io.github.addoncommunity.galactifun.api.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@Getter
public class Extension extends UnplaceableBlock {

    @Nonnull
    private final AtmosphericEffect effect;
    private final int extraRange;
    private final int extraProtection;
    private final int extraEnergy;

    @ParametersAreNonnullByDefault
    public Extension(String name, AtmosphericEffect effect, int extraRange, int extraProtection, ItemStack[] recipe, int extraEnergy) {
        super(CoreCategory.ITEMS, createItem(name, effect, extraRange, extraProtection, extraEnergy), RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.effect = effect;
        this.extraRange = extraRange;
        this.extraProtection = extraProtection;
        this.extraEnergy = extraEnergy;
    }

    /**
     * My cheaty way of getting around that fact that no code can be executed in a
     * constructor before a {@code super()} call
     */
    @Nonnull
    @ParametersAreNonnullByDefault
    private static SlimefunItemStack createItem(String name, AtmosphericEffect workFor, int extraRange, int extraProtection, int extraEnergy) {
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("&7Works on blocks that negate " + workFor + " in an area");
        if (extraRange != 0) {
            lore.add("&7Extra Blocks Affected: " + extraRange);
        }
        if (extraProtection != 0) {
            lore.add("&7Extra Protection Levels: " + extraProtection);
        }
        if (extraEnergy != 0) {
            lore.add("&cExtra Energy Use Per Slimefun Tick: " + extraEnergy);
        }

        return new SlimefunItemStack(
                ChatUtils.removeColorCodes(name).toUpperCase(Locale.ROOT).replace(' ', '_'),
                Material.ACTIVATOR_RAIL,
                "&fEffect Extension: " + name,
                lore.toArray(new String[0])
        );
    }
}
