package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.CelestialObject;

/**
 * Task applying effects for celestial objects
 */
public final class GalacticTicker implements Runnable {

    public static long INTERVAL = 100L;
    
    @Override
    public void run() {
        for (CelestialObject object : GalacticRegistry.CELESTIAL_OBJECTS.values()) {
            object.tickWorld();
        }
    }

}
