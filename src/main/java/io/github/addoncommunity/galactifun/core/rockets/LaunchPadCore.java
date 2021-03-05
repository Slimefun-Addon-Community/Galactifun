package io.github.addoncommunity.galactifun.core.rockets;

import io.github.addoncommunity.galactifun.core.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.core.lists.Heads;
import io.github.mooy1.infinitylib.abstracts.AbstractTicker;
import io.github.thebusybiscuit.slimefun4.api.events.PlayerRightClickEvent;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Optional;

public class LaunchPadCore extends AbstractTicker {

    private static final BlockFace[] faces = new BlockFace[]{BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 16, 17, 23, 24, 25, 26, 32, 34, 35, 41, 43, 44, 50, 51, 52, 53};
    private static final int[] BORDER = new int[]{18, 19, 20, 21, 22, 31, 40, 49};

    private static final int[] INVENTORY_SLOTS = new int[]{27, 28, 29, 30, 36, 37, 38, 39, 45, 46, 47, 48};

    public LaunchPadCore(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler((BlockUseHandler) this::onInteract);
    }

    @Override
    protected void tick(@Nonnull BlockMenu blockMenu, @Nonnull Block block, @Nonnull Config config) {

    }

    @Override
    protected void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);

        for (int i : BORDER) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture(), ChestMenuUtils.getEmptyClickHandler());
        }

        preset.addItem(33, new CustomItem(
            HeadTexture.FUEL_BUCKET.getAsItemStack(),
            "&6Put Fuel Here"
        ), ChestMenuUtils.getEmptyClickHandler());

        preset.addItem(6, new CustomItem(
            Heads.ROCKET.getAsItemStack(),
            "&4Insert Rocket Here"
        ), ChestMenuUtils.getEmptyClickHandler());
    }

    @Nonnull
    @Override
    protected int[] getTransportSlots(@Nonnull DirtyChestMenu dirtyChestMenu, @Nonnull ItemTransportFlow itemTransportFlow, ItemStack itemStack) {
        return new int[]{42};
    }

    private void onInteract(PlayerRightClickEvent e) {
        Optional<Block> ob = e.getClickedBlock();
        if (ob.isPresent()) {
            Block b = ob.get();
            Player p = e.getPlayer();
            if (isSurroundedByFloors(b)) {
                BlockStorage.getInventory(b).open(p);
            } else {
                p.sendMessage(ChatColor.RED + "Surround this block with 9 launch pad floors before attempting to use it");
                e.cancel();
            }
        }
    }

    private boolean isSurroundedByFloors(Block b) {
        for (BlockFace face : faces) {
            if (!BlockStorage.check(b.getRelative(face), GalactifunItems.LAUNCH_PAD_FLOOR.getItemId())) {
                return false;
            }
        }

        return true;
    }
}
