package io.github.addoncommunity.galactifun.api.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Skull;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.items.knowledge.KnowledgeLevel;
import io.github.addoncommunity.galactifun.core.WorldSelector;
import io.github.addoncommunity.galactifun.core.managers.WorldManager;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.common.PersistentType;
import io.github.mooy1.infinitylib.common.Scheduler;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.RandomizedSet;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.libraries.paperlib.PaperLib;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class Rocket extends SlimefunItem implements RecipeDisplayItem {

    public static final NamespacedKey CARGO_KEY = Galactifun.createKey("cargo");

    // todo Move static to some sort of RocketManager
    private static final List<String> LAUNCH_MESSAGES = Galactifun.instance().getConfig().getStringList("rockets.launch-msgs");
    private static final double DISTANCE_PER_FUEL = 2_000_000 / Util.KM_PER_LY;

    @Getter
    private final int fuelCapacity;
    @Getter
    private final int storageCapacity;
    @Getter
    private final Map<String, Double> allowedFuels = new HashMap<>();

    public Rocket(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int fuelCapacity, int storageCapacity) {
        super(category, item, recipeType, recipe);

        this.fuelCapacity = fuelCapacity;
        this.storageCapacity = storageCapacity;
        for (Map.Entry<ItemStack, Double> entry : getAllowedFuels().entrySet()) {
            allowedFuels.put(StackUtils.getIdOrType(entry.getKey()), entry.getValue());
        }

        addItemHandler((BlockUseHandler) e -> e.getClickedBlock().ifPresent(block -> {
            e.cancel();
            openGUI(e.getPlayer(), block);
        }));

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

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent e, ItemStack itemStack, List<ItemStack> list) {
                if (Boolean.parseBoolean(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "isLaunching"))) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "The rocket is currently launching!");
                }
            }
        });
    }

    private void openGUI(@Nonnull Player p, @Nonnull Block b) {
        if (!BlockStorage.check(b, this.getId())) return;

        if (BSUtils.getStoredBoolean(b.getLocation(), "isLaunching")) {
            p.sendMessage(ChatColor.RED + "The rocket is already launching!");
            return;
        }

        WorldManager worldManager = Galactifun.worldManager();
        PlanetaryWorld currentWorld = worldManager.getWorld(p.getWorld());
        if (currentWorld == null) {
            p.sendMessage(ChatColor.RED + "You cannot travel to space from this world!");
            return;
        }

        int fuel = BSUtils.getStoredInt(b.getLocation(), "fuel");
        if (fuel == 0) {
            p.sendMessage(ChatColor.RED + "The rocket has no fuel!");
            return;
        }

        String fuelType = BlockStorage.getLocationInfo(b.getLocation(), "fuelType");
        if (fuelType == null) return;
        double eff = allowedFuels.get(fuelType);

        // ly
        double maxDistance = fuel * DISTANCE_PER_FUEL * eff;

        new WorldSelector((player, obj, lore) -> {
            if (obj instanceof PlanetaryWorld) {
                double distance = obj.distanceTo(currentWorld);
                if (distance > maxDistance) return false;

                lore.add(Component.empty());
                lore.add(Component.text()
                        .color(NamedTextColor.YELLOW)
                        .append(Component.text("Fuel: "))
                        .append(Component.text((long) Math.ceil(distance / (DISTANCE_PER_FUEL * eff))))
                        .build());
            }
            return true;
        }, (player, destination) -> {
            player.closeInventory();
            int usedFuel = (int) Math.ceil(destination.distanceTo(currentWorld) / (DISTANCE_PER_FUEL * eff));
            p.sendMessage(ChatColor.YELLOW + "Please enter destination coordinates in the form of <x> <z> (i.e. -123 456) or type in anything else to cancel:");
            ChatUtils.awaitInput(p, s -> {
                if (Util.COORD_PATTERN.matcher(s).matches()) {
                    String[] coords = Util.SPACE_PATTERN.split(s);
                    Block destBlock = Util.getHighestBlockAt(
                            destination.world(),
                            Integer.parseInt(coords[0]),
                            Integer.parseInt(coords[1]),
                            l -> (l.isBuildable() || l.isLiquid()) && !BlockStorage.check(l, BaseItems.LANDING_HATCH.getItemId())
                    );
                    destBlock.getChunk().load();
                    if (!destBlock.getWorld().getWorldBorder().isInside(destBlock.getLocation())) {
                        p.sendMessage(ChatColor.RED + "Destination is outside of world border");
                    } else if (!Slimefun.getProtectionManager().hasPermission(p, destBlock, Interaction.PLACE_BLOCK)) {
                        p.sendMessage(ChatColor.RED + "You do not have permission to land there");
                    } else {
                        Block down = destBlock.getRelative(BlockFace.DOWN);
                        if (down.getType() == Material.CHEST) {
                            destBlock = down;
                        } else {
                            destBlock.setType(Material.CHEST);
                        }
                        launch(
                                p,
                                b,
                                StackUtils.itemByIdOrType(fuelType).asQuantity(fuel - usedFuel),
                                destination,
                                destBlock
                        );
                    }
                } else {
                    p.sendMessage(ChatColor.RED + "Launch cancelled");
                }
            });
        }).open(p);
    }

    private void launch(Player p, Block rocket, ItemStack fuelLeft, PlanetaryWorld destination, final Block destBlock) {
        BSUtils.addBlockInfo(rocket, "isLaunching", true);

        World playerWorld = p.getWorld();
        new BukkitRunnable() {
            private final Block pad = rocket.getRelative(BlockFace.DOWN);
            private int times = 0;

            @Override
            public void run() {
                if (this.times++ < 20) {
                    for (BlockFace face : Util.SURROUNDING_FACES) {
                        Block block = this.pad.getRelative(face);
                        playerWorld.spawnParticle(getLaunchParticles(), block.getLocation(), 100, 0.5, 0.5, 0.5);
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(Galactifun.instance(), 0, 10);

        RandomizedSet<String> launchMessages = new RandomizedSet<>(LAUNCH_MESSAGES);
        sendLaunchMessage(40, p, launchMessages);
        sendLaunchMessage(80, p, launchMessages);
        sendLaunchMessage(120, p, launchMessages);
        sendLaunchMessage(160, p, launchMessages);
        Scheduler.run(200, () -> {
            p.sendMessage(ChatColor.YELLOW + "Verifying blast awesomeness...");
            Chest chest = (Chest) PaperLib.getBlockState(destBlock, false).getState();
            Inventory inv = chest.getBlockInventory();
            inv.addItem(fuelLeft);
            inv.addItem(getItem());
            PersistentDataContainer container = ((Skull) rocket.getState()).getPersistentDataContainer();
            container.getOrDefault(CARGO_KEY, PersistentType.ITEM_STACK_LIST, new ArrayList<>()).forEach(inv::addItem);

            boolean showLaunchAnimation = false;
            for (Entity entity : playerWorld.getEntities()) {
                if ((entity instanceof LivingEntity && !(entity instanceof ArmorStand)) || entity instanceof Item) {
                    if (entity.getLocation().distanceSquared(rocket.getLocation()) <= 25) {
                        if (entity instanceof Player) {
                            entity.setMetadata("CanTpAlienWorld", new FixedMetadataValue(Galactifun.instance(), true));
                        }
                        PaperLib.teleportAsync(entity, destBlock.getLocation().add(0, 1, 0));
                        if (KnowledgeLevel.get(p, destination) == KnowledgeLevel.NONE) {
                            KnowledgeLevel.BASIC.set(p, destination);
                        }
                    } else if (entity.getLocation().distance(rocket.getLocation()) <= 64) {
                        if (entity instanceof Player) {
                            showLaunchAnimation = true;
                        }
                    }
                }
            }

            // launch animation
            if (showLaunchAnimation) {
                Location rocketLocation = rocket.getLocation().add(0.5, -1, 0.5);
                ArmorStand armorStand = rocketLocation.getWorld().spawn(rocketLocation, ArmorStand.class);

                Skull skull = (Skull) PaperLib.getBlockState(rocket, false).getState();
                ItemStack stack = new ItemStack(skull.getType());
                stack.editMeta(meta -> ((SkullMeta) meta).setPlayerProfile(skull.getPlayerProfile()));

                armorStand.getEquipment().setHelmet(stack);
                armorStand.setInvisible(true);
                armorStand.setInvulnerable(true);
                armorStand.setMarker(false);
                armorStand.setBasePlate(false);

                new BukkitRunnable() {
                    private int i = 0;

                    @Override
                    public void run() {
                        i++;
                        armorStand.setVelocity(new Vector(0, 0.8 + i / 10D, 0));
                        rocketLocation.getWorld().spawnParticle(getLaunchParticles(), armorStand.getLocation(), 10);
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

    private static void sendLaunchMessage(int delay, Player p, RandomizedSet<String> choices) {
        String msg = choices.getRandom();
        choices.remove(msg);
        Scheduler.run(delay, () -> p.sendMessage(Component.text()
                .color(NamedTextColor.GOLD)
                .append(Component.text(msg))
                .append(Component.text("..."))
                .build()));
    }

    protected abstract Map<ItemStack, Double> getAllowedFuels();

    @Nonnull
    protected Particle getLaunchParticles() {
        return Particle.ASH;
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> ret = new ArrayList<>();

        for (Map.Entry<ItemStack, Double> entry : getAllowedFuels().entrySet()) {
            ret.add(new CustomItemStack(
                    entry.getKey(),
                    ItemUtils.getItemName(entry.getKey()),
                    "&7Efficiency: " + entry.getValue() + 'x'
            ));
        }

        return ret;
    }
}
