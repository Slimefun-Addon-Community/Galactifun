package io.github.addoncommunity.galactifun.core.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import lombok.NonNull;

import org.bukkit.Location;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class ProtectionManager {

    private final Map<BlockPosition, Map<AtmosphericEffect, Integer>> protectedBlocks = new HashMap<>();

    private final Set<BlockPosition> oxygen = new HashSet<>();

    @Nonnull
    public Map<AtmosphericEffect, Integer> protectionsAt(@Nonnull Location l) {
        return this.protectedBlocks.getOrDefault(new BlockPosition(l), new HashMap<>());
    }

    public int protectionAt(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return protectionsAt(l).getOrDefault(effect, 0);
    }

    public void addProtection(@Nonnull BlockPosition pos, @Nonnull AtmosphericEffect effect, int level) {
        this.protectedBlocks.computeIfAbsent(pos, k -> new HashMap<>()).merge(effect, level, Integer::sum);
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public void clearProtectedBlocks() {
        this.protectedBlocks.clear();
    }

    @Nonnull
    public Map<AtmosphericEffect, Integer> getEffectsAt(@Nonnull Location l) {
        AlienWorld world = Galactifun.worldManager().getAlienWorld(l.getWorld());
        if (world == null) return new HashMap<>();
        return subtractProtections(world.atmosphere(), protectionsAt(l));
    }

    public int getEffectAt(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return getEffectsAt(l).getOrDefault(effect, 0);
    }

    @Nonnull
    public Map<AtmosphericEffect, Integer> subtractProtections(@NonNull Atmosphere atmosphere, @NonNull Map<AtmosphericEffect, Integer> protections) {
        Map<AtmosphericEffect, Integer> ret = new HashMap<>();
        for (Map.Entry<AtmosphericEffect, Integer> eff : atmosphere.effects().entrySet()) {
            int val = eff.getValue() - protections.getOrDefault(eff.getKey(), 0);
            if (val > 0) ret.put(eff.getKey(), val);
        }

        return ret;
    }

    public void addOxygenBlock(@Nonnull BlockPosition l) {
        this.oxygen.add(l);
    }

    public boolean isOxygenBlock(@Nonnull Location l) {
        return this.oxygen.contains(new BlockPosition(l));
    }

    /**
     * @deprecated for internal use only
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public void clearOxygenBlocks() {
        this.oxygen.clear();
    }
}
