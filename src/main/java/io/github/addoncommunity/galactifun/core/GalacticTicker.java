package io.github.addoncommunity.galactifun.core;

import io.github.addoncommunity.galactifun.api.universe.CelestialWorld;

/**
 * Task applying effects for celestial objects
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
