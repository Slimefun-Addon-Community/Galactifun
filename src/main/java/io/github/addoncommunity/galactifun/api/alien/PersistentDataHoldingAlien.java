package io.github.addoncommunity.galactifun.api.alien;

import io.github.addoncommunity.galactifun.Galactifun;
import me.mrCookieSlime.Slimefun.cscorelib2.data.PersistentDataAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataHolder;

import javax.annotation.Nonnull;

/**
 * Implement this interface to mark an alien as a persistent data holder and have the 2 methods declared
 * here be called on {@link Galactifun#onEnable()} and {@link Galactifun#onDisable()}. Not to be confused
 * with {@link PersistentDataHolder}
 *
 * @author Seggan
 *
 * @see Alien
 * @see PersistentDataAPI
 */
public interface PersistentDataHoldingAlien {

    /**
     * This is called on {@link Galactifun#onEnable()} for every instance of the {@link Alien}
     *
     * @param entity the entity
     */
    void load(@Nonnull LivingEntity entity);

    /**
     * This is called on {@link Galactifun#onDisable()} for every instance of the {@link Alien}
     *
     * @param entity the entity
     */
    void save(@Nonnull LivingEntity entity);
}
