package io.github.addoncommunity.galactifun.api.universe.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;

/**
 * Abstract class for an alien
 * 
 * @see Martian
 *
 * @author Seggan
 * @author GallowsDove
 * @author Mooy1
 *
 */
public abstract class Alien implements Listener {

    private static final Map<String, Alien> ALIENS = new HashMap<>();

    @Nullable
    public static Alien getByID(@Nonnull String id) {
        return ALIENS.get(id);
    }

    @Nullable
    public static Alien getByEntity(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, Alien.KEY);
        return id == null ? null : getByID(id);
    }

    private static final NamespacedKey KEY = Galactifun.inst().getKey("alien");

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Getter
    @Nonnull
    private final EntityType type;
    protected final int maxHealth;
    
    public Alien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int maxHealth) {
        
        Validate.notNull(this.id = id);
        Validate.notNull(this.name = ChatColors.color(name));
        Validate.notNull(this.type = type);
        Validate.isTrue(type.isAlive());
        Validate.isTrue((this.maxHealth = maxHealth) > 0);
        Validate.isTrue(getSpawnChance() > 0 && getSpawnChance() <= 100);
        Validate.notNull(getSpawnOffset());

        ALIENS.put(id, this);

    }

    @Nonnull
    public final LivingEntity spawn(@Nonnull Location loc, @Nonnull World world) {
        LivingEntity entity = (LivingEntity) world.spawnEntity(loc, this.type);
        PersistentDataAPI.setString(entity, KEY, this.id);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.maxHealth);
        entity.setHealth(this.maxHealth);
        entity.setCustomName(this.name);
        entity.setCustomNameVisible(true);
        entity.setRemoveWhenFarAway(true);

        onSpawn(entity);

        return entity;
    }

    @SuppressWarnings("unchecked")
    public <E extends Event> Alien addHandler(Class<E> eventClass, Function<E, Entity> alienAccessor, Consumer<E> eventHandler) {
        Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.HIGH, (listener, event) -> {
            if (Alien.getByEntity(alienAccessor.apply((E) event)) == this) {
                eventHandler.accept((E) event);
            }
        }, Galactifun.inst(), true);
        return this;
    }

    protected void onUniqueTick() { }

    protected void onMobTick(@Nonnull LivingEntity mob) { }

    protected void onSpawn(@Nonnull LivingEntity entity) { }

    /**
     * Returns the chance for the alien to spawn per spawn attempt
     *
     * @return the chance for the alien to spawn
     */
    protected int getSpawnChance() {
        return 1;
    }

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
     *
     * @return {@code true} if the alien can spawn in this light level, {@code false} otherwise
     */
    protected boolean getSpawnInLightLevel(int lightLevel) {
        return lightLevel <= 7;
    }

    protected Vector getSpawnOffset() {
        return new Vector();
    }

}
