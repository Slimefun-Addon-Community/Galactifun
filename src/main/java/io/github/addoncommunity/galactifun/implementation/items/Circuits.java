package io.github.addoncommunity.galactifun.implementation.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.RecipeTypes;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

@Getter
public enum Circuits {
    DIAMOND(new ItemStack(Material.DIAMOND)),
    REDSTONE(new ItemStack(Material.REDSTONE_BLOCK)),
    LAPIS(new ItemStack(Material.LAPIS_BLOCK)),
    GLOWSTONE(new ItemStack(Material.GLOWSTONE))
    ;

    private final SlimefunItemStack item;
    private final ItemStack otherItem;

    Circuits(ItemStack otherItem) {
        this.otherItem = otherItem;

        this.item = new SlimefunItemStack(
            this.name() + "_CIRCUIT",
            Material.POWERED_RAIL,
            "&7" + WordUtils.capitalizeFully(this.name().toLowerCase(Locale.ROOT)) + " Circuit"
        );
    }

    public static void setup(Galactifun addon) {
        for (Circuits circuit : Circuits.values()) {
            new SlimefunItem(Categories.COMPONENTS, circuit.item, RecipeTypes.CIRCUIT_PRESS, new ItemStack[] {
                SlimefunItems.SILICON, circuit.otherItem, null,
                null, null, null,
                null, null, null
            }).register(addon);
        }
    }
}
