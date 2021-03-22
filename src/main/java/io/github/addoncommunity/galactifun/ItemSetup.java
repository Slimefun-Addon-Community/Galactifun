package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.base.BaseRocket;
import io.github.addoncommunity.galactifun.base.items.Circuits;
import io.github.addoncommunity.galactifun.base.items.Components;
import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import io.github.addoncommunity.galactifun.base.GalactifunItems;
import io.github.addoncommunity.galactifun.base.RecipeTypes;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.addoncommunity.galactifun.base.items.CircuitPress;
import io.github.addoncommunity.galactifun.base.items.LaunchPadCore;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class ItemSetup {

    public static void setup(Galactifun addon) {
        LaunchPadCore.addFuel(SlimefunItems.OIL_BUCKET, 0.5);
        LaunchPadCore.addFuel(SlimefunItems.FUEL_BUCKET, 1);
        new LaunchPadCore().register(addon);

        new SlimefunItem(CoreCategories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            null, null, null,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
        }).register(addon);

        Metals.setup(addon);
        BaseRocket.setup(addon);
        Components.setup(addon);

        new CircuitPress().setCapacity(1024).setEnergyConsumption(25).setProcessingSpeed(1).register(addon);
        Circuits.setup(addon);
        new AssemblyTable().register(addon);

        // drops
        new SlimefunItem(CoreCategories.MAIN_CATEGORY, GalactifunItems.MUNPOWDER, RecipeTypes.ALIEN_DROP, new ItemStack[]{
            null, null, null,
            null, new CustomItem(Material.CREEPER_HEAD, "&fMutant Creeper"), null,
            null, null, null
        }).register(addon);

        new SlimefunItem(CoreCategories.MAIN_CATEGORY, GalactifunItems.FALLEN_METEOR, RecipeTypes.PLANET, new ItemStack[]{
            BaseRegistry.MARS.getItem(), null, null,
            null, null, null,
            null, null, null
        }).register(addon);
    }
}
