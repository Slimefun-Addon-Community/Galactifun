package io.github.addoncommunity.galactifun.api.universe.world.populators;

import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

/**
 * Populator utility for simple ore population
 *
 * @author GallowsDove
 * @author Mooy1
 * 
 */
public class OrePopulator extends BlockPopulator {
    
    private final int attempts;
    private final int chance;
    private final int miny;
    private final int maxy;
    private final int minSize;
    private final int maxSize;
    @Nonnull
    private final Material ore;
    @Nullable
    private final String id;
    @Nonnull
    private final Set<Material> source;


    public OrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                        @Nonnull SlimefunItemStack slimefunItem, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize= maxSize;
        this.ore = slimefunItem.getType();
        this.id = slimefunItem.getItemId();
        this.source = EnumSet.of(source[0], Arrays.copyOfRange(source, 1, source.length));
    }

    public OrePopulator(int attempts, int chance, int miny, int maxy, int minSize, int maxSize,
                        @Nonnull Material ore, @Nonnull Material... source) {
        this.attempts = attempts;
        this.chance = chance;
        this.miny = miny;
        this.maxy = maxy;
        this.minSize = minSize;
        this.maxSize= maxSize;
        this.ore = ore;
        this.id = null;
        this.source = EnumSet.of(source[0], Arrays.copyOfRange(source, 1, source.length));
    }

    @Override
    public void populate(@Nonnull World world, @Nonnull Random random, @Nonnull Chunk chunk) {
        for (int i = 1 ; i < this.attempts ; i++) {
            if (random.nextInt(100) < this.chance) {
                int x = random.nextInt(16);
                int y = random.nextInt(this.maxy - this.miny) + this.miny;
                int z = random.nextInt(16);
                
                int length = 0;
                while (length < this.maxSize && this.source.contains(chunk.getBlock(x, y, z).getType())) {
                    chunk.getBlock(x, y, z).setType(this.ore);
                    
                    if (this.id != null) {
                        BlockStorage.store(chunk.getBlock(x, y, z), this.id);
                    }

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
