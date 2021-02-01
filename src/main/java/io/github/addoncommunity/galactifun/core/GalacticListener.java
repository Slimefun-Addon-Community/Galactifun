package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.mobs.Mob;
import io.github.addoncommunity.galactifun.api.universe.CelestialObject;
import io.github.addoncommunity.galactifun.core.util.Log;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Difficulty;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Listeners for celestial object handlers
 * 
 * @author Mooy1
 * @author GallowsDove
 */
public final class GalacticListener implements Listener {

    public static void setup() {
        new GalacticListener();
    }
    
    private GalacticListener() {
        PluginUtils.registerListener(this);
    }
    
    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
        CelestialObject object = GalacticRegistry.getCelestialObject(e);
        
        if (object != null) {
            object.applyWorldEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        CelestialObject object = GalacticRegistry.getCelestialObject(e);

        if (object != null) {
            object.applyWorldEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlanetRespawn(@Nonnull PlayerRespawnEvent e) {
        CelestialObject object = GalacticRegistry.getCelestialObject(e);

        if (object != null) {
            object.applyWorldEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        Entity entity = e.getEntity();
        if (!GalacticRegistry.CELESTIAL_OBJECTS.containsKey(entity.getWorld().getName())) {
            return;
        }

        if (MobManager.INSTANCE.getByEntity(entity) == null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        if (e.getWorld().getDifficulty() != Difficulty.PEACEFUL && e.getChunk().getX() == 0 && e.getChunk().getZ() == 0) {
            for (Mob mob : MobManager.INSTANCE.getRegistry().values()) {
                if (mob.canSpawn(e.getWorld())) {
                    double d = ThreadLocalRandom.current().nextDouble(100.0D);
                    Log.info("Chance generated: {} <= {}", d, mob.getChanceToSpawn(e.getChunk()));
                    if (d <= mob.getChanceToSpawn(e.getChunk())) {
                        int minX = e.getChunk().getX() >> 4;
                        int minZ = e.getChunk().getZ() >> 4;
                        int x = minX + ThreadLocalRandom.current().nextInt(16);
                        int z = minZ + ThreadLocalRandom.current().nextInt(16);
                        Log.info("Spawning {} at {},{} ({},{})", mob.getId(), x, z, e.getChunk().getX(), e.getChunk().getZ());
                        Block b = e.getWorld().getHighestBlockAt(x, z);
                        MobManager.INSTANCE.spawn(mob, b.getLocation());
                    }
                }
            }
        }
    }
}
