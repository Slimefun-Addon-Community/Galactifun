package io.github.addoncommunity.galactifun.core;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.core.categories.AssemblyCategory;
import io.github.addoncommunity.galactifun.core.categories.GalacticCategory;
import io.github.mooy1.infinitylib.categories.MultiCategory;
import io.github.mooy1.infinitylib.categories.SubCategory;
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
    public static final Category BLOCKS = new SubCategory(
            Galactifun.inst().getKey("blocks"), new CustomItem(Material.COBBLESTONE, "&fGalactifun Blocks")
    );

    public static void setup(Galactifun galactifun) {
        Category universe = new GalacticCategory(galactifun.getKey("galactic_flex"),
                new CustomItem(Material.END_STONE, "&bThe Universe"), galactifun.getGalacticExplorer());

        Category assembly = new AssemblyCategory(galactifun.getKey("assembly_flex"),
                new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes"));

        new MultiCategory(galactifun.getKey("main"),
                new CustomItem(Material.BEACON, "&bGalactifun"),
                EQUIPMENT, ITEMS, COMPONENTS, MACHINES, BLOCKS, universe, assembly
        ).register(galactifun);
    }
    
}
