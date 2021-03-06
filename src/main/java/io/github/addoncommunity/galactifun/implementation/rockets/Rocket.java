package io.github.addoncommunity.galactifun.implementation.rockets;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.Heads;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

@Getter
@AllArgsConstructor
public enum Rocket {
    ONE(1, 10, 9, new ItemStack[0]),
    TWO(2, 100, 18, new ItemStack[0]),
    ;

    private final int tier;
    private final int fuelCapacity;
    private final int storageCapacity;
    @Nonnull
    private final ItemStack[] recipe;
    @Nonnull
    private final SlimefunItemStack item = createItem();

    @Nonnull
    private SlimefunItemStack createItem() {
        return new SlimefunItemStack(
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
            new SlimefunItem(Categories.MAIN_CATEGORY, rocket.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, rocket.getRecipe()).register(addon);
        }
    }
}
