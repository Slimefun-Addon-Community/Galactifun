package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.rockets.LaunchPadCore;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.inventory.ItemStack;

public final class Setup {

    private Setup() {
    }

    public static void setup(Galactifun addon) {
        new LaunchPadCore(Categories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9])
            .register(addon);

        new SlimefunItem(Categories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9])
            .register(addon);
    }
}
