package io.github.addoncommunity.galactifun.core.tasks;

import io.github.addoncommunity.galactifun.api.mob.Alien;
import io.github.mooy1.infinitylib.ConfigUtils;
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

    public static final int INTERVAL = ConfigUtils.getInt("mob-tick-interval", 1, 20, 2);
    
    @Override
    public void run() {
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                Alien alien = Alien.getByEntity(entity);
                if (alien != null) {
                    alien.onMobTick(entity);
                }
            }
        }
    }
    
}
