package io.github.addoncommunity.galactifun.api.worlds.populators;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;

/**
 * Populator utility for simple ore population. To populate Slimefun ores use this +
 * {@link AlienWorld#addBlockMapping(Material, SlimefunItemStack)}
 *
 * @author GallowsDove
 * @author Mooy1
 */
public class OrePopulator extends BlockPopulator {

    private final int attempts;
    private final int chance;
    private final int miny;
    private final int maxy;
    private final int minSize;
    private final int maxSize;
    private final Material ore;
    private final Set<Material> replaceable;


    public OrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                        @Nonnull SlimefunItemStack slimefunItem, @Nonnull Material... replaceable) {
        this(attempts, chance, miny, maxy, minSize, maxSize, slimefunItem.getType(), replaceable);
    }

    public OrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                        @Nonnull Material ore, @Nonnull Material... replaceable) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.ore = ore;
        this.replaceable = replaceable.length > 0 ? EnumSet.of(replaceable[0], replaceable) : EnumSet.allOf(Material.class);
    }

    @Override
    public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
        for (int i = 1; i < this.attempts; i++) {
            if (random.nextInt(100) < this.chance) {
                int x = random.nextInt(16);
                int y = random.nextInt(this.maxy - this.miny) + this.miny;
                int z = random.nextInt(16);

                int length = 0;
                while (length < this.maxSize && this.replaceable.contains(chunk.getBlock(x, y, z).getType())) {
                    chunk.getBlock(x, y, z).setType(this.ore, false);

                    if ((length < this.minSize) || (random.nextInt(100) < 50)) {
                        switch (random.nextInt(6)) {
                            case 0 -> x = Math.min(x + 1, 15);
                            case 1 -> y = Math.min(y + 1, this.maxy);
                            case 2 -> z = Math.min(z + 1, 15);
                            case 3 -> x = Math.max(x - 1, 0);
                            case 4 -> y = Math.max(y - 1, this.miny);
                            case 5 -> z = Math.max(z - 1, 0);
                        }
                        length++;
                    }
                }
            }
        }
    }

}
