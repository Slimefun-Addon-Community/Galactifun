package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import io.github.addoncommunity.galactifun.Galactifun;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * The class for managing Galactifun Structures
 *
 * @author Seggan
 * @author Mooy1
 */
public final class GalacticStructure {

    private static final File USER_STRUCTURE_FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");
    public static final Map<String, GalacticStructure> DEFAULT_STRUCTURES = loadDefaultStructures();
    public static final Map<String, GalacticStructure> STRUCTURES = new HashMap<>();
    
    public static void loadStructures(Galactifun galactifun) {
        galactifun.log("Loading structures...");
        
        // user structures
        USER_STRUCTURE_FOLDER.mkdir();
        for (File file : Objects.requireNonNull(USER_STRUCTURE_FOLDER.listFiles())) {
            if (file.getName().endsWith(".gs")) {
                loadStructure(file);
            }
        }
    }

    private static Map<String, GalacticStructure> loadDefaultStructures(String... names) {
        Map<String, GalacticStructure> defaultStructures = new HashMap<>();
        for (String name : names) {
            try (InputStream resource = Objects.requireNonNull(Galactifun.inst().getResource("structures/" + name + ".gs"))) {
                GalacticStructure structure = new GalacticStructure(name, new Scanner(resource).next());
                defaultStructures.put(name, structure);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Collections.unmodifiableMap(defaultStructures);
    }
    
    public static void createStructure(String name, Block pos1, Block pos2) {
        GalacticStructure structure = new GalacticStructure(name, pos1, pos2);
        STRUCTURES.put(structure.name, structure);
    }
    
    private static void loadStructure(File file) {
        try {
            GalacticStructure structure = new GalacticStructure(
                    file.getName().replace(".gs", ""),
                    Files.readString(file.toPath(), StandardCharsets.UTF_8)
            );
            STRUCTURES.put(structure.name, structure);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final StructureBlockBuilder blockBuilder = new StructureBlockBuilder();
    private final StructureBlock[][][] blockCube;
    private final String name;
    
    /**
     * Create structure
     */
    private GalacticStructure(String name, Block pos1, Block pos2) {
        this.name = name;
        
        // dimensions
        int dx = pos2.getX() - pos1.getX() + 1;
        int dy = pos2.getY() - pos1.getY() + 1;
        int dz = pos2.getZ() - pos1.getZ() + 1;
        
        // create arrays
        this.blockCube = new StructureBlock[Math.abs(dx)][Math.abs(dy)][Math.abs(dz)];
        JsonArray array = new JsonArray();
        
        // add dimensions
        JsonObject dimensions = new JsonObject();
        dimensions.add("x", new JsonPrimitive(this.blockCube.length));
        dimensions.add("y", new JsonPrimitive(this.blockCube[0].length));
        dimensions.add("z", new JsonPrimitive(this.blockCube[0][0].length));
        array.add(dimensions);
        
        // add blocks
        for (int x = 0 ; x < dx ; x++) {
            for (int y = 0 ; y < dy ; y++) {
                for (int z = 0 ; z < dz ; z++) {
                    // load, add, save object
                    JsonObject save = new JsonObject();
                    this.blockCube[x][y][z] = createBlock(pos1.getRelative(x, y, z), save);
                    array.add(save);
                }
            }
        }
        
        // save
        Galactifun.inst().runAsync(() -> {
            File file = new File(USER_STRUCTURE_FOLDER, this.name + ".gs");
            try {
                Files.writeString(file.toPath(), array.toString(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                Galactifun.inst().log(Level.SEVERE, "Failed to save Galactic Structure " + this.name + "!");
                e.printStackTrace();
            }
        });
    }

    /**
     * Load structure
     */
    private GalacticStructure(String name, String json) {
        this.name = name;
        
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();

            // load dimensions
            JsonObject dimensions = array.get(0).getAsJsonObject();
            int dimX = dimensions.get("x").getAsInt();
            int dimY = dimensions.get("y").getAsInt();
            int dimZ = dimensions.get("z").getAsInt();

            // create array
            this.blockCube = new StructureBlock[dimX][dimY][dimZ];

            // load blocks
            int i = 1;
            for (int x = 0 ; x < dimX ; x++) {
                for (int y = 0 ; y < dimY ; y++) {
                    for (int z = 0 ; z < dimZ ; z++, i++) {
                        this.blockCube[x][y][z] = loadBlock(array.get(i).getAsJsonObject());
                    }
                }
            }
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE, "Failed to load Galactic Structure " + this.name + "!");
            throw e;
        }
    }
    
    public void paste(Block pos) {
        // TODO find a way to rotate the whole structure and paste accordingly
        for (int x = 0 ; x < this.blockCube.length ; x++) {
            for (int y = 0 ; y < this.blockCube[x].length ; y++) {
                for (int z = 0 ; z < this.blockCube[x][y].length ; z++) {
                    this.blockCube[x][y][z].paste(pos.getRelative(x, y, z));
                }
            }
        }
    }

    private StructureBlock loadBlock(JsonObject object) {
        if (object.size() == 0) {
            return BasicStructureBlock.AIR;
        }
        
        Material material = Material.valueOf(object.get("m").getAsString());
        
        if (object.size() == 1) {
            BasicStructureBlock.get(material);
        }

        this.blockBuilder.setMaterial(material);
        
        this.blockBuilder.addProperty(new DirectionalProperty(BlockFace.valueOf(object.get("d").getAsString())));
        
        return this.blockBuilder.build();
    }

    private StructureBlock createBlock(Block block, JsonObject save) {
        if (block.getType() == Material.AIR) {
            return BasicStructureBlock.AIR;
        }

        Material type = block.getType();
        save.add("m", new JsonPrimitive(type.name()));
        this.blockBuilder.setMaterial(type);
        
        BlockData data = block.getBlockData();
        
        if (data instanceof Directional) {
            BlockFace dir = ((Directional) data).getFacing();
            save.add("d", new JsonPrimitive(dir.name()));
            this.blockBuilder.addProperty(new DirectionalProperty(dir));
        }
        
        return this.blockBuilder.build();
    }

}
