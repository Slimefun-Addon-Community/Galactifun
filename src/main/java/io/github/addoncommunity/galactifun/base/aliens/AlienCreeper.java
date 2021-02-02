package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.api.universe.CelestialWorld;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nonnull;

public class AlienCreeper extends Alien {
    public AlienCreeper(@Nonnull CelestialWorld... worlds) {
        super("ALIEN_CREEPER", "Alien Creeper", EntityType.CREEPER, 40, worlds);


    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned, @Nonnull Location loc) {
        Creeper spawnedCreeper = (Creeper) spawned;
        spawned.setCanPickupItems(false);
        spawned.setRemoveWhenFarAway(true);
        spawnedCreeper.setPowered(true);
    }

    @Override
    public double getChanceToSpawn(@Nonnull Chunk chunk) {
        return 2;
    }

    @Override
    public int getMaxAmountInChunk(@Nonnull Chunk chunk) {
        return 1;
    }

    @Override
    protected void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    protected void onHit(@Nonnull EntityDamageByEntityEvent e) {
        Creeper creeper = (Creeper) e.getEntity();
        if (e.getDamager() instanceof Player) {
            creeper.setTarget((Player) e.getDamager());
        }
        else if (e.getDamager() instanceof Projectile) {
            Projectile p = (Projectile) e.getDamager();
            if (p.getShooter() instanceof Player) {
                creeper.setTarget((Player) p.getShooter());
            }
        }
    }

}
