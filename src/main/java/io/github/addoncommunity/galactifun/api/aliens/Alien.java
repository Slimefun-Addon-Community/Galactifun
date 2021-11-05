package io.github.addoncommunity.galactifun.api.aliens;

import java.util.Objects;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.core.managers.AlienManager;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;

/**
 * Abstract class for an alien
 *
 * @author Seggan
 * @author GallowsDove
 * @author Mooy1
 * @see Martian
 */
@Getter
public class Alien<T extends Mob> {

    private final Class<T> clazz;
    private final String id;
    private final String name;
    private final double spawnChance;
    private final double maxHealth;
    private AlienManager alienManager;

    @ParametersAreNonnullByDefault
    public Alien(@Nonnull Class<T> clazz, @Nonnull String id, @Nonnull String name, double maxHealth, double spawnChance) {
        Validate.isTrue(maxHealth > 0);
        Validate.isTrue(spawnChance > 0 && spawnChance <= 100);

        this.clazz = clazz;
        this.id = id;
        this.name = ChatColors.color(name);
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
    }

    @Nonnull
    public final T spawn(@Nonnull Location loc, @Nonnull World world) {
        T mob = world.spawn(loc, this.clazz);

        PersistentDataAPI.setString(mob, this.alienManager.key(), this.id);

        Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.maxHealth);
        mob.setHealth(this.maxHealth);
        mob.setCustomName(this.name);
        mob.setCustomNameVisible(true);
        mob.setRemoveWhenFarAway(true);

        onSpawn(mob);
        return mob;
    }

    public final void register(@Nonnull AlienManager alienManager) {
        if (isRegistered()) {
            throw new IllegalStateException("Alien already registered!");
        }
        this.alienManager = alienManager;
        alienManager.register(this);
    }

    public final boolean isRegistered() {
        return this.alienManager != null;
    }

    public final int attemptSpawn(Random rand, World world) {
        int spawned = 0;
        for (Chunk chunk : world.getLoadedChunks()) {
            if (rand.nextDouble() * 100 > this.spawnChance) {
                continue;
            }

            int x = rand.nextInt(16) + (chunk.getX() << 4);
            int z = rand.nextInt(16) + (chunk.getZ() << 4);
            Block b = world.getHighestBlockAt(x, z).getRelative(0, 1, 0);

            // currently doesn't allow for aquatic aliens
            if (b.getType().isAir() && canSpawnInLightLevel(b.getLightLevel())) {
                spawn(b.getLocation().add(0, getSpawnHeightOffset(), 0), world);
                spawned++;
            }
        }
        return spawned;
    }

    public final void onEntityTick(@Nonnull LivingEntity mob) {
        onTick(this.clazz.cast(mob));
    }

    //@formatter:off

    public void onSpawn(@Nonnull T spawned) { }

    public void onUniqueTick() { }

    public void onTick(@Nonnull T mob) { }

    public void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    public void onHit(@Nonnull EntityDamageByEntityEvent e) { }

    public void onAttack(@Nonnull EntityDamageByEntityEvent e) { }

    public void onTarget(@Nonnull EntityTargetEvent e) { }

    public void onDeath(@Nonnull EntityDeathEvent e) { }

    public void onCastSpell(@Nonnull EntitySpellCastEvent e) { }

    public void onDamage(@Nonnull EntityDamageEvent e) { }

    public void onShoot(@Nonnull ProjectileLaunchEvent e) { }

    //@formatter:on

    /**
     * This method returns whether the alien can spawn in the given light level. By default uses
     * the {@link Zombie} light level conditions
     *
     * @param lightLevel the light level of the block the alien is attempting to spawn on
     *
     * @return {@code true} if the alien can spawn in this light level, {@code false} otherwise
     */
    protected boolean canSpawnInLightLevel(int lightLevel) {
        return lightLevel <= 7;
    }

    protected double getSpawnHeightOffset() {
        return 0;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
