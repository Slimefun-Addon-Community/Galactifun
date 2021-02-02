package io.github.addoncommunity.galactifun.api.mob;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.CelestialWorld;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Abstract class for an alien
 *
 * @author Seggan
 */
@Getter
public abstract class Alien {
    public static final NamespacedKey KEY = new NamespacedKey(Galactifun.getInstance(), "galactifun_alien");

    @Nonnull
    private final String id;
    @Nullable
    private final String name;
    private final int health;
    @Nonnull
    private final EntityType type;

    public Alien(@Nonnull String id, @Nullable String name, @Nonnull EntityType type, int health) {
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");

        this.id = id;
        this.name = name;
        this.health = health;
        this.type = type;

    }

    public final void register(@Nonnull CelestialWorld homeWorld) {
        homeWorld.getNativeSpecies().add(this);
        GalacticRegistry.register(id, this);
    }

    public final void spawn(@Nonnull Location loc) {
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
        PersistentDataAPI.setString(entity, KEY, id);

        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        entity.setHealth(health);

        if (name != null) {
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', name));
            entity.setCustomNameVisible(true);
        }

        onSpawn(entity, loc);
    }

    protected void onSpawn(@Nonnull LivingEntity spawned, @Nonnull Location loc) {
    }

    public void onMobTick(@Nonnull LivingEntity mob) {
    }

    protected void onHit(@Nonnull EntityDamageByEntityEvent e) {
    }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) {
    }

    protected void onTarget(@Nonnull EntityTargetEvent e) {
    }

    protected void onDeath(@Nonnull EntityDeathEvent e) {
    }

    public abstract double getChanceToSpawn(@Nonnull Chunk chunk);

    public abstract int getMaxAmountInChunk(@Nonnull Chunk chunk);

    public abstract boolean canSpawn(@Nonnull Chunk chunk);

}
