package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Planet;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.GenerationType;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.Random;

public final class Mars extends Planet {
    
    public Mars() {
        super("Mars", 144_610_000L, -1, 55_910_000L, SolarType.NORMAL, Atmosphere.MARS_LIKE, GenerationType.HILLY_CAVES);
        addPopulator(new BlockPopulator() {

            // boulder populator
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextBoolean() && random.nextBoolean()) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);

                    Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);

                    if (b.getType() == Material.GRANITE) return;

                    b.getRelative(BlockFace.UP).setType(Material.GRANITE);
                }
            }
        });
    }
    
    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        if (y == top) {
            return Material.RED_SAND;
        }
        if (random.nextDouble() > 0.2) {
            // 4/5 blocks are terracotta
            return Material.TERRACOTTA;
        } else {
            if (y > 15) {
                // Blue ice is the other 1/5 if y > 15
                return Material.BLUE_ICE;
            } else {
                // Otherwise iron ore
                return Material.IRON_ORE;
            }
        }
    }

    @Nonnull
    @Override
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.NETHER_WASTES;
    }

}
