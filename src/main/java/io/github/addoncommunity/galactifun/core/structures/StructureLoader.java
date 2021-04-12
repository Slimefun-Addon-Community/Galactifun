package io.github.addoncommunity.galactifun.core.structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

import lombok.experimental.UtilityClass;

import org.apache.commons.codec.Charsets;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;

/**
 * A class for loading Galactic Structures
 *
 * @author Mooy1
 */
@UtilityClass
public final class StructureLoader {

    /**
     * An empty structure fallback for errors
     */
    private static final GalacticStructure ERROR = new GalacticStructure("ERROR", StructureRotation.NORTH, 0, 0, 0);

    /**
     * All loaded structures
     */
    private static final Map<String, GalacticStructure> STRUCTURES = new HashMap<>();

    /**
     * Get the paths of all loaded structures
     */
    public static Set<String> structurePaths() {
        return STRUCTURES.keySet();
    }

    /**
     * Gets a structure or loads from a plugins resources
     */
    public static GalacticStructure getFromPlugin(JavaPlugin plugin, String path) {
        return STRUCTURES.get(plugin.getName() + ":" + path);
    }

    /**
     * Gets a structure or loads from a file path
     */
    public static GalacticStructure getFromPath(String path) {
        return STRUCTURES.get(path);
    }

    /**
     * Loads a structure from the file's path
     */
    public static GalacticStructure loadFromFilePath(String filePath) {
        // check file name
        if (!filePath.endsWith(".gs")) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + filePath + "' that doesn't end with '.gs'!"
            );
            return ERROR;
        }

        // load
        try {
            GalacticStructure load = load(filePath.replace(".gs", ""), new FileInputStream(filePath));
            STRUCTURES.put(filePath, load);
            return load;
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + filePath + "' due to " +
                            e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
            );
            return ERROR;
        }
    }

    /**
     * Loads a structure from Galactifun
     */
    public static GalacticStructure loadFromGalactifun(String path) {
        return loadFromPlugin(Galactifun.inst(), path);
    }

    /**
     * Loads a structure from a plugin's resources
     */
    public static GalacticStructure loadFromPlugin(JavaPlugin plugin, String path) {
        // check name
        if (!path.endsWith(".gs")) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from resources of plugin '"
                            + plugin.getName() + "' in '" + path + "' that doesn't end with '.gs'!"
            );
            return ERROR;
        }

        // load
        try {
            GalacticStructure load = load(path.substring(path.lastIndexOf('/') + 1, path.length() - 3), Objects.requireNonNull(plugin.getResource(path)));
            STRUCTURES.put(plugin.getName() + ":" + path, load);
            return load;
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from resources of plugin '"
                            + plugin.getName() + "' in '" + path + "' due to " +
                            e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
            );
            return ERROR;
        }
    }

    /**
     * Creates a new structure from the world
     */
    public static GalacticStructure create(String name, StructureRotation rotation, Block pos1, Block pos2) {
        GalacticStructure structure = new GalacticStructure(name, rotation,
                pos2.getX() - pos1.getX(),
                pos2.getY() - pos1.getY(),
                pos2.getZ() - pos1.getZ()
        );
        GalacticStructure.StructureIterator iterator = structure.new StructureIterator();
        while (iterator.hasNext()) {
            iterator.setNextBlock(createBlock(pos1.getRelative(iterator.x, iterator.y, iterator.z)));
        }
        return structure;
    }

    /**
     * Saves a structure to a file
     */
    public static void save(GalacticStructure structure, File folder) {
        StringBuilder save = new StringBuilder();

        // add dimensions
        save.append(structure.rotation)
                .append(',')
                .append(structure.dx)
                .append(',')
                .append(structure.dy)
                .append(',')
                .append(structure.dz);

        // add blocks
        GalacticStructure.StructureIterator iterator = structure.new StructureIterator();
        while (iterator.hasNext()) {
            save.append(';').append(iterator.getNextBlock().save());
        }

        // save
        File file = new File(folder, structure.path + ".gs");
        STRUCTURES.put(file.getPath(), structure);
        Galactifun.inst().runAsync(() -> {
            file.getParentFile().mkdirs();
            try {
                Files.writeString(file.toPath(), save.toString(), Charsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static GalacticStructure load(String name, InputStream stream) throws IOException {
        String[] split = PatternUtils.SEMICOLON.split(new String(stream.readAllBytes()));
        String[] dims = PatternUtils.COMMA.split(split[0]);
        GalacticStructure structure = new GalacticStructure(name,
                StructureRotation.valueOf(dims[0]),
                Integer.parseInt(dims[1]),
                Integer.parseInt(dims[2]),
                Integer.parseInt(dims[3])
        );
        int i = 1;
        GalacticStructure.StructureIterator iterator = structure.new StructureIterator();
        while (iterator.hasNext()) {
            iterator.setNextBlock(loadBlock(split[i]));
        }
        return structure;
    }

    private static StructureBlock createBlock(Block block) {
        if (block.getType() == Material.AIR) {
            return StructureBlock.AIR;
        }

        Material material = block.getType();

        BlockData data = block.getBlockData();

        if (data instanceof Directional) {
            return new DirectionalStructureBlock(material, ((Directional) data).getFacing());
        }

        return StructureBlock.get(material);
    }

    private static StructureBlock loadBlock(String block) {
        if (block.length() == 0) {
            return StructureBlock.AIR;
        }
        String[] split = PatternUtils.COMMA.split(block);
        switch (split.length) {
            case 1: return new StructureBlock(Material.valueOf(split[0]));
            case 2: return new DirectionalStructureBlock(Material.valueOf(split[0]), BlockFace.valueOf(split[1]));
        }
        throw new IllegalArgumentException("Failed to load structure block from String '" + block + "'");
    }

}
