package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.Location;

import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class ProtectionManager {

    private final Map<BlockPosition, Map<AtmosphericEffect, Integer>> protectedBlocks = new HashMap<>();

    @Nonnull
    public Map<AtmosphericEffect, Integer> protectionAt(@Nonnull Location l) {
        return this.protectedBlocks.getOrDefault(new BlockPosition(l), new HashMap<>());
    }

    public int protectionAt(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return protectionAt(l).getOrDefault(effect, 0);
    }

    public void addProtection(@Nonnull BlockPosition pos, @Nonnull AtmosphericEffect effect, int level) {
        this.protectedBlocks.computeIfAbsent(pos, k -> new HashMap<>()).merge(effect, level, Integer::sum);
    }

    public void clearProtectedBlocks() {
        this.protectedBlocks.clear();
    }

}
