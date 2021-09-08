package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.base.BaseMats;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.reactors.Reactor;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;

public final class FusionReactor extends Reactor {

    public FusionReactor(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreCategory.MACHINES, item, AssemblyTable.TYPE, recipe);
    }

    @Override
    public void extraTick(@Nonnull Location l) {
        // do nothing
    }

    @Nullable
    @Override
    public ItemStack getCoolant() {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack getFuelIcon() {
        return BaseMats.FUSION_PELLET;
    }

    @Nonnull
    @Override
    public ItemStack getProgressBar() {
        return SlimefunItems.LAVA_CRYSTAL;
    }

    @Override
    public int getEnergyProduction() {
        return 32_768; // 65,536 J/s
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(60 * 60 * 3, BaseMats.FUSION_PELLET, new SlimefunItemStack(SlimefunItems.CARBON, 6)));
    }

    @Override
    public int getCapacity() {
        return 65_536;
    }

}
