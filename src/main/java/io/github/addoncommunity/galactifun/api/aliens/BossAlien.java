package io.github.addoncommunity.galactifun.api.aliens;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.aliens.TitanKing;

/**
 * Abstract class for an alien boss
 *
 * @author Seggan
 * @author GallowsDove
 * @see TitanKing
 */
// TODO cleanup bossbar stuff
public abstract class BossAlien<T extends Mob> extends Alien<T> {

    private final BossBarStyle style;
    private int tick = 0;

    @ParametersAreNonnullByDefault
    public BossAlien(Class<T> clazz, String id, String name, double maxHealth, int spawnChance, @Nonnull BossBarStyle style) {
        super(clazz, id, name, maxHealth, spawnChance);
        this.style = style;
    }

    /**
     * Returns the max distance from the boss that you can see its {@link BossBar}
     *
     * @return the max distance to see the bossbar
     */
    protected int BossBarDistance() {
        return 20;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onSpawn(@Nonnull T spawned) {
        BossBar bossbar = this.style.create(Galactifun.alienManager().bossKey(), name());
        bossbar.setVisible(true);
        bossbar.setProgress(1.0);
        spawned.setRemoveWhenFarAway(false);
        Galactifun.alienManager().bossInstances().put(spawned, bossbar);
    }

    @Override
    public final void onHit(@Nonnull EntityDamageByEntityEvent e) {
        this.onBossHit(e);

        if (!e.isCancelled() && e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = BossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / maxHealth());
            }
        }
    }

    @Override
    public void onDamage(@Nonnull EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = BossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / maxHealth());
            }
        }
    }

    /**
     * This method only exists so it can be called before {@link #onHit}
     *
     * @param e the event
     */
    protected void onBossHit(@Nonnull EntityDamageByEntityEvent e) {
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onDeath(@Nonnull EntityDeathEvent e) {
        BossBar bossbar = BossBarForEntity(e.getEntity());
        bossbar.removeAll();
        Galactifun.alienManager().bossInstances().remove(e.getEntity());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onUniqueTick() {
        this.tick++;
        if (this.tick >= 10) {
            this.tick = 0;
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onMobTick(@Nonnull Mob mob) {
        if (this.tick == 0) {
            Location l = mob.getLocation();
            long dist = (long) BossBarDistance() * BossBarDistance();

            BossBar bossbar = BossBarForEntity(mob);
            List<Player> players = bossbar.getPlayers();

            for (Player player : mob.getWorld().getPlayers()) {
                double distSquared = l.distanceSquared(player.getLocation());

                if (distSquared <= dist && !players.contains(player)) {
                    bossbar.addPlayer(player);
                } else if (distSquared > dist && players.contains(player)) {
                    bossbar.removePlayer(player);
                }
            }
        }
    }

    @Nonnull
    protected final BossBar BossBarForEntity(LivingEntity entity) {
        AlienManager manager = Galactifun.alienManager();
        BossBar bossbar = manager.bossInstances().get(entity);
        if (bossbar != null) {
            return bossbar;
        }

        bossbar = this.style.create(manager.bossKey(), name());
        bossbar.setVisible(true);
        bossbar.setProgress(entity.getHealth() / maxHealth());
        manager.bossInstances().put(entity, bossbar);
        return bossbar;
    }

    public static void removeBossBars() {
        for (BossBar bossbar : Galactifun.alienManager().bossInstances().values()) {
            bossbar.setVisible(false);
            bossbar.removeAll();
        }
    }

}
