package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.mars;

import io.github.addoncommunity.galactifun.api.mobs.Mob;
import io.github.addoncommunity.galactifun.api.universe.Planet;
import io.github.addoncommunity.galactifun.api.universe.attributes.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.DayCycle;
import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.attributes.Terrain;
import io.github.addoncommunity.galactifun.api.universe.populators.GalacticBoulderPopulator;
import io.github.addoncommunity.galactifun.core.MobManager;
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
public final class Mars extends Planet {

    public Mars() {
        super("Mars", 144_610_000L, 55_910_000L, new Gravity(.378), new DayCycle(1.03),
                new Atmosphere(0, false, false, false, false, World.Environment.NETHER),
                Terrain.HILLY_CAVERNS);

        new Martian().register();
    }

    @Nonnull
    @Override
    protected Material generateBlock(@Nonnull Random random, int top, int x, int y, int z) {
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
    protected Biome getBiome(@Nonnull Random random, int chunkX, int chunkZ) {
        return Biome.NETHER_WASTES;
    }

    @Override
    protected void getPopulators(@Nonnull List<BlockPopulator> populators) {
        populators.add(new GalacticBoulderPopulator(2, 20, Material.GRANITE, Material.RED_SAND));
    }

    @Override
    protected void onMobSpawn(@Nonnull CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            if (e.getEntityType() == EntityType.MAGMA_CUBE) {
                // Shouldn't be null
                Mob martian = Objects.requireNonNull(MobManager.INSTANCE.getById("MARTIAN"));


                if (MobManager.countInChunk(e.getLocation().getChunk(), martian) < martian.getMaxAmountInChunk(e.getLocation().getChunk()) &&
                    ThreadLocalRandom.current().nextDouble(100) <= martian.getChanceToSpawn(e.getLocation().getChunk())) {
                    MobManager.INSTANCE.spawn(martian, e.getLocation());
                }
            }

            e.setCancelled(true);
        }
    }
}
