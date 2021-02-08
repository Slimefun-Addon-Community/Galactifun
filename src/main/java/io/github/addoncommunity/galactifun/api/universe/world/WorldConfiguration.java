package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.PluginUtils;
import lombok.Getter;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.logging.Level;

/**
 * A class for managing the config file for worlds
 * 
 * @author Mooy1
 */
public final class WorldConfiguration {

    /**
     * The world config
     */
    @Getter
    private static FileConfiguration config;

    private static File file;
    
    private static final String FILE_NAME = "worlds.yml";
    
    public static void load(@Nonnull Galactifun galactifun) {
        file = new File(galactifun.getDataFolder(), FILE_NAME);
        
        if (!file.exists()) {
            try {
                galactifun.saveResource(FILE_NAME, false);
            } catch (IllegalArgumentException e) {
                PluginUtils.log(Level.SEVERE, "Failed to save default worlds.yml file!");
            }
        }
        
        config = YamlConfiguration.loadConfiguration(file);

        InputStream defaultResource = galactifun.getResource(FILE_NAME);
        
        Objects.requireNonNull(defaultResource, "Failed to get default worlds.yml file!");
        
        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defaultResource)));
        
        config.options().copyDefaults(true).copyHeader(true);
        
        save();
    }

    private static void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            PluginUtils.log(Level.SEVERE, "Failed to save worlds.yml config file!");
            e.printStackTrace();
        }
    }
    
    @Nonnull
    public static WorldConfiguration loadConfiguration(@Nonnull String worldName, boolean defaultEnabled) {
        if (!config.isConfigurationSection(worldName)) {
            
            ConfigurationSection section = config.createSection(worldName);
            
            section.set("enabled", defaultEnabled);
            
            save();
            
            return new WorldConfiguration(section);
        } else {
            return new WorldConfiguration(config.getConfigurationSection(worldName));
        }
    }
    
    private WorldConfiguration(@Nullable ConfigurationSection section) {
        Validate.notNull(section);
        this.section = section;
    }
    
    @Nonnull
    private final ConfigurationSection section;
    
    public boolean isEnabled() {
        return this.section.getBoolean("enabled");
    }
    
}
