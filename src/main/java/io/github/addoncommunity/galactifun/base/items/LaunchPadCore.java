package io.github.addoncommunity.galactifun.base.items;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.items.Rocket;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.common.StackUtils;
import io.github.mooy1.infinitylib.machines.TickingMenuBlock;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public final class LaunchPadCore extends TickingMenuBlock {

    private static final int[] BACKGROUND = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 10, 11, 12, 13, 14, 15, 16, 17,
            23, 25, 26,
            32, 34, 35,
            41, 42, 43, 44,
            50, 51, 52, 53
    };
    private static final int[] BORDER = {
            18, 19, 20, 21, 22, 31, 40, 49
    };
    private static final int[] INVENTORY_SLOTS = {
            27, 28, 29, 30, 36, 37, 38, 39, 45, 46, 47, 48
    };
    private static final int FUEL_SLOT = 33;

    // TODO improve fuel system
    public static final Map<String, Integer> FUELS = new HashMap<>();

    static {
        FUELS.put(SlimefunItems.OIL_BUCKET.getItemId(), 1);
        FUELS.put(SlimefunItems.FUEL_BUCKET.getItemId(), 2);
    }

    public LaunchPadCore(ItemGroup category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe);
        addItemHandler((BlockUseHandler) LaunchPadCore::onInteract);
    }

    @Override
    protected void tick(@Nonnull Block block, @Nonnull BlockMenu menu) {
        Block b = block.getRelative(BlockFace.UP);

        SlimefunItem sfItem = BlockStorage.check(b);
        if (!(sfItem instanceof Rocket rocket)) return;

        Location l = b.getLocation();

        String string = BlockStorage.getLocationInfo(l, "isLaunching");
        if (Boolean.parseBoolean(string)) return;

        string = BlockStorage.getLocationInfo(l, "fuel");
        int fuel = 0;
        if (string != null) {
            fuel = Integer.parseInt(string);
        }

        string = BlockStorage.getLocationInfo(l, "fuelType");

        if (fuel < rocket.fuelCapacity()) {
            ItemStack fuelItem = menu.getItemInSlot(FUEL_SLOT);
            if (fuelItem == null) return;
            String id = StackUtils.getId(fuelItem);

            if (id != null && FUELS.containsKey(id) && (string == null || id.equals(string))) {
                menu.consumeItem(FUEL_SLOT);
                BSUtils.addBlockInfo(l.getBlock(), "fuel", ++fuel);
                if (string == null) {
                    BlockStorage.addBlockInfo(l, "fuelType", id);
                }
            }
        }
    }

    public static boolean canBreak(@Nonnull Player p, @Nonnull Block b) {
        if (BSUtils.getStoredBoolean(b.getRelative(BlockFace.UP).getLocation(), "isLaunching")) {
            p.sendMessage(ChatColor.RED + "You cannot break the launchpad a rocket is launching on!");
            return false;
        }
        return true;
    }

    @Override
    protected void onBreak(BlockBreakEvent e, @Nonnull BlockMenu menu) {
        if (canBreak(e.getPlayer(), e.getBlock())) {
            Location l = e.getBlock().getLocation();
            menu.dropItems(l, INVENTORY_SLOTS);
            menu.dropItems(l, 33);

            Block rocketBlock = e.getBlock().getRelative(BlockFace.UP);
            SlimefunItem item = BlockStorage.check(rocketBlock);

            if (item instanceof Rocket) {
                World world = l.getWorld();
                rocketBlock.setType(Material.AIR);
                BlockStorage.clearBlockInfo(rocketBlock);
                world.dropItemNaturally(rocketBlock.getLocation(), item.getItem().clone());
            }
        } else {
            e.setCancelled(true);
        }
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);

        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(24, new CustomItemStack(
                HeadTexture.FUEL_BUCKET.getAsItemStack(),
                "&6Insert Fuel Here"
        ), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected int[] getInputSlots() {
        return new int[] {FUEL_SLOT};
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    private static void onInteract(@Nonnull PlayerRightClickEvent e) {
        Optional<Block> ob = e.getClickedBlock();
        if (ob.isPresent()) {
            Block b = ob.get();
            Player p = e.getPlayer();

            if (isSurroundedByFloors(b)) {
                SlimefunItem item = SlimefunItem.getByItem(p.getInventory().getItem(e.getHand()));
                if (item == null || !item.getId().startsWith("ROCKET_TIER_")) {
                    e.cancel();
                }

                BlockStorage.getInventory(b).open(p);
            } else {
                e.cancel();
                p.sendMessage(ChatColor.RED + "Surround this block with 8 launch pad floors before attempting to use it");
            }
        }
    }

    private static boolean isSurroundedByFloors(Block b) {
        for (BlockFace face : Util.SURROUNDING_FACES) {
            if (!BlockStorage.check(b.getRelative(face), BaseItems.LAUNCH_PAD_FLOOR.getItemId())) {
                return false;
            }
        }

        return true;
    }

}
