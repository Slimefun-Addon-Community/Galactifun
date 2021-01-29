package io.github.seggan.galactifun.base.milkyway.solarsystem;

import io.github.seggan.galactifun.api.Planet;
import io.github.seggan.galactifun.api.attributes.Atmosphere;
import io.github.seggan.galactifun.api.attributes.SolarType;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mars extends Planet {
    
    public Mars() {
        super("Mars", 144_610_000L, -1, 55_910_000L, SolarType.NORMAL, Atmosphere.MARS_LIKE);
    }
    
    @Override
    protected void generateChunk(@Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {
        // stuff
    }

    @Nonnull
    @Override
    public List<BlockPopulator> getDefaultPopulators(@Nonnull World world) {
        return Collections.singletonList(new BlockPopulator() {
            
            // boulder populator
            @Override
            public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
                if (random.nextBoolean()) {
                    int x = random.nextInt(16);
                    int z = random.nextInt(16);

                    Block b = world.getHighestBlockAt(x * chunk.getX(), z * chunk.getZ());

                    if (b.getType() == Material.GRANITE) return;

                    b.getRelative(BlockFace.UP).setType(Material.GRANITE);

                }
            }
        });
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }
    
}
