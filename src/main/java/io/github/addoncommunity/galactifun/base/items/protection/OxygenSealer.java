package io.github.addoncommunity.galactifun.base.items.protection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.HologramOwner;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public final class OxygenSealer extends MenuBlock implements EnergyNetComponent, HologramOwner {

    private static final String PROTECTING = "oxygenating";
    private static final String NO_OXYGEN = "no_oxygen";
    private static final Set<BlockPosition> allBlocks = new HashSet<>();
    private static final int OXYGEN_SLOT = 4;
    private static int counter = 0;
    private final int range;

    public OxygenSealer(SlimefunItemStack item, ItemStack[] recipe, int range) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.range = range;

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                allBlocks.add(new BlockPosition(b));

                int req = 64;
                if (getCharge(b.getLocation()) < req) {
                    BlockStorage.addBlockInfo(b, PROTECTING, "false");
                } else {
                    BlockStorage.addBlockInfo(b, PROTECTING, "true");
                    removeCharge(b.getLocation(), req);
                }
            }

            @Override
            public void uniqueTick() {
                // to prevent memory leaks if something happens (block breaks aren't the only thing that can)
                allBlocks.removeIf(pos -> !(BlockStorage.check(pos.toLocation()) instanceof OxygenSealer));

                // every 6 slimefun ticks (every 3 seconds)
                if (counter < 6) {
                    counter++;
                } else {
                    counter = 0;
                    OxygenSealer.this.uniqueTick();
                }
            }
        });
    }

    private void uniqueTick() {
        //noinspection deprecation
        Galactifun.protectionManager().clearOxygenBlocks();
        for (BlockPosition l : allBlocks) {
            updateProtections(l);
        }
    }


    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        updateProtections(new BlockPosition(b));
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu) {
        removeHologram(e.getBlock());
        allBlocks.remove(new BlockPosition(e.getBlock()));
        uniqueTick();
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        for (int i = 0; i < 9; i++) {
            if (i == 4) continue;
            preset.addItem(i, MenuBlock.BACKGROUND_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    protected int[] getInputSlots() {
        return new int[] { OXYGEN_SLOT };
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    @Override
    public int getCapacity() {
        return 256;
    }

    private void updateProtections(BlockPosition pos) {
        Location l = pos.toLocation();
        Block b = pos.getBlock();

        if (!BSUtils.getStoredBoolean(l, PROTECTING)) {
            updateHologram(b, "&cNot Enough Energy");
            return;
        }

        BlockMenu menu = BlockStorage.getInventory(b);
        if (!SlimefunUtils.isItemSimilar(menu.getItemInSlot(OXYGEN_SLOT), Gas.OXYGEN.item(), false, false)) {
            updateHologram(b, "&cNo Oxygen");
            BSUtils.addBlockInfo(b, NO_OXYGEN, true);
            return;
        }
        // to protect against people looping back and forth one oxygen tank and tricking it into thinking there is oxygen
        if (Galactifun.slimefunTickCount() % 18 == 0 || BSUtils.getStoredBoolean(l, NO_OXYGEN)) {
            menu.consumeItem(OXYGEN_SLOT);
            BSUtils.addBlockInfo(b, NO_OXYGEN, false);
        }

        int range = this.range;
        for (BlockFace face : Util.SURROUNDING_FACES) {
            if (BlockStorage.check(b.getRelative(face), BaseItems.SUPER_FAN.getItemId())) {
                range += range * 0.15;
            }
        }

        // check if sealed using flood fill
        Optional<Set<BlockPosition>> returned = Util.floodFill(l, range);
        // not sealed; continue on to the next block
        if (returned.isEmpty()) {
            updateHologram(b, "&cArea Not Sealed or Too Big");
            return;
        }

        for (BlockPosition bp : returned.get()) {
            // add a protection to the location
            Galactifun.protectionManager().addOxygenBlock(bp);
        }

        updateHologram(b, "&aOperational");
    }

}
