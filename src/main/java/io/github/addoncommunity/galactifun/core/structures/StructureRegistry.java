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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.utils.PatternUtils;
import org.apache.commons.codec.Charsets;

/**
 * A class for loading Galactic Structures
 *
 * @author Mooy1
 */
@UtilityClass
public final class StructureRegistry {

    /**
     * Folder for saved structures
     */
    private static final File STRUCTURE_FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");

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
     * Gets a structure that was loaded from a file by name
     */
    @Nullable
    public static GalacticStructure getStructure(String name) {
        return STRUCTURES.get(name);
    }

    /**
     * Gets a structure from Galactifun
     */
    @Nonnull
    public static GalacticStructure getGalactifunStructure(String name) {
        return getPluginStructure(Galactifun.inst(), name);
    }

    /**
     * Gets a structure or loads from a plugins resources
     */
    @Nonnull
    public static GalacticStructure getPluginStructure(JavaPlugin plugin, String name) {
        if (!name.endsWith(".gs")) {
            name = name.concat(".gs");
        }
        GalacticStructure structure = STRUCTURES.get(plugin.getName() + ":" + name);
        if (structure != null) {
            return structure;
        }
        try {
            structure = load(Objects.requireNonNull(plugin.getResource(name),
                "No galactic structure found in " + plugin.getName() + "'s resources named " + name));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load galactic structure '" + name + "' from '" + plugin.getName() + "'", e);
        }
        STRUCTURES.put(plugin.getName() + ":" + name.substring(name.lastIndexOf('/') + 1, name.length() - 3), structure);
        return structure;
    }

    /**
     * Creates a new structure from the world
     */
    @Nonnull
    public static GalacticStructure createStructure(StructureRotation rotation, Block pos1, Block pos2) {
        GalacticStructure structure = new GalacticStructure(rotation,
            pos2.getX() - pos1.getX(),
            pos2.getY() - pos1.getY(),
            pos2.getZ() - pos1.getZ()
        );
        structure.setAll((x, y, z) -> {
            Block block = pos1.getRelative(x, y, z);
            if (block.getType() == Material.AIR) {
                return StructureBlock.AIR;
            }
            Material material = block.getType();
            BlockData data = block.getBlockData();
            if (data instanceof Directional) {
                return new DirectionalStructureBlock(material, ((Directional) data).getFacing());
            }
            return StructureBlock.get(material);
        });
        return structure;
    }

    /**
     * Saves a structure to a file
     */
    public static void saveStructure(String name, GalacticStructure structure) {
        StringBuilder save = new StringBuilder();

        // add dimensions
        save.append(structure.rotation)
            .append(',').append(structure.dx)
            .append(',').append(structure.dy)
            .append(',').append(structure.dz);

        // add blocks
        structure.getAll((block, x, y, z) -> save.append(';').append(block.save()));

        // save
        File file = new File(STRUCTURE_FOLDER, name + ".gs");
        STRUCTURES.put(name, structure);
        file.getParentFile().mkdirs();
        try {
            Files.writeString(file.toPath(), save.toString(), Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads structures saved in structure folder
     */
    public static void loadStructureFolder(Galactifun galactifun) {
        galactifun.log("Loading structures...");
        if (STRUCTURE_FOLDER.mkdir()) {
            return;
        }
        for (File file : Objects.requireNonNull(STRUCTURE_FOLDER.listFiles((dir, name) -> name.endsWith(".gs")))) {
            try {
                GalacticStructure structure = load(new FileInputStream(file));
                STRUCTURES.put(file.getName().substring(0, file.getName().length() - 3), structure);
            } catch (Exception e) {
                Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure '" + file.getName() + "' from structure folder due to " +
                        e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
                );
            }
        }
    }

    /**
     * Loads structures from an input stream
     */
    private static GalacticStructure load(InputStream stream) throws IOException {
        String[] split = PatternUtils.SEMICOLON.split(new String(stream.readAllBytes()), -1);
        String[] dims = PatternUtils.COMMA.split(split[0]);
        GalacticStructure structure = new GalacticStructure(
            StructureRotation.valueOf(dims[0]),
            Integer.parseInt(dims[1]),
            Integer.parseInt(dims[2]),
            Integer.parseInt(dims[3])
        );
        AtomicInteger i = new AtomicInteger(1);
        structure.setAll((x, y, z) -> {
            String block = split[i.getAndIncrement()];
            if (block.length() == 0) {
                return StructureBlock.AIR;
            }
            String[] blockSplit = PatternUtils.COMMA.split(block);
            return switch (blockSplit.length) {
                case 1 -> new StructureBlock(Material.valueOf(blockSplit[0]));
                case 2 -> new DirectionalStructureBlock(Material.valueOf(blockSplit[0]), BlockFace.valueOf(blockSplit[1]));
                default -> throw new IllegalArgumentException("Failed to load structure block from String '" + block + "'");
            };
        });
        return structure;
    }

}
