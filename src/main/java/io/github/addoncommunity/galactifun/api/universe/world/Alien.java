package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.PluginUtils;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
 * @see io.github.addoncommunity.galactifun.base.aliens.Martian
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

    private static final NamespacedKey KEY = new NamespacedKey(Galactifun.getInstance(), "alien");

    @Nonnull
    private final String id;
    @Nonnull
    private final String name;
    @Nonnull
    private final EntityType type;
    
    @Getter
    private final int chance;
    private final int health;
    
    public Alien(@Nonnull String id, @Nonnull String name, @Nonnull EntityType type,
                 int chance, int health) {

        Validate.notNull(id);
        Validate.notNull(name);
        Validate.notNull(type);
        Validate.isTrue(type.isAlive(), "Entity type " + type + " is not alive!");
        Validate.isTrue(health > 0);
        Validate.isTrue(chance > 0 && chance <= 100);
        
        this.chance = chance;
        this.id = id;
        this.name = ChatColors.color(name);
        this.health = health;
        this.type = type;
        
        ALIENS.put(id, this);

    }

    public final void spawn(@Nonnull Location loc, @Nonnull World world) {
        LivingEntity entity = (LivingEntity) world.spawnEntity(loc, this.type);
        PersistentDataAPI.setString(entity, KEY, this.id);

        Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(this.health);
        entity.setHealth(this.health);
        entity.setCustomName(this.name);
        entity.setCustomNameVisible(true);

        onSpawn(entity);
    }

    protected void onSpawn(@Nonnull LivingEntity spawned) { }

    protected void onMobTick(@Nonnull LivingEntity mob) { }

    protected void onHit(@Nonnull EntityDamageByEntityEvent e) { }

    protected void onInteract(@Nonnull PlayerInteractEntityEvent e) { }

    protected void onTarget(@Nonnull EntityTargetEvent e) { }

    protected void onDeath(@Nonnull EntityDeathEvent e) { }

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
            }

            @EventHandler
            public void onAlienDie(@Nonnull EntityDeathEvent e) {
                Alien alien = Alien.getByEntity(e.getEntity());
                if (alien != null) {
                    alien.onDeath(e);
                }
            }
        });
    }
    
}
