package io.github.addoncommunity.galactifun.implementation.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@Getter
public enum Metals {
    ALUMINUM_COMPOSITE("Aluminum Composite", Material.IRON_INGOT, new String[]{
        "",
        "&7You'll never guess how long it took us",
        "&7to name this material"
    }, SlimefunItems.ALUMINUM_INGOT, SlimefunItems.MAGNESIUM_DUST, SlimefunItems.ZINC_DUST,
        SlimefunItems.TIN_DUST, SlimefunItems.ALUMINUM_DUST),
    TUNGSTEN("Tungsten Ingot", Material.NETHERITE_INGOT, new String[]{
        "",
        "&7A strong metal obtained either from",
        "&7giving Martians reinforced plates or",
        "&from smelting fallen meteors"
    }, GalactifunItems.FALLEN_METEOR),
    ;
    private final String name;
    private final ItemStack[] recipe;
    private final SlimefunItemStack item;

    @ParametersAreNonnullByDefault
    Metals(String name, Material material, String[] lore, ItemStack... recipe) {
        // makes name white if no color codes
        if (name.contains("&")) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
        } else {
            this.name = ChatColor.translateAlternateColorCodes('&', "&f" + name);
        }
        if (recipe.length > 9) {
            throw new IllegalArgumentException("Recipe > 9!");
        }
        this.recipe = Arrays.copyOf(recipe, 9);

        this.item = new SlimefunItemStack(
            this.name(),
            material,
            this.name,
            lore
        );
    }

    public static void setup(@Nonnull Galactifun addon) {
        for (Metals metal : Metals.values()) {
            new SlimefunItem(Categories.MAIN_CATEGORY, metal.item, RecipeType.SMELTERY, metal.recipe).register(addon);
        }
    }
}
