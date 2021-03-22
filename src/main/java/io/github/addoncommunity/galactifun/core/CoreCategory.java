package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.core.categories.AssemblyCategory;
import io.github.addoncommunity.galactifun.core.categories.GalacticCategory;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.mooy1.infinitylib.slimefun.utils.MultiCategory;
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
public final class CoreCategory {

    /* cheat categories */
    public static final Category ASSEMBLY = new Category(
            PluginUtils.getKey("assembly"), new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes")
    );
    
    /* normal categories */
    public static final Category EQUIPMENT = new Category(
            PluginUtils.getKey("equipment"), new CustomItem(Material.IRON_HELMET, "&fEquipment")
    );
    public static final Category ITEMS = new Category(
            PluginUtils.getKey("items"), new CustomItem(GalactifunHead.ROCKET, "&fGalactifun")
    );
    public static final Category COMPONENTS = new Category(
            PluginUtils.getKey("components"), new CustomItem(Material.IRON_INGOT, "&fGalactifun Components")
    );
    public static final Category MACHINES = new Category(
            PluginUtils.getKey("machines"), new CustomItem(Material.REDSTONE_LAMP, "&fGalactifun Machines")
    );
    
    /* flex categories */
    private static final Category ASSEMBLY_FLEX = new AssemblyCategory(
            PluginUtils.getKey("assembly_flex"), new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes")
    );
    private static final Category GALACTIC_FLEX = new GalacticCategory(
            PluginUtils.getKey("galactic_flex"), new CustomItem(Material.END_STONE, "&bThe Universe")
    );
    
    /* multi category */
    private static final Category MAIN = new MultiCategory(PluginUtils.getKey("main"),
            new CustomItem(Material.BEACON, "&bGalactifun"),
            EQUIPMENT, ITEMS, COMPONENTS, MACHINES, GALACTIC_FLEX, ASSEMBLY_FLEX
    );

    public static void setup(Galactifun galactifun) {
        MAIN.register(galactifun);
        ASSEMBLY.register(galactifun);
    }
    
}
