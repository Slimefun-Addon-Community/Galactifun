package io.github.addoncommunity.galactifun.core.profile;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.ConfigUtils;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * A galactic profile, similar to {@link io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile}
 * 
 * @author Mooy1
 */
public final class GalacticProfile {
    
    private static final Map<UUID, GalacticProfile> PROFILES = new HashMap<>();
    private static final File FILE = new File(Galactifun.getInstance().getDataFolder(), "profiles");
    private static final Configuration DEFAULTS = ConfigUtils.getDefaults("profile.yml");
    
    @Nonnull
    public static GalacticProfile get(@Nonnull Player p) {
        return get(p.getUniqueId());
    }
    
    @Nonnull
    public static GalacticProfile get(@Nonnull UUID uuid) {
        return PROFILES.computeIfAbsent(uuid, k -> new GalacticProfile(new File(FILE, k.toString() + ".yml"), k));
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
        if (!FILE.exists()) {
            FILE.mkdir();
        } else {
            for (File file : Objects.requireNonNull(FILE.listFiles())) {
                UUID uuid;
                try {
                    uuid = UUID.fromString(file.getName());
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
     * The backpack of this profile
     */
    @Getter
    @Nonnull
    private final GalacticBackpack backpack;

    /**
     * Loads or creates a profile for the uuid
     */
    private GalacticProfile(@Nonnull File file, @Nonnull UUID uuid) {
        // load config
        Config config = ConfigUtils.loadWithDefaults(file, DEFAULTS);
        
        // attempt to add the players name to config which could help server admins
        config.setValue("player", Bukkit.getOfflinePlayer(uuid).getName());
        
        // load objects
        this.backpack = new GalacticBackpack(this, config);
        this.config = config;
    }

    /**
     * Saves the profile and all of it's objects to the config
     */
    private void save() {
        this.backpack.save(this.config);
        this.config.save();
    }

    /**
     * Closes all player interactions with the profile
     */
    private void close() {
        this.backpack.close();
    }

    /**
     * Marks this profile to be saved
     */
    void markDirty() {
        this.dirty = true;
    }
    
}
