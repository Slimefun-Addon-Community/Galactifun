package io.github.addoncommunity.galactifun.base.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuit;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitUpgrade;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AContainer;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;

public final class SpaceSuitUpgrader extends AContainer {

    public SpaceSuitUpgrader(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Nullable
    @Override
    protected MachineRecipe findNextRecipe(BlockMenu inv) {
        ItemStack suitStack = null;
        SpaceSuit suit = null;
        SpaceSuitUpgrade upgrade = null;
        ItemStack upgradeStack = null;

        for (int slot : getInputSlots()) {
            ItemStack item = inv.getItemInSlot(slot);

            if (item != null && item.hasItemMeta()) {
                SlimefunItem sfItem = SlimefunItem.getByItem(item);

                if (suit == null && sfItem instanceof SpaceSuit) {
                    suit = (SpaceSuit) sfItem;
                    suitStack = item;
                } else if (upgrade == null && sfItem instanceof SpaceSuitUpgrade) {
                    upgrade = (SpaceSuitUpgrade) sfItem;
                    upgradeStack = item;
                }

                if (suit != null && upgrade != null) {
                    ItemMeta meta = suitStack.getItemMeta();
                    if (upgrade.addTo(meta, suit.maxUpgrades())) {
                        ItemStack newSuit = suitStack.clone();
                        newSuit.setItemMeta(meta);
                        upgradeStack.setAmount(upgradeStack.getAmount() - 1);
                        suitStack.setAmount(0);
                        return new MachineRecipe(5 / getSpeed(), new ItemStack[] {upgradeStack}, new ItemStack[] {newSuit});
                    }
                }
            }
        }

        return null;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.ANVIL);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "SPACE_SUIT_UPGRADER";
    }

}
