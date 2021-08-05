package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;

// TODO implement oxygen
public final class SpaceSuitProfile {

    private static final Map<UUID, SpaceSuitProfile> profiles = new HashMap<>();

    private final Map<SpaceSuitStat, Integer> stats = new HashMap<>();

    @Nonnull
    public static SpaceSuitProfile get(Player p) {
        return profiles.computeIfAbsent(p.getUniqueId(), uuid -> new SpaceSuitProfile());
    }

    static {
        Galactifun.instance().scheduleRepeatingSync(SpaceSuitProfile::updateAll, 200);
    }

    private static void updateAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isValid() && !p.isDead()) {
                get(p).update(p);
            }
        }
    }

    public int getStat(SpaceSuitStat stat) {
        return this.stats.getOrDefault(stat, 0);
    }

    private void update(@Nonnull Player p) {
        this.stats.clear();
        for (ItemStack item : p.getInventory().getArmorContents()) {
            SpaceSuitUpgrade.getUpgrades(item, this.stats);

        }
    }

}
