package io.github.addoncommunity.galactifun.base;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

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

    public static final SlimefunItemStack MOON_DUST = new SlimefunItemStack(
            "MOON_DUST",
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            "&7Moon Dust"
    );

    public static final SlimefunItemStack MARS_DUST = new SlimefunItemStack(
            "MARS_DUST",
            Material.RED_SAND,
            "&cMars Dust"
    );

    public static final SlimefunItemStack DRY_ICE = new SlimefunItemStack(
            "DRY_ICE",
            Material.PACKED_ICE,
            "&bDry Ice"
    );

    public static void setup() {
        register(MOON_DUST, BaseRegistry.THE_MOON);
        register(MARS_DUST, BaseRegistry.MARS);
        register(DRY_ICE, BaseRegistry.MARS, BaseRegistry.TITAN_MOON);
    }

    private static void register(@Nonnull SlimefunItemStack item, @Nonnull AlienWorld... worlds) {
        register(item.getType(), item, worlds);
    }

    @ParametersAreNonnullByDefault
    private static void register(Material mat, SlimefunItemStack item, AlienWorld... worlds) {
        for (AlienWorld world : worlds) {
            new SlimefunItem(CoreCategory.BLOCKS, item, CoreRecipeType.WORLD_GEN, new ItemStack[]{
                    world.getItem(), null, null,
                    null, null, null,
                    null, null, null
            }).register(Galactifun.inst());

            world.addBlockMapping(mat, item);
        }
    }
}
