package io.github.addoncommunity.galactifun.core.profile;

import org.bukkit.inventory.ItemStack;

/**
 * A small utility enum that defines a few different paths and slots for items in the {@link GalacticProfile}'s menu
 * 
 * @author Mooy1
 */
enum ItemComponent {

    // TODO add empty items
    HELMET("helmet", null),
    CHESTPLATE("chestplate", null),
    LEGGINGS("leggings", null),
    BOOTS("boots", null),
    OXYGEN("oxygen", null),
    UPGRADES("upgrades", null);

    final String path;
    final int[] slots;
    final ItemStack empty;

    ItemComponent(String path, ItemStack empty, int... slots) {
        this.path = path + ".";
        this.slots = slots;
        this.empty = empty;
    }
    
    static final ItemComponent[] components = values();

}
