package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import io.github.mooy1.infinitylib.common.PersistentType;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;

@ParametersAreNonnullByDefault
public final class SpaceSuitUpgrade extends UnplaceableBlock {

    private static final NamespacedKey UPGRADES_KEY = AbstractAddon.createKey("upgrades");
    private static final Map<String, SpaceSuitUpgrade> UPGRADES = new HashMap<>();

    private final SpaceSuitStat stat;
    private final int value;

    public SpaceSuitUpgrade(ItemGroup category, SlimefunItemStack item, RecipeType recipeType,
                            ItemStack[] recipe, SpaceSuitStat stat, int value) {
        super(category, item, recipeType, recipe);
        this.stat = stat;
        this.value = value;
    }

    public static void getUpgrades(ItemMeta meta, Map<SpaceSuitStat, Integer> stats) {
        PersistentDataContainer container = meta.getPersistentDataContainer();

        List<String> ids = container.get(UPGRADES_KEY, PersistentType.STRING_LIST);

        if (ids != null) {
            for (String id : ids) {
                SpaceSuitUpgrade upgrade = UPGRADES.get(id);
                if (upgrade != null) {
                    stats.compute(upgrade.stat, (stat, value) -> value == null ? upgrade.value : value + upgrade.value);
                }
            }
        }
    }

    public boolean addTo(ItemMeta meta, int maxUpgrades) {
        PersistentDataContainer container = meta.getPersistentDataContainer();
        List<String> ids = container.get(UPGRADES_KEY, PersistentType.STRING_LIST);
        boolean success = false;

        if (ids != null) {
            if (ids.size() < maxUpgrades) {
                ids.add(getId());
                container.set(UPGRADES_KEY, PersistentType.STRING_LIST, ids);
                success = true;
            }
        } else if (maxUpgrades > 0) {
            container.set(UPGRADES_KEY, PersistentType.STRING_LIST, Collections.singletonList(getId()));
            success = true;
        }

        if (success) {
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                lore.add(toLore());
                meta.setLore(lore);
            } else {
                meta.setLore(Collections.singletonList(toLore()));
            }
        }

        return success;
    }

    @Nonnull
    public String toLore() {
        return ChatColors.color(stat.name() + " " + value);
    }

    @Override
    public void postRegister() {
        UPGRADES.put(getId(), this);
    }

}
