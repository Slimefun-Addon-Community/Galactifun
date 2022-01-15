package io.github.addoncommunity.galactifun.api.worlds.populators.relics;

import java.util.Random;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;

import org.bukkit.Location;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.util.Util;

@AllArgsConstructor
public class FallenSatellitePopulator extends BlockPopulator {

    private final double chance;

    @Override
    public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
        if (random.nextDouble() * 100 < chance) {
            Vector v = random.nextBoolean() ? new Vector(1, 0, 0) : new Vector(0, 0, 1);
            int x = (cx << 4) + random.nextInt(16);
            int z = (cz << 4) + random.nextInt(16);
            Location l = Util.getHighestBlockAt(region, x, z);
            Util.setSlimefunBlock(region, l.add(0, 1, 0), BaseItems.FALLEN_SATELLITE_RELIC);
            if (random.nextBoolean()) {
                Util.setSlimefunBlock(region, l.add(v), BaseItems.BROKEN_SOLAR_PANEL_RELIC);
                v.multiply(-1);
                l.add(v);
            }
            if (random.nextBoolean()) {
                Util.setSlimefunBlock(region, l.add(v), BaseItems.BROKEN_SOLAR_PANEL_RELIC);
            }
        }
    }

}
