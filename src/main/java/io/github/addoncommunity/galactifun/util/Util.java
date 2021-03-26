package io.github.addoncommunity.galactifun.util;

import lombok.experimental.UtilityClass;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;
import java.util.Collection;
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

    public static final BlockFace[] SURROUNDING_FACES = {
            BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
            BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST
    };

    public static int random(int from, int to, @Nonnull Random random) {
        return from + random.nextInt(1 + to - from);
    }

    /**
     * Gets the closest {@code double} by value in the collection to the search double
     *
     * @param collection collection to search
     * @param search double to search for
     * @return closest double in the collection to search
     */
    public static double getClosest(@Nonnull Collection<Double> collection, double search) {
        double dist = Double.MAX_VALUE;
        double closest = search;
        for (Double obj : collection) {
            double newDist = Math.abs(obj - search);
            if (newDist < dist) {
                dist = newDist;
                closest = search;
            }
        }

        return closest;
    }


    /**
     * Fast floor, faster than {@code (int) Math.floor(num)}
     *
     * @param num the number to floor
     * @return the floored number
     */
    public static int fastFloor(double num) {
        return num >= 0 ? (int) num : (int) num - 1;
    }
    
}
