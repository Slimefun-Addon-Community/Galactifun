package io.github.addoncommunity.galactifun.api.items.spacesuit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.addoncommunity.galactifun.Galactifun;

// TODO implement oxygen
@ParametersAreNonnullByDefault
public final class SpaceSuitProfile {

    private static final Map<UUID, SpaceSuitProfile> profiles = new HashMap<>();
    private static final double OXYGEN_PER_TICK = .05;

    private final Map<SpaceSuitStat, Integer> stats = new HashMap<>();
    private final int[] oxygen = new int[4];
    private final int[] oxygenChanged = new int[4];

    @Nonnull
    public static SpaceSuitProfile get(Player p) {
        return profiles.computeIfAbsent(p.getUniqueId(), uuid -> {
            SpaceSuitProfile profile = new SpaceSuitProfile();
            profile.update(p);
            return profile;
        });
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

    /**
     * Consumes 1 oxygen per second
     *
     * @return Whether a full breath was taken
     */
    public boolean consumeOxygen(int ticksSinceLastBreath) {
        int consume = (int) (ticksSinceLastBreath * OXYGEN_PER_TICK);
        for (int i = 0; i < 4; i++) {
            int slot = oxygenChanged[i];
            if (slot >= consume) {
                oxygenChanged[i] -= consume;
                break;
            } else if (slot != 0) {
                consume -= slot;
                oxygenChanged[i] = 0;
            }
        }
        return consume == 0;
    }

    public int getStat(SpaceSuitStat stat) {
        return this.stats.getOrDefault(stat, 0);
    }

    private void update(Player p) {
        this.stats.clear();
        ItemStack[] armorContents = p.getInventory().getArmorContents();
        for (int i = 0, armorContentsLength = armorContents.length; i < armorContentsLength; i++) {
            ItemStack item = armorContents[i];
            if (!item.getType().isAir() && item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                SpaceSuitUpgrade.getUpgrades(meta, this.stats);
                oxygenChanged[i] = oxygen[i] = SpaceSuit.getOxygen(item, meta, oxygenChanged[i] - oxygen[i]);
            }
        }
    }

}
