package io.github.addoncommunity.galactifun.base.aliens;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.GameMode;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

/**
 * A class for a charged alien creeper, passive until attacked
 *
 * @author GallowsDove
 * @author Mooy1
 */
public final class MutantCreeper extends Alien<Creeper> {

    public MutantCreeper(String id, String name, double maxHealth, double spawnChance) {
        super(Creeper.class, id, name, maxHealth, spawnChance);
    }

    @Override
    public void onSpawn(@Nonnull Creeper spawned) {
        spawned.setPowered(true);
    }

    @Override
    public void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new SlimefunItemStack(BaseMats.MUNPOWDER, ThreadLocalRandom.current().nextInt(2)));
    }

    @Override
    public void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    public void onHit(@Nonnull EntityDamageByEntityEvent e) {
        Creeper creeper = (Creeper) e.getEntity();
        if (e.getDamager() instanceof Player p) {
            if (p.getGameMode() != GameMode.CREATIVE) {
                creeper.setTarget((Player) e.getDamager());
            }
        } else if (e.getDamager() instanceof Projectile pr) {
            if (pr.getShooter() instanceof Player p) {
                if (p.getGameMode() != GameMode.CREATIVE) {
                    creeper.setTarget(p);
                }
            }
        }
    }

}
