package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

// TODO make machine to add upgrades to stuff
public class SpaceSuitUpgrade extends UnplaceableBlock {

    private static final NamespacedKey UPGRADES_KEY = Galactifun.instance().getKey("upgrades");
    private static final Map<String, SpaceSuitUpgrade> UPGRADES = new HashMap<>();

    public static void getUpgrades(ItemStack item, Map<SpaceSuitStat, Integer> stats) {
        if (item.getType().isAir() || !item.hasItemMeta()) {
            return;
        }

        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        String string = container.get(UPGRADES_KEY, PersistentDataType.STRING);

        if (string == null) {
            return;
        }

        for (String id : PatternUtils.SEMICOLON.split(string)) {
            SpaceSuitUpgrade upgrade = UPGRADES.get(id);
            if (upgrade != null) {
                stats.compute(upgrade.stat, (stat, value) -> value == null ? upgrade.value : value + upgrade.value);
            }
        }
    }

    private final SpaceSuitStat stat;
    private final int value;

    public SpaceSuitUpgrade(Category category, SlimefunItemStack item, RecipeType recipeType,
                            ItemStack[] recipe, SpaceSuitStat stat, int value) {
        super(category, item, recipeType, recipe);
        this.stat = stat;
        this.value = value;
    }

    public boolean addTo(PersistentDataContainer container, int maxUpgrades) {
        String string = container.get(UPGRADES_KEY, PersistentDataType.STRING);

        if (string != null) {
            int i = 0;
            int upgrades = 0;
            while ((i = string.indexOf(';', i)) != -1) {
                upgrades++;
                i++;
            }
            if (upgrades > maxUpgrades) {
                return false;
            }
        }

        container.set(UPGRADES_KEY, PersistentDataType.STRING, string + ';' + getId());
        return true;
    }

    @Override
    public void preRegister() {
        super.preRegister();
        UPGRADES.put(getId(), this);
    }

}
