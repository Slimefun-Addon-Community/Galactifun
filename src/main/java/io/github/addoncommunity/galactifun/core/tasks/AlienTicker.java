package io.github.addoncommunity.galactifun.core.tasks;

import io.github.addoncommunity.galactifun.api.alien.Alien;
import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.mooy1.infinitylib.ConfigUtils;
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
        for (CelestialWorld world : CelestialWorld.getAll()) {
            for (LivingEntity entity : world.getWorld().getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien != null) {
                    alien.onMobTick(entity);
                }
            }
        }
    }
    
}
