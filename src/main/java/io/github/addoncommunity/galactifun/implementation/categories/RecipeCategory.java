package io.github.addoncommunity.galactifun.implementation.categories;

import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.machines.AssemblyTable;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RecipeCategory extends FlexCategory {

    private static final int[] SLOTS = new int[]{
        1, 2, 3, 4, 5, 6,
        10, 11, 12, 13, 14, 15,
        19, 20, 21, 22, 23, 24,
        28, 29, 30, 31, 32, 33,
        37, 38, 39, 40, 41, 42,
        46, 47, 48, 49, 50, 51
    };

    public RecipeCategory() {
        super(PluginUtils.getKey("recipe_category"), new CustomItem(Material.SMITHING_TABLE, "&fAssembly Table Recipes"));
    }

    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideMode layout) {
        return true;
    }

    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideMode layout) {
        ChestMenu menu = new ChestMenu("Assembly Table Recipes");
        menu.setEmptySlotsClickable(false);

        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(1, new CustomItem(ChestMenuUtils.getBackButton(p, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(p, "guide.back.guide"))),
            (p12, slot, item, action) -> {
                SlimefunPlugin.getRegistry().getSlimefunGuide(layout).openMainMenu(profile, 1);
                return false;
            });

        int i = 9;
        for (Pair<SlimefunItemStack, ItemStack[]> item : AssemblyTable.ITEMS.values()) {
            menu.addItem(i++, item.getFirstValue().clone(), (p1, slot, item1, action) -> {
                if (layout == SlimefunGuideMode.CHEAT_MODE) {
                    p1.getInventory().addItem(item1.clone());
                } else {
                    displayItem(p1, profile, item);
                }
                return false;
            });
        }

        menu.open(p);
    }

    private void displayItem(Player p, PlayerProfile profile, Pair<SlimefunItemStack, ItemStack[]> item) {
        ChestMenu menu = new ChestMenu("Recipe for " + item.getFirstValue().getItemMeta().getDisplayName());
        menu.setEmptySlotsClickable(false);

        menu.addItem(0, new CustomItem(ChestMenuUtils.getBackButton(p, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(p, "guide.back.guide"))),
            (p12, slot, it, action) -> {
                this.open(p, profile, SlimefunGuideMode.SURVIVAL_MODE);
                return false;
            });

        int i = 0;
        for (ItemStack stack : item.getSecondValue()) {
            ItemStack newStack = stack;
            if (newStack == null) {
                newStack = new ItemStack(Material.AIR);
            }

            menu.addItem(SLOTS[i++], newStack, (p1, slot, item1, action) -> {
                if (item1 != null) {
                    SlimefunGuide.displayItem(profile, item1, true);
                }
                return false;
            });
        }

        menu.addItem(18, GalactifunItems.ASSEMBLY_TABLE, ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(26, item.getFirstValue(), ChestMenuUtils.getEmptyClickHandler());

        menu.open(p);
    }
}
