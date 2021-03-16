package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
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

    public static final SlimefunItemStack ASSEMBLY_TABLE = new SlimefunItemStack(
        "ASSEMBLY_TABLE",
        Material.SMITHING_TABLE,
        "&fAssembly Table",
        "",
        "&7Used to construct many things",
        "",
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
        LoreBuilder.powerPerSecond(2048)
    );

    public static final SlimefunItemStack CIRCUIT_PRESS = new SlimefunItemStack(
        "CIRCUIT_PRESS",
        Material.PISTON,
        "&fCircuit Press",
        "",
        "&7Creates circuits",
        "",
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
        LoreBuilder.powerPerSecond(500),
        LoreBuilder.powerBuffer(1024)
    );

    public static final SlimefunItemStack MUNPOWDER = new SlimefunItemStack(
        "MUNPOWDER",
        Material.GUNPOWDER,
        "&7Munpowder",
        "",
        "&7The gunpowder of the moon"
    );

    public static final SlimefunItemStack FALLEN_METEOR = new SlimefunItemStack(
        "FALLEN_METEOR",
        Material.ANCIENT_DEBRIS,
        "&4Fallen Meteor",
        "",
        "&7These meteors contain Tungsten"
    );
}
