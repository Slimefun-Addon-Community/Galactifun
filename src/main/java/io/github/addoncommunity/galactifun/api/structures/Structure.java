package io.github.addoncommunity.galactifun.api.structures;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.libraries.dough.common.CommonPatterns;

/**
 * A structure that can be pasted
 *
 * @author Seggan
 * @author Mooy1
 */
// TODO cleanup iterator and rotation stuff
public final class Structure {

    private static final Map<String, Structure> STRUCTURES = new HashMap<>();

    /**
     * Get the paths of all loaded structures
     */
    @Nonnull
    public static Set<String> getLoadedKeys() {
        return STRUCTURES.keySet();
    }

    /**
     * Gets a structure that was loaded from a file by name
     */
    @Nullable
    public static Structure getByKey(String name) {
        return STRUCTURES.get(name);
    }

    /**
     * Gets a structure or loads from a plugins resources
     */
    @Nonnull
    public static Structure get(JavaPlugin plugin, String path) {
        if (!path.endsWith(".gs")) {
            path = path.concat(".gs");
        }

        String key = plugin.getName() + ":" + path;
        Structure structure = STRUCTURES.get(key);

        if (structure != null) {
            return structure;
        }

        try (InputStream stream = Objects.requireNonNull(plugin.getResource(path),
                "No galactic structure found in " + plugin.getName() + "'s resources named " + path)) {

            structure = loadFromString(new String(stream.readAllBytes()));

        } catch (IOException e) {
            throw new IllegalStateException("Failed to read galactic structure '" + path + "' from '" + plugin.getName() + "'", e);
        }

        STRUCTURES.put(key, structure);
        return structure;
    }

    @Nonnull
    public static Structure loadFromString(String string) {
        try {
            String[] split = CommonPatterns.SEMICOLON.split(string, -1);
            String[] dims = CommonPatterns.COMMA.split(split[0]);

            Structure structure = new Structure(StructureRotation.valueOf(dims[0]),
                    Integer.parseInt(dims[1]),
                    Integer.parseInt(dims[2]),
                    Integer.parseInt(dims[3])
            );

            AtomicInteger i = new AtomicInteger(1);
            structure.setEach((x, y, z) -> {
                String block = split[i.getAndIncrement()];
                if (block.length() == 0) {
                    return StructureBlock.AIR;
                }
                String[] blockSplit = CommonPatterns.COMMA.split(block);
                return switch (blockSplit.length) {
                    case 1 -> StructureBlock.of(Material.valueOf(blockSplit[0]));
                    case 2 -> new RotatableBlock(Material.valueOf(blockSplit[0]), BlockFace.valueOf(blockSplit[1]));
                    default -> throw new IllegalArgumentException("Failed to load structure block from String '" + block + "'");
                };
            });

            return structure;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load galactic structure from given string!");
        }
    }

    @Nonnull
    public static Structure create(StructureRotation rotation, Block pos1, Block pos2) {
        Structure structure = new Structure(rotation,
                pos2.getX() - pos1.getX(),
                pos2.getY() - pos1.getY(),
                pos2.getZ() - pos1.getZ()
        );
        structure.setEach((x, y, z) -> {
            Block block = pos1.getRelative(x, y, z);
            if (block.getType() == Material.AIR) {
                return StructureBlock.AIR;
            }
            Material material = block.getType();
            BlockData data = block.getBlockData();
            if (data instanceof Directional dir) {
                return new RotatableBlock(material, dir.getFacing());
            }
            return StructureBlock.of(material);
        });
        return structure;
    }

    /**
     * The 3d array of structure blocks
     */
    private final StructureBlock[][][] structure;

    /**
     * The default rotation of this structure
     */
    private final StructureRotation rotation;

    /**
     * The dimensions of this structure
     */
    private final int dx;
    private final int dy;
    private final int dz;

    private Structure(StructureRotation rotation, int dx, int dy, int dz) {
        this.rotation = rotation;
        this.structure = new StructureBlock
                [Math.abs(this.dx = dx) + 1]
                [Math.abs(this.dy = dy) + 1]
                [Math.abs(this.dz = dz) + 1];
    }

    public void paste(Block pos, StructureRotation rotation) {
        StructureRotation dif = this.rotation.rotationTo(rotation);
        forEach((block, x, y, z) -> block.paste(pos.getRelative(x, y, z), dif));
    }

    public String saveToString() {
        StringBuilder save = new StringBuilder();
        save.append(this.rotation)
                .append(',').append(this.dx)
                .append(',').append(this.dy)
                .append(',').append(this.dz);
        forEach((block, x, y, z) -> save.append(';').append(block.save()));
        return save.toString();
    }

    private void setEach(Setter setter) {
        iterate((x, y, z, ax, ay, az) -> this.structure[ax][ay][az] = setter.set(x, y, z));
    }

    private void forEach(Accessor getter) {
        iterate((x, y, z, ax, ay, az) -> getter.get(this.structure[ax][ay][az], x, y, z));
    }

    private void iterate(Iterator iterator) {
        boolean loop = this.structure.length != 0;
        int ax = 0;
        int ay = 0;
        int az = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        while (loop) {
            iterator.iterate(x, y, z, ax, ay, az);
            if (x == this.dx) {
                x = 0;
                ax = 0;
                if (y == this.dy) {
                    y = 0;
                    ay = 0;
                    if (z == this.dz) {
                        loop = false;
                    } else {
                        az++;
                        if (this.dz > 0) {
                            z++;
                        } else {
                            z--;
                        }
                    }
                } else {
                    ay++;
                    if (this.dy > 0) {
                        y++;
                    } else {
                        y--;
                    }
                }
            } else {
                ax++;
                if (this.dx > 0) {
                    x++;
                } else {
                    x--;
                }
            }
        }
    }

    @FunctionalInterface
    private interface Iterator {

        void iterate(int x, int y, int z, int ax, int ay, int az);

    }

    @FunctionalInterface
    private interface Accessor {

        void get(StructureBlock block, int x, int y, int z);

    }

    @FunctionalInterface
    private interface Setter {

        StructureBlock set(int x, int y, int z);

    }

}
