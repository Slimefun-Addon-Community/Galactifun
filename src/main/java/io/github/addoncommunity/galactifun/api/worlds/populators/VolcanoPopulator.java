package io.github.addoncommunity.galactifun.api.worlds.populators;

import java.util.Random;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

/**
 * Volcano populator
 *
 * @author Seggan
 * @author Mooy1
 */
@AllArgsConstructor
public class VolcanoPopulator extends BlockPopulator {

    private final int minY;
    private final Material belowLiquid;
    private final Material liquid;

    @Override
    public void populate(@Nonnull WorldInfo worldInfo, @Nonnull Random random, int cx, int cz, @Nonnull LimitedRegion region) {
        int startX = region.getCenterChunkX() << 4;
        int startZ = region.getCenterChunkZ() << 4;

        int bx = startX;
        int by = 0;
        int bz = startZ;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < worldInfo.getMaxHeight(); y++) {
                    int realX = startX + x;
                    int realZ = startZ + z;

                    if (region.getType(realX, y, realZ).isAir()) {
                        if (y > by && y >= minY) {
                            bx = realX;
                            by = y;
                            bz = realZ;
                        }
                        break;
                    }
                }
            }
        }

        if (by >= minY) {
            region.setType(bx, by, bz, liquid);
            region.scheduleFluidUpdate(bx, by, bz);
            for (int y = 7; y > 0; y--) {
                region.setType(bx, by - y, bz, belowLiquid);
            }
        }
    }

}
