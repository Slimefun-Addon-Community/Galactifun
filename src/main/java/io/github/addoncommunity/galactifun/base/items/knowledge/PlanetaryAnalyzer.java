package io.github.addoncommunity.galactifun.base.items.knowledge;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.PlanetaryWorld;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.addoncommunity.galactifun.core.WorldSelector;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.implementation.items.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;

public final class PlanetaryAnalyzer extends SimpleSlimefunItem<BlockUseHandler> {

    public PlanetaryAnalyzer(SlimefunItemStack item, ItemStack[] recipe) {
        super(CoreCategory.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
    }

    @Nonnull
    @Override
    public BlockUseHandler getItemHandler() {
        return e -> {
            Player p = e.getPlayer();
            NamespacedKey key = Galactifun.instance().getKey("analyzing_" + p.getUniqueId());

            PlanetaryWorld world = Galactifun.worldManager().getWorld(p.getWorld());
            if (world == null) {
                p.sendMessage(ChatColor.RED + "You must be on a planet to use this!");
                return;
            }

            if (PersistentDataAPI.getBoolean(world.worldStorage(), key)) {
                p.sendMessage(ChatColor.RED + "Already analyzing!");
                return;
            }

            new WorldSelector((pl, w, l) -> {
                if (w instanceof PlanetaryWorld pw) {
                    if (KnowledgeLevel.get(pl, pw) == KnowledgeLevel.ADVANCED) return false;
                }
                return world.distanceTo(w) <= 0.25;
            }, (pl, w) -> {
                pl.sendMessage(ChatColor.GREEN + "Analyzing planet " + w.name());
                PlanetaryWorld pw = (PlanetaryWorld) w;
                PersistentDataAPI.setBoolean(world.worldStorage(), key, true);
                Galactifun.instance().runSync(() -> {
                    PersistentDataAPI.setBoolean(world.worldStorage(), key, false);
                    KnowledgeLevel.BASIC.set(pl, pw);
                }, 30 * 60 * 20);
            }).open(p);
        };
    }

}
