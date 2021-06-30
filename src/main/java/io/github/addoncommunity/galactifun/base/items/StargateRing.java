package io.github.addoncommunity.galactifun.base.items;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.util.BSUtils;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class StargateRing extends SlimefunItem {

    public StargateRing(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        addItemHandler(new BlockBreakHandler(true, true) {
            @Override
            @ParametersAreNonnullByDefault
            public void onPlayerBreak(BlockBreakEvent e, ItemStack item, List<ItemStack> drops) {
                if (BSUtils.getStoredBoolean(e.getBlock().getLocation(), "locked")) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Deactivate the Stargate before destroying it");
                }
            }
        });
    }
}
