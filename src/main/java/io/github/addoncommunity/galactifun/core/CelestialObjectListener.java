package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.CelestialObject;
import io.github.mooy1.infinitylib.PluginUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
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
        if (e.getEntity().getWorld().getName().equals("mars")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
        CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

        if (object != null) {
            int g = -object.getGravity() - 1;

            e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
            e.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
            new PotionEffect(PotionEffectType.JUMP, 2147483647, g).apply(e.getPlayer());
            if (g > 0) {
                new PotionEffect(PotionEffectType.SLOW_FALLING, 2147483647, (g-1)/2).apply(e.getPlayer());
            }
        } else {
            CelestialObject from = Registry.getCelestialObject(e.getFrom().getName());

            if (from != null) {
                e.getPlayer().removePotionEffect(PotionEffectType.JUMP);
                e.getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
            }
        }
    }

    @EventHandler
    public void onMilkDrink(@Nonnull PlayerItemConsumeEvent e) {
        if (e.getItem().getType() == Material.MILK_BUCKET) {
            CelestialObject object = Registry.getCelestialObject(e.getPlayer().getWorld().getName());

            if (object != null) {
                int g = -object.getGravity() - 1;

                Player p = e.getPlayer();

                for (PotionEffect effect : p.getActivePotionEffects())
                    p.removePotionEffect(effect.getType());

                new PotionEffect(PotionEffectType.JUMP, 2147483647, g).apply(p);
                if (g > 0) {
                    new PotionEffect(PotionEffectType.SLOW_FALLING, 2147483647, (g-1)/2).apply(p);
                }

                e.setCancelled(true);
            }
        }
    }
    
}
