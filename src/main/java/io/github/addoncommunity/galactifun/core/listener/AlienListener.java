package io.github.addoncommunity.galactifun.core.listener;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import javax.annotation.Nonnull;

/**
 * A listener class for Aliens
 *
 * @author Seggan
 */
public final class AlienListener implements Listener {
    
    public AlienListener() {
        PluginUtils.registerListener(this);
    }

    @EventHandler
    public void onAlienTarget(@Nonnull EntityTargetEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onTarget(e);
        }
    }

    @EventHandler
    public void onAlienInteract(@Nonnull PlayerInteractEntityEvent e) {
        Alien alien = Alien.getByEntity(e.getRightClicked());
        if (alien != null) {
            alien.onInteract(e);
        }
    }

    @EventHandler
    public void onAlienHit(@Nonnull EntityDamageByEntityEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onHit(e);
        }
    }

    @EventHandler
    public void onAlienDie(@Nonnull EntityDeathEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onDeath(e);
        }
    }
    
}
