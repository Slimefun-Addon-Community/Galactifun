package io.github.addoncommunity.galactifun.base.items.protection;

import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.items.Extension;
import io.github.addoncommunity.galactifun.api.items.ProtectingBlock;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.AtmosphericEffect;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public final class CoolingUnit extends ProtectingBlock {

    private final int tier;

    public CoolingUnit(SlimefunItemStack item, ItemStack[] recipe, int tier) {
        super(item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.tier = tier;
    }

    @Override
    protected void applyExtensions(@Nonnull List<Extension> extensions, @Nonnull Block b) {
        for (Extension extension : extensions) {

        }
    }

    @Override
    protected int getEnergyRequirement() {
        return this.tier * 512;
    }

    @Override
    protected AtmosphericEffect getEffect() {
        return AtmosphericEffect.HEAT;
    }

    @Override
    protected int getUpgradeSlots() {
        return (this.tier - 1) * 2;
    }

    @Override
    public int getCapacity() {
        return getEnergyRequirement() * 2;
    }
}
