package io.github.addoncommunity.galactifun.api.worlds;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class WorldManager implements Listener {

    private final Map<World, PlanetaryWorld> enabledWorlds = new HashMap<>();
    private final Map<World, AlienWorld> enabledAlienWorlds = new HashMap<>();

    public WorldManager(Galactifun galactifun) {
        galactifun.registerListener(this);
        AlienManager alienManager = galactifun.getAlienManager();
        galactifun.scheduleRepeatingSync(() -> {
            for (AlienWorld world : this.enabledAlienWorlds.values()) {
                world.tickWorld(alienManager);
            }
        }, 100);
    }

    void register(PlanetaryWorld world) {
        this.enabledWorlds.put(world.getWorld(), world);
    }

    void register(AlienWorld alienWorld) {
        this.enabledAlienWorlds.put(alienWorld.getWorld(), alienWorld);
    }

    @Nullable
    public PlanetaryWorld getWorld(@Nonnull World world) {
        return this.enabledAlienWorlds.get(world);
    }

    @Nullable
    public AlienWorld getAlienWorld(@Nonnull World world) {
        return this.enabledAlienWorlds.get(world);
    }

    @Nonnull
    public Collection<PlanetaryWorld> getEnabledWorlds() {
        return Collections.unmodifiableCollection(this.enabledWorlds.values());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e) {
        AlienWorld object = getAlienWorld(e.getFrom());
        if (object != null) {
            object.getGravity().removeGravity(e.getPlayer());
        }
        object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        AlienWorld object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (!e.getPlayer().hasPermission("galactifun.admin")) {
            if (e.getTo().getWorld() != null) {
                AlienWorld world = getAlienWorld(e.getTo().getWorld());
                if (world != null) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            AlienWorld world = getAlienWorld(e.getEntity().getWorld());
            if (world != null && !world.canSpawnVanillaMobs()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
        if (this.enabledAlienWorlds.containsKey(e.getPlayer().getWorld())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        World w = b.getWorld();
        AlienWorld world = this.enabledAlienWorlds.get(w);
        if (world != null) {
            SlimefunItemStack item = world.getMappedItem(b);
            if (item != null) {
                Location l = b.getLocation();
                if (BlockStorage.getLocationInfo(l, "stored") != null) {
                    e.setDropItems(false);
                    w.dropItemNaturally(l.add(0.5, 0.5, 0.5), item.clone());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        AlienWorld world = this.enabledAlienWorlds.get(b.getWorld());
        if (world != null && world.getMappedItem(b) != null) {
            BlockStorage.addBlockInfo(b, "placed", "true");
        }
    }

}
