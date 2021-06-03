package io.github.addoncommunity.galactifun.core.categories;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.core.GalacticExplorer;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;

/**
 * Category for exploring the universe
 * 
 * @author Mooy1
 */
public final class GalacticCategory extends FlexCategory {

    private final GalacticExplorer explorer;
    
    public GalacticCategory(NamespacedKey key, ItemStack item, GalacticExplorer explorer) {
        super(key, item);
        this.explorer = explorer;
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return true;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        this.explorer.explore(player, (player1, i, itemStack, itemStack1, clickAction) -> {
            playerProfile.getGuideHistory().goBack(SlimefunPlugin.getRegistry().getSlimefunGuide(slimefunGuideMode));
            return false;
        });
        playerProfile.getGuideHistory().add(this, 1);
    }

}
