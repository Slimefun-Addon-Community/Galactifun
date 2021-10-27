package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;

public class MoonCheese extends SimpleSlimefunItem<BlockPlaceHandler> {

    public MoonCheese(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Nonnull
    @Override
    public BlockPlaceHandler getItemHandler() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@Nonnull BlockPlaceEvent e) {
                e.setCancelled(true);
                Player p = e.getPlayer();
                p.setFoodLevel(Math.min(p.getFoodLevel() + 2, 20));
                p.setSaturation(p.getSaturation() + 2);
                ItemUtils.consumeItem(e.getItemInHand(), false);
            }
        };
    }

}
