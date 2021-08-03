package io.github.addoncommunity.galactifun.base.items;

import java.util.LinkedHashMap;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.RecipeMap;
import io.github.mooy1.infinitylib.recipes.RecipeOutput;
import io.github.mooy1.infinitylib.recipes.ShapedRecipe;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;

/**
 * Class for the assembly table. Based off of InfinityExpansion's Infinity Workbench
 *
 * @author Mooy1
 * @author Seggan
 */
public final class AssemblyTable extends AbstractEnergyCrafter {

    private static final RecipeMap<ItemStack> RECIPES = new RecipeMap<>(ShapedRecipe::new);
    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();

    public static final RecipeType TYPE = new RecipeType(Galactifun.instance().getKey("assembly_table"),
            BaseItems.ASSEMBLY_TABLE, (stacks, stack) -> {
        SlimefunItemStack item = (SlimefunItemStack) stack;
        RECIPES.put(stacks, item);
        ITEMS.put(item.getItemId(), new Pair<>(item, stacks));
    });

    private static final int ENERGY = 2048;
    private static final int[] INPUT_SLOTS = {
            0, 1, 2, 3, 4, 5,
            9, 10, 11, 12, 13, 14,
            18, 19, 20, 21, 22, 23,
            27, 28, 29, 30, 31, 32,
            36, 37, 38, 39, 40, 41,
            45, 46, 47, 48, 49, 50
    };
    private static final int[] OUTPUT_SLOTS = { MenuPreset.OUTPUT + 27 };
    private static final int STATUS_SLOT = MenuPreset.OUTPUT;
    private static final int[] STATUS_BORDER = { 6, 7, 8, 15, 17, 24, 25, 26 };

    public AssemblyTable(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe, ENERGY, STATUS_SLOT);
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.OUTPUT_BORDER) {
            blockMenuPreset.addItem(i + 27, MenuPreset.OUTPUT_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.STATUS_ITEM, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.INVALID_INPUT, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected void onBreak(@Nonnull BlockBreakEvent e, @Nonnull BlockMenu inv, @Nonnull Location l) {
        inv.dropItems(l, OUTPUT_SLOTS);
        inv.dropItems(l, INPUT_SLOTS);
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            craft(b, menu, p);
            return false;
        });
    }

    private void craft(@Nonnull Block b, @Nonnull BlockMenu inv, @Nonnull Player p) {
        int charge = getCharge(b.getLocation());

        if (charge < ENERGY) {
            p.sendMessage(new String[] {
                    ChatColor.RED + "Not enough energy!",
                    ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + ENERGY + " J"
            });
            return;
        }

        RecipeOutput<ItemStack> output = RECIPES.get(StackUtils.arrayFrom(inv, INPUT_SLOTS));

        if (output == null) {
            p.sendMessage(ChatColor.RED + "Invalid Recipe!");
            return;
        }

        if (!inv.fits(output.getOutput(), OUTPUT_SLOTS)) {
            p.sendMessage(ChatColor.GOLD + "Not enough room!");
            return;
        }

        output.consumeInput();
        p.sendMessage(ChatColor.GREEN + "Successfully crafted: " + StackUtils.getDisplayName(output.getOutput()));
        inv.pushItem(output.getOutput().clone(), OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
    }

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack output = RECIPES.getNoConsume(StackUtils.arrayFrom(inv, INPUT_SLOTS));
        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.INVALID_RECIPE);
        } else { //correct recipe
            output = output.clone();
            StackUtils.addLore(output, "", "&a-------------------", "&a\u21E8 Click to craft", "&a-------------------");
            inv.replaceExistingItem(STATUS_SLOT, output);
        }
    }

}
