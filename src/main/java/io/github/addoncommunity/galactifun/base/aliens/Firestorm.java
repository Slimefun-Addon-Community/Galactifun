package io.github.addoncommunity.galactifun.base.aliens;

import lombok.NonNull;

import org.bukkit.Location;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;

public final class Firestorm extends Alien<Blaze> implements Listener {


    public Firestorm(@NonNull String id, @NonNull String name, double maxHealth, int spawnChance) {
        super(Blaze.class, id, name, maxHealth, spawnChance);

        Galactifun.inst().registerListener(this);
    }

    @Override
    protected void onShoot(ProjectileLaunchEvent e, Mob entity) {
        e.getEntity().setMetadata("electrified", new FixedMetadataValue(
                Galactifun.inst(),
                true
        ));
    }

    @Override
    protected void onDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onFireballHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if (projectile.hasMetadata("electrified")) {
            projectile.getWorld().strikeLightning(projectile.getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    private void onStrikeLightning(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            if (e.getEntityType() == EntityType.BLAZE && Galactifun.alienManager().getAlien(e.getEntity()) == null) {
                Location l = e.getEntity().getLocation();
                e.getEntity().remove();
                this.spawn(l, l.getWorld());
            }
        }
    }
}
