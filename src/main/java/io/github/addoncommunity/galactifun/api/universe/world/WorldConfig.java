package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;

/**
 * A class for managing the config file for worlds
 * 
 * @author Mooy1
 */
public final class WorldConfig {

    /**
     * The config object
     */
    private static final Config config = Galactifun.inst().attachDefaults(new Config(Galactifun.inst(), "worlds.yml"), "worlds.yml");

    /**
     * The file configuration
     */
    private static final FileConfiguration fileConfig = config.getConfiguration();
    
    @Nonnull
    public static WorldConfig load(@Nonnull String worldName, boolean defaultEnabled) {
        ConfigurationSection section = fileConfig.getConfigurationSection(worldName);

        if (section == null) {
            section = fileConfig.createSection(worldName);
            section.set("enabled", defaultEnabled);
            config.save();
        }
        
        return new WorldConfig(section);
    }
    
    private WorldConfig(@Nonnull ConfigurationSection section) {
        this.section = section;
    }
    
    @Nonnull
    private final ConfigurationSection section;
    
    public boolean isEnabled() {
        return this.section.getBoolean("enabled");
    }
    
}
