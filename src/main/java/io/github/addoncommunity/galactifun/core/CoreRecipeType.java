package io.github.addoncommunity.galactifun.core;

import io.github.mooy1.infinitylib.core.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

@UtilityClass
public final class CoreRecipeType {

    public static final RecipeType ALIEN_DROP = new RecipeType(PluginUtils.getKey("alien_drop"), RecipeType.MOB_DROP.toItem());
    public static final RecipeType WORLD_GEN = new RecipeType(PluginUtils.getKey("world_gen"), new CustomItem(
            Material.END_STONE,
            "&fNaturally Generated",
            "",
            "&7Find this material on a alien world"
    ));
    
}
