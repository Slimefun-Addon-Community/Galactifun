package io.github.addoncommunity.galactifun.base.items;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.SneakyThrows;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

// TODO clean up if possible
public final class StargateController extends SlimefunItem implements Listener {

    private static final int[] BACKGROUND = new int[]{1, 2, 6, 7, 8};
    private static final int ADDRESS_SLOT = 3;
    private static final int DESTINATION_SLOT = 4;
    private static final int DEACTIVATE_SLOT = 5;

    private static final ComponentPosition[] RING_POSITIONS = new ComponentPosition[]{
            // bottom
            new ComponentPosition(0, 1),
            new ComponentPosition(0, -1),

            // corners
            new ComponentPosition(1, -2),
            new ComponentPosition(1, 2),
            new ComponentPosition(5, -2),
            new ComponentPosition(5, 2),

            // left side
            new ComponentPosition(2, 3),
            new ComponentPosition(3, 3),
            new ComponentPosition(4, 3),

            // right side
            new ComponentPosition(2, -3),
            new ComponentPosition(3, -3),
            new ComponentPosition(4, -3),

            // top
            new ComponentPosition(6, -1),
            new ComponentPosition(6, 0),
            new ComponentPosition(6, 1),
    };

    private static final ComponentPosition[] PORTAL_POSITIONS;

    static {
        List<ComponentPosition> portalPositions = new LinkedList<>(Arrays.asList(
                new ComponentPosition(1, -1),
                new ComponentPosition(1, 0),
                new ComponentPosition(1, 1)
        ));
        for (int y = 2; y <= 4; y++) {
            for (int z = -2; z <= 2; z++) {
                portalPositions.add(new ComponentPosition(y, z));
            }
        }
        portalPositions.add(new ComponentPosition(5, -1));
        portalPositions.add(new ComponentPosition(5, 0));
        portalPositions.add(new ComponentPosition(5, 1));

        PORTAL_POSITIONS = portalPositions.toArray(new ComponentPosition[0]);
    }

