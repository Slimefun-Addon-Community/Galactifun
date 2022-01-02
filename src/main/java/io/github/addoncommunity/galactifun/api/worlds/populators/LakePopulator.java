package io.github.addoncommunity.galactifun.api.worlds.populators;

import java.util.Random;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

/**
 * Lake populator
 *
 * @author Seggan
 * @author Mooy1
 */
@AllArgsConstructor
public class LakePopulator extends BlockPopulator {

    private final int maxY;
    @Nonnull
    private final Material liquid;

    @Override
    public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
        int startX = region.getCenterChunkX() << 4;
        int startZ = region.getCenterChunkZ() << 4;

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = 0; y < maxY; y++) {
                    if (region.getType(x, y, z).isAir()) {
                        region.setType(x, y, z, liquid);
                    }
                }
            }
        }
    }

}
