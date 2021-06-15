package io.github.addoncommunity.galactifun.api.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

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
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class WorldManager implements Listener {

    @Getter
    private final int maxAliensPerPlayer;
    private final List<PlanetaryWorld> spaceWorlds = new ArrayList<>();
    private final Map<World, AlienWorld> alienWorlds = new HashMap<>();

    public WorldManager(Galactifun galactifun) {
        galactifun.registerListener(this);
        galactifun.scheduleRepeatingSync(() -> this.alienWorlds.values().forEach(AlienWorld::tickWorld), 100);
        this.maxAliensPerPlayer = galactifun.getConfig().getInt("aliens.max-per-player", 4, 64);
    }

    void register(PlanetaryWorld world) {
        this.spaceWorlds.add(world);
        if (world instanceof AlienWorld alienWorld) {
            this.alienWorlds.put(alienWorld.getWorld(), alienWorld);
        }
    }

    @Nullable
    public PlanetaryWorld getWorld(@Nonnull World world) {
        return this.alienWorlds.get(world);
    }

    @Nullable
    public AlienWorld getAlienWorld(@Nonnull World world) {
        return this.alienWorlds.get(world);
    }

    @Nonnull
    public List<PlanetaryWorld> getSpaceWorlds() {
        return Collections.unmodifiableList(this.spaceWorlds);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlanetChange(@Nonnull PlayerChangedWorldEvent e) {
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
    private void onPlanetJoin(@Nonnull PlayerJoinEvent e) {
        AlienWorld object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
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
    private void onCreatureSpawn(@Nonnull CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            AlienWorld world = getAlienWorld(e.getEntity().getWorld());
            if (world != null && !world.canSpawnVanillaMobs()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onWaypointCreate(@Nonnull WaypointCreateEvent e) {
        if (this.alienWorlds.containsKey(e.getPlayer().getWorld())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onCropGrow(@Nonnull BlockGrowEvent e) {
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
    private void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        World w = b.getWorld();
        AlienWorld world = this.alienWorlds.get(w);
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
    private void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        AlienWorld world = this.alienWorlds.get(b.getWorld());
        if (world != null && world.getMappedItem(b) != null) {
            BlockStorage.addBlockInfo(b, "placed", "true");
        }
    }

}
