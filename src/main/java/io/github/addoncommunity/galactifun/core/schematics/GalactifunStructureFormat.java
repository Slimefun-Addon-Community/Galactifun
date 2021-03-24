package io.github.addoncommunity.galactifun.core.schematics;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.primitives.Ints;
import io.github.addoncommunity.galactifun.Galactifun;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class GalactifunStructureFormat {

    private static final BiMap<Integer, Material> ids = HashBiMap.create();

    static {
        String file;
        try (InputStream stream = Galactifun.class.getClassLoader().getResourceAsStream("ids.txt")) {
            Scanner s = new Scanner(stream).useDelimiter("\\A");
            file = s.hasNext() ? s.next() : "";
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        for (String s : file.split("\n")) {
            String[] split = s.split(" ");
            ids.put(Integer.parseInt(split[0]), Material.getMaterial(split[1]));
        }
    }

    @Getter
    private final Set<SimpleBlock> blocks;

    public GalactifunStructureFormat(@Nonnull World world, @Nonnull BlockVector3 pos1, @Nonnull BlockVector3 pos2) {
        this(world, new CuboidRegion(pos1, pos2));
    }

    public GalactifunStructureFormat(@Nonnull World world, @Nonnull CuboidRegion region) {
        this(getFromRegion(region, world));
    }

    private GalactifunStructureFormat(Set<SimpleBlock> blocks) {
        this.blocks = blocks;
    }

    /**
     * Represents a simple block, with location (of {@link BlockVector3}) and {@link Material}
     *
     * @author Seggan
     */
    @Data
    public static class SimpleBlock {
        private final Material material;
        private final BlockVector3 location;
    }

    @Nonnull
    private static Set<SimpleBlock> getFromRegion(@Nonnull CuboidRegion region, @Nonnull World world) {
        Set<SimpleBlock> blockSet = new HashSet<>();
        BlockVector3 origin = region.getPos1();

        for (BlockVector3 loc : region) {
            Material mat = loc.getLocation(world).getBlock().getType();

            blockSet.add(new SimpleBlock(mat, loc.subtract(origin)));
        }

        return blockSet;
    }

    public int[] serialize() {
        List<Integer> result = new ArrayList<>();
        for (SimpleBlock block : this.blocks) {
            result.add(ids.inverse().get(block.getMaterial()));

            BlockVector3 loc = block.getLocation();
            result.add(loc.getBlockX());
            result.add(loc.getBlockY());
            result.add(loc.getBlockZ());
        }

        return Ints.toArray(result);
    }

    public void save(File file) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            for (int i : this.serialize()) {
                writer.write(i);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
