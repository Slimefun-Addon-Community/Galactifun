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
import java.util.concurrent.atomic.AtomicInteger;
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
     * Folder for saved structures
     */
    private static final File STRUCTURE_FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");
    
    public static void loadStructureFolder(Galactifun galactifun) {
        galactifun.log("Loading structures...");
        if (!STRUCTURE_FOLDER.mkdir()) {
            for (File file : Objects.requireNonNull(STRUCTURE_FOLDER.listFiles())) { 
                load(file.getPath());
            }
        }
    }
    
    /**
     * An empty structure fallback for errors
     */
    private static final GalacticStructure ERROR = new GalacticStructure("ERROR", StructureRotation.DEFAULT, 0, 0, 0);

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
    public static GalacticStructure getFromPath(String name) {
        return STRUCTURES.get(name);
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
     * Saves a structure to a file
     */
    public static void save(GalacticStructure structure) {
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
        structure.getAll((block, x, y, z) -> save.append(';').append(block.save()));

        // save
        File file = new File(STRUCTURE_FOLDER, structure.name + ".gs");
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

    /**
     * Creates a new structure from the world
     */
    public static GalacticStructure create(String name, StructureRotation rotation, Block pos1, Block pos2) {
        GalacticStructure structure = new GalacticStructure(name, rotation,
                pos2.getX() - pos1.getX(),
                pos2.getY() - pos1.getY(),
                pos2.getZ() - pos1.getZ()
        );
        structure.setAll((x, y, z) -> createBlock(pos1.getRelative(x, y, z)));
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

    private static GalacticStructure load(String name, InputStream stream) throws IOException {
        String[] split = PatternUtils.SEMICOLON.split(new String(stream.readAllBytes()));
        String[] dims = PatternUtils.COMMA.split(split[0]);
        GalacticStructure structure = new GalacticStructure(name,
                StructureRotation.valueOf(dims[0]),
                Integer.parseInt(dims[1]),
                Integer.parseInt(dims[2]),
                Integer.parseInt(dims[3])
        );
        AtomicInteger i = new AtomicInteger(1);
        structure.setAll((x, y, z) -> loadBlock(split[i.getAndIncrement()]));
        return structure;
    }
    
    private static GalacticStructure load(String name) {
        // check file name
        if (!name.endsWith(".gs")) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + name + "' that doesn't end with '.gs'!"
            );
            return ERROR;
        }

        // load
        try {
            GalacticStructure load = load(name.replace(".gs", ""), new FileInputStream(name));
            STRUCTURES.put(name, load);
            return load;
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + name + "' due to " +
                            e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
            );
            return ERROR;
        }
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
