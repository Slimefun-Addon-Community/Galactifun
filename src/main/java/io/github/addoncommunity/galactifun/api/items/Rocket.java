package io.github.addoncommunity.galactifun.api.items;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.api.worlds.WorldManager;
import io.github.addoncommunity.galactifun.base.items.LaunchPadCore;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;

public final class Rocket extends SlimefunItem {

    // todo Move static to some sort of RocketManager
    private static final List<String> LAUNCH_MESSAGES = Galactifun.inst().getConfig().getStringList("rockets.launch-msgs");

    @Getter
    private final int fuelCapacity;
    @Getter
    private final int storageCapacity;

    public Rocket(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int fuelCapacity, int storageCapacity) {
        super(category, item, recipeType, recipe);
        
        this.fuelCapacity = fuelCapacity;
        this.storageCapacity = storageCapacity;

        addItemHandler((BlockUseHandler) e -> e.getClickedBlock().ifPresent(block -> openGUI(e.getPlayer(), block)));

        addItemHandler(new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                Block b = e.getBlock();
                BlockData data = b.getBlockData();
                if (data instanceof Rotatable) {
                    ((Rotatable) data).setRotation(BlockFace.NORTH);
                }
                b.setBlockData(data, true);
            }
        });
    }

    private void openGUI(@Nonnull Player p, @Nonnull Block b) {
        if (!BlockStorage.check(b, this.getId())) return;

        String string = BlockStorage.getLocationInfo(b.getLocation(), "isLaunching");
        if (Boolean.parseBoolean(string)) {
            p.sendMessage(ChatColor.RED + "The rocket is already launching!");
            return;
        }

        WorldManager worldManager = Galactifun.worldManager();
        PlanetaryWorld world = worldManager.getWorld(p.getWorld());
        if (world == null) {
            p.sendMessage(ChatColor.RED + "You cannot travel to space from this world!");
            return;
        }

        string = BlockStorage.getLocationInfo(b.getLocation(), "fuel");
        if (string == null) return;
        int fuel = Integer.parseInt(string);
        if (fuel == 0) {
            p.sendMessage(ChatColor.RED + "The rocket has no fuel!");
            return;
        }

        string = BlockStorage.getLocationInfo(b.getLocation(), "fuelType");
        if (string == null) return;
        Integer eff = LaunchPadCore.FUELS.get(string);
        if (eff == null) return;

        long maxDistance = Math.round(2_000_000 * eff * fuel);

        List<PlanetaryWorld> reachable = new ArrayList<>();
        for (PlanetaryWorld planetaryWorld : worldManager.getSpaceWorlds()) {
            if (planetaryWorld.getDistanceTo(world) * Util.KM_PER_LY <= maxDistance) {
                reachable.add(planetaryWorld);
            }
        }

        if (reachable.isEmpty()) {
            p.sendMessage(ChatColor.RED + "No known destinations within range!");
            return;
        }

        ChestMenu menu = new ChestMenu("Choose a destination");
        menu.setEmptySlotsClickable(false);

        int i = 0;
        for (PlanetaryWorld planetaryWorld : reachable) {
            double distance = planetaryWorld.getDistanceTo(world);
            ItemStack item = planetaryWorld.getItem().clone();
            ItemMeta meta = item.getItemMeta();
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore.remove(lore.size() - 1);

                if (distance > 0) {
                    lore.add(ChatColors.color("&7Distance: " + (distance < 1
                        ? LorePreset.format(distance * Util.KM_PER_LY) + " Kilometers"
                        : distance + " Light Years")
                    ));
                } else {
                    lore.add(ChatColors.color("&7You are here!"));
                }

                meta.setLore(lore);
                item = item.clone();
                item.setItemMeta(meta);
            }

            String fuelType = string;
            menu.addItem(i++, item, (p1, slot, it, action) -> {
                p1.closeInventory();
                int usedFuel = (int) Math.ceil((distance * Util.KM_PER_LY) / 2_000_000);
                p1.sendMessage(ChatColor.YELLOW + "You are going to " + planetaryWorld.getName() + " and will use " +
                    usedFuel + " fuel. Are you sure you want to do that? (yes/no)");
                ChatUtils.awaitInput(p1, (input) -> {
                    if (input.equalsIgnoreCase("yes")) {
                        p.sendMessage(ChatColor.YELLOW + "Please enter destination coordinates in the form of <x> <z> (i.e. -123 456):");
                        ChatUtils.awaitInput(p, (response) -> {
                            String trimmed = response.trim();
                            if (Util.COORD_PATTERN.matcher(trimmed).matches()) {
                                String[] split = Util.SPACE_PATTERN.split(trimmed);
                                int x = Integer.parseInt(split[0]);
                                int z = Integer.parseInt(split[1]);
                                launch(p1, b, planetaryWorld, fuel - usedFuel, fuelType, x, z);
                            } else {
                                p.sendMessage(ChatColor.RED + "Invalid coordinate format! Please use the format <x> <z>");
                            }
                        });
                    }
                });
                return false;
            });
        }

        menu.open(p);
    }
    
    private void launch(@Nonnull Player p, @Nonnull Block b, PlanetaryWorld worldTo, int fuelLeft, String fuelType, int x, int z) {
        BlockStorage.addBlockInfo(b, "isLaunching", "true");

        World world = p.getWorld();

        new BukkitRunnable() {
            private int times = 0;
            private final Block pad = b.getRelative(BlockFace.DOWN);

            @Override
            public void run() {
                if (this.times++ < 20) {
                    for (BlockFace face : Util.SURROUNDING_FACES) {
                        Block block = this.pad.getRelative(face);
                        world.spawnParticle(Particle.ASH, block.getLocation(), 100, 0.5, 0.5, 0.5);
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Galactifun.inst(), 0, 10);

        World to = worldTo.getWorld();

        Block destBlock = to.getHighestBlockAt(x, z).getRelative(BlockFace.UP);

        Chunk destChunk = destBlock.getChunk();
        if (!destChunk.isLoaded()) {
            destChunk.load(true);
        }

        Galactifun.inst().runSync(() -> {
            destBlock.setType(Material.CHEST);
            BlockData data = destBlock.getBlockData();
            if (data instanceof Rotatable) {
                ((Rotatable) data).setRotation(BlockFace.NORTH);
            }
            destBlock.setBlockData(data, true);

            BlockState state = PaperLib.getBlockState(destBlock, false).getState();
            if (state instanceof Chest chest) {
                Inventory inv = chest.getInventory();
                inv.clear(); // just in case
                inv.addItem(this.getItem().clone());

                ItemStack fuel = StackUtils.getItemByID(fuelType);
                if (fuel != null) {
                    fuel = fuel.clone();
                    fuel.setAmount(fuelLeft);
                    inv.addItem(fuel);
                }
            }
            state.update();

            p.sendMessage(ChatColor.GOLD + LAUNCH_MESSAGES.get(ThreadLocalRandom.current().nextInt(LAUNCH_MESSAGES.size())) + "...");
        }, 40);

        Galactifun.inst().runSync(() -> p.sendMessage(ChatColor.GOLD + LAUNCH_MESSAGES.get(ThreadLocalRandom.current().nextInt(LAUNCH_MESSAGES.size())) + "..."), 80);
        Galactifun.inst().runSync(() -> p.sendMessage(ChatColor.GOLD + LAUNCH_MESSAGES.get(ThreadLocalRandom.current().nextInt(LAUNCH_MESSAGES.size())) + "..."), 120);
        Galactifun.inst().runSync(() -> p.sendMessage(ChatColor.GOLD + LAUNCH_MESSAGES.get(ThreadLocalRandom.current().nextInt(LAUNCH_MESSAGES.size())) + "..."), 160);
        Galactifun.inst().runSync(() -> {
            p.sendMessage(ChatColor.GOLD + "Verifying blast awesomeness...");

            for (Entity entity : world.getEntities()) {
                if ((entity instanceof LivingEntity && !(entity instanceof ArmorStand)) || entity instanceof Item) {
                    if (entity.getLocation().distanceSquared(b.getLocation()) <= 25) {
                        PaperLib.teleportAsync(entity, destBlock.getLocation().add(0, 1, 0));
                    }
                }
            }

            b.setType(Material.AIR);
            BlockStorage.clearBlockInfo(b);
        }, 200);
    }
}
