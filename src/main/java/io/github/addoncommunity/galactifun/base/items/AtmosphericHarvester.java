package io.github.addoncommunity.galactifun.base.items;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Atmosphere;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.base.GalactifunHead;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.libraries.dough.collections.RandomizedSet;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public final class AtmosphericHarvester extends AbstractMachineBlock {

    private static final int[] SLOTS = new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
            17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35 };
    private static final Map<PlanetaryWorld, RandomizedSet<Integer>> weights = new HashMap<>();

    private static final ItemStack PROCESSING = new CustomItemStack(
            GalactifunHead.ATMOSPHERIC_HARVESTER, "&fHarvesting..."
    );

    public AtmosphericHarvester(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.energyPerTick = 32;
        this.energyCapacity = 128;
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu menu) {
        PlanetaryWorld world = Galactifun.worldManager().getWorld(b.getWorld());
        if (world == null) return false;

        if (menu.hasViewer()) {
            menu.replaceExistingItem(getStatusSlot(), PROCESSING);
        }

        int multiplier = 1;
        Atmosphere atmosphere = world.atmosphere();
        double pressure = atmosphere.pressure();
        if (pressure >= 1) {
            // only calculated once per atmosphere, so we can afford all the square rooting
            RandomizedSet<Integer> set = weights.computeIfAbsent(world, k -> {
                double max = Math.sqrt(pressure);
                RandomizedSet<Integer> internal = new RandomizedSet<>();
                for (int i = 1; i <= pressure; i++) {
                    // f(x) = max(0.5, -√x + √a), where a is the maximum input that returns a positive value
                    internal.add(i, (float) Math.max(0.5, -Math.sqrt(i) + max));
                }
                return internal;
            });
            multiplier = set.getRandom();
        } else {
            if (ThreadLocalRandom.current().nextDouble() > atmosphere.pressure()) return true;
        }

        Gas gas = atmosphere.weightedCompositionSet().getRandom();
        // we are on an atmosphereless world, so we can't harvest anything
        if (gas == null) return false;
        ItemStack stack = gas.item().asQuantity(multiplier);
        menu.pushItem(stack, SLOTS);

        return true;
    }

    @Override
    protected int getStatusSlot() {
        return 4;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        preset.setSize(36);
        preset.addMenuClickHandler(getStatusSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected int[] getInputSlots() {
        return new int[0];
    }

    @Override
    protected int[] getOutputSlots() {
        return SLOTS;
    }


}
