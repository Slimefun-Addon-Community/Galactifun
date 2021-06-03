package io.github.addoncommunity.galactifun.api.universe.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.entity.ai.MobGoals;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.goals.AbstractGoal;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.apache.commons.lang.Validate;

/**
 * Abstract class for an alien
 *
 * @author Seggan
 * @author GallowsDove
 * @author Mooy1
 * @see Martian
 */
public abstract class Alien<T extends Mob> {

    public static final Map<String, Alien<?>> ALIENS = new HashMap<>();
    private static final NamespacedKey KEY = Galactifun.inst().getKey("alien");

    static {
        Galactifun.inst().registerListener(new Listener() {

            @EventHandler
            public void onAlienTarget(@Nonnull EntityTargetEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onTarget(e);
                }
            }

            @EventHandler
            public void onAlienInteract(@Nonnull PlayerInteractEntityEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getRightClicked());
                if (alien != null) {
                    alien.onInteract(e);
                }
            }

            @EventHandler
            public void onAlienHit(@Nonnull EntityDamageByEntityEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getEntity());
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
                Alien<?> alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onDeath(e);
                }
            }

            @EventHandler
            public void onAlienCombust(@Nonnull EntityCombustEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    e.setCancelled(true);
                }
            }

            @EventHandler
            public void onAlienCastSpell(@Nonnull EntitySpellCastEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onCastSpell(e);
                }
            }

            @EventHandler
            public void onAlienDamage(@Nonnull EntityDamageEvent e) {
                Alien<?> alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onDamage(e);
                }
            }
        });
    }

    @Getter
    @Nonnull
    private final Class<T> clazz;
    protected final int maxHealth;
    @Nonnull
    private final String id;
    @Nonnull
    private final String name;

    public Alien(@Nonnull Class<T> clazz, @Nonnull String id, @Nonnull String name, int maxHealth) {
        this.clazz = clazz;

        Validate.notNull(this.id = id);
        Validate.notNull(this.name = ChatColors.color(name));
        Validate.isTrue((this.maxHealth = maxHealth) > 0);
        Validate.isTrue(getSpawnChance() > 0 && getSpawnChance() <= 100);
        Validate.notNull(getSpawnOffset());

        ALIENS.put(id, this);

    }

    @Nullable
    public static Alien<?> getByID(@Nonnull String id) {
        return ALIENS.get(id);
    }

    @Nullable
    public static Alien<?> getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, Alien.KEY);
        return id == null ? null : getByID(id);
    }

    @Nonnull
    public final T spawn(@Nonnull Location loc, @Nonnull World world) {
        T mob = world.spawn(loc, clazz);
        PersistentDataAPI.setString(mob, KEY, this.id);

        Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.maxHealth);
        mob.setHealth(this.maxHealth);
        mob.setCustomName(this.name);
        mob.setCustomNameVisible(true);

        mob.setRemoveWhenFarAway(true);

        Map<AbstractGoal<T>, Integer> goals = new HashMap<>();
        this.getGoals(goals, mob);
        if (!goals.isEmpty()) {
            MobGoals mobGoals = Bukkit.getMobGoals();
            mobGoals.removeAllGoals(mob);
            for (Map.Entry<AbstractGoal<T>, Integer> goal : goals.entrySet()) {
                mobGoals.addGoal(mob, goal.getValue(), goal.getKey());
            }
        }

        onSpawn(mob);

        return mob;
    }

    protected void onSpawn(@Nonnull T spawned) {
    }

    protected void onUniqueTick() {
    }

    protected void onMobTick(@Nonnull Mob mob) {
    }

    protected void onHit(@Nonnull EntityDamageByEntityEvent e) {
    }

    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) {
    }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) {
    }

    protected void onTarget(@Nonnull EntityTargetEvent e) {
    }

    protected void onDeath(@Nonnull EntityDeathEvent e) {
    }

    protected void onCastSpell(EntitySpellCastEvent e) {
    }

    protected void onDamage(EntityDamageEvent e) {
    }

    /**
     * Gets the AI of the Alien. The map is a map of a mob goal and its priority
     *
     * @param goals a map of the Alien's goals, add the goals to this or add nothing for default AI
     * @param mob the mob
     */
    protected void getGoals(@Nonnull Map<AbstractGoal<T>, Integer> goals, @Nonnull T mob) {
    }

    /**
     * Returns the chance for the alien to spawn per spawn attempt
     *
     * @return the chance for the alien to spawn
     */
    protected abstract int getSpawnChance();

    /**
     * This will return the max possible aliens spawned per spawn attempt
     *
     * @return aliens per group
     */
    protected int getMaxAliensPerGroup() {
        return 1;
    }

    /**
     * This method returns whether the alien can spawn in the given light level. By default uses
     * the {@link Zombie} light level conditions
     *
     * @param lightLevel the light level of the block the alien is attempting to spawn on
     * @return {@code true} if the alien can spawn in this light level, {@code false} otherwise
     */
    protected boolean getSpawnInLightLevel(int lightLevel) {
        return lightLevel <= 7;
    }

    protected Vector getSpawnOffset() {
        return new Vector();
    }

}
