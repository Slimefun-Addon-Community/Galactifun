package io.github.addoncommunity.galactifun.base.aliens;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nonnull;

/**
 * A class for an alien creeper
 *
 * @author GallowsDove
 */
public final class MutantCreeper extends Alien {
    public MutantCreeper(@Nonnull CelestialWorld... worlds) {
        super("MUTANT_CREEPER", "Mutant Creeper", EntityType.CREEPER, 40, worlds);
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
        return 40;
    }

    @Override
    public int getMaxAmountInChunk(@Nonnull Chunk chunk) {
        return 1;
    }

    @Override
    public void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onHit(@Nonnull EntityDamageByEntityEvent e) {
        Creeper creeper = (Creeper) e.getEntity();
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getGameMode() != GameMode.CREATIVE) {
                creeper.setTarget((Player) e.getDamager());
            }
        }
        else if (e.getDamager() instanceof Projectile) {
            Projectile pr = (Projectile) e.getDamager();
            if (pr.getShooter() instanceof Player) {
                Player p = (Player) pr.getShooter();
                if (p.getGameMode() != GameMode.CREATIVE) {
                    creeper.setTarget(p);
                }
            }
        }
    }
}
