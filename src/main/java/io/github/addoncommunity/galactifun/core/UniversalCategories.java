package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

/**
 * Slimefun categories 
 * 
 * @author Mooy1
 */
@UtilityClass
public final class UniversalCategories {

    public static final UniversalCategory UNIVERSAL_CATEGORY = new UniversalCategory();
    public static final Category EQUIPMENT = new Category(PluginUtils.getKey("equipment"), new CustomItem(Material.IRON_HELMET));
    
    public static void setup(Galactifun galactifun) {
        UNIVERSAL_CATEGORY.register(galactifun);
        EQUIPMENT.register(galactifun);
    }
    
}
