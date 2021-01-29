package io.github.seggan.galactifun.core;

import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import javax.annotation.Nonnull;

/**
 * Listeners for celestial object handlers
 */
public final class CelestialObjectListener implements Listener {

    public static void setup() {
        new CelestialObjectListener();
    }
    
    private CelestialObjectListener() {
        PluginUtils.registerListener(this);
    }
    
    @EventHandler
    public void onMobSpawn(@Nonnull EntitySpawnEvent e) {
        // TODO improve
        if (e.getEntity().getWorld().getName().equals("mars")) {
            e.setCancelled(true);
        }
    }
    
}
