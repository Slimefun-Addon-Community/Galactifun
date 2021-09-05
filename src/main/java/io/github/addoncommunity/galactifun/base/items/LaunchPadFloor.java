package io.github.addoncommunity.galactifun.base.items;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.util.Util;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

public final class LaunchPadFloor extends SlimefunItem {

    public LaunchPadFloor(ItemGroup category, SlimefunItemStack itemStack, RecipeType type, ItemStack[] recipe) {
        super(category, itemStack, type, recipe);

        addItemHandler(new BlockBreakHandler(false, false) {
            @Override
            public void onPlayerBreak(@Nonnull BlockBreakEvent e, @Nonnull ItemStack itemStack, @Nonnull List<ItemStack> list) {
                for (BlockFace face : Util.SURROUNDING_FACES) {
                    Block b = e.getBlock().getRelative(face);
                    if (BlockStorage.check(b, BaseItems.LAUNCH_PAD_CORE.getItemId()) && !LaunchPadCore.canBreak(e.getPlayer(), b)) {
                        e.setCancelled(true);
                    }
                }
            }
        });
    }

}
