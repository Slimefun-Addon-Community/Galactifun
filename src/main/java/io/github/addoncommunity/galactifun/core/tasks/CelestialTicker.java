package io.github.addoncommunity.galactifun.core.tasks;

import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;

/**
 * Task applying effects for celestial objects
 * 
 * @author Mooy1
 */
public final class CelestialTicker implements Runnable {

    public static final long INTERVAL = 100L;
    
    @Override
    public void run() {
        for (CelestialWorld object : CelestialWorld.WORLDS.values()) {
            object.tickWorld();
        }
    }

}
