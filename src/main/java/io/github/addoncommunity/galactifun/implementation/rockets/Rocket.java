package io.github.addoncommunity.galactifun.implementation.rockets;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.Heads;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Getter
public enum Rocket {
    ONE(1, 10, 9, new ItemStack[9]),
    TWO(2, 100, 18, new ItemStack[9]),
    ;

    private final int tier;
    private final int fuelCapacity;
    private final int storageCapacity;
    @Nonnull
    private final ItemStack[] recipe;
    @Nonnull
    private final SlimefunItemStack item;

    Rocket(int tier, int fuelCapacity, int storageCapacity, @Nonnull ItemStack... recipe) {
        this.tier = tier;
        this.fuelCapacity = fuelCapacity;
        this.storageCapacity = storageCapacity;
        this.recipe = recipe;
        this.item = new SlimefunItemStack(
            "ROCKET_TIER_" + this.name(),
            Heads.ROCKET.getTexture(),
            "&4Rocket Tier " + tier,
            "",
            "&7Fuel Capacity: " + fuelCapacity,
            "&7Cargo Capacity: " + storageCapacity
        );
    }

    public static void setup(Galactifun addon) {
        for (Rocket rocket : Rocket.values()) {
            new RocketItem(Categories.MAIN_CATEGORY, rocket.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, rocket.getRecipe()).register(addon);
        }
    }

    @Nullable
    public static Rocket getById(String id) {
        for (Rocket rocket : Rocket.values()) {
            if (rocket.getItem().getItemId().equals(id)) {
                return rocket;
            }
        }

        return null;
    }

    private static class RocketItem extends SlimefunItem {

        public RocketItem(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
            super(category, item, recipeType, recipe);

            addItemHandler((BlockUseHandler) e -> launch(e.getPlayer()));

            addItemHandler(new BlockPlaceHandler(true) {
                @Override
                public void onPlayerPlace(BlockPlaceEvent e) {
                    Block b = e.getBlock();
                    BlockData data = b.getBlockData();
                    if (data instanceof Rotatable) {
                        ((Rotatable) data).setRotation(BlockFace.NORTH);
                    }
                    b.setBlockData(data, true);
                }
            });
        }

        private void launch(Player p) {

        }
    }
}
