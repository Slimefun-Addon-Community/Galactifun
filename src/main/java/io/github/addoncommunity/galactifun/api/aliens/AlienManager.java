package io.github.addoncommunity.galactifun.api.aliens;

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
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import io.github.addoncommunity.galactifun.Galactifun;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;

public final class AlienManager implements Listener, Runnable {

    @Getter
    private final NamespacedKey key;
    @Getter
    private final int maxAliensPerPlayer;
    private final Map<String, Alien<?>> aliens = new HashMap<>();

    public AlienManager(Galactifun galactifun) {
        galactifun.registerListener(this);
        galactifun.scheduleRepeatingSync(this, galactifun.getConfig().getInt("aliens.tick-interval", 1, 20));

        this.key = new NamespacedKey(galactifun, "alien");
        this.maxAliensPerPlayer = galactifun.getConfig().getInt("aliens.max-per-player", 4, 64);
    }

    void register(Alien<?> alien) {
        this.aliens.put(alien.getId(), alien);
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

    public Collection<Alien<?>> getAliens() {
        return Collections.unmodifiableCollection(this.aliens.values());
    }

    @Override
    public void run() {
        for (Alien<?> alien : this.aliens.values()) {
            alien.onUniqueTick();
        }

        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien<?> alien = getAlien(entity);
                if (alien != null) {
                    alien.onMobTick((Mob) alien.getClazz().cast(entity));
                }
            }
        }
    }

    @EventHandler
    public void onAlienTarget(@Nonnull EntityTargetEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onTarget(e);
        }
    }

    @EventHandler
    public void onAlienInteract(@Nonnull PlayerInteractEntityEvent e) {
        Alien<?> alien = getAlien(e.getRightClicked());
        if (alien != null) {
            alien.onInteract(e);
        }
    }

    @EventHandler
    public void onAlienHit(@Nonnull EntityDamageByEntityEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onHit(e);
        }
        alien = getAlien(e.getDamager());
        if (alien != null) {
            alien.onAttack(e);
        }
    }

    @EventHandler
    public void onAlienDie(@Nonnull EntityDeathEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onDeath(e);
        }
    }

    @EventHandler
    public void onAlienCombust(@Nonnull EntityCombustEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAlienCastSpell(@Nonnull EntitySpellCastEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onCastSpell(e);
        }
    }

    @EventHandler
    public void onAlienDamage(@Nonnull EntityDamageEvent e) {
        Alien<?> alien = getAlien(e.getEntity());
        if (alien != null) {
            alien.onDamage(e);
        }
    }

}
