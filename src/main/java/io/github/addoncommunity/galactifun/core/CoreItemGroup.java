package io.github.addoncommunity.galactifun.core;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.core.categories.AssemblyItemGroup;
import io.github.addoncommunity.galactifun.core.categories.GalacticItemGroup;
import io.github.mooy1.infinitylib.groups.MultiGroup;
import io.github.mooy1.infinitylib.groups.SubGroup;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

/**
 * Slimefun item categories
 *
 * @author Mooy1
 */
// TODO move these categories somewhere not public, addons should use their own
@UtilityClass
public final class CoreItemGroup {

    /* cheat categories */
    public static final ItemGroup ASSEMBLY = new SubGroup(
            "assembly", CustomItemStack.create(Material.SMITHING_TABLE, "&fAssembly Table Recipes")
    );

    /* normal categories */
    public static final ItemGroup EQUIPMENT = new SubGroup(
            "equipment", CustomItemStack.create(Material.IRON_HELMET, "&fEquipment")
    );
    public static final ItemGroup ITEMS = new SubGroup(
            "items", CustomItemStack.create(GalactifunHead.ROCKET, "&fGalactifun")
    );
    public static final ItemGroup COMPONENTS = new SubGroup(
            "components", CustomItemStack.create(Material.IRON_INGOT, "&fGalactifun Components")
    );
    public static final ItemGroup MACHINES = new SubGroup(
            "machines", CustomItemStack.create(Material.REDSTONE_LAMP, "&fGalactifun Machines")
    );
    public static final ItemGroup BLOCKS = new SubGroup(
            "blocks", CustomItemStack.create(Material.COBBLESTONE, "&fGalactifun Blocks")
    );
    public static final ItemGroup RELICS = new SubGroup(
            "relics", CustomItemStack.create(Material.CHISELED_POLISHED_BLACKSTONE, "&fGalactifun Relics")
    );

    public static final AssemblyItemGroup ASSEMBLY_CATEGORY = new AssemblyItemGroup(
            Galactifun.createKey("assembly_flex"),
            CustomItemStack.create(Material.SMITHING_TABLE, "&fAssembly Table Recipes"));

    public static void setup(Galactifun galactifun) {
        ItemGroup universe = new GalacticItemGroup(Galactifun.createKey("galactic_flex"),
                CustomItemStack.create(Material.END_STONE, "&bThe Universe"));

        new MultiGroup("main",
                CustomItemStack.create(Material.BEACON, "&bGalactifun"),
                EQUIPMENT, ITEMS, COMPONENTS, MACHINES, BLOCKS, universe, ASSEMBLY_CATEGORY, RELICS
        ).register(galactifun);
    }

}
