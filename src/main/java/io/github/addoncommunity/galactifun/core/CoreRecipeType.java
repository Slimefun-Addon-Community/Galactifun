package io.github.addoncommunity.galactifun.core;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

// TODO find a better place for these, maybe make an AlienDrop and WorldGenBlock class which extend SlimefunItem
@UtilityClass
public final class CoreRecipeType {

    public static final RecipeType ALIEN_DROP = new RecipeType(AbstractAddon.createKey("alien_drop"), RecipeType.MOB_DROP.toItem());
    public static final RecipeType WORLD_GEN = new RecipeType(AbstractAddon.createKey("world_gen"), new CustomItemStack(
            Material.END_STONE,
            "&fNaturally Generated",
            "",
            "&7Find this material on a alien world"
    ));

}
