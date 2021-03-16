package io.github.addoncommunity.galactifun.implementation.lists;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.GalacticCategory;
import io.github.addoncommunity.galactifun.implementation.categories.RecipeCategory;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;

/**
 * Slimefun item categories 
 * 
 * @author Mooy1
 */
@UtilityClass
public final class Categories {

    public static final Category RECIPES = new RecipeCategory();
    public static final GalacticCategory GALACTIC_CATEGORY = new GalacticCategory();

    public static final Category EQUIPMENT = new Category(PluginUtils.getKey("equipment"), new CustomItem(Material.IRON_HELMET));
    public static final Category MAIN_CATEGORY = new Category(PluginUtils.getKey("main_category"), new CustomItem(Heads.ROCKET.getAsItemStack(), "&fGalactifun"));
    public static final Category COMPONENTS = new Category(PluginUtils.getKey("components"), new CustomItem(Material.IRON_INGOT, "&fGalactifun Components"));
    public static final Category MACHINES = new Category(PluginUtils.getKey("galactifun_machines"), new CustomItem(Material.REDSTONE_LAMP, "&fGalactifun Machines"));

    public static void setup(Galactifun galactifun) {
        GALACTIC_CATEGORY.register(galactifun);
        EQUIPMENT.register(galactifun);
        MAIN_CATEGORY.register(galactifun);
        COMPONENTS.register(galactifun);
        RECIPES.register(galactifun);
        MACHINES.register(galactifun);
    }
    
}
