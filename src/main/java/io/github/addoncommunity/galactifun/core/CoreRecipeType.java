package io.github.addoncommunity.galactifun.core;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.Galactifun;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

// TODO find a better place for these, maybe make an AlienDrop and WorldGenBlock class which extend SlimefunItem
@UtilityClass
public final class CoreRecipeType {

    public static final RecipeType ALIEN_DROP = new RecipeType(Galactifun.inst().getKey("alien_drop"), RecipeType.MOB_DROP.toItem());
    public static final RecipeType WORLD_GEN = new RecipeType(Galactifun.inst().getKey("world_gen"), new CustomItem(
            Material.END_STONE,
            "&fNaturally Generated",
            "",
            "&7Find this material on a alien world"
    ));
    
}
