package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.Moon;
import io.github.addoncommunity.galactifun.api.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.attributes.SolarType;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

public class TheMoon extends Moon {
    
    // MIN_HEIGHT + MAX_DEVIATION is the max height the world would generate
    private static final double MAX_DEVIATION = 20;

    // The minimum height for the noise generator
    private static final double MIN_HEIGHT = 45;
    
    public TheMoon() {
        super("The Moon", 238_900, -3, 14_600_000L, SolarType.NORMAL, Atmosphere.MOON_LIKE);
    }

    @Override
    protected void generateChunk(@Nonnull World world, @Nonnull ChunkData chunk, @Nonnull Random random, @Nonnull BiomeGrid biome, int chunkX, int chunkZ) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(random, 8);
        generator.setScale(0.01);
        int height;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                chunk.setBlock(x, 0, z, Material.BEDROCK);

                height = (int) (MIN_HEIGHT + MAX_DEVIATION * generator.noise(
                        startX + x, startZ + z, 0.5D, 0.5D, true)
                );
                
                for (int y = 1 ; y < height ; y++) {
                    if (random.nextDouble() > 0.04 || y > 40) {
                        chunk.setBlock(x, y, z, Material.ANDESITE);
                    } else {
                        chunk.setBlock(x, y, z, Material.GOLD_ORE);
                    }
                }
                
                for (int y = 0 ; y < 256 ; y++) {
                    biome.setBiome(x, y, z, Biome.BADLANDS);
                }
            }
        }
    }

}
