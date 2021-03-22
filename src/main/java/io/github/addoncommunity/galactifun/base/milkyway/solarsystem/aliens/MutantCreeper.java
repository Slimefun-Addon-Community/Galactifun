package io.github.addoncommunity.galactifun.base.milkyway.solarsystem.aliens;

import io.github.addoncommunity.galactifun.api.universe.world.Alien;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import javax.annotation.Nonnull;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A class for a charged alien creeper, passive until attacked
 *
 * @author GallowsDove
 * @author Mooy1
 */
public final class MutantCreeper extends Alien {
    
    public MutantCreeper() {
        super("MUTANT_CREEPER", "Mutant Creeper", EntityType.CREEPER, 40);
    }

    @Override
    public void onSpawn(@Nonnull LivingEntity spawned) {
        ((Creeper) spawned).setPowered(true);
    }

    @Override
    protected void onDeath(@Nonnull EntityDeathEvent e) {
        e.getDrops().clear();
        e.getDrops().add(new SlimefunItemStack(BaseMats.MUNPOWDER, ThreadLocalRandom.current().nextInt(2)));
    }

    @Override
    public void onTarget(@Nonnull EntityTargetEvent e) {
        e.setCancelled(true);
    }

    @Override
    protected int getSpawnChance() {
        return 40;
    }

    @Override
    public void onHit(@Nonnull EntityDamageByEntityEvent e) {
        Creeper creeper = (Creeper) e.getEntity();
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (p.getGameMode() != GameMode.CREATIVE) {
                creeper.setTarget((Player) e.getDamager());
            }
        } else if (e.getDamager() instanceof Projectile) {
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
