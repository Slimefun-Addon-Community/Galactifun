package io.github.addoncommunity.galactifun.core.managers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
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

    public AlienManager(Galactifun galactifun) {
        Events.registerListener(this);
        Scheduler.repeat(galactifun.getConfig().getInt("aliens.tick-interval", 1, 20), this::tick);

        this.key = Galactifun.createKey("alien");
        this.bossKey = Galactifun.createKey("boss_alien");
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

    private void tick() {
        for (Alien<?> alien : this.aliens.values()) {
            alien.onUniqueTick();
        }

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien<?> alien = getAlien(entity);
                if (alien != null) {
                    alien.onEntityTick(entity);
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

    public void onDisable() {
        this.aliens().forEach(a -> {
            if (a instanceof BossAlien<?> b) {
                b.removeBossBars();
            }
        });
    }

}
