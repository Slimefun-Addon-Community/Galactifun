package io.github.addoncommunity.galactifun.core.profile;

import io.github.addoncommunity.galactifun.Galactifun;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class GalacticProfile {
    
    private static final Map<UUID, GalacticProfile> PROFILES = new HashMap<>();

    /**
     * Gets, loads, or creates a new galactic profile
     */
    @Nonnull
    public static GalacticProfile get(@Nonnull Player p) {
        return PROFILES.computeIfAbsent(p.getUniqueId(), GalacticProfile::new);
    }

    public static void saveAll() {
        for (GalacticProfile profile : PROFILES.values()) {
            if (profile.dirty) {
                profile.save();
            }
        }
    }

    public static void closeAll() {
        for (GalacticProfile profile : PROFILES.values()) {
            profile.close();
        }
    }
    
    public static void loadAll() {
        
    }
    
    /**
     * Used to determine if this profile needs to be saved
     */
    private boolean dirty;
    
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

    private GalacticProfile(@Nonnull UUID uuid) {
        
        Config config = new Config(, );
        this.config = config;
        this.backpack = new GalacticBackpack(this, config);
        
    }
    
    private void save() {
        this.backpack.save(this.config);
    }
    
    private void close() {
        this.backpack.close();
    }
    
    void dirty() {
        this.dirty = true;
    }
    
}
