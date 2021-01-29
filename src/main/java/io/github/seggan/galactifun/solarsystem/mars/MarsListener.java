package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.Galactifun;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

class MarsListener implements Listener {

    public MarsListener() {
        Bukkit.getPluginManager().registerEvents(this, Galactifun.getInstance());
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        if (e.getLocation().getWorld().getName().equals("mars")) {
            e.setCancelled(true);
        }
    }
}
