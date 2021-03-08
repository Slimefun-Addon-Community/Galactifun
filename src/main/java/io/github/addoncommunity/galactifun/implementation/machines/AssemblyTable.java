package io.github.addoncommunity.galactifun.implementation.machines;

import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.objects.AbstractEnergyCrafter;
import io.github.mooy1.infinitylib.items.LoreUtils;
import io.github.mooy1.infinitylib.player.MessageUtils;
import io.github.mooy1.infinitylib.presets.MenuPreset;
import io.github.mooy1.infinitylib.recipes.large.LargeRecipeMap;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class for the assembly table. Based off of InfinityExpansion's Infinity Workbench
 *
 * @author Mooy1
 * @author Seggan
 */
public class AssemblyTable extends AbstractEnergyCrafter {

    private static final int ENERGY = 2048;

    public static final int[] INPUT_SLOTS = {
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

    public static final LargeRecipeMap RECIPES = new LargeRecipeMap(36);
    public static final LinkedHashMap<String, Pair<SlimefunItemStack, ItemStack[]>> ITEMS = new LinkedHashMap<>();
    public static final List<String> IDS = new ArrayList<>();

    public AssemblyTable() {
        super(Categories.MAIN_CATEGORY, GalactifunItems.ASSEMBLY_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9], ENERGY, STATUS_SLOT);

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            BlockMenu inv = BlockStorage.getInventory(b);

            if (inv != null) {
                Location l = b.getLocation();
                inv.dropItems(l, OUTPUT_SLOTS);
                inv.dropItems(l, INPUT_SLOTS);
            }

            return true;
        });
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
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(STATUS_SLOT, (p, slot, item, action) -> {
            craft(b, menu, p);
            return false;
        });
    }

    public void craft(@Nonnull Block b, @Nonnull BlockMenu inv, @Nonnull Player p) {
        int charge = getCharge(b.getLocation());

        if (charge < ENERGY) {
            MessageUtils.messageWithCD(p, 1000,
                ChatColor.RED + "Not enough energy!",
                ChatColor.GREEN + "Charge: " + ChatColor.RED + charge + ChatColor.GREEN + "/" + ENERGY + " J"
            );
            return;
        }

        ItemStack output = RECIPES.get(inv, INPUT_SLOTS);

        if (output == null) {
            MessageUtils.messageWithCD(p, 1000, ChatColor.RED + "Invalid Recipe!");
            return;
        }

        if (!inv.fits(output, OUTPUT_SLOTS)) {
            MessageUtils.messageWithCD(p, 1000, ChatColor.GOLD + "Not enough room!");
            return;
        }

        for (int slot : INPUT_SLOTS) {
            if (inv.getItemInSlot(slot) != null) {
                inv.consumeItem(slot);
            }
        }

        MessageUtils.message(p, ChatColor.GREEN + "Successfully crafted: " + ChatColor.WHITE + output.getItemMeta().getDisplayName());

        inv.pushItem(output.clone(), OUTPUT_SLOTS);
        setCharge(b.getLocation(), 0);
    }

    @Override
    public void update(@Nonnull BlockMenu inv) {
        ItemStack output = RECIPES.get(inv, INPUT_SLOTS);
        if (output == null) {
            inv.replaceExistingItem(STATUS_SLOT, MenuPreset.invalidRecipe);
        } else { //correct recipe
            inv.replaceExistingItem(STATUS_SLOT, getDisplayItem(output.clone()));
        }
    }

    @Nonnull
    private static ItemStack getDisplayItem(@Nonnull ItemStack output) {
        LoreUtils.addLore(output, "", "&a-------------------", "&a\u21E8 Click to craft", "&a-------------------");
        return output;
    }
}
