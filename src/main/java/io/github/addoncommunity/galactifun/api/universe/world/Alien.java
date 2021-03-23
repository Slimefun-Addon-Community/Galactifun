package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.mooy1.infinitylib.core.PluginUtils;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    private static final NamespacedKey KEY = new NamespacedKey(Galactifun.inst(), "alien");

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
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");
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

    protected void onSpawn(@Nonnull LivingEntity spawned) { }

    protected void onUniqueTick() { }

    protected void onMobTick(@Nonnull LivingEntity mob) { }

    protected void onHit(@Nonnull EntityDamageByEntityEvent e) { }
    
    protected void onAttack(@Nonnull EntityDamageByEntityEvent e) { }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    protected void onTarget(@Nonnull EntityTargetEvent e) { }

    protected void onDeath(@Nonnull EntityDeathEvent e) { }

    protected void onCastSpell(EntitySpellCastEvent e) { }

    protected void onDamage(EntityDamageEvent e) { }
    
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
     *
     * @return {@code true} if the alien can spawn in this light level, {@code false} otherwise
     */
    protected boolean getSpawnInLightLevel(int lightLevel) {
        return lightLevel <= 7;
    }

    protected Vector getSpawnOffset() {
        return new Vector();
    }

    static {
        PluginUtils.registerListener(new Listener() {

            @EventHandler
            public void onAlienTarget(@Nonnull EntityTargetEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onTarget(e);
                }
            }

            @EventHandler
            public void onAlienInteract(@Nonnull PlayerInteractEntityEvent e) {
                Alien alien = Alien.getByEntity(e.getRightClicked());
                if (alien != null) {
                    alien.onInteract(e);
                }
            }

            @EventHandler
            public void onAlienHit(@Nonnull EntityDamageByEntityEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
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
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onDeath(e);
                }
            }

            @EventHandler
            public void onAlienCombust(@Nonnull EntityCombustEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    e.setCancelled(true);
                }
            }

            @EventHandler
            public void onAlienCastSpell(@Nonnull EntitySpellCastEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onCastSpell(e);
                }
            }

            @EventHandler
            public void onAlienDamage(@Nonnull EntityDamageEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onDamage(e);
                }
            }
        });
    }

}
