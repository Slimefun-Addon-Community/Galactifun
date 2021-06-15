package io.github.addoncommunity.galactifun.util;

import java.util.Random;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.block.BlockFace;

/**
 * Utilities
 *
 * @author Seggan
 * @author Mooy1
 */
@UtilityClass
public final class Util {

    public static final double KM_PER_LY = 9_700_000_000D;
    public static final Pattern COORD_PATTERN = Pattern.compile("^-?\\d+ -?\\d+$");
    public static final Pattern SPACE_PATTERN = Pattern.compile(" ");
    public static final BlockFace[] SURROUNDING_FACES = {
            BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST,
            BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST
    };

    /**
     * From and to are inclusive
     */
    public static int random(int from, int to, @Nonnull Random random) {
        return from + random.nextInt(1 + to - from);
    }
    
}
