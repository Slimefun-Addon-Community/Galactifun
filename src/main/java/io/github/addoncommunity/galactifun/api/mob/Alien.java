package io.github.addoncommunity.galactifun.api.mob;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
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
    private final int health;
    @Getter
    private final int chance;
    @Nonnull
    private final EntityType type;

    public Alien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int chance, int health, @Nonnull CelestialWorld... worlds) {
        Validate.notNull(id);
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");

        this.id = id;
        this.chance = chance;
        this.name = ChatColors.color(name);
        this.health = health;
        this.type = type;
        
        for (CelestialWorld world : worlds) {
            world.addSpecies(this);
        }

        ALIENS.put(id, this);

    }

    public final void spawn(@Nonnull Location loc) {
        Objects.requireNonNull(loc.getWorld());
        
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, this.type);
        PersistentDataAPI.setString(entity, KEY, this.id);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.health);
        entity.setHealth(this.health);
        entity.setCustomName(this.name);
        entity.setCustomNameVisible(true);

        onSpawn(entity, loc);
    }

    public boolean canSpawn(@Nonnull Location l) {
        return countInChunk(l.getChunk()) < getMaxAmountInChunk(l.getChunk());
    }

    public void onSpawn(@Nonnull LivingEntity spawned, @Nonnull Location loc) { }

    public void onMobTick(@Nonnull LivingEntity mob) { }

    public void onHit(@Nonnull EntityDamageByEntityEvent e) { }

    public void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    public void onTarget(@Nonnull EntityTargetEvent e) { }

    public void onDeath(@Nonnull EntityDeathEvent e) { }
    
    protected abstract int getMaxAmountInChunk(@Nonnull Chunk chunk);
    
    protected final int countInChunk(@Nonnull Chunk chunk) {
        int i = 0;
        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof LivingEntity) {
                Alien alien = getByEntity(entity);
                if (alien != null && alien.id.equals(this.id)) {
                    i++;
                }
            }
        }
        return i;
    }
    
}
