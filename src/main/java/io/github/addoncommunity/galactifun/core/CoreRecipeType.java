package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.base.BaseItems;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

// TODO find a better place for these, maybe make an AlienDrop and WorldGenBlock class which extend SlimefunItem
@UtilityClass
public final class CoreRecipeType {

    public static final RecipeType ALIEN_DROP = new RecipeType(Galactifun.createKey("alien_drop"), RecipeType.MOB_DROP.toItem());
    public static final RecipeType ATMOSPHERIC_HARVESTER = new RecipeType(Galactifun.createKey("atmospheric_harvester"), BaseItems.ATMOSPHERIC_HARVESTER);
    public static final RecipeType CHEMICAL_REACTOR = new RecipeType(Galactifun.createKey("chemical_reactor"), BaseItems.CHEMICAL_REACTOR);
    public static final RecipeType WORLD_GEN = new RecipeType(Galactifun.createKey("world_gen"), new CustomItemStack(
            Material.END_STONE,
            "&fNaturally Generated",
            "",
            "&7Find this material on a alien world"
    ));

}
