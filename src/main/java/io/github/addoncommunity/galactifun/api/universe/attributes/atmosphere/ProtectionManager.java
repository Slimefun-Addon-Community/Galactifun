package io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import lombok.NonNull;

import org.bukkit.Location;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import me.mrCookieSlime.Slimefun.cscorelib2.blocks.BlockPosition;

public final class ProtectionManager {

    private final Map<BlockPosition, Map<AtmosphericEffect, Integer>> protectedBlocks = new HashMap<>();

    @Nonnull
    public Map<AtmosphericEffect, Integer> getProtectionsFor(@Nonnull Location l) {
        return protectedBlocks.getOrDefault(new BlockPosition(l), new HashMap<>());
    }

    public int getProtectionFor(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return getProtectionsFor(l).getOrDefault(effect, 0);
    }

    public void addProtection(@Nonnull BlockPosition pos, @Nonnull AtmosphericEffect effect, int level) {
        Map<AtmosphericEffect, Integer> prots = protectedBlocks.computeIfAbsent(pos, k -> new HashMap<>());
        prots.merge(effect, level, Integer::sum);
    }

    public void clearProtectedBlocks() {
        protectedBlocks.clear();
    }

    @Nonnull
    public Map<AtmosphericEffect, Integer> getEffectsAt(@Nonnull Location l) {
        AlienWorld world = Galactifun.worldManager().getAlienWorld(l.getWorld());
        if (world == null) return new HashMap<>();
        return subtractProtections(world.getAtmosphere(), getProtectionsFor(l));
    }

    public int getEffectAt(@Nonnull Location l, @Nonnull AtmosphericEffect effect) {
        return getEffectsAt(l).getOrDefault(effect, 0);
    }

    @Nonnull
    public Map<AtmosphericEffect, Integer> subtractProtections(@NonNull Atmosphere atmosphere, @NonNull Map<AtmosphericEffect, Integer> protections) {
        Map<AtmosphericEffect, Integer> ret = new HashMap<>();
        for (Map.Entry<AtmosphericEffect, Integer> eff : atmosphere.getEffects().entrySet()) {
            int val = eff.getValue() - protections.getOrDefault(eff.getKey(), 0);
            if (val > 0) ret.put(eff.getKey(), val);
        }

        return ret;
    }
}
