package io.github.addoncommunity.galactifun.api.aliens;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.MobGoals;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;

/**
 * Abstract class for an alien
 *
 * @author Seggan
 * @author GallowsDove
 * @author Mooy1
 *
 * @see Martian
 */
@Getter
public abstract class Alien<T extends Mob> {

    private final Class<T> clazz;
    private final String id;
    private final String name;

    public Alien(@Nonnull Class<T> clazz, @Nonnull String id, @Nonnull String name) {
        this.clazz = clazz;
        Validate.notNull(this.id = id);
        Validate.notNull(this.name = ChatColors.color(name));
        Validate.isTrue(getMaxHealth() > 0);
        Validate.isTrue(getSpawnChance() > 0 && getSpawnChance() <= 100);
        Validate.notNull(getSpawnOffset());

        Galactifun.inst().getAlienManager().register(this);
    }

    @Nonnull
    public final T spawn(@Nonnull Location loc, @Nonnull World world) {
        T mob = world.spawn(loc, this.clazz);

        // TODO better way to access key
        PersistentDataAPI.setString(mob, Galactifun.inst().getAlienManager().getKey(), this.id);

        Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(getMaxHealth());
        mob.setHealth(getMaxHealth());
        mob.setCustomName(this.name);
        mob.setCustomNameVisible(true);
        mob.setRemoveWhenFarAway(true);

        Map<Goal<T>, Integer> goals = this.getGoals();
        if (!goals.isEmpty()) {
            MobGoals mobGoals = Bukkit.getMobGoals();
            mobGoals.removeAllGoals(mob);
            for (Map.Entry<Goal<T>, Integer> goal : goals.entrySet()) {
                mobGoals.addGoal(mob, goal.getValue(), goal.getKey());
            }
        }

        onSpawn(mob);
        return mob;
    }

    protected void onSpawn(@Nonnull T spawned) { }

    protected void onUniqueTick() { }

    protected void onMobTick(@Nonnull Mob mob) { }

    public final int attemptSpawn(Random rand, World world) {
        int spawned = 0;
        for (Chunk chunk : world.getLoadedChunks()) {
            if (rand.nextInt(100) > getSpawnChance() || spawned >= getMaxAliensPerGroup()) {
                break;
            }

            int x = rand.nextInt(16) + chunk.getX() << 4;
            int z = rand.nextInt(16) + chunk.getZ() << 4;
            Block b = world.getHighestBlockAt(x, z).getRelative(0, 1, 0);

            // currently doesn't allow for aquatic aliens
            if (b.getType().isAir() && getSpawnInLightLevel(b.getLightLevel())) {
                spawn(b.getLocation().add(getSpawnOffset()), world);
                spawned++;
            }
        }
        return spawned;
    }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    protected void onHit(@Nonnull EntityDamageByEntityEvent e) { }
    
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) { }

    protected void onTarget(@Nonnull EntityTargetEvent e) { }

    protected void onDeath(@Nonnull EntityDeathEvent e) { }

    protected void onCastSpell(EntitySpellCastEvent e) { }

    protected void onDamage(EntityDamageEvent e) { }

    /**
     * Gets the AI of the Alien. The returned map is a map of a mob goal and its priority
     *
     * @return a map of the Alien's goals, or an empty map for default AI
     */
    protected Map<Goal<T>, Integer> getGoals() {
        return Map.of();
    }
    protected abstract int getMaxHealth();

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
