package io.github.addoncommunity.galactifun.base.milkyway.solarsystem;

import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.api.universe.world.WorldTerrain;
import io.github.addoncommunity.galactifun.api.universe.world.populators.BoulderPopulator;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for Mars
 *
 * @author Seggan
 * @author Mooy1
 *
 */
public final class Mars extends CelestialWorld {

    public Mars() {
        super("Mars", 233_500_000L, 55_910_000L, new Gravity(3.711), Material.RED_SAND,
                new DayCycle(1.03), WorldTerrain.HILLY_CAVERNS,
                new Atmosphere(0, false, false, false, false, World.Environment.NETHER));
    }

    @Nonnull
    @Override
    public Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
        // top 4 blocks
        if (y > top - 4) {
            return Material.RED_SAND;
        }
        if (random.nextDouble() > 0.1 || y > 40) {
            // 90% of blocks are terracotta
            return Material.TERRACOTTA;
        } else {
            if (y > 15) {
                // Blue ice is the other 10% or if y > 15
                return Material.BLUE_ICE;
            } else {
                // Otherwise iron ore
                return Material.IRON_ORE;
            }
        }
    }

    @Nonnull
    @Override
    public Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.NETHER_WASTES;
    }

    @Override
    public void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new BoulderPopulator(2, 20, Material.GRANITE, Material.RED_SAND));
    }
    
    // TODO clean up A LOT
    @Override
    public void onMobSpawn(@Nonnull CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {

            if (e.getEntityType() == EntityType.MAGMA_CUBE) {
                Martian alien = (Martian) GalacticRegistry.getAlien("MARTIAN");

                Objects.requireNonNull(alien);

                if (ThreadLocalRandom.current().nextDouble(100) <= alien.getChanceToSpawn(e.getLocation().getChunk()) &&
                    alien.canSpawn(e.getLocation().getChunk())) {
                    alien.spawn(e.getLocation());
                }
            }

            if (e.getEntityType() == EntityType.ENDERMAN) {
                MutantCreeper alien = (MutantCreeper) GalacticRegistry.getAlien("ALIEN_CREEPER");

                Objects.requireNonNull(alien);

                if (ThreadLocalRandom.current().nextDouble(100) <= alien.getChanceToSpawn(e.getLocation().getChunk()) &&
                        alien.canSpawn(e.getLocation().getChunk())) {
                    alien.spawn(e.getLocation());
                }
            }

            e.setCancelled(true);
        }
    }
    
}
