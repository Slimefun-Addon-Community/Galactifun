package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.aliens.TitanKing;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for an alien boss
 *
 * @see TitanKing
 *
 * @author Seggan
 * @author GallowsDove
 *
 */

public abstract class BossAlien extends Alien {

    private static final NamespacedKey KEY = new NamespacedKey(Galactifun.inst(), "galactifun_boss");
    private static final Map<LivingEntity, BossBar> instances = new HashMap<>();
    
    private final BossBarStyle style;
    private int tick = 0;

    public BossAlien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int health) {
        super(id, name, type, health);
        Validate.notNull(this.style = createBossBarStyle());
    }
    
    /**
     * Returns the max distance from the boss that you can see its {@link BossBar}
     *
     * @return the max distance to see the bossbar
     */
    protected int getBossBarDistance() {
        return 20;
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onSpawn(@Nonnull LivingEntity spawned) {
        BossBarStyle style = this.style;
        BossBar bossbar = Bukkit.createBossBar(KEY, style.name, style.color, style.style, style.flags);
        bossbar.setVisible(true);
        bossbar.setProgress(1.0);

        spawned.setRemoveWhenFarAway(false);

        instances.put(spawned, bossbar);
    }

    @Override
    public final void onHit(@Nonnull EntityDamageByEntityEvent e) {
        this.onBossHit(e);

        if (!e.isCancelled() && e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = getBossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / this.maxHealth);
            }
        }
    }

    @Override
    public void onDamage(@Nonnull EntityDamageEvent e) {
        if (e.getEntity() instanceof LivingEntity entity) {
            BossBar bossbar = getBossBarForEntity(entity);

            double finalHealth = entity.getHealth() - e.getFinalDamage();
            if (finalHealth > 0) {
                bossbar.setProgress(finalHealth / this.maxHealth);
            }
        }
    }

    /**
     * This method only exists so it can be called before {@link #onHit}
     *
     * @param e the event
     */
    protected void onBossHit(@Nonnull EntityDamageByEntityEvent e) {}

    @Override
    @OverridingMethodsMustInvokeSuper
    public void onDeath(@Nonnull EntityDeathEvent e) {
        BossBar bossbar = getBossBarForEntity(e.getEntity());
        bossbar.removeAll();
        instances.remove(e.getEntity());
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
    public void onMobTick(@Nonnull LivingEntity mob) {
        if (this.tick == 0) {
            Location l = mob.getLocation();
            long dist = (long) getBossBarDistance() * getBossBarDistance();

            BossBar bossbar = getBossBarForEntity(mob);
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
    protected final BossBar getBossBarForEntity(LivingEntity entity) {
        if (instances.containsKey(entity)) {
            return instances.get(entity);
        }

        BossBarStyle style = createBossBarStyle();
        BossBar bossbar = Bukkit.createBossBar(KEY, style.name, style.color, style.style, style.flags);
        bossbar.setVisible(true);
        bossbar.setProgress(entity.getHealth() / this.maxHealth);
        instances.put(entity, bossbar);
        return bossbar;
    }

    public static void removeBossBars() {
        for (BossBar bossbar: instances.values()) {
            bossbar.setVisible(false);
            bossbar.removeAll();
        }
    }

    // TODO maybe add a default style?
    @Nonnull
    protected abstract BossBarStyle createBossBarStyle();

    protected record BossBarStyle(String name, BarColor color, BarStyle style, BarFlag... flags) {
        public BossBarStyle(String name, BarColor color, BarStyle style, BarFlag... flags) {
            this.name = name;
            this.color = color;
            this.style = style;
            this.flags = flags;
        }
    }
    
}
