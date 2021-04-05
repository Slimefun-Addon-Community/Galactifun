package io.github.addoncommunity.galactifun.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import io.github.addoncommunity.galactifun.Galactifun;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Rotatable;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

/**
 * The class for managing the Galactifun Structure Format (GSF)
 *
 * @author Seggan
 */
public final class GalacticStructure {

    private static final File USER_STRUCTURE_FOLDER = new File(Galactifun.inst().getDataFolder(), "structures");
    private static final Map<String, GalacticStructure> RESOURCE_STRUCTURES = new HashMap<>();
    private static final Map<String, GalacticStructure> USER_STRUCTURES = new HashMap<>();
    
    public static void loadStructures(Galactifun galactifun) {
        galactifun.log("Loading structures...");
        
        try {
            // default structures
            for (File file : Objects.requireNonNull(new File(galactifun.getClass().getResource("structures").getFile()).listFiles())) {
                GalacticStructure structure = new GalacticStructure(
                        file.getName().replace(".gsg", ""),
                        Files.readString(file.toPath(), StandardCharsets.UTF_8)
                );
                RESOURCE_STRUCTURES.put(structure.name, structure);
            }
            
            // user structures
            for (File file : Objects.requireNonNull(USER_STRUCTURE_FOLDER.listFiles())) {
                if (file.getName().endsWith(".gsf")) {
                    GalacticStructure structure = new GalacticStructure(
                            file.getName().replace(".gsg", ""),
                            Files.readString(file.toPath(), StandardCharsets.UTF_8)
                    );
                    USER_STRUCTURES.put(structure.name, structure);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Nullable
    public static GalacticStructure getResourceStructure(String name) {
        return RESOURCE_STRUCTURES.get(name);
    }
    
    @Nullable
    public static GalacticStructure getUserStructure(String name) {
        return USER_STRUCTURES.get(name);
    }
    
    public static void createUserStructure(String name, Block pos1, Block pos2) {
        USER_STRUCTURES.put(name, new GalacticStructure(name, pos1, pos2));
    }

    /**
     * 3 dimensional array of Structure Blocks in this structure
     */
    private final StructureBlock[][][] blockCube;
    private final String name;
    
    /**
     * Create structure
     */
    private GalacticStructure(String name, Block pos1, Block pos2) {
        this.name = name;
        
        // dimensions
        int dimX = pos2.getX() - pos1.getX() + 1;
        int dimY = pos2.getY() - pos1.getY() + 1;
        int dimZ = pos2.getZ() - pos1.getZ() + 1;
        
        // create arrays
        this.blockCube = new StructureBlock[dimX][dimY][dimZ];
        JsonArray array = new JsonArray();
        
        // add dimensions
        JsonObject dimensions = new JsonObject();
        dimensions.add("x", new JsonPrimitive(this.blockCube.length));
        dimensions.add("y", new JsonPrimitive(this.blockCube[0].length));
        dimensions.add("z", new JsonPrimitive(this.blockCube[0][0].length));
        array.add(dimensions);
        
        // add blocks
        for (int x = 0 ; x < dimX ; x++) {
            for (int y = 0 ; y < dimY ; y++) {
                for (int z = 0 ; z < dimZ ; z++) {
                    // load, add, save object
                    array.add((this.blockCube[x][y][z] = new StructureBlock(pos1.getRelative(x, y, z))).save(new JsonObject()));
                }
            }
        }
        
        // save
        Galactifun.inst().runAsync(() -> {
            File file = new File(USER_STRUCTURE_FOLDER, this.name + ".gsf");
            try {
                Files.writeString(file.toPath(), array.getAsString(), StandardCharsets.UTF_8);
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
                        this.blockCube[x][y][z] = new StructureBlock(array.get(i).getAsJsonObject());
                    }
                }
            }
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE, "Failed to load Galactic Structure " + this.name + "!");
            throw e;
        }
    }
    
    public void paste(Block pos) {
        for (int x = 0 ; x < this.blockCube.length ; x++) {
            for (int y = 0 ; y < this.blockCube[x].length ; y++) {
                for (int z = 0 ; z < this.blockCube[x][y].length ; z++) {
                    this.blockCube[x][y][z].paste(pos.getRelative(x, y, z));
                }
            }
        }
    }

    /**
     * A block within a structure
     * 
     * @author Seggan
     * @author Mooy1
     */
    private static final class StructureBlock {
        
        private Material material = Material.AIR;
        private BlockFace rotation;
        
        private StructureBlock(JsonObject object) {
            if (object.size() == 1) {
                tryLoadMaterial(object);
            } else if (object.size() == 2) {
                tryLoadMaterial(object);
                tryLoadRotation(object);
            }
        }
        
        private void tryLoadMaterial(JsonObject object) {
            this.material = Material.valueOf(object.get("m").getAsString());
        }
        
        private void tryLoadRotation(JsonObject object) {
            String test = object.get("r").getAsString();
            if (test != null) {
                this.rotation = BlockFace.valueOf(test);
            }
        }
        
        private StructureBlock(Block block) {
            this.material = block.getType();
            BlockData data= block.getBlockData();
            if (data instanceof Rotatable) {
                this.rotation = ((Rotatable) data).getRotation();
            } else {
                this.rotation = null;
            }
        }
        
        private JsonObject save(JsonObject object) {
            if (this.material != Material.AIR) {
                object.add("m", new JsonPrimitive(this.material.name()));
                
                if (this.rotation != null) {
                    object.add("r", new JsonPrimitive(this.rotation.name()));
                }
            }
            return object;
        }
        
        private void paste(Block block) {
            block.setType(this.material);
            if (this.rotation != null) {
                Rotatable rotatable = (Rotatable) block.getBlockData();
                rotatable.setRotation(this.rotation);
                block.setBlockData(rotatable);
            }
        }
        
    }

}
