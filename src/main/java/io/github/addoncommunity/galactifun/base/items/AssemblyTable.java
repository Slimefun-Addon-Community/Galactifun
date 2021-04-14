package io.github.addoncommunity.galactifun.base.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.slimefun.presets.MenuPreset;
import io.github.mooy1.infinitylib.slimefun.recipes.RecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.SimpleRecipeMap;
import io.github.mooy1.infinitylib.slimefun.recipes.inputs.MultiInput;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;

/**
 * Class for the assembly table. Based off of InfinityExpansion's Infinity Workbench
 *
 * @author Mooy1
 * @author Seggan
 */
public final class AssemblyTable extends AbstractEnergyCrafter {

    private static final RecipeMap<MultiInput, ItemStack> RECIPES = new SimpleRecipeMap<>();
    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();

    public static final RecipeType TYPE = new RecipeType(Galactifun.inst().getKey("assembly_table"), BaseItems.ASSEMBLY_TABLE, (stacks, stack) -> {
        SlimefunItemStack item = (SlimefunItemStack) stack;
        RECIPES.put(new MultiInput(stacks), item);
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
    private static final int[] OUTPUT_SLOTS = {MenuPreset.slot3 + 27};
    private static final int STATUS_SLOT = MenuPreset.slot3;
    private static final int[] STATUS_BORDER = {6, 7, 8, 15, 17, 24, 25, 26};

    public AssemblyTable(Category category, SlimefunItemStack item, RecipeType type, ItemStack[] recipe) {
        super(category, item, type, recipe, ENERGY, STATUS_SLOT);
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset blockMenuPreset) {
        for (int i : MenuPreset.slotChunk3) {
            blockMenuPreset.addItem(i + 27, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : STATUS_BORDER) {
            blockMenuPreset.addItem(i, MenuPreset.borderItemStatus, ChestMenuUtils.getEmptyClickHandler());
        }
        blockMenuPreset.addItem(STATUS_SLOT, MenuPreset.invalidInput, ChestMenuUtils.getEmptyClickHandler());
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

        ItemStack output = RECIPES.get(new MultiInput(inv, INPUT_SLOTS));

        if (output == null) {
            p.sendMessage( ChatColor.RED + "Invalid Recipe!");
            return;
        }

        if (!inv.fits(output, OUTPUT_SLOTS)) {
            p.sendMessage( ChatColor.GOLD + "Not enough room!");
            return;
        }

        for (int slot : INPUT_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot);
            }
        }

        p.sendMessage( ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + output.getItemMeta().getDisplayName());

        inv.pushItem(output.clone(), OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
    }

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack output = RECIPES.get(new MultiInput(inv, INPUT_SLOTS));
        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
        } else { //correct recipe
            output = output.clone();
            LoreUtils.addLore(output, "", "&a-------------------", "&a\u21E8 Click to craft", "&a-------------------");
            inv.replaceExistingItem(STATUS_SLOT, output);
        }
    }

}
