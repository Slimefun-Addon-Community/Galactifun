package io.github.addoncommunity.galactifun.core.listener;

import io.github.addoncommunity.galactifun.api.universe.attributes.Gravity;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import javax.annotation.Nonnull;

/**
 * Listeners for celestial world
 * 
 * @author Mooy1
 * @author GallowsDove
 */
public final class CelestialListener implements Listener {
    
    public CelestialListener() {
        PluginUtils.registerListener(this);
    }
    
    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
        Gravity.removeGravity(e.getPlayer());
        applyWorldEffects(e);
    }

    @EventHandler
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        applyWorldEffects(e);
    }

    @EventHandler
    public void onPlanetRespawn(@Nonnull PlayerRespawnEvent e) {
        applyWorldEffects(e);
    }

    private static void applyWorldEffects(@Nonnull PlayerEvent e) {
        CelestialWorld object = CelestialWorld.getByWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }
    
    @EventHandler
    public void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (!e.getPlayer().hasPermission("galactifun.admin") && e.getTo() != null && e.getTo().getWorld() != null) {
            CelestialWorld world = CelestialWorld.getByWorld(e.getTo().getWorld());
            if (world != null) {
                e.setCancelled(true); // TODO we should add ways to 'fast travel' to worlds that are super expensive so that people can build bases there
            }
        }
    }
    
    @EventHandler
    public void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
        CelestialWorld world = CelestialWorld.getByWorld(e.getEntity().getWorld());
        if (world != null) {
            world.onMobSpawn(e);
        }
    }
    
    @EventHandler
    public void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
        CelestialWorld world = CelestialWorld.getByWorld(e.getPlayer().getWorld());
        if (world != null) {
            e.setCancelled(true);
        }
    }

}
