package io.github.addoncommunity.galactifun.base.items;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.addoncommunity.galactifun.api.items.Rocket;
import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import io.github.addoncommunity.galactifun.base.GalactifunItems;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.slimefun.abstracts.TickingContainer;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public final class LaunchPadCore extends TickingContainer {

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

    @Getter
    private static final BiMap<ItemStack, Double> fuels = HashBiMap.create();

    public LaunchPadCore() {
        super(CoreCategories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            SlimefunItems.REINFORCED_PLATE, Components.NOZZLE.getItem(), SlimefunItems.REINFORCED_PLATE,
            SlimefunItems.CARGO_MOTOR, SlimefunItems.OIL_PUMP, SlimefunItems.CARGO_MOTOR,
            SlimefunItems.REINFORCED_PLATE, Components.ADVANCED_PROCESSING_UNIT.getItem(), SlimefunItems.REINFORCED_PLATE,
        });


        addItemHandler((BlockUseHandler) LaunchPadCore::onInteract);
    }

    @Override
    protected void tick(@Nonnull BlockMenu menu, @Nonnull Block block, @Nonnull Config config) {
        Block b = block.getRelative(BlockFace.UP);
        
        SlimefunItem sfItem = BlockStorage.check(b);
        if (!(sfItem instanceof Rocket)) {
            return;
        }
        Rocket rocket = (Rocket) sfItem;
        
        Location l = b.getLocation();
        
        String s = BlockStorage.getLocationInfo(l, "isLaunching");
        if (Boolean.parseBoolean(s)) return;

        s = BlockStorage.getLocationInfo(l, "fuel");
        int fuel = 0;
        if (s != null) {
            fuel = Integer.parseInt(s);
        }
        s = BlockStorage.getLocationInfo(l, "fuelEff");
        double eff = 0;
        if (s != null) {
            eff = Double.parseDouble(s);
        }

        if (fuel < rocket.getFuelCapacity()) {
            // TODO improve a lot
            ItemStack slotItem = menu.getItemInSlot(FUEL_SLOT);
            for (ItemStack stack : fuels.keySet()) {
                if (SlimefunUtils.isItemSimilar(slotItem, stack, false, false)) {
                    menu.consumeItem(FUEL_SLOT);
                    fuel++;
                    eff += fuels.get(stack);
                    break;
                }
            }

            BlockStorage.addBlockInfo(l, "fuel", Integer.toString(fuel));
            BlockStorage.addBlockInfo(l, "fuelEff", Double.toString(eff));
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        menu.dropItems(l, INVENTORY_SLOTS);
        menu.dropItems(l, FUEL_SLOT);

        Block rocketBlock = e.getBlock().getRelative(BlockFace.UP);
        SlimefunItem rocket = BlockStorage.check(rocketBlock);
        if (rocket instanceof Rocket) {
            rocketBlock.setType(Material.AIR);
            BlockStorage.clearBlockInfo(rocketBlock);
            e.getBlock().getWorld().dropItemNaturally(rocketBlock.getLocation(), rocket.getItem().clone());
        }
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);

        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(24, new CustomItem(
            HeadTexture.FUEL_BUCKET.getAsItemStack(),
            "&6Insert Fuel Here"
        ), ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        if (itemTransportFlow == ItemTransportFlow.INSERT) {
            return new int[]{FUEL_SLOT};
        } else {
            return new int[0];
        }
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
            if (!BlockStorage.check(b.getRelative(face), GalactifunItems.LAUNCH_PAD_FLOOR.getItemId())) {
                return false;
            }
        }

        return true;
    }

    public static void addFuel(ItemStack fuel, double efficiency) {
        fuels.put(fuel, efficiency);
    }
}
