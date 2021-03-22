package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.core.ConfigUtils;
import io.github.mooy1.infinitylib.core.PluginUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ChestMenu;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A galactic profile, similar to {@link io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile}
 * 
 * @author Mooy1
 */
public final class GalacticProfile {
    
    private static final NamespacedKey OXYGEN_KEY = PluginUtils.getKey("oxygen");
    private static final Map<UUID, GalacticProfile> PROFILES = new HashMap<>();
    private static final File FOLDER = new File(Galactifun.getInstance().getDataFolder(), "profiles");
    private static final Configuration DEFAULTS = ConfigUtils.getDefaults("profile.yml");
    
    @Nonnull
    public static GalacticProfile get(@Nonnull Player p) {
        return get(p.getUniqueId());
    }
    
    @Nonnull
    public static GalacticProfile get(@Nonnull UUID uuid) {
        return PROFILES.computeIfAbsent(uuid, k -> new GalacticProfile(new File(FOLDER, k.toString() + ".yml"), k));
    }
    
    static {
        // auto saver
        PluginUtils.scheduleRepeatingSync(GalacticProfile::saveAll, 12000);
    }

    /**
     * Saves all profiles
     */
    public static void saveAll() {
        int i = 0;
        for (GalacticProfile profile : PROFILES.values()) {
            if (profile.dirty) {
                profile.save();
                profile.dirty = false;
                i++;
            }
        }
        PluginUtils.log("Saved a total of " + i + " Galactic Profiles");
    }

    /**
     * Closes all profiles
     */
    public static void unloadAll() {
        PluginUtils.log("Closing Galactic Profiles...");
        for (GalacticProfile profile : PROFILES.values()) {
            profile.close();
        }
    }

    /**
     * Creates the profile directory if needed
     */
    public static void loadAll() {
        PluginUtils.log("Loading Galactic Profiles...");
        File[] files = FOLDER.listFiles();
        if (files == null || !FOLDER.exists()) {
            FOLDER.mkdir();
        } else {
            for (File file : files) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(file.getName().replace(".yml", ""));
                } catch (IllegalArgumentException e) {
                    continue;
                }
                PROFILES.put(uuid, new GalacticProfile(file, uuid));
            }
        }
    }
    
    /**
     * Used to determine if this profile needs to be saved
     */
    private boolean dirty = true;
    
    /**
     * The config file for this profile
     */
    @Nonnull
    private final Config config;

    /**
     * The menu for items
     */
    @Nonnull
    private final ChestMenu menu;

    @Getter
    private int oxygen; // todo load this on start and on menu close
    @Getter
    private int maxOxygen; // todo load this on start and on menu close

    /**
     * Loads or creates a profile for the uuid
     */
    private GalacticProfile(@Nonnull File file, @Nonnull UUID uuid) {
        // load config
        Config config = ConfigUtils.loadWithDefaults(file, DEFAULTS);
        
        // attempt to add the players name to config which could help server admins
        config.setValue("player", Bukkit.getOfflinePlayer(uuid).getName());
        
        // load objects
        this.menu = loadMenu(config);
        this.config = config;
    }
    
    private ChestMenu loadMenu(Config config) {
        ChestMenu menu = new ChestMenu(Galactifun.getInstance(), "&7Galactic Backpack");

        // TODO update oxygen values upon closing
        menu.addMenuCloseHandler(player -> markDirty());

        // todo load items
        

        // todo add background items
        
        return menu;
    }

    public void openMenu(@Nonnull Player p) {
        this.menu.open(p);
    }

    /**
     * Saves the profile and all of it's objects to the config
     */
    private void save() {
        // todo save items
        
        this.config.save();
    }

    /**
     * Closes all player interactions with the profile
     */
    private void close() {
        this.menu.close();
    }

    /**
     * Marks this profile to be saved
     */
    private void markDirty() {
        this.dirty = true;
    }

    public void setOxygen(int oxygen) {
        Validate.isTrue(oxygen >= 0 && oxygen <= getMaxOxygen(), "Cannot set oxygen less than 0 or greater than max!");
        // todo do some stuff with pdcs
    }
    
}
