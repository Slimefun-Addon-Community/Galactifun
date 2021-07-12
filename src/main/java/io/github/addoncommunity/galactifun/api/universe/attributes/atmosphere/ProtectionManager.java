package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;

import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class ProtectionManager {

    private static final Map<BlockPosition, Map<AtmosphericEffect, Integer>> protectedBlocks = new HashMap<>();

    @Nonnull
    public static Map<AtmosphericEffect, Integer> getProtectionsFor(@Nonnull Location l) {
        return protectedBlocks.getOrDefault(new BlockPosition(l), new HashMap<>());
    }

    public static int getProtectionFor(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return getProtectionsFor(l).getOrDefault(effect, 0);
    }

    public static void addProtection(@Nonnull BlockPosition pos, @Nonnull AtmosphericEffect effect, int level) {
        Map<AtmosphericEffect, Integer> prots = protectedBlocks.computeIfAbsent(pos, k -> new HashMap<>());
        prots.merge(effect, level, Integer::sum);
    }

    public static void clearProtectedBlocks() {
        protectedBlocks.clear();
    }
}
