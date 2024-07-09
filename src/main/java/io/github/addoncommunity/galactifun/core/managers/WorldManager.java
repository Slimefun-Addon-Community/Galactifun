package io.github.addoncommunity.galactifun.core.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.ExclusiveGEOResource;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitProfile;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.api.worlds.OrbitWorld;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseUniverse;
import io.github.addoncommunity.galactifun.base.universe.earth.Earth;
import io.github.addoncommunity.galactifun.util.ChunkStorage;
import io.github.mooy1.infinitylib.common.Events;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.events.ExplosiveToolBreakBlocksEvent;
import io.github.thebusybiscuit.slimefun4.api.events.GEOResourceGenerationEvent;
import io.github.thebusybiscuit.slimefun4.api.events.WaypointCreateEvent;
import io.github.thebusybiscuit.slimefun4.api.geo.GEOResource;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

public final class WorldManager implements Listener {

    private static final String PLACED = "placed";

    @Getter
    private final int maxAliensPerPlayer;
    private final Map<World, PlanetaryWorld> spaceWorlds = new HashMap<>();
    private final Map<World, AlienWorld> alienWorlds = new HashMap<>();
    private final YamlConfiguration config;
    private final YamlConfiguration defaultConfig;

    private final Map<UUID, Integer> respawnTimes = new HashMap<>();
    private final Map<UUID, Long> lastDeaths = new HashMap<>();
    private final Map<UUID, Long> oxygenDamage = new HashMap<>();

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
                        && !Galactifun.protectionManager().isOxygenBlock(p.getLocation())
                        && !SpaceSuitProfile.get(p).consumeOxygen(20)
                        && !p.isDead()) {
                    p.sendMessage(ChatColor.RED + "You have run out of oxygen!");
                    double damage = oxygenDamage.merge(p.getUniqueId(), 2L, (a, b) -> a * b);
                    p.setHealth(Math.max(p.getHealth() - damage, 0));
                } else {
                    oxygenDamage.remove(p.getUniqueId());
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
    
    @Nonnull
    public Collection<AlienWorld> alienWorlds() {
        return Collections.unmodifiableCollection(this.alienWorlds.values());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPortalCreate(PortalCreateEvent e) {
        if (!Galactifun.instance().getConfig().getBoolean("worlds.allow-nether-portals") && getAlienWorld(e.getWorld()) != null) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void portal(PlayerPortalEvent e){
        if (!Galactifun.instance().getConfig().getBoolean("worlds.allow-nether-portals") && getAlienWorld(e.getFrom().getWorld()) != null){
            e.setCancelled(true);
        }
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
        if (e instanceof PlayerTeleportEndGatewayEvent) return;
        if (!e.getPlayer().hasPermission("galactifun.admin")) {
            if (e.getTo().getWorld() != null && e.getFrom().getWorld() != e.getTo().getWorld()) {
                PlanetaryWorld world = getWorld(e.getTo().getWorld());
                PlanetaryWorld world2 = getWorld(e.getFrom().getWorld());
                if (world != null && world2 != null) {
                    boolean canTp = false;
                    for (MetadataValue value : e.getPlayer().getMetadata("CanTpAlienWorld")) {
                        canTp = value.asBoolean();
                    }
                    if (canTp || e.getFrom().getWorld().equals(e.getTo().getWorld())) {
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
            if (item != null && !removePlacedBlock(b)) {
                e.setDropItems(false);
                List<ItemStack> drops = new ArrayList<>();
                drops.add(item.clone());
                item.getItem().callItemHandler(BlockBreakHandler.class, h -> h.onPlayerBreak(e, item, drops));
                for (ItemStack drop : drops) {
                    w.dropItemNaturally(b.getLocation().add(0.5, 0, 0.5), drop);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBlockExplode(BlockExplodeEvent e) {
        handleExplosion(e.blockList().iterator());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onEntityExplode(EntityExplodeEvent e) {
        handleExplosion(e.blockList().iterator());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onExplosivePickUse(ExplosiveToolBreakBlocksEvent e) {
        handleExplosion(e.getAdditionalBlocks().iterator());
    }

    private void handleExplosion(Iterator<Block> blocks) {
        while (blocks.hasNext()) {
            Block b = blocks.next();
            World w = b.getWorld();
            AlienWorld world = this.getAlienWorld(w);
            if (world != null) {
                SlimefunItemStack item = world.getMappedItem(b);
                if (item != null && !removePlacedBlock(b)) {
                    blocks.remove();
                    w.dropItemNaturally(b.getLocation().add(0.5, 0, 0.5), item.clone());
                    Scheduler.run(() -> b.setType(Material.AIR));
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
        AlienWorld world = this.getAlienWorld(b.getWorld());
        if (world != null && world.getMappedItem(b) != null) {
            addPlacedBlock(b);
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
    private void onPlayerPlaceWater(PlayerBucketEmptyEvent e) {
        if (e.getBucket() != Material.WATER_BUCKET) return;
        Player p = e.getPlayer();
        PlanetaryWorld world = this.getWorld(p.getWorld());
        if (world != null && world != BaseUniverse.EARTH) {
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
                if (toBePlaced.isEmpty()) {
                    toBePlaced.setType(Material.ICE);
                }
            } else if (manager.getEffectAt(l, AtmosphericEffect.HEAT) > 1) {
                p.getWorld().spawnParticle(Particle.SMOKE_NORMAL, l, 5);
            } else {
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    private void onGEOResourceGenerate(GEOResourceGenerationEvent e) {
        PlanetaryWorld world = this.getWorld(e.getWorld());
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

    @EventHandler(ignoreCancelled = true)
    private void onPlayerFallInOrbit(EntityDamageEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.VOID) return;
        PlanetaryWorld world = this.getWorld(e.getEntity().getWorld());

        if (world instanceof OrbitWorld orbitWorld && orbitWorld.getPlanet() instanceof PlanetaryWorld planet) {
            e.setCancelled(true);
            Location l = e.getEntity().getLocation();
            e.getEntity().teleport(new Location(
                    planet.world(),
                    l.getX(),
                    planet.world().getMaxHeight(),
                    l.getZ()
            ));
        }
    }

    public void addPlacedBlock(Block b) {
        ChunkStorage.tag(b, PLACED);
    }

    /**
     * Removes a non-world-mapped block from the placed blocks list
     *
     * @return true if the block was a placed block, false if it was not
     */
    public boolean removePlacedBlock(Block b) {
        return ChunkStorage.untag(b, PLACED);
    }

}
