package io.github.addoncommunity.galactifun.core.listener;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
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

        alien = Alien.getByEntity(e.getDamager());
        if (alien != null) {
            alien.onAttack(e);
        }
    }

    @EventHandler
    public void onAlienDie(@Nonnull EntityDeathEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onDeath(e);
        }
    }

    // All aliens are fireproof
    @EventHandler
    public void onAlienCombust(@Nonnull EntityCombustEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAlienSpellCast(@Nonnull EntitySpellCastEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onCastSpell(e);
        }
    }

    @EventHandler
    public void onAlienDamage(@Nonnull EntityDamageEvent e) {
        Alien alien = Alien.getByEntity(e.getEntity());
        if (alien != null) {
            alien.onDamage(e);
        }
    }
    
}
