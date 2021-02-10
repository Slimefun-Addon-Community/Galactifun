package io.github.addoncommunity.galactifun.core.profile;

import io.github.addoncommunity.galactifun.Galactifun;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

/**
 * An object representing a player's galactic backpack which can hold their space suit, gear, and oxygen tanks
 * 
 * @author Mooy1
 */
public final class  GalacticBackpack {
    
    /**
     * The menu for this backpack
     */
    @Nonnull
    private final ChestMenu menu;

    /**
     * Loads or creates a new galactic backpack
     */
    GalacticBackpack(@Nonnull GalacticProfile profile, @Nonnull Config config) {
        ChestMenu menu = new ChestMenu(Galactifun.getInstance(), "&7Galactic Backpack", profile::markDirty);
        
        
        
        this.menu = menu;
    }
    
    void save(@Nonnull Config config) {
        
    }
    
    void close() {
        this.menu.close();
    }

    public void open(@Nonnull Player p) {
        this.menu.open(p);
    }

}
