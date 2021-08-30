package io.github.addoncommunity.galactifun.core.categories;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.core.WorldSelector;
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

    public GalacticCategory(NamespacedKey key, ItemStack item) {
        super(key, item);
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return true;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        new WorldSelector((player1, i, itemStack, itemStack1, clickAction) -> {
            playerProfile.getGuideHistory().goBack(SlimefunPlugin.getRegistry().getSlimefunGuide(slimefunGuideMode));
            return false;
        }).open(player);
        playerProfile.getGuideHistory().add(this, 1);
    }

}
