package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.Getter;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.items.StackUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectiveArmor;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;

@ParametersAreNonnullByDefault
public class SpaceSuit extends SlimefunItem implements ProtectiveArmor {

    private static final Map<String, SpaceSuit> SPACE_SUITS = new HashMap<>();
    private static final NamespacedKey SPACE_SUIT_KEY = Galactifun.instance().getKey("space_suit");
    private static final NamespacedKey OXYGEN_KEY = Galactifun.instance().getKey("oxygen");
    private static final String OXYGEN_LORE = ChatColors.color("&bOxygen: &7");

    @Getter
    private final int maxUpgrades;
    @Getter
    private final int maxOxygen;

    /**
     * Keep in mind you need 1 oxygen per second irl
     */
    public SpaceSuit(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe, int maxUpgrades, int maxOxygen) {
        super(category, item, recipeType, recipe);
        this.maxUpgrades = maxUpgrades;
        this.maxOxygen = maxOxygen;
    }

    @Nonnull
    @Override
    public ProtectionType[] getProtectionTypes() {
        return new ProtectionType[] {
                ProtectionType.RADIATION,
                ProtectionType.BEES
        };
    }

    @Override
    public void postRegister() {
        SPACE_SUITS.put(getId(), this);
    }

    @Override
    public boolean isFullSetRequired() {
        return true;
    }

    @Nonnull
    @Override
    public final NamespacedKey getArmorSetId() {
        return SPACE_SUIT_KEY;
    }

    public static int getOxygen(ItemStack item, ItemMeta meta, int change) {
        SpaceSuit suit = SPACE_SUITS.get(StackUtils.getID(meta));
        if (suit == null) {
            return 0;
        }
        int oxygen = suit.getOxygen(meta);
        int oxygenChanged = Math.max(0, Math.min(suit.maxOxygen, oxygen + change));
        if (oxygenChanged != oxygen) {
            suit.setOxygen(meta, oxygenChanged);
            item.setItemMeta(meta);
        }
        return oxygenChanged;
    }

    public final int getOxygen(ItemMeta meta) {
        return Math.min(maxOxygen, meta.getPersistentDataContainer()
                .getOrDefault(OXYGEN_KEY, PersistentDataType.INTEGER, 0));
    }

    public final void setOxygen(ItemMeta meta, int oxygen) {
        oxygen = Math.max(0, Math.min(oxygen, maxOxygen));
        meta.getPersistentDataContainer().set(OXYGEN_KEY, PersistentDataType.INTEGER, oxygen);
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            for (int i = 0; i < lore.size(); i++) {
                if (lore.get(i).startsWith(OXYGEN_LORE)) {
                    lore.set(i, oxygenLore(oxygen, maxOxygen));
                }
            }
            meta.setLore(lore);
        }
    }

    public static String oxygenLore(int amount, int max) {
        return OXYGEN_LORE + amount + " / " + max;
    }

}
