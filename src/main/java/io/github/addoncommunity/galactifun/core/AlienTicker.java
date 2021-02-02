package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.mob.Alien;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;

public class AlienTicker implements Runnable {

    public final int INTERVAL;

    public AlienTicker() {
        INTERVAL = Galactifun.getInstance().getConfig().getInt("mob-tick-interval", 2);
    }

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
