package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.persistence.PersistenceUtils;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

// TODO make machine to add upgrades to stuff
@ParametersAreNonnullByDefault
public final class SpaceSuitUpgrade extends UnplaceableBlock {

    private static final NamespacedKey UPGRADES_KEY = Galactifun.instance().getKey("upgrades");
    private static final Map<String, SpaceSuitUpgrade> UPGRADES = new HashMap<>();

    private final SpaceSuitStat stat;
    private final int value;

    public SpaceSuitUpgrade(Category category, SlimefunItemStack item, RecipeType recipeType,
                            ItemStack[] recipe, SpaceSuitStat stat, int value) {
        super(category, item, recipeType, recipe);
        this.stat = stat;
        this.value = value;
    }

    public boolean addTo(PersistentDataContainer container, int maxUpgrades) {
        List<String> ids = container.get(UPGRADES_KEY, PersistenceUtils.STRING_LIST);

        if (ids != null) {
            if (ids.size() < maxUpgrades) {
                ids.add(getId());
                container.set(UPGRADES_KEY, PersistenceUtils.STRING_LIST, ids);
                return true;
            }
        } else if (maxUpgrades > 0) {
            container.set(UPGRADES_KEY, PersistenceUtils.STRING_LIST, Collections.singletonList(getId()));
            return true;
        }
        return false;
    }

    @Override
    public void preRegister() {
        super.preRegister();
        UPGRADES.put(getId(), this);
    }

    public static void getUpgrades(ItemMeta meta, Map<SpaceSuitStat, Integer> stats) {
        PersistentDataContainer container = meta.getPersistentDataContainer();

        List<String> ids = container.get(UPGRADES_KEY, PersistenceUtils.STRING_LIST);

        if (ids != null) {
            for (String id : ids) {
                SpaceSuitUpgrade upgrade = UPGRADES.get(id);
                if (upgrade != null) {
                    stats.compute(upgrade.stat, (stat, value) -> value == null ? upgrade.value : value + upgrade.value);
                }
            }
        }
    }

}
