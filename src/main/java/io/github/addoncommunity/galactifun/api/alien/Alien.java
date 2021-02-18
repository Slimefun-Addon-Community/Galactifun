package io.github.addoncommunity.galactifun.api.alien;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.world.AlienWorld;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract class for an alien
 *
 * @author Seggan
 * @author GallowsDove
 * @author Mooy1
 */
public abstract class Alien {

    public static final Map<String, Alien> ALIENS = new HashMap<>();

    @Nullable
    public static Alien getByID(@Nonnull String id) {
        return ALIENS.get(id);
    }

    @Nullable
    public static Alien getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, Alien.KEY);
        return id == null ? null : getByID(id);
    }

    private static final NamespacedKey KEY = new NamespacedKey(Galactifun.getInstance(), "alien");

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Getter
    @Nonnull
    private final EntityType type;

    private final int health;
    
    public Alien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type,
                 int health, @Nonnull AlienWorld... worlds) {

        Validate.notNull(id);
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");
        Validate.isTrue(health > 0);

        this.id = id;
        this.name = ChatColors.color(name);
        this.health = health;
        this.type = type;

        for (AlienWorld world : worlds) {
            Validate.notNull(world);
            world.addSpecies(this);
        }

        // validation of spawn parameters
        Validate.isTrue(getChance() > 0 && getChance() <= 100);

        ALIENS.put(id, this);

    }

    public final void spawn(@Nonnull Location loc, @Nonnull World world) {
        LivingEntity entity = (LivingEntity) world.spawnEntity(loc, this.type);
        PersistentDataAPI.setString(entity, KEY, this.id);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.health);
        entity.setHealth(this.health);
        entity.setCustomName(this.name);
        entity.setCustomNameVisible(true);

        entity.setRemoveWhenFarAway(true);

        onSpawn(entity);
    }
    
    public void onSpawn(@Nonnull LivingEntity spawned) { }

    public void onMobTick(@Nonnull LivingEntity mob) { }

    /**
     * Called when the alien is hit by an entity
     *
     * @param e the event
     */
    public void onHit(@Nonnull EntityDamageByEntityEvent e) { }

    /**
     * Called when the alien hits another entity
     *
     * @param e the event
     */
    public void onAttack(@Nonnull EntityDamageByEntityEvent e) {}

    public void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    public void onTarget(@Nonnull EntityTargetEvent e) { }

    public void onDeath(@Nonnull EntityDeathEvent e) { }

    /**
     * Returns the chance for the alien to spawn per spawn attempt
     *
     * @return the chance for the alien to spawn
     */
    public abstract double getChance();

    /**
     * This will return the max possible aliens spawned per spawn attempt
     *
     * @return aliens per group
     */
    public int getMaxAliensPerGroup() {
        return 1;
    }

    /**
     * This method returns whether the alien can spawn in the given light level. By default uses
     * the {@link Zombie} light level conditions
     *
     * @param lightLevel the light level of the block the alien is attempting to spawn on
     *
     * @return {@code true} if the alien can spawn in this light level, {@code false} otherwise
     */
    public boolean canSpawnInLightLevel(int lightLevel) {
        return lightLevel <= 7;
    }
}
