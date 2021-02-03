package io.github.addoncommunity.galactifun.core.galactic;

import io.github.addoncommunity.galactifun.api.universe.world.CelestialWorld;
import io.github.addoncommunity.galactifun.core.GalacticRegistry;

/**
 * Task applying effects for celestial objects
 * 
 * @author Mooy1
 */
public final class GalacticTicker implements Runnable {

    public static final long INTERVAL = 100L;
    
    @Override
    public void run() {
        for (CelestialWorld object : GalacticRegistry.CELESTIAL_WORLDS.values()) {
            object.tickWorld();
        }
    }

}
