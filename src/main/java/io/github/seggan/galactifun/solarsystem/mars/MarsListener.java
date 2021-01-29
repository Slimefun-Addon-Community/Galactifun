package io.github.seggan.galactifun.solarsystem.mars;

import io.github.seggan.galactifun.Galactifun;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MarsListener implements Listener {

    public MarsListener() {
        Bukkit.getPluginManager().registerEvents(this, Galactifun.getInstance());
    }

    @EventHandler
    public void onMobSpawn(@Nonnull EntitySpawnEvent e) {
        if (e.getEntity().getWorld().getName().equals("mars")) {
            e.setCancelled(true);
        }
    }
    
}
