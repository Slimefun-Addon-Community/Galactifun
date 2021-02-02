package io.github.addoncommunity.galactifun.api.mob;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class AlienListener implements Listener {

    public AlienListener() {
        Bukkit.getPluginManager().registerEvents(this, Galactifun.getInstance());
    }

    @EventHandler
    public final void onAlienTarget(EntityTargetEvent e) {
        Alien alien = GalacticRegistry.getAlien(e.getEntity());
        if (alien != null) {
            alien.onTarget(e);
        }
    }

    @EventHandler
    public final void onAlienInteract(PlayerInteractEntityEvent e) {
        Alien alien = GalacticRegistry.getAlien(e.getRightClicked());
        if (alien != null) {
            alien.onInteract(e);
        }
    }

    @EventHandler
    public final void onAlienHit(EntityDamageByEntityEvent e) {
        Alien alien = GalacticRegistry.getAlien(e.getEntity());
        if (alien != null) {
            alien.onHit(e);
        }
    }

    @EventHandler
    public final void onAlienDie(EntityDeathEvent e) {
        Alien alien = GalacticRegistry.getAlien(e.getEntity());
        if (alien != null) {
            alien.onDeath(e);
        }
    }
}
