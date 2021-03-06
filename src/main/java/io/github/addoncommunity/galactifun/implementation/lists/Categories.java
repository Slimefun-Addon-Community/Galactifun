package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.GalacticCategory;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

/**
 * Slimefun item categories 
 * 
 * @author Mooy1
 */
@UtilityClass
public final class Categories {

    public static final GalacticCategory GALACTIC_CATEGORY = new GalacticCategory();
    public static final Category EQUIPMENT = new Category(PluginUtils.getKey("equipment"), new CustomItem(Material.IRON_HELMET));
    public static final Category MAIN_CATEGORY = new Category(
        new NamespacedKey(Galactifun.getInstance(), "main_category"),
        new CustomItem(
            Heads.ROCKET.getAsItemStack(),
            "&fGalactifun"
        )
    );

    public static void setup(Galactifun galactifun) {
        GALACTIC_CATEGORY.register(galactifun);
        EQUIPMENT.register(galactifun);
        MAIN_CATEGORY.register(galactifun);
    }
    
}
