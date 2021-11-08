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
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.ExclusiveGEOResource;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitProfile;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.base.universe.earth.Earth;
import io.github.addoncommunity.galactifun.util.PersistentBlockPositions;
import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.events.GEOResourceGenerationEvent;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public final class WorldManager implements Listener {

    private static final NamespacedKey PLACED = Galactifun.createKey("placed");

    @Getter
    private final int maxAliensPerPlayer;
    private final Map<World, PlanetaryWorld> spaceWorlds = new HashMap<>();
    private final Map<World, AlienWorld> alienWorlds = new HashMap<>();
    private final YamlConfiguration config;
    private final YamlConfiguration defaultConfig;

    private final Map<UUID, Integer> respawnTimes = new HashMap<>();
    private final Map<UUID, Long> lastDeaths = new HashMap<>();

    public WorldManager(Galactifun galactifun) {
        this.maxAliensPerPlayer = galactifun.getConfig().getInt("aliens.max-per-player", 4, 64);

        Events.registerListener(this);
        Scheduler.repeat(100, () -> this.alienWorlds.values().forEach(AlienWorld::tickWorld));
        Scheduler.repeat(20, this::tickOxygen);

        File configFile = new File("plugins/Galactifun", "worlds.yml");
        this.config = new YamlConfiguration();
        this.defaultConfig = new YamlConfiguration();
        this.config.setDefaults(this.defaultConfig);

        // Load the config
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
                this.config.options().copyDefaults(true);
                this.config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void register(PlanetaryWorld world) {
        if (this.spaceWorlds.containsKey(world.world())) {
            throw new IllegalArgumentException("Alien World " + world.id() + " is already registered!");
        }
        this.spaceWorlds.put(world.world(), world);
        if (world instanceof AlienWorld alienWorld) {
            this.alienWorlds.put(world.world(), alienWorld);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getSetting(AlienWorld world, String path, Class<T> clazz, T defaultValue) {
        path = world.id() + '.' + path;
        this.defaultConfig.set(path, defaultValue);
        if (clazz == String.class) {
            return (T) this.config.getString(path);
        } else {
            return this.config.getObject(path, clazz);
        }
    }

    private void tickOxygen() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                PlanetaryWorld world = spaceWorlds.get(p.getWorld());
                if (world != null
                        && world.atmosphere().requiresOxygenTank()
                        && !SpaceSuitProfile.get(p).consumeOxygen(20)
                        && !Galactifun.protectionManager().isOxygenBlock(p.getLocation())) {
                    p.damage(8);
                }
            }
        }
    }

    @Nullable
    public PlanetaryWorld getWorld(@Nonnull World world) {
        return this.spaceWorlds.get(world);
    }

    @Nullable
    public AlienWorld getAlienWorld(@Nonnull World world) {
        return this.alienWorlds.get(world);
    }

    @Nonnull
    public Collection<PlanetaryWorld> spaceWorlds() {
        return Collections.unmodifiableCollection(this.spaceWorlds.values());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlanetChange(@Nonnull PlayerChangedWorldEvent e) {
        AlienWorld object = getAlienWorld(e.getFrom());
        if (object != null) {
            object.gravity().removeGravity(e.getPlayer());
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

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlayerChangeGameMode(@Nonnull PlayerGameModeChangeEvent e) {
        AlienWorld object = getAlienWorld(e.getPlayer().getWorld());
        if (object != null && !(e.getNewGameMode() == GameMode.CREATIVE || e.getNewGameMode() == GameMode.SPECTATOR)) {
            object.applyEffects(e.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onPlayerTeleport(@Nonnull PlayerTeleportEvent e) {
        if (!e.getPlayer().hasPermission("galactifun.admin")) {
            if (e.getTo().getWorld() != null) {
                AlienWorld world = getAlienWorld(e.getTo().getWorld());
                if (world != null) {
                    boolean canTp = false;
                    for (MetadataValue value : e.getPlayer().getMetadata("CanTpAlienWorld")) {
                        canTp = value.asBoolean();
                    }
                    if (canTp) {
                        e.getPlayer().removeMetadata("CanTpAlienWorld", Galactifun.instance());
                    } else {
                        e.setCancelled(true);
                    }
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
            ProtectionManager manager = Galactifun.protectionManager();
            Location l = block.getLocation();
            if (manager.getEffectAt(l, AtmosphericEffect.COLD) > 1) {
                Scheduler.run(() -> block.setType(Material.ICE));
            } else if (manager.getEffectAt(l, AtmosphericEffect.HEAT) > 1) {
                Scheduler.run(block::breakNaturally);
            } else {
                int attempts = world.atmosphere().growthAttempts();
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        World w = b.getWorld();
        AlienWorld world = this.alienWorlds.get(w);
        if (world != null) {
            SlimefunItemStack item = world.getMappedItem(b);
            if (item != null) {
                BlockPosition pos = new BlockPosition(b);
                Set<BlockPosition> placed = b.getChunk().getPersistentDataContainer().getOrDefault(
                        PLACED,
                        PersistentBlockPositions.INSTANCE,
                        new HashSet<>()
                );
                if (placed.contains(pos)) {
                    placed.remove(pos);
                    b.getChunk().getPersistentDataContainer().set(
                            PLACED,
                            PersistentBlockPositions.INSTANCE,
                            placed
                    );
                } else {
                    Location l = b.getLocation();
                    e.setDropItems(false);
                    w.dropItemNaturally(l.add(0.5, 0.5, 0.5), item.clone());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onSleep(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = e.getPlayer();
        PlanetaryWorld world = this.getWorld(p.getWorld());
        if (world == null || world.atmosphere().environment() == World.Environment.NORMAL) return;
        Block b = e.getClickedBlock();
        if (b != null && Tag.BEDS.isTagged(b.getType())) {
            e.setCancelled(true);
            p.setBedSpawnLocation(p.getLocation(), true);
            p.sendMessage("Respawn point set");
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    private void onPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        AlienWorld world = this.alienWorlds.get(b.getWorld());
        if (world != null && world.getMappedItem(b) != null) {
            Set<BlockPosition> placed = b.getChunk().getPersistentDataContainer().getOrDefault(
                    PLACED,
                    PersistentBlockPositions.INSTANCE,
                    new HashSet<>()
            );
            placed.add(new BlockPosition(b));
            b.getChunk().getPersistentDataContainer().set(
                    PLACED,
                    PersistentBlockPositions.INSTANCE,
                    placed
            );
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    private void onRespawnLoop(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (this.getWorld(p.getWorld()) != null) {
            Long lastBoxed = this.lastDeaths.get(p.getUniqueId());
            if (lastBoxed != null) {
                long timeSince = System.currentTimeMillis() - lastBoxed;
                if (timeSince < (60 * 1000)) {
                    int times = this.respawnTimes.merge(p.getUniqueId(), 1, Integer::sum);
                    if (times > 3) {
                        p.sendMessage(ChatColor.YELLOW + """
                                A possible respawn loop has been detected!
                                Do you wish to go back to Earth? (yes/no)"""
                        );
                        ChatUtils.awaitInput(p, s -> {
                            if (s.equalsIgnoreCase("yes")) {
                                PaperLib.teleportAsync(p, BaseUniverse.EARTH.world().getSpawnLocation());
                                WorldManager.this.respawnTimes.remove(p.getUniqueId());
                            }
                        });
                    }
                }
            }

            this.lastDeaths.put(p.getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerPlaceWater(PlayerBucketEmptyEvent e) {
        if (e.getBucket() != Material.WATER_BUCKET) return;
        Player p = e.getPlayer();
        PlanetaryWorld world = this.getWorld(p.getWorld());
        if (world != null) {
            e.setCancelled(true);
            if (p.getGameMode() != GameMode.CREATIVE) {
                ItemStack item = p.getInventory().getItem(e.getHand());
                if (item != null) {
                    ItemUtils.consumeItem(item, true);
                }
            }
            ProtectionManager manager = Galactifun.protectionManager();
            Block clicked = e.getBlockClicked();
            Block toBePlaced = clicked.getRelative(e.getBlockFace());
            Location l = toBePlaced.getLocation();
            if (manager.getEffectAt(l, AtmosphericEffect.COLD) > 1) {
                toBePlaced.setType(Material.ICE);
            } else if (manager.getEffectAt(l, AtmosphericEffect.HEAT) > 1) {
                p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, l, 5);
            } else {
                BlockData data = clicked.getBlockData();
                if (data instanceof Waterlogged waterlogged) {
                    waterlogged.setWaterlogged(true);
                    clicked.setBlockData(waterlogged);
                } else {
                    toBePlaced.setType(Material.WATER);
                }
            }
        }
    }

    @EventHandler
    public void onGEOResourceGenerate(GEOResourceGenerationEvent e) {
        PlanetaryWorld world = this.spaceWorlds.get(e.getWorld());
        if (world == null) return;

        if (e.getResource() instanceof ExclusiveGEOResource exclusiveResource) {
            if (exclusiveResource.getWorlds().contains(world)) return;
        } else {
            if (world instanceof Earth) return;

            for (GEOResource resource : world.resources()) {
                if (resource.equals(e.getResource())) return;
            }
        }

        e.setValue(0);
    }

}
