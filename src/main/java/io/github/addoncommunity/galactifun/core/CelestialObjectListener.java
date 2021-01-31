package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.CelestialObject;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nonnull;

/**
 * Listeners for celestial object handlers
 */
public final class CelestialObjectListener implements Listener {

    public static void setup() {
        new CelestialObjectListener();
    }
    
    private CelestialObjectListener() {
        PluginUtils.registerListener(this);
    }
    
    @EventHandler
    public void onMobSpawn(@Nonnull EntitySpawnEvent e) {
        // TODO improve, should have some methods in celestial objects for events
        String name = e.getEntity().getWorld().getName();
        if (name.equals("mars") || name.equals("venus")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
        CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

        if (object != null) {
            object.getGravity().applyGravity(e.getPlayer());
        } else {
            CelestialObject from = Registry.getCelestialObject(e.getFrom().getName());

            if (from != null) {
                e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
            }
        }
    }

    @EventHandler
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

        if (object != null) {
            Bukkit.getScheduler().runTaskLater(Galactifun.getInstance(), () -> object.getGravity().applyGravity(e.getPlayer()), 20);
        }
    }

    @EventHandler
    public void onPlanetRespawn(@Nonnull PlayerRespawnEvent e) {
        CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

        if (object != null) {
            Bukkit.getScheduler().runTask(Galactifun.getInstance(), () -> object.getGravity().applyGravity(e.getPlayer()));
        }
    }

    @EventHandler
    public void onMilkDrink(@Nonnull PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

            if (object != null) {
                Player p = e.getPlayer();

                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());

                Bukkit.getScheduler().runTask(Galactifun.getInstance(), () -> object.getGravity().applyGravity(e.getPlayer()));
            }
        }
    }
    
}
