package io.github.addoncommunity.galactifun.implementation.lists;

import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

/**
 * Is named {@code GalactifunItems} instead of {@code Items} so planet packs would be able to distinguish
 * between their items and our items
 */
@UtilityClass
public final class GalactifunItems {

    public static final SlimefunItemStack LAUNCH_PAD_CORE = new SlimefunItemStack(
        "LAUNCH_PAD_CORE",
        Material.SEA_LANTERN,
        "&fLaunch Pad Core",
        "",
        "&7Surround with 8 &fLaunch Pad Floor&7s",
        "&7to use"
    );

    public static final SlimefunItemStack LAUNCH_PAD_FLOOR = new SlimefunItemStack(
        "LAUNCH_PAD_FLOOR",
        Material.STONE_SLAB,
        "&fLaunch Pad Floor",
        "",
        "&7Used in constructing the Launch Pad"
    );
}
