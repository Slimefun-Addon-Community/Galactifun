package io.github.addoncommunity.galactifun.api.universe.attributes.terrain.populators;

import io.github.mooy1.infinitylib.PluginUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Populator utility for surface boulders
 *
 * @author GallowsDove
 * @author Seggan
 *
 */
public class GalacticBoulderPopulator extends BlockPopulator {
    private final int attempts;
    private final int chance;
    @Nonnull private final Material ore;
    @Nullable private final String id;
    @Nonnull private final List<Material> source;


    public GalacticBoulderPopulator(int attempts, int chance, @Nonnull SlimefunItemStack slimefunItem, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.ore = slimefunItem.getType();
        this.id = slimefunItem.getItemId();
        this.source = new ArrayList<>(Arrays.asList(source));
    }

    public GalacticBoulderPopulator(int attempts, int chance, @Nonnull Material ore, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.ore = ore;
        this.id = null;
        this.source = new ArrayList<>(Arrays.asList(source));
    }

    @Override
    public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
        for (int i = 0 ; i < this.attempts ; i++) {
            if (random.nextInt(100) < this.chance) {

                int x = random.nextInt(16);
                int z = random.nextInt(16);

                Block b = world.getHighestBlockAt((chunk.getX() << 4) + x, (chunk.getZ() << 4) + z);

                if (this.source.contains(b.getType())) {
                    b.getRelative(BlockFace.UP).setType(this.ore);

                    if (this.id != null) {
                        final int fx = x;
                        final int fy = b.getRelative(BlockFace.UP).getY();
                        final int fz = z;

                        // Cam produce concurrentModificationException error, currently non-avoidable
                        PluginUtils.runSync(() -> BlockStorage.store(chunk.getBlock(fx, fy, fz), this.id));
                    }
                }
            }
        }
    }
}
