package io.github.addoncommunity.galactifun.util;

import lombok.experimental.UtilityClass;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Utilities
 * 
 * @author Seggan
 * @author Mooy1
 */
@UtilityClass
public final class Util {

    public static final double KM_PER_LY = 9_700_000_000D;
    
    /**
     * Random number from to inclusive
     */
    public static int randomFrom(int from, int to, @Nonnull Random random) {
        return from + random.nextInt(1 + to - from);
    }
    
}
