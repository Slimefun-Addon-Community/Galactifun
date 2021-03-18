package io.github.addoncommunity.galactifun.implementation.rockets;

import io.github.addoncommunity.galactifun.implementation.items.Components;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class LaunchPadFloor extends SlimefunItem {

    public LaunchPadFloor() {
        super(Categories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            null, null, null,
            Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
            Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
        });

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                for (BlockFace face : Util.SURROUNDING_FACES) {
                    Block b = e.getBlock().getRelative(face);
                    if (BlockStorage.check(b, GalactifunItems.LAUNCH_PAD_CORE.getItemId()) && !LaunchPadCore.canBreak(e.getPlayer(), b)) {
                        e.setCancelled(true);
                    }
                }
            }
        });
    }
}
