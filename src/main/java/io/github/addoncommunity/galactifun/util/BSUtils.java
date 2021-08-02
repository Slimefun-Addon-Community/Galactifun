package io.github.addoncommunity.galactifun.util;

import java.util.function.Function;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.block.Block;

import me.mrCookieSlime.Slimefun.api.BlockStorage;

@UtilityClass
public class BSUtils {

    @ParametersAreNonnullByDefault
    public static <T> void addBlockInfo(Block b, String key, T o) {
        addBlockInfo(b, key, o, String::valueOf);
    }

    @ParametersAreNonnullByDefault
    public static <T> void addBlockInfo(Block b, String key, T o, Function<T, String> map) {
        BlockStorage.addBlockInfo(b, key, map.apply(o));
    }

    @Nullable
    @ParametersAreNonnullByDefault
    public static <T> T getLocationInfo(Location l, String key, Function<String, T> map) {
        String s = BlockStorage.getLocationInfo(l, key);
        if (s == null) return null;

        return map.apply(s);
    }

    @ParametersAreNonnullByDefault
    public static int getStoredInt(Location l, String key) {
        String s = BlockStorage.getLocationInfo(l, key);
        if (s == null || s.isEmpty() || s.isBlank()) return 0;

        return Integer.parseInt(s);
    }

    @ParametersAreNonnullByDefault
    public static double getStoredDouble(Location l, String key) {
        String s = BlockStorage.getLocationInfo(l, key);
        if (s == null || s.isEmpty() || s.isBlank()) return 0;

        return Double.parseDouble(s);
    }

    @ParametersAreNonnullByDefault
    public static boolean getStoredBoolean(Location l, String key) {
        return Boolean.parseBoolean(BlockStorage.getLocationInfo(l, key));
    }

}
