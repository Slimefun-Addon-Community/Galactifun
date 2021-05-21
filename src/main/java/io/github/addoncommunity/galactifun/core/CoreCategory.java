package io.github.addoncommunity.galactifun.core;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.core.categories.AssemblyCategory;
import io.github.addoncommunity.galactifun.core.categories.GalacticCategory;
import io.github.mooy1.infinitylib.slimefun.utils.MultiCategory;
import io.github.mooy1.infinitylib.slimefun.utils.SubCategory;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;

/**
 * Slimefun item categories 
 * 
 * @author Mooy1
 */
@UtilityClass
public final class CoreCategory {

    /* cheat categories */
    public static final Category ASSEMBLY = new SubCategory(
            Galactifun.inst().getKey("assembly"), new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes")
    );
    
    /* normal categories */
    public static final Category EQUIPMENT = new SubCategory(
            Galactifun.inst().getKey("equipment"), new CustomItem(Material.IRON_HELMET, "&fEquipment")
    );
    public static final Category ITEMS = new SubCategory(
            Galactifun.inst().getKey("items"), new CustomItem(GalactifunHead.ROCKET, "&fGalactifun")
    );
    public static final Category COMPONENTS = new SubCategory(
            Galactifun.inst().getKey("components"), new CustomItem(Material.IRON_INGOT, "&fGalactifun Components")
    );
    public static final Category MACHINES = new SubCategory(
            Galactifun.inst().getKey("machines"), new CustomItem(Material.REDSTONE_LAMP, "&fGalactifun Machines")
    );
    
    /* flex categories */
    private static final Category ASSEMBLY_FLEX = new AssemblyCategory(
            Galactifun.inst().getKey("assembly_flex"), new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes")
    );
    private static final Category GALACTIC_FLEX = new GalacticCategory(
            Galactifun.inst().getKey("galactic_flex"), new CustomItem(Material.END_STONE, "&bThe Universe")
    );
    
    /* multi category */
    private static final Category MAIN = new MultiCategory(Galactifun.inst().getKey("main"),
            new CustomItem(Material.BEACON, "&bGalactifun"),
            EQUIPMENT, ITEMS, COMPONENTS, MACHINES, GALACTIC_FLEX, ASSEMBLY_FLEX
    );

    public static void setup(Galactifun galactifun) {
        MAIN.register(galactifun);
        ASSEMBLY.register(galactifun);
    }
    
}
