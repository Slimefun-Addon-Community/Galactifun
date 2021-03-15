package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.base.BaseRegistry;
import io.github.addoncommunity.galactifun.implementation.items.Circuits;
import io.github.addoncommunity.galactifun.implementation.items.Components;
import io.github.addoncommunity.galactifun.implementation.items.Metals;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.lists.RecipeTypes;
import io.github.addoncommunity.galactifun.implementation.machines.AssemblyTable;
import io.github.addoncommunity.galactifun.implementation.machines.CircuitPress;
import io.github.addoncommunity.galactifun.implementation.rockets.LaunchPadCore;
import io.github.addoncommunity.galactifun.implementation.rockets.Rocket;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class Setup {

    private Setup() {
    }

    public static void setup(Galactifun addon) {
        LaunchPadCore.addFuel(SlimefunItems.OIL_BUCKET, 0.5);
        LaunchPadCore.addFuel(SlimefunItems.FUEL_BUCKET, 1);
        new LaunchPadCore().register(addon);

        new SlimefunItem(Categories.MAIN_CATEGORY, GalactifunItems.LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
            null, null, null,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
            SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
        }).register(addon);

        Metals.setup(addon);
        Rocket.setup(addon);
        Components.setup(addon);

        new CircuitPress().setCapacity(1024).setEnergyConsumption(25).register(addon);
        Circuits.setup(addon);
        new AssemblyTable().register(addon);

        // drops
        new SlimefunItem(Categories.MAIN_CATEGORY, GalactifunItems.MUNPOWDER, RecipeTypes.CUSTOM_MOB_DROP, new ItemStack[]{
            null, null, null,
            null, new CustomItem(Material.CREEPER_HEAD, "&fMutant Creeper"), null,
            null, null, null
        }).register(addon);

        new SlimefunItem(Categories.MAIN_CATEGORY, GalactifunItems.FALLEN_METEOR, RecipeTypes.PLANET, new ItemStack[]{
            BaseRegistry.MARS.getItem(), null, null,
            null, null, null,
            null, null, null
        }).register(addon);
    }
}
