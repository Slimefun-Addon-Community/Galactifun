package io.github.addoncommunity.galactifun.base;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.CoreRecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

@UtilityClass
public final class GeneratedItems {

    public static final SlimefunItemStack MOON_SAND = new SlimefunItemStack(
            "MOON_SAND",
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            "&7Moon Sand"
    );

    public static void setup() {
        register(MOON_SAND, BaseRegistry.THE_MOON);
    }

    private static void register(SlimefunItemStack item, AlienWorld world) {
        new SlimefunItem(CoreCategory.BLOCKS, item, CoreRecipeType.WORLD_GEN, new ItemStack[]{
                world.getItem(), null, null,
                null, null, null,
                null, null, null
        }).register(Galactifun.inst());
    }
}
