package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.mooy1.infinitylib.config.ConfigUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
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
     * The config object
     */
    private static final Config config = ConfigUtils.loadConfig("worlds.yml");

    /**
     * The file configuration
     */
    private static final FileConfiguration fileConfig = config.getConfiguration();
    
    @Nonnull
    public static WorldConfig loadConfiguration(@Nonnull String worldName, boolean defaultEnabled) {
        if (!fileConfig.isConfigurationSection(worldName)) {
            
            ConfigurationSection section = fileConfig.createSection(worldName);
            
            section.set("enabled", defaultEnabled);
            
            config.save();
            
            return new WorldConfig(section);
        } else {
            return new WorldConfig(fileConfig.getConfigurationSection(worldName));
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
