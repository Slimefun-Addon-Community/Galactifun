package io.github.addoncommunity.galactifun.implementation.rockets;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class LaunchPadCore extends AbstractTicker {

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 23, 25, 26, 32, 34, 35, 41, 42, 43, 44, 50, 51, 52, 53};
    private static final int[] BORDER = new int[]{18, 19, 20, 21, 22, 31, 40, 49};

    private static final int[] INVENTORY_SLOTS = new int[]{27, 28, 29, 30, 36, 37, 38, 39, 45, 46, 47, 48};

    @Getter
    private static final BiMap<ItemStack, Double> fuels = HashBiMap.create();

    public LaunchPadCore() {
        super(Categories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9]);

        addItemHandler((BlockUseHandler) this::onInteract);
    }

    @Override
    protected void tick(@Nonnull BlockMenu menu, @Nonnull Block block, @Nonnull Config config) {
        Block b = block.getRelative(BlockFace.UP);
        for (Rocket rocket : Rocket.values()) {
            if (BlockStorage.check(b, rocket.getItem().getItemId())) {
                String s = BlockStorage.getLocationInfo(b.getLocation(), "isLaunching");
                if (Boolean.parseBoolean(s)) return;

                s = BlockStorage.getLocationInfo(b.getLocation(), "fuel");
                int fuel = 0;
                if (s != null) {
                    fuel = Integer.parseInt(s);
                }
                s = BlockStorage.getLocationInfo(b.getLocation(), "fuelEff");
                double eff = 0;
                if (s != null) {
                    eff = Double.parseDouble(s);
                }

                if (fuel < rocket.getFuelCapacity()) {
                    ItemStack slotItem = menu.getItemInSlot(33);
                    for (ItemStack stack : fuels.keySet()) {
                        if (SlimefunUtils.isItemSimilar(slotItem, stack, false, false)) {
                            menu.consumeItem(33);
                            fuel++;
                            eff += fuels.get(stack);
                        }
                    }

                    BlockStorage.addBlockInfo(b, "fuel", Integer.toString(fuel));
                    BlockStorage.addBlockInfo(b, "fuelEff", Double.toString(eff));
                }

                break;
            }
        }
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu, @Nonnull Location l) {
        World world = l.getWorld();
        for (int i : INVENTORY_SLOTS) {
            world.dropItemNaturally(l, menu.getItemInSlot(i));
        }
        world.dropItemNaturally(l, menu.getItemInSlot(33));

        Block rocketBlock = l.add(0, 1, 0).getBlock();
        Rocket rocket = Rocket.getById(BlockStorage.checkID(rocketBlock));
        if (rocket != null) {
            rocketBlock.setType(Material.AIR);
            BlockStorage.clearBlockInfo(rocketBlock);
            world.dropItemNaturally(rocketBlock.getLocation(), rocket.getItem().clone());
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
        return new int[]{33};
    }

    private void onInteract(@Nonnull PlayerRightClickEvent e) {
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

    private boolean isSurroundedByFloors(Block b) {
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
