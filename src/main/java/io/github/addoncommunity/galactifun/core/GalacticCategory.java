package io.github.addoncommunity.galactifun.core;

import io.github.mooy1.infinitylib.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * Category for exploring the universe
 * 
 * @author Mooy1
 */
public final class GalacticCategory extends FlexCategory {
    
    public GalacticCategory() {
        super(PluginUtils.getKey("galactic_category"), new CustomItem(Material.END_STONE, "&bThe Universe"));
    }

    @Override
    public boolean isVisible(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        return true;
    }

    @Override
    public void open(@Nonnull Player player, @Nonnull PlayerProfile playerProfile, @Nonnull SlimefunGuideMode slimefunGuideMode) {
        GalacticExplorer.explore(player);
        playerProfile.getGuideHistory().add(this, 1);
    }

}
