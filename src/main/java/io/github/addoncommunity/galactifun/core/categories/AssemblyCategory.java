package io.github.addoncommunity.galactifun.core.categories;

import java.util.AbstractMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.base.BaseItems;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;

/**
 * Category for displaying Assembly recipes
 *
 * @author Seggan
 * @author Mooy1
 */
public final class AssemblyCategory extends FlexItemGroup {

    private static final int[] SLOTS = new int[] {
            1, 2, 3, 4, 5, 6,
            10, 11, 12, 13, 14, 15,
            19, 20, 21, 22, 23, 24,
            28, 29, 30, 31, 32, 33,
            37, 38, 39, 40, 41, 42,
            46, 47, 48, 49, 50, 51
    };

    public AssemblyCategory(NamespacedKey key, ItemStack item) {
        super(key, item);
    }

    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideMode layout) {
        return true;
    }

    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideMode layout) {
        profile.getGuideHistory().add(this, 1);

        ChestMenu menu = new ChestMenu("Assembly Table Recipes");
        menu.setEmptySlotsClickable(false);

        for (int i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(1, new CustomItemStack(ChestMenuUtils.getBackButton(p, "",
                ChatColor.GRAY + Slimefun.getLocalization().getMessage(p, "guide.back.guide")))
        );
        menu.addMenuClickHandler(1, (p12, slot, item, action) -> {
            profile.getGuideHistory().goBack(Slimefun.getRegistry().getSlimefunGuide(layout));
            return false;
        });

        int i = 9;
        for (Map.Entry<ItemStack, ItemStack[]> item : AssemblyTable.ITEMS.entrySet()) {
            if (item.getKey() instanceof SlimefunItemStack slimefunItemStack) {
                Map.Entry<SlimefunItemStack, ItemStack[]> newEntry = new AbstractMap.SimpleImmutableEntry<>(slimefunItemStack, item.getValue());
                menu.addItem(i++, item.getKey(), (p1, slot, item1, action) -> {
                    if (layout == SlimefunGuideMode.CHEAT_MODE) {
                        p1.getInventory().addItem(item1.clone());
                    } else {
                        displayItem(p1, profile, newEntry);
                    }
                    return false;
                });
            }
        }

        menu.open(p);
    }

    private void displayItem(Player p, PlayerProfile profile, Map.Entry<SlimefunItemStack, ItemStack[]> item) {
        ChestMenu menu = new ChestMenu("Recipe for " + item.getKey().getDisplayName());
        menu.setEmptySlotsClickable(false);

        menu.addItem(0, new CustomItemStack(ChestMenuUtils.getBackButton(p, "",
                ChatColor.GRAY + Slimefun.getLocalization().getMessage(p, "guide.back.guide")))
        );
        menu.addMenuClickHandler(0, (p12, slot, it, action) -> {
            this.open(p, profile, SlimefunGuideMode.SURVIVAL_MODE);
            return false;
        });

        for (int i = 0; i < item.getValue().length; i++) {
            ItemStack stack = item.getValue()[i];
            if (stack != null) {
                menu.addItem(SLOTS[i], stack, (p1, slot, item1, action) -> {
                    if (item1 != null) {
                        SlimefunGuide.displayItem(profile, item1, true);
                    }
                    return false;
                });
            }
        }

        menu.addItem(18, BaseItems.ASSEMBLY_TABLE, ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(26, item.getKey(), ChestMenuUtils.getEmptyClickHandler());

        menu.open(p);
    }

}
