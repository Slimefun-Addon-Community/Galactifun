package io.github.addoncommunity.galactifun.core.profile;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.PluginUtils;
import io.github.mooy1.infinitylib.config.ConfigUtils;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.Bukkit;
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
    
    private static final Map<UUID, GalacticProfile> PROFILES = new HashMap<>();
    private static final File FILE = new File(Galactifun.getInstance().getDataFolder(), "profiles");
    
    @Nonnull
    public static GalacticProfile get(@Nonnull Player p) {
        return get(p.getUniqueId());
    }
    
    @Nonnull
    public static GalacticProfile get(@Nonnull UUID uuid) {
        return PROFILES.computeIfAbsent(uuid, GalacticProfile::new);
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
        PluginUtils.log("Auto saved a total of " + i + " Galactic Profiles");
    }

    /**
     * Closes all profiles
     */
    public static void unload() {
        for (GalacticProfile profile : PROFILES.values()) {
            profile.close();
        }
    }

    /**
     * Creates the profile directory if needed
     */
    public static void load() {
        if (!FILE.exists()) {
            FILE.mkdir();
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
    private GalacticProfile(@Nonnull UUID uuid) {
        Config config = ConfigUtils.loadConfig(FILE, uuid.toString() + ".yml", "profile.yml");
        config.setValue("player", Bukkit.getOfflinePlayer(uuid).getName());
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
    void dirty() {
        this.dirty = true;
    }
    
}