    public StargateController(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        Galactifun.inst().registerListener(this);

        addItemHandler((BlockUseHandler) e -> e.getClickedBlock().ifPresent(b -> onUse(e, e.getPlayer(), b)));

        addItemHandler(new BlockBreakHandler(true, true) {
            @Override
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                if (Boolean.parseBoolean(BlockStorage.getLocationInfo(e.getBlock().getLocation(), "locked"))) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Deactivate the Stargate before destroying it");
                }
            }
        });
    }

    public static boolean isPartOfStargate(@Nonnull Block b) {
        for (ComponentPosition position : RING_POSITIONS) {
            if (!position.isInSameRing(b)) {
                return false;
            }
        }

        return true;
    }

    @Nonnull
    public static Optional<List<Block>> getRingBlocks(@Nonnull Block b) {
        List<Block> rings = new ArrayList<>();
        for (ComponentPosition position : RING_POSITIONS) {
            if (position.isInSameRing(b)) {
                rings.add(position.getBlock(b));
            } else {
                return Optional.empty();
            }
        }

        return Optional.of(rings);
    }

    @Nonnull
    public static Optional<List<Block>> getPortalBlocks(@Nonnull Block b) {
        List<Block> portals = new ArrayList<>();
        for (ComponentPosition position : PORTAL_POSITIONS) {
            if (position.isPortal(b)) {
                portals.add(position.getBlock(b));
            } else {
                return Optional.empty();
            }
        }

        return Optional.of(portals);
    }

    public static void lockBlocks(Block controller, boolean lock) {
        String data = Boolean.toString(lock);
        getRingBlocks(controller).ifPresent(l -> l.forEach(b -> BlockStorage.addBlockInfo(b, "locked", data)));
        getPortalBlocks(controller).ifPresent(l -> l.forEach(b -> BlockStorage.addBlockInfo(b, "locked", data)));
    }

    private void onUse(PlayerRightClickEvent event, Player p, Block b) {
        if (!isPartOfStargate(b)) {
            p.sendMessage(ChatColor.RED + "The Stargate is not assembled!");
            return;
        }
        event.cancel();
        if (getPortalBlocks(b).isEmpty()) {
            for (ComponentPosition position : PORTAL_POSITIONS) {
                Block portal = position.getBlock(b);
                portal.setType(Material.END_GATEWAY);
                EndGateway gateway = (EndGateway) portal.getState();
                gateway.setAge(201);
                gateway.update(false, false);
                BlockStorage.addBlockInfo(portal, "portal", "true");
            }

            String destAddress = BlockStorage.getLocationInfo(b.getLocation(), "destination");
            if (destAddress != null) {
                setDestination(destAddress, b, p);
            }

            lockBlocks(b, true);
            p.sendMessage(ChatColor.YELLOW + "Stargate activated!");
            return;
        }

        ChestMenu menu = getMenu(b);
        menu.open(p);
    }

    @Nonnull
    @SneakyThrows(NoSuchAlgorithmException.class)
    private ChestMenu getMenu(@Nonnull Block b) {
        ChestMenu menu = new ChestMenu(this.getItemName());
        for (int i : BACKGROUND) {
            menu.addItem(i, MenuPreset.BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }

        Location l = b.getLocation();

        String address = BlockStorage.getLocationInfo(l, "gfsgAddress");
        if (address == null) {
            String lString = l.getWorld().getName() + l.getBlockX() + l.getBlockY() + l.getBlockZ();
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(lString.getBytes(StandardCharsets.UTF_8));
            address = String.format(
                    "%x%x%x%x%x%x",
                    hash[0],
                    hash[1],
                    hash[2],
                    hash[3],
                    hash[4],
                    hash[5]
            );
            BlockStorage.addBlockInfo(b, "gfsgAddress", address);
        }

        String destination = BlockStorage.getLocationInfo(l, "destination");
        destination = destination == null ? "" : destination;

        String temp = address;
        menu.addItem(ADDRESS_SLOT, new CustomItem(
                Material.BOOK,
                "&fAddress: " + address,
                "&7Click to send the address to chat"
        ), (p, i, s, c) -> {
            p.sendMessage(ChatColor.YELLOW + "Address: " + temp);
            p.closeInventory();
            return false;
        });

        menu.addItem(DEACTIVATE_SLOT, new CustomItem(
                Material.BARRIER,
                "&fClick to Deactivate the Stargate"
        ), (p, i, s, c) -> {
            getPortalBlocks(b).ifPresent(li -> {
                for (Block block : li) {
                    block.setType(Material.AIR);
                    BlockStorage.clearBlockInfo(block);
                }
            });
            lockBlocks(b, false);
            p.closeInventory();
            return false;
        });

        menu.addItem(DESTINATION_SLOT, new CustomItem(
                Material.RAIL,
                "&fClick to Set Destination",
                "&7Current Destination: " + destination
        ), (p, i, s, c) -> {
            p.sendMessage(ChatColor.YELLOW + "Type in the destination address");
            ChatUtils.awaitInput(p, st -> setDestination(st, b, p));
            p.closeInventory();
            return false;
        });

        return menu;
    }

    private void setDestination(String destination, Block b, Player p) {
        Location dest;
        worldLoop:
        {
            for (BlockStorage storage : SlimefunPlugin.getRegistry().getWorlds().values()) {
                for (Map.Entry<Location, Config> configEntry : storage.getRawStorage().entrySet()) {
                    String bAddress = configEntry.getValue().getString("gfsgAddress");
                    if (bAddress != null && bAddress.equals(destination)) {
                        dest = configEntry.getKey();
                        break worldLoop;
                    }
                }
            }
            p.sendMessage(ChatColor.RED + "No destination found!");
            return;
        }

        Optional<List<Block>> portalOptional = getPortalBlocks(b);
        if (portalOptional.isEmpty()) {
            p.sendMessage(ChatColor.RED + "The Stargate is not lit for some reason...");
            return;
        }

        for (Block portal : portalOptional.get()) {
            if (portal.getState() instanceof EndGateway gateway) {
                gateway.setExitLocation(dest);
                gateway.update(false, false);
            } else {
                portal.setType(Material.END_GATEWAY);
                EndGateway gateway = (EndGateway) portal.getState();
                gateway.setExitLocation(dest);
                gateway.update(false, false);
            }
        }

        p.sendMessage(ChatColor.YELLOW + String.format(
                "Set Stargate destination to %d %d %d in %s",
                dest.getBlockX(),
                dest.getBlockY(),
                dest.getBlockZ(),
                dest.getWorld().getName()
        ));

        BlockStorage.addBlockInfo(b, "destination", destination);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onGateBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (b.getType() == Material.END_GATEWAY &&
                Boolean.parseBoolean(BlockStorage.getLocationInfo(b.getLocation(), "locked"))) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Deactivate the Stargate before destroying it");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onUsePortal(PlayerTeleportEndGatewayEvent e) {
        if (!Boolean.parseBoolean(BlockStorage.getLocationInfo(e.getGateway().getLocation(), "portal"))) return;
        Location dest = e.getGateway().getExitLocation();
        if (dest == null) return;

        Block b = dest.getBlock();
        if (BlockStorage.check(b, BaseItems.STARGATE_CONTROLLER.getItemId()) &&
                StargateController.getPortalBlocks(b).isEmpty()) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "The destination Stargate is not activated");
        }
    }

    private static final record ComponentPosition(int y, int z) {

        public boolean isInSameRing(@Nonnull Block b) {
            return BlockStorage.check(b.getRelative(0, this.y, this.z)) instanceof StargateRing;
        }

        @Nonnull
        public Block getBlock(@Nonnull Block b) {
            return b.getRelative(0, this.y, this.z);
        }

        public boolean isPortal(@Nonnull Block b) {
            return b.getRelative(0, this.y, this.z).getType() == Material.END_GATEWAY;
        }
    }
}
