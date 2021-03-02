package io.github.addoncommunity.galactifun.api.universe.world;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.mooy1.infinitylib.PluginUtils;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Implement this interface to mark an alien as a persistent data holder and have the 2 methods declared
 * here be called on {@link Galactifun#onEnable()} and {@link Galactifun#onDisable()}. Not to be confused
 * with {@link PersistentDataHolder}
 *
 * @author Seggan
 * @author Mooy1
 *
 * @see Alien
 * @see PersistentDataAPI
 */
public interface PersistentAlien {
    
    NamespacedKey KEY = PluginUtils.getKey("data");
    
    /**
     * This is called on {@link Galactifun#onEnable()} for every instance of the {@link Alien}
     *
     * @param entity the entity
     */
    void load(@Nonnull LivingEntity entity, @Nonnull String data);

    /**
     * This is called on {@link Galactifun#onDisable()} for every instance of the {@link Alien}
     *
     * @param entity the entity
     */
    @Nullable
    String save(@Nonnull LivingEntity entity);
    
    static void loadAll() {
        PluginUtils.log("Loading Alien Entity data...");
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien instanceof PersistentAlien) {
                    String data = entity.getPersistentDataContainer().get(KEY, PersistentDataType.STRING);
                    if (data != null) {
                        ((PersistentAlien) alien).load(entity, data);
                    }
                }
            }
        }
    }
    
    static void saveAll() {
        PluginUtils.log("Saving entity data...");
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien instanceof PersistentAlien) {
                    String data = ((PersistentAlien) alien).save(entity);
                    if (data != null) {
                        entity.getPersistentDataContainer().set(KEY, PersistentDataType.STRING, data);
                    }
                }
            }
        }
    }
    
}
