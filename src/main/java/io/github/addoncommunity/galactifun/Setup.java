package io.github.addoncommunity.galactifun;

import io.github.addoncommunity.galactifun.implementation.items.Circuits;
import io.github.addoncommunity.galactifun.implementation.items.Components;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.machines.AssemblyTable;
import io.github.addoncommunity.galactifun.implementation.machines.CircuitPress;
import io.github.addoncommunity.galactifun.implementation.rockets.LaunchPadCore;
import io.github.addoncommunity.galactifun.implementation.rockets.Rocket;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
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

        Rocket.setup(addon);
        Components.setup(addon);

        new CircuitPress().setEnergyConsumption(25).setCapacity(1024).register(addon);
        Circuits.setup(addon);
        new AssemblyTable().register(addon);
    }
}
