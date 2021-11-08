package io.github.addoncommunity.galactifun.api.aliens;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import io.github.addoncommunity.galactifun.core.managers.AlienManager;

/**
 * Abstract class for an alien boss
 *
 * @author Seggan
 * @author GallowsDove
 * @see TitanKing
 */
public abstract class BossAlien<T extends Mob> extends Alien<T> {

    private static final int BOSS_BAR_DISTANCE_SQUARED = 400;

    private final Map<LivingEntity, BossBar> bossBars = new HashMap<>();
    private final BossBarStyle style;
    private int tick = 0;

    @ParametersAreNonnullByDefault
    public BossAlien(Class<T> clazz, String id, String name, double maxHealth, double spawnChance, @Nonnull BossBarStyle style) {
        super(clazz, id, name, maxHealth, spawnChance);
        this.style = style;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onSpawn(@Nonnull T spawned) {
        BossBar bossbar = this.style.create(Galactifun.alienManager().bossKey(), name());
        bossbar.setVisible(true);
        bossbar.setProgress(1.0);
        spawned.setRemoveWhenFarAway(false);
        this.bossBars.put(spawned, bossbar);
    }

    @Override
    public final void onHit(@Nonnull EntityDamageByEntityEvent e) {
        this.onBossHit(e);

        if (!e.isCancelled() && e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = getBossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / maxHealth());
            }
        }
    }

    @Override
    public void onDamage(@Nonnull EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = getBossBarForEntity(entity);

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
        BossBar bossbar = getBossBarForEntity(e.getEntity());
        bossbar.removeAll();
        this.bossBars.remove(e.getEntity());
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onUniqueTick() {
        this.tick++;
        if (this.tick >= 10) {
            this.tick = 0;
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onTick(@Nonnull Mob mob) {
        if (this.tick == 0) {
            Location l = mob.getLocation();

            BossBar bossbar = getBossBarForEntity(mob);
            List<Player> players = bossbar.getPlayers();

            for (Player player : mob.getWorld().getPlayers()) {
                double distSquared = l.distanceSquared(player.getLocation());

                if (distSquared <= BOSS_BAR_DISTANCE_SQUARED) {
                    /*
                    cannot merge nested if, because if players contains the player,
                    the player is removed, causing a flickering of the bossbar
                     */
                    if (!players.contains(player)) {
                        bossbar.addPlayer(player);
                    }
                } else {
                    bossbar.removePlayer(player);
                }
            }

            for (Player player : players) {
                if (!player.getWorld().equals(mob.getWorld())) {
                    bossbar.removePlayer(player);
                }
            }
        }
    }

    @Nonnull
    protected final BossBar getBossBarForEntity(LivingEntity entity) {
        AlienManager manager = Galactifun.alienManager();
        BossBar bossbar = this.bossBars.get(entity);
        if (bossbar != null) {
            return bossbar;
        }

        bossbar = this.style.create(manager.bossKey(), name());
        bossbar.setVisible(true);
        bossbar.setProgress(entity.getHealth() / maxHealth());
        this.bossBars.put(entity, bossbar);
        return bossbar;
    }

    public void removeBossBars() {
        for (BossBar bossbar : this.bossBars.values()) {
            bossbar.setVisible(false);
            bossbar.removeAll();
        }
    }

}
