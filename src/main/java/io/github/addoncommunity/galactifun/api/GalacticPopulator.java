package io.github.addoncommunity.galactifun.api;

/*
*
* Populator utility for simple ore population
*
* @author GallowsDove
*
 */

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

// TODO add support for Slimefun blocks

public class GalacticPopulator extends BlockPopulator {
    private final int attempts;
    private final int chance;
    private final int miny;
    private final int maxy;
    private final int minSize;
    private final int maxSize;
    @Nonnull Material ore;
    @Nonnull List<Material> source;


    public GalacticPopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize, @Nonnull Material ore,
                             @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize= maxSize;
        this.ore = ore;
        this.source = new ArrayList<>(Arrays.asList(source));


    }

    @Override
    public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
        for (int i = 1; i < attempts; i++) {
            if (random.nextInt(100) < chance) {
                int x = random.nextInt(15);
                int y = random.nextInt(maxy - miny) + miny;
                int z = random.nextInt(15);

                if (source.contains(chunk.getBlock(x, y, z).getType())) {
                    boolean canContinue = true;
                    int length = 0;

                    while (canContinue) {
                        chunk.getBlock(x, y, z).setType(ore);
                        if ((length < minSize) || (random.nextInt(100) < 50)) {
                            switch (random.nextInt(6)) {
                                case 0:
                                    x = Math.min(x + 1, 15);
                                    break;
                                case 1:
                                    y = Math.min (y + 1, maxy);
                                    break;
                                case 2:
                                    z = Math.min(z + 1, 15);
                                    break;
                                case 3:
                                    x = Math.max(x - 1, 0);
                                    break;
                                case 4:
                                    y = Math.max(y - 1, miny);
                                    break;
                                case 5:
                                    z = Math.max(z - 1, 0);
                                    break;
                            }
                            length++;

                            canContinue = (source.contains(chunk.getBlock(x, y, z).getType())) &&
                                    (length < maxSize);
                        } else canContinue = false;
                    }
                }
            }
        }
    }
}
