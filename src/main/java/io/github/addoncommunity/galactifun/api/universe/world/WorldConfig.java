package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.mooy1.infinitylib.config.CustomConfig;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A class for managing the config file for worlds
 * 
 * @author Mooy1
 */
public final class WorldConfig {

    /**
     * The config object used to save and load
     */
    private static final CustomConfig customConfig = new CustomConfig("worlds.yml");

    /**
     * The file configuration used to read and write
     */
    private static final FileConfiguration config = customConfig.getConfig();
    
    @Nonnull
    public static WorldConfig loadConfiguration(@Nonnull String worldName, boolean defaultEnabled) {
        if (!config.isConfigurationSection(worldName)) {
            
            ConfigurationSection section = config.createSection(worldName);
            
            section.set("enabled", defaultEnabled);
            
            customConfig.save();
            
            return new WorldConfig(section);
        } else {
            return new WorldConfig(config.getConfigurationSection(worldName));
        }
    }
    
    private WorldConfig(@Nullable ConfigurationSection section) {
        Validate.notNull(section);
        this.section = section;
    }
    
    @Nonnull
    private final ConfigurationSection section;
    
    public boolean isEnabled() {
        return this.section.getBoolean("enabled");
    }
    
}
