package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import io.github.addoncommunity.galactifun.Galactifun;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        
        // default structures
        for (File file : Objects.requireNonNull(new File(galactifun.getClass().getResource("structures").getFile()).listFiles())) {
            try {
                RESOURCE_STRUCTURES.put(file.getName().replace(".gsg", ""), 
                        new GalacticStructure(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // user structures
        for (File file : Objects.requireNonNull(USER_STRUCTURE_FOLDER.listFiles())) {
            if (file.getName().endsWith(".gsf")) {
                try {
                    USER_STRUCTURES.put(file.getName().replace(".gsf", ""),
                            new GalacticStructure(Files.readString(file.toPath(), StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
    
    /**
     * Create structure
     */
    private GalacticStructure(String name, Block pos1, Block pos2) {
        
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
                    JsonObject jsonObject = new JsonObject();
                    Block b = pos1.getRelative(x, y, z);
                    if (b.getType() != Material.AIR) {
                        StructureBlock block = this.blockCube[x][y][z] = new StructureBlock(b.getType());
                        block.save(jsonObject);
                    }
                    array.add(jsonObject);
                }
            }
        }
        
        // save
        Galactifun.inst().runAsync(() -> {
            File file = new File(USER_STRUCTURE_FOLDER, name + ".gsf");
            try {
                Files.writeString(file.toPath(), array.getAsString(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Load structure
     */
    private GalacticStructure(String json) {
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
                    JsonObject object = array.get(i).getAsJsonObject();
                    if (object.size() != 0) {
                        this.blockCube[x][y][z] = new StructureBlock(object);
                    }
                }
            }
        }
    }
    
    public void paste(Block pos) {
        for (int x = 0 ; x < this.blockCube.length ; x++) {
            StructureBlock[][] blockSlice = this.blockCube[x];
            for (int y = 0 ; y < blockSlice.length ; y++) {
                StructureBlock[] blockColumn = blockSlice[y];
                for (int z = 0 ; z < blockColumn.length ; z++) {
                    StructureBlock block = blockColumn[z];
                    if (block != null) {
                        block.paste(pos.getRelative(x, y, z));
                    } else {
                        pos.getRelative(x, y, z).setType(Material.AIR);
                    }
                }
            }
        }
    }
    
}
