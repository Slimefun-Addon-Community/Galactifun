package io.github.addoncommunity.galactifun.core.tasks;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.mooy1.infinitylib.config.ConfigUtils;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

/**
 * Ticker for aliens
 *
 * @author Seggan
 * @author Mooy1
 */
public final class AlienTicker implements Runnable {

    public static final int INTERVAL = ConfigUtils.getInt("aliens.tick-interval", 1, 20, 4);
    
    @Override
    public void run() {
        for (World world : CelestialWorld.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien != null) {
                    alien.onMobTick(entity);
                }
            }
        }
    }
    
}
