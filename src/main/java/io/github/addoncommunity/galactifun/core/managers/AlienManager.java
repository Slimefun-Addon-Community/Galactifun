package io.github.addoncommunity.galactifun.core.managers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;

public final class AlienManager implements Listener {

    @Getter
    private final NamespacedKey key;
    @Getter
    private final NamespacedKey bossKey;
    private final Map<String, Alien<?>> aliens = new HashMap<>();
    private final Set<UUID> alienIds = new HashSet<>();
    private final YamlConfiguration config;

    public AlienManager(Galactifun galactifun) {
        Events.registerListener(this);
        Scheduler.repeat(galactifun.getConfig().getInt("aliens.tick-interval", 1, 20), this::tick);

        File configFile = new File("plugins/Galactifun", "uuids.yml");
        this.config = new YamlConfiguration();

        // Load the uuids
        if (configFile.exists()) {
            try {
                this.config.load(configFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Save the config after startup
        Scheduler.run(() -> {
            try {
                this.config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        this.key = Galactifun.createKey("alien");
        this.bossKey = Galactifun.createKey("boss_alien");
        this.alienIds.addAll(this.config.getStringList("uuids").stream().map(UUID::fromString).toList());
    }

    public void register(Alien<?> alien) {
        if (this.aliens.containsKey(alien.id())) {
            throw new IllegalArgumentException("Alien " + alien.id() + " has already been registered!");
        }
        this.aliens.put(alien.id(), alien);
    }

    @Nullable
    public Alien<?> getAlien(@Nonnull String id) {
        return this.aliens.get(id);
    }

    @Nullable
    public Alien<?> getAlien(@Nonnull Entity entity) {
        String id = PersistentDataAPI.getString(entity, this.key);
        return id == null ? null : getAlien(id);
    }

    @Nonnull
    public Collection<Alien<?>> aliens() {
        return Collections.unmodifiableCollection(this.aliens.values());
    }

    @Nonnull
    public Set<UUID> alienIds() {
        return Collections.unmodifiableSet(this.alienIds);
    }

    public void addAlien(@Nonnull UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (entity != null && getAlien(entity) != null) {
            this.alienIds.add(uuid);
        }
    }

    private void tick() {
        for (Alien<?> alien : this.aliens.values()) {
            alien.onUniqueTick();
        }

        for (UUID uuid : this.alienIds) {
            Entity entity = Bukkit.getEntity(uuid);
            if (entity instanceof LivingEntity livingEntity) {
                Alien<?> alien = getAlien(livingEntity);
                if (alien != null) {
                    alien.onEntityTick(livingEntity);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienTarget(@Nonnull EntityTargetEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onTarget(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienInteract(@Nonnull PlayerInteractEntityEvent e) {
        Alien<?> alien = getAlien(e.getRightClicked());
        if (alien != null) {
            alien.onInteract(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienHit(@Nonnull EntityDamageByEntityEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onHit(e);
        }
        alien = getAlien(e.getDamager());
        if (alien != null) {
            alien.onAttack(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienDie(@Nonnull EntityDeathEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onDeath(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienCombust(@Nonnull EntityCombustEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienCastSpell(@Nonnull EntitySpellCastEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onCastSpell(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienDamage(@Nonnull EntityDamageEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onDamage(e);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienShoot(@Nonnull ProjectileLaunchEvent e) {
        ProjectileSource source = e.getEntity().getShooter();
        if (source instanceof Mob mob) {
            Alien<?> alien = getAlien(mob);
            if (alien != null) {
                alien.onShoot(e);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onAlienRemove(@Nonnull EntityRemoveFromWorldEvent e) {
        this.alienIds.remove(e.getEntity().getUniqueId());
    }

    public void onDisable() {
        this.aliens().forEach(a -> {
            if (a instanceof BossAlien<?> b) {
                b.removeBossBars();
            }
        });
        this.config.set("uuids", this.alienIds.stream().map(UUID::toString).toList());
        try {
            this.config.save(new File("plugins/Galactifun", "uuids.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
