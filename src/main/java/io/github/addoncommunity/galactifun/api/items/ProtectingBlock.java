package io.github.addoncommunity.galactifun.api.items;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
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
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public abstract class ProtectingBlock extends MenuBlock implements EnergyNetComponent, HologramOwner {

    protected static final String PROTECTING = "protecting";
    protected static final String ENABLED = "enabled";

    private static final Set<BlockPosition> allBlocks = new HashSet<>();
    private static final ItemStack ENABLED_ITEM = CustomItemStack.create(
            Material.STRUCTURE_VOID,
            "&aEnabled",
            "",
            "&7Click to disable"
    );
    private static final ItemStack DISABLED_ITEM = CustomItemStack.create(
            Material.BARRIER,
            "&cDisabled",
            "",
            "&7Click to enable"
    );
    private static int counter = 0;

    @ParametersAreNonnullByDefault
    public ProtectingBlock(SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, recipeType, recipe);

        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return true;
            }

            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                allBlocks.add(new BlockPosition(b));

                BlockMenu menu = BlockStorage.getInventory(b);
                int req = getEnergyRequirement();
                if (getCharge(b.getLocation()) < req || !BSUtils.getStoredBoolean(b.getLocation(), ENABLED)) {
                    BlockStorage.addBlockInfo(b, PROTECTING, "false");
                } else {
                    BlockStorage.addBlockInfo(b, PROTECTING, "true");
                    removeCharge(b.getLocation(), req);
                }

                ProtectingBlock.this.tick(b, menu);
            }

            @Override
            public void uniqueTick() {
                // to prevent memory leaks if something happens (block breaks aren't the only thing that can)
                allBlocks.removeIf(pos -> !(BlockStorage.check(pos.toLocation()) instanceof ProtectingBlock));

                // every 6 slimefun ticks (every 3 seconds)
                if (counter < 6) {
                    counter++;
                } else {
                    counter = 0;
                    ProtectingBlock.this.uniqueTick();
                }
            }
        });
    }

    private void uniqueTick() {
        //noinspection deprecation
        Galactifun.protectionManager().clearProtectedBlocks();
        for (BlockPosition l : new HashSet<>(allBlocks)) {
            updateProtections(l);
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b, ENABLED, "true");
        updateProtections(new BlockPosition(b));
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu menu) {
        removeHologram(e.getBlock());
        allBlocks.remove(new BlockPosition(e.getBlock()));
        uniqueTick();
    }

    @Override
    protected final void setup(@Nonnull BlockMenuPreset preset) {
        for (int i = 0; i < 9; i++) {
            if (i == 4) continue;
            preset.addItem(i, MenuBlock.BACKGROUND_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    @OverridingMethodsMustInvokeSuper
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        if (BSUtils.getStoredBoolean(b.getLocation(), ENABLED)) {
            menu.replaceExistingItem(4, ENABLED_ITEM);
            menu.addMenuClickHandler(4, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, ENABLED, "false");
                this.onNewInstance(menu, b);
                return false;
            });
        } else {
            menu.replaceExistingItem(4, DISABLED_ITEM);
            menu.addMenuClickHandler(4, (p, slot, item, action) -> {
                BlockStorage.addBlockInfo(b, ENABLED, "true");
                this.onNewInstance(menu, b);
                return false;
            });
        }
    }

    @Nonnull
    @Override
    public final EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }

    /**
     * @return energy per Slimefun tick
     */
    protected abstract int getEnergyRequirement();

    @Nonnull
    protected abstract AtmosphericEffect getEffect();

    public abstract int getProtection();

    public abstract int getRange();

    protected void tick(@Nonnull Block b, @Nonnull BlockMenu menu) {
    }

    private void updateProtections(@Nonnull BlockPosition pos) {
        Location l = pos.toLocation();
        Block b = pos.getBlock();
        if (!BSUtils.getStoredBoolean(l, ENABLED)) {
            updateHologram(b, "&cNot Enabled");
            return;
        }

        if (!BSUtils.getStoredBoolean(l, PROTECTING)) {
            updateHologram(b, "&cNot Enough Energy");
            return;
        }

        // removed all non-instances before, so safe cast
        ProtectingBlock inst = Objects.requireNonNull((ProtectingBlock) BlockStorage.check(l));

        int range = getRange();
        for (BlockFace face : Util.SURROUNDING_FACES) {
            if (BlockStorage.check(b.getRelative(face), BaseItems.SUPER_FAN.getItemId())) {
                range += range * 0.15;
            }
        }

        // check if sealed using flood fill
        Optional<Set<BlockPosition>> returned = Util.floodFill(l, range);
        // not sealed; continue on to the next block
        if (returned.isEmpty()) {
            updateHologram(pos.getBlock(), "&cArea Not Sealed or Too Big");
            return;
        }

        for (BlockPosition bp : returned.get()) {
            // add a protection to the location
            Galactifun.protectionManager().addProtection(bp, inst.getEffect(), inst.getProtection());
        }

        updateHologram(b, "&aOperational");
    }

    @Override
    protected int[] getInputSlots() {
        return new int[0];
    }

    @Override
    protected int[] getOutputSlots() {
        return new int[0];
    }

}
