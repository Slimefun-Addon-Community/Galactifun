package io.github.addoncommunity.galactifun.core.alien;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;
import io.github.mooy1.infinitylib.misc.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

/**
 * Ticker for aliens
 *
 * @author Seggan
 * @author Mooy1
 */
public final class AlienTicker implements Runnable {

    public static final int INTERVAL = ConfigUtils.getOrDefault("mob-tick-interval", 1, 20, 2);
    
    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = GalacticRegistry.getAlien(entity);
                if (alien != null) {
                    alien.onMobTick(entity);
                }
            }
        }
    }
    
}
