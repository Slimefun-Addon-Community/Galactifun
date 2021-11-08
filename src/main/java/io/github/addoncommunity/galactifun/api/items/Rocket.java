package io.github.addoncommunity.galactifun.api.items;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
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
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.base.items.LaunchPadCore;
import io.github.addoncommunity.galactifun.base.items.knowledge.KnowledgeLevel;
import io.github.addoncommunity.galactifun.core.WorldSelector;
import io.github.addoncommunity.galactifun.core.managers.WorldManager;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class Rocket extends SlimefunItem {

    // todo Move static to some sort of RocketManager
    private static final List<String> LAUNCH_MESSAGES = Galactifun.instance().getConfig().getStringList("rockets.launch-msgs");
    private static final int DISTANCE_PER_FUEL = 2_000_000;

    @Getter
    private final int fuelCapacity;
    @Getter
    private final int storageCapacity;

    public Rocket(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int fuelCapacity, int storageCapacity) {
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

    private static Runnable sendRandomMessage(Player p) {
        return () -> p.sendMessage(ChatColor.GOLD + LAUNCH_MESSAGES.get(ThreadLocalRandom.current().nextInt(LAUNCH_MESSAGES.size())) + "...");
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
        String fuelType = string;
        Integer eff = LaunchPadCore.FUELS.get(string);
        if (eff == null) return;

        // km
        long maxDistance = Math.round(DISTANCE_PER_FUEL * eff * fuel);

        new WorldSelector((player, obj, lore) -> {
            if (obj instanceof PlanetaryWorld) {
                // km
                double dist = obj.distanceTo(world) * Util.KM_PER_LY;
                if (dist > maxDistance) return false;

                lore.add(Component.empty());
                lore.add(Component.text()
                        .color(NamedTextColor.YELLOW)
                        .append(Component.text("Distance: "))
                        .append(Component.text((long) dist))
                        .build()
                );
                lore.add(Component.text()
                        .color(NamedTextColor.YELLOW)
                        .append(Component.text("Fuel: "))
                        .append(Component.text((long) Math.ceil(dist / DISTANCE_PER_FUEL)))
                        .build()
                );
            }

            return true;
        }, (player, pw) -> {
            player.closeInventory();
            int usedFuel = (int) Math.ceil((pw.distanceTo(world) * Util.KM_PER_LY) / DISTANCE_PER_FUEL);
            player.sendMessage(ChatColor.YELLOW + "You are going to " + pw.name() + " and will use " +
                    usedFuel + " fuel. Are you sure you want to do that? (yes/no)");
            ChatUtils.awaitInput(player, (input) -> {
                if (input.equalsIgnoreCase("yes")) {
                    p.sendMessage(ChatColor.YELLOW + "Please enter destination coordinates in the form of <x> <z> (i.e. -123 456):");
                    ChatUtils.awaitInput(p, (response) -> {
                        String trimmed = response.trim();
                        if (Util.COORD_PATTERN.matcher(trimmed).matches()) {
                            String[] split = Util.SPACE_PATTERN.split(trimmed);
                            int x = Integer.parseInt(split[0]);
                            int z = Integer.parseInt(split[1]);
                            launch(player, b, pw, fuel - usedFuel, fuelType, x, z);
                        } else {
                            p.sendMessage(ChatColor.RED + "Invalid coordinate format! Please use the format <x> <z>");
                        }
                    });
                }
            });
        }).open(p);
    }

    public void launch(@Nonnull Player p, @Nonnull Block rocket, PlanetaryWorld worldTo, int fuelLeft, String fuelType, int x, int z) {
        BlockStorage.addBlockInfo(rocket, "isLaunching", "true");

        World world = p.getWorld();

        new BukkitRunnable() {
            private final Block pad = rocket.getRelative(BlockFace.DOWN);
            private int times = 0;

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
        }.runTaskTimer(Galactifun.instance(), 0, 10);

        World to = worldTo.world();

        Chunk destChunk = to.getChunkAt(x >> 4, z >> 4);
        if (!destChunk.isLoaded()) {
            destChunk.load(true);
        }

        Scheduler.run(40, sendRandomMessage(p));
        Scheduler.run(80, sendRandomMessage(p));
        Scheduler.run(120, sendRandomMessage(p));
        Scheduler.run(160, sendRandomMessage(p));
        Scheduler.run(200, () -> {
            p.sendMessage(ChatColor.GOLD + "Verifying blast awesomeness...");

            Block destBlock = null;
            for (int y = to.getMaxHeight(); y > to.getMinHeight(); y--) {
                Block b = to.getBlockAt(x, y, z);
                if (b.isSolid() && !BlockStorage.check(b, BaseItems.LANDING_HATCH.getItemId())) {
                    destBlock = b.getRelative(BlockFace.UP);
                    break;
                }
            }

            if (destBlock == null) {
                destBlock = to.getBlockAt(x, to.getMaxHeight() - 4, z);
            }

            destBlock.setType(Material.CHEST);
            BlockState state = PaperLib.getBlockState(destBlock, false).getState();
            if (state instanceof Chest chest) {
                Inventory inv = chest.getInventory();
                inv.addItem(this.getItem().clone());

                SlimefunItem sfi = SlimefunItem.getById(fuelType);
                ItemStack fuel = sfi == null ? new ItemStack(Material.valueOf(fuelType)) : sfi.getItem();
                fuel = fuel.clone();
                fuel.setAmount(fuelLeft);
                inv.addItem(fuel);
            }
            state.update();

            boolean showLaunchAnimation = false;
            for (Entity entity : world.getEntities()) {
                if ((entity instanceof LivingEntity && !(entity instanceof ArmorStand)) || entity instanceof Item) {
                    if (entity.getLocation().distanceSquared(rocket.getLocation()) <= 25) {
                        if (entity instanceof Player){
                            entity.setMetadata("CanTpAlienWorld", new FixedMetadataValue(Galactifun.instance(), true));
                        }
                        PaperLib.teleportAsync(entity, destBlock.getLocation().add(0, 1, 0));

                        if (KnowledgeLevel.get(p, worldTo) == KnowledgeLevel.NONE) {
                            KnowledgeLevel.BASIC.set(p, worldTo);
                        }

                    } else if (entity.getLocation().distance(rocket.getLocation()) <= 64) {
                        if (entity instanceof Player) {
                            showLaunchAnimation = true;
                        }
                    }
                }
            }
            //Launch animation

            if (showLaunchAnimation) {
                Location rocketLocation = rocket.getLocation().add(0.5, -1, 0.5);
                ArmorStand armorStand = rocketLocation.getWorld().spawn(rocketLocation, ArmorStand.class);
                armorStand.getEquipment().setHelmet(GalactifunHead.ROCKET);
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setMarker(false);
                armorStand.setBasePlate(false);

                new BukkitRunnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        i++;
                        armorStand.setVelocity(new Vector(0, 0.8 + i / 10, 0));
                        rocketLocation.getWorld().spawnParticle(Particle.FLAME, armorStand.getLocation(), 5);
                        rocketLocation.getWorld().spawnParticle(Particle.LAVA, armorStand.getLocation(), 5);
                        if (i > 40) {
                            armorStand.remove();
                            this.cancel();
                        }
                    }
                }.runTaskTimer(Galactifun.instance(), 0, 8);
            }

            rocket.setType(Material.AIR);
            BlockStorage.clearBlockInfo(rocket);
        });
    }

}
