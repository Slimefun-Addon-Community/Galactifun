package io.github.addoncommunity.galactifun.api.universe.populators;

import io.github.mooy1.infinitylib.PluginUtils;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * Populator utility for simple ore population
 *
 * @author GallowsDove
 * @author Mooy1
 * 
 */
public class GalacticOrePopulator extends BlockPopulator {
    
    private final int attempts;
    private final int chance;
    private final int miny;
    private final int maxy;
    private final int minSize;
    private final int maxSize;
    @Nonnull private final Material ore;
    @Nullable private final String id;
    @Nonnull private final Material[] source;


    public GalacticOrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                                @Nonnull SlimefunItemStack slimefunItem, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize= maxSize;
        this.ore = slimefunItem.getType();
        this.id = slimefunItem.getItemId();
        this.source = source;
    }

    public GalacticOrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                                @Nonnull Material ore, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize= maxSize;
        this.ore = ore;
        this.id = null;
        this.source = source;
    }

    @Override
    public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
        for (int i = 1 ; i < this.attempts ; i++) {
            if (random.nextInt(100) < this.chance) {
                int x = random.nextInt(16);
                int y = random.nextInt(this.maxy - this.miny) + this.miny;
                int z = random.nextInt(16);
                
                int length = 0;
                while (ArrayUtils.contains(this.source, chunk.getBlock(x, y, z).getType()) && length < this.maxSize) {
                    chunk.getBlock(x, y, z).setType(this.ore);
                    
                    if (this.id != null) {
                        final int fx = x;
                        final int fy = y;
                        final int fz = z;

                        // Can produce concurrentModificationException error, currently non-avoidable ??
                        PluginUtils.runSync(() -> BlockStorage.store(chunk.getBlock(fx, fy, fz), this.id));
                    }

                    if ((length < this.minSize) || (random.nextInt(100) < 50)) {
                        switch (random.nextInt(6)) {
                            case 0:
                                x = Math.min(x + 1, 15);
                                break;
                            case 1:
                                y = Math.min(y + 1, this.maxy);
                                break;
                            case 2:
                                z = Math.min(z + 1, 15);
                                break;
                            case 3:
                                x = Math.max(x - 1, 0);
                                break;
                            case 4:
                                y = Math.max(y - 1, this.miny);
                                break;
                            case 5:
                                z = Math.max(z - 1, 0);
                                break;
                        }
                        length++;
                    }
                }
            }
        }
    }
}
