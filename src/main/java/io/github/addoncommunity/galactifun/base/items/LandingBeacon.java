package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.AbstractContainer;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

public final class LandingBeacon extends AbstractContainer {

    private static final int[] BACKGROUND = new int[] { 5, 6, 7, 8, 14, 15, 16, 17, 23, 25, 26, 32, 33, 34, 35, 41, 42, 43, 44 };
    private static final int[] BORDER = new int[] { 0, 1, 2, 3, 4, 13, 22, 31, 36, 37, 38, 39, 40 };
    private static final int MODE_SLOT = 24;

    private static final int[] INPUT_SLOTS = new int[] { 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30 };

    public LandingBeacon() {
        super(CoreCategory.EQUIPMENT, BaseItems.LANDING_BEACON, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, null, null,
                SlimefunItems.GPS_MARKER_TOOL, new ItemStack(Material.BEACON), SlimefunItems.GPS_MARKER_TOOL,
                BaseMats.SPACE_GRADE_PLATE, BaseMats.SPACE_GRADE_PLATE, BaseMats.SPACE_GRADE_PLATE
        });
    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);

        for (int i : BORDER) {
            preset.addItem(i, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(MODE_SLOT, Mode.ALLOW_ALL.item());
    }

    @Override
    protected void onPlace(@Nonnull BlockPlaceEvent e, @Nonnull Block b) {
        BlockStorage.addBlockInfo(b, "mode", Mode.ALLOW_ALL.name());
        BlockStorage.addBlockInfo(b, "player", e.getPlayer().getUniqueId().toString());
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(MODE_SLOT, (p, slot, item, action) -> {
            Mode current = Mode.valueOf(BlockStorage.getLocationInfo(b.getLocation(), "mode"));
            int nextNum = current.ordinal() + 1;
            Mode next = Mode.values()[nextNum >= Mode.values().length ? 0 : nextNum];
            BlockStorage.addBlockInfo(b, "mode", next.name());
            menu.replaceExistingItem(MODE_SLOT, next.item());
            return false;
        });
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu menu, @Nonnull ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return INPUT_SLOTS;
        }

        return new int[0];
    }

    @Getter
    @AllArgsConstructor
    public enum Mode {
        ALLOW_ALL(new CustomItem(
                Material.GREEN_DYE,
                "&aAllow All",
                "",
                "&7Allow anyone to land on",
                "&7this Landing Beacon"
        )),
        PLAYER_ONLY(new CustomItem(
                Material.YELLOW_DYE,
                "&ePlayer Only",
                "",
                "&7Allow only you to land on",
                "&7this Landing Beacon"
        )),
        DISABLED(new CustomItem(
                Material.RED_DYE,
                "&cDisabled",
                "",
                "&7This Landing Beacon is disabled;",
                "&7no one can land on it"
        ));

        private final ItemStack item;
    }

}
