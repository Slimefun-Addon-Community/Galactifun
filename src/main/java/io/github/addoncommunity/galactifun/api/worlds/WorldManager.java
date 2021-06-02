package io.github.addoncommunity.galactifun.api.worlds;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public final class WorldManager implements Listener, Runnable {

    private final AlienManager alienManager;
    private final Map<World, CelestialWorld> worlds = new HashMap<>();
    private final Map<World, AlienWorld> alienWorlds = new HashMap<>();

    public WorldManager(Galactifun galactifun, AlienManager alienManager) {
        this.alienManager = alienManager;

        galactifun.registerListener(this);
        galactifun.scheduleRepeatingSync(this, 100);
    }

    @Nullable
    public CelestialWorld getWorld(@Nonnull World world) {
        return this.worlds.get(world);
    }

    @Nullable
    public AlienWorld getAlienWorld(@Nonnull World world) {
        return this.alienWorlds.get(world);
    }

    @Nonnull
    public Collection<CelestialWorld> getWorlds() {
        return Collections.unmodifiableCollection(this.worlds.values());
    }

    @Override
    public void run() {
        for (AlienWorld world : this.alienWorlds.values()) {
            world.tickWorld(this.alienManager);
        }
    }

    @EventHandler
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e){
        AlienWorld object = getAlienWorld(e.getFrom());
        if (object != null) {
            object.getGravity().removeGravity(e.getPlayer());
        }
        object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        AlienWorld object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (!e.getPlayer().hasPermission("galactifun.admin")) {
            if (e.getTo().getWorld() != null) {
                AlienWorld world = getAlienWorld(e.getTo().getWorld());
                if (world != null) {
                    e.setCancelled(true);
                    // TODO we should add ways to 'fast travel' to worlds that are super expensive so that people can build bases there
                }
            }
        }
    }

    @EventHandler
    public void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            AlienWorld world = getAlienWorld(e.getEntity().getWorld());
            if (world != null && !world.canSpawnVanillaMobs()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
        if (this.alienWorlds.containsKey(e.getPlayer().getWorld())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCropGrow(@Nonnull BlockGrowEvent e) {
        Block block = e.getBlock();
        AlienWorld world = getAlienWorld(block.getWorld());
        if (world != null) {
            int attempts = world.getAtmosphere().getGrowthAttempts();
            if (attempts != 0 && SlimefunTag.CROPS.isTagged(block.getType())) {
                BlockData data = block.getBlockData();
                if (data instanceof Ageable ageable) {
                    ageable.setAge(ageable.getAge() + attempts);
                    block.setBlockData(ageable);
                }
            }
        }
    }

}
