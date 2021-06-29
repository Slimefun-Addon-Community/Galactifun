package io.github.addoncommunity.galactifun.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;

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
    public static final Set<Material> IMPERMEABLE_BLOCKS = EnumSet.noneOf(Material.class);
    private static final BlockFace[] ALL_SIDES = {
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };

    static {
        IMPERMEABLE_BLOCKS.addAll(SlimefunTag.GLASS_BLOCKS.getValues());
        IMPERMEABLE_BLOCKS.addAll(Arrays.asList(
                Material.IRON_DOOR,
                Material.IRON_TRAPDOOR,
                Material.OBSIDIAN,
                Material.COPPER_BLOCK,
                Material.IRON_BLOCK,
                Material.GOLD_BLOCK,
                Material.DIAMOND_BLOCK,
                Material.NETHERITE_BLOCK
        ));
        IMPERMEABLE_BLOCKS.addAll(Util.getAllMaterialsContaining("WAXED"));
    }

    /**
     * From and to are inclusive
     */
    public static int random(int from, int to, @Nonnull Random random) {
        return from + random.nextInt(1 + to - from);
    }

    @Nonnull
    public static String timeSince(double nanoTime) {
        nanoTime = System.nanoTime() - nanoTime;
        if (nanoTime >= 1_000_000_000D) {
            return ((int) (nanoTime / 1_000_000D)) / 1000 + " s";
        } else {
            return ((int) (nanoTime / 1_000D)) / 1000D + " ms";
        }
    }

    /**
     * Queue-based flood fill algorithm
     *
     * @param start starting block
     * @param max maximum traversed blocks
     *
     * @return blocks traversed, or an empty optional if traversed blocks > max
     */
    @Nonnull
    public static Optional<Set<Location>> floodFill(@Nonnull Location start, int max) {
        if (max == 0) return Optional.empty();

        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new ArrayDeque<>();
        queue.add(start.getBlock());

        while (!queue.isEmpty()) {
            if (visited.size() > max) return Optional.empty();

            Block next = queue.remove();
            visited.add(next);
            for (BlockFace face : ALL_SIDES) {
                Block b = next.getRelative(face);
                if (!IMPERMEABLE_BLOCKS.contains(b.getType()) && !visited.contains(b) && !queue.contains(b)) {
                    queue.add(b);
                }
            }
        }

        return Optional.of(visited.parallelStream().map(Block::getLocation).collect(Collectors.toSet()));
    }

    @Nonnull
    public static Set<Material> getAllMaterialsContaining(@Nonnull String s) {
        Set<Material> materials = EnumSet.noneOf(Material.class);

        for (Material material : Material.values()) {
            if (material.name().contains(s)) {
                materials.add(material);
            }
        }

        return materials;
    }
    
}
