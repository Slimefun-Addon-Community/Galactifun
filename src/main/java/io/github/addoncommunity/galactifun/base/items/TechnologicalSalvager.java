package io.github.addoncommunity.galactifun.base.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.api.items.Relic;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.mooy1.infinitylib.machines.AbstractMachineBlock;
import io.github.mooy1.infinitylib.machines.MenuBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineProcessHolder;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.operations.CraftingOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;

public class TechnologicalSalvager extends AbstractMachineBlock implements MachineProcessHolder<CraftingOperation> {

    private static final int[] BACKGROUND = new int[] { 0, 1, 2, 3, 4, 13, 31, 36, 37, 38, 39, 40 };
    private static final int[] INPUT_BORDER = new int[] { 9, 10, 11, 12, 18, 21, 27, 28, 29, 30 };
    private static final int[] INPUT_SLOTS = { 19, 20 };
    private static final int[] OUTPUT_BORDER = new int[] { 5, 6, 7, 8, 14, 17, 23, 26, 32, 35, 41, 42, 43, 44 };
    private static final int[] OUTPUT_SLOTS = { 15, 16, 24, 25, 33, 34 };

    private static final ItemStack ANALYZING = new CustomItemStack(Material.LIME_STAINED_GLASS_PANE, "&a分析中...");

    private final MachineProcessor<CraftingOperation> processor = new MachineProcessor<>(this);

    public TechnologicalSalvager(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreItemGroup.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);

        energyPerTick(32);
        energyCapacity(64);
    }

    @Override
    protected boolean process(@Nonnull Block b, @Nonnull BlockMenu menu) {
        CraftingOperation operation = processor.getOperation(b);
        if (operation != null) {
            if (operation.isFinished()) {
                ItemStack[] results = operation.getResults();
                for (ItemStack result : results) {
                    menu.pushItem(result.clone(), OUTPUT_SLOTS);
                }
                processor.endOperation(b);
            } else {
                operation.addProgress(1);
            }
            return true;
        } else {
            for (int i : INPUT_SLOTS) {
                ItemStack item = menu.getItemInSlot(i);
                if (SlimefunItem.getByItem(item) instanceof Relic relic) {
                    List<ItemStack> results = new ArrayList<>();
                    for (Map.Entry<ItemStack, IntIntPair> entry : relic.required().entrySet()) {
                        int amount = ThreadLocalRandom.current().nextInt(entry.getValue().leftInt(),
                                entry.getValue().rightInt() + 1);
                        if (amount > 0) {
                            results.add(entry.getKey().asQuantity(amount));
                        }
                    }
                    if (ThreadLocalRandom.current().nextDouble() < relic.optionals().sumWeights()) {
                        results.add(relic.optionals().getRandom());
                    }

                    menu.replaceExistingItem(getStatusSlot(), ANALYZING);

                    processor.startOperation(b, new CraftingOperation(
                            new ItemStack[]{item},
                            results.toArray(ItemStack[]::new),
                            240
                    ));

                    menu.consumeItem(i);
                    return true;
                }
            }
            menu.replaceExistingItem(getStatusSlot(), MenuBlock.IDLE_ITEM);
            return false;
        }
    }

    @Override
    protected int getStatusSlot() {
        return 22;
    }

    @Override
    protected void setup(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int i : INPUT_BORDER) {
            preset.addItem(i, MenuBlock.INPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : OUTPUT_BORDER) {
            preset.addItem(i, MenuBlock.OUTPUT_BORDER, ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(getStatusSlot(), ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected int[] getInputSlots() {
        return INPUT_SLOTS;
    }

    @Override
    protected int[] getOutputSlots() {
        return OUTPUT_SLOTS;
    }

    @Nonnull
    @Override
    public MachineProcessor<CraftingOperation> getMachineProcessor() {
        return processor;
    }

}
