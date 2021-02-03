package io.github.addoncommunity.galactifun.api.mob;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO improve mob spawning

/**
 * Abstract class for an alien
 *
 * @author Seggan
 * @author GallowsDove
 */
public abstract class Alien {
    
    public static final NamespacedKey KEY = new NamespacedKey(Galactifun.getInstance(), "alien");

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    private final int health;
    @Nonnull
    private final EntityType type;
    @Nonnull
    private final List<CelestialWorld> worlds;

    public Alien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type, int health, @Nonnull CelestialWorld... worlds) {
        Validate.notNull(id);
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");

        this.id = id;
        this.name = ChatColors.color(name);
        this.health = health;
        this.type = type;
        this.worlds = new ArrayList<>(Arrays.asList(worlds));

        for (CelestialWorld world : this.worlds) {
            world.addSpecies(this);
        }

        GalacticRegistry.register(this.id, this);

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

    public boolean canSpawn(@Nonnull Chunk chunk) {
        CelestialWorld world = GalacticRegistry.getCelestialWorld(chunk.getWorld());
        if (world != null) {
            return (this.worlds.contains(world) && countInChunk(chunk) < getMaxAmountInChunk(chunk));
        }
        return false;
    }

    public void onSpawn(@Nonnull LivingEntity spawned, @Nonnull Location loc) { }

    public void onMobTick(@Nonnull LivingEntity mob) { }

    public void onHit(@Nonnull EntityDamageByEntityEvent e) { }

    public void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    public void onTarget(@Nonnull EntityTargetEvent e) { }

    public void onDeath(@Nonnull EntityDeathEvent e) { }

    public abstract double getChanceToSpawn(@Nonnull Chunk chunk);

    public abstract int getMaxAmountInChunk(@Nonnull Chunk chunk);

    public int countInChunk(@Nonnull Chunk chunk) {
        int i = 0;
        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof LivingEntity) {
                Alien alien = GalacticRegistry.getAlien(entity);
                if (alien != null && alien.id.equals(this.id)) {
                    i++;
                }
            }
        }

        return i;
    }
    
}
