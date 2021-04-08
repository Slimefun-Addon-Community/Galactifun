package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.structures.blocks.PropertyStructureBlock;
import io.github.addoncommunity.galactifun.core.structures.blocks.StructureBlock;
import io.github.addoncommunity.galactifun.core.structures.properties.DirectionalProperty;
import io.github.addoncommunity.galactifun.core.structures.properties.StructureBlockProperty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * A structure that can be pasted
 *
 * @author Seggan
 * @author Mooy1
 */
public final class GalacticStructure {

    /**
     * An empty structure fallback for errors
     */
    private static final GalacticStructure ERROR = new GalacticStructure("ERROR", 0, 0, 0);

    /**
     * All loaded structures
     */
    static final Map<String, GalacticStructure> STRUCTURES = new HashMap<>();
    
    /**
     * Loads a structure from galactifun resources
     */
    public static GalacticStructure getOrLoadFromGalactifun(String path) {
        return getOrLoadFromPlugin(Galactifun.inst(), path);
    }

    /**
     * Gets a structure or loads from a plugins resources
     */
    public static GalacticStructure getOrLoadFromPlugin(JavaPlugin plugin, String path) {
        return STRUCTURES.computeIfAbsent(plugin.getName() + ":" + path, s -> loadFromPlugin(plugin, path));
    }
    
    /**
     * Gets a structure or loads from a file path
     */
    public static GalacticStructure getOrLoadFromFile(String filePath) {
        return STRUCTURES.computeIfAbsent(filePath, GalacticStructure::loadFromFile);
    }

    /**
     * Gets a structure or loads from a file
     */
    public static GalacticStructure getOrLoadFromFile(File file) {
        return STRUCTURES.computeIfAbsent(file.getPath(), GalacticStructure::loadFromFile);
    }

    /**
     * Creates a new structure from the world
     */
    public static GalacticStructure create(String name, Block pos1, Block pos2) {
        GalacticStructure structure = new GalacticStructure(name,
                pos2.getX() - pos1.getX(),
                pos2.getY() - pos1.getY(),
                pos2.getZ() - pos1.getZ()
        );
        do {
            structure.setCurrentBlock(createBlock(pos1.getRelative(structure.x, structure.y, structure.z)));
        } while (structure.loopDimensions());
        return structure;
    }
    
    /**
     * The name of this structure
     */
    private final String name;

    /**
     * The 3d array of structure blocks
     */
    private final StructureBlock[][][] structure;

    /**
     * The dimensions of this structure
     */
    private final int dx;
    private final int dy;
    private final int dz;

    /**
     * The current index of structure array
     */
    private int ax;
    private int ay;
    private int az;

    /**
     * The current block for looping purposes
     */
    private int x;
    private int y;
    private int z;
    
    private GalacticStructure(String name, int dx, int dy, int dz) {
        this.name = name;
        this.structure = new StructureBlock[Math.abs(this.dx = dx) + 1][Math.abs(this.dy = dy) + 1][Math.abs(this.dz = dz) + 1];
    }
    
    public void paste(Block pos, StructureRotation rotation) {
        // TODO implement rotation
        do {
            getCurrentBlock().paste(pos.getRelative(this.x, this.y, this.z), rotation);
        } while (loopDimensions());
    }
    
    public void save(File folder) {
        JsonArray array = new JsonArray();

        // add dimensions
        JsonObject dimensions = new JsonObject();
        dimensions.add("dx", new JsonPrimitive(this.dx));
        dimensions.add("dy", new JsonPrimitive(this.dy));
        dimensions.add("dz", new JsonPrimitive(this.dz));
        array.add(dimensions);

        // add blocks
        do {
            JsonObject object = new JsonObject();
            getCurrentBlock().save(object);
            array.add(object);
        } while (loopDimensions());

        File file = new File(folder, this.name + ".gs");
        STRUCTURES.put(file.getPath(), this);

        // save
        Galactifun.inst().runAsync(() -> {
            file.getParentFile().mkdirs();
            try {
                Streams.write(array, new JsonWriter(new FileWriter(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private StructureBlock getCurrentBlock() {
        return this.structure[this.ax][this.ay][this.az];
    }

    private void setCurrentBlock(StructureBlock block) {
        this.structure[this.ax][this.ay][this.az] = block;
    }

    private boolean loopDimensions() {
        if (this.x == this.dx) {
            this.x = 0;
            this.ax = 0;
            if (this.y == this.dy) {
                this.y = 0;
                this.ay = 0;
                if (this.z == this.dz) {
                    this.z = 0;
                    this.az = 0;
                    return false;
                } else {
                    this.az++;
                    if (this.dz > 0) {
                        this.z++;
                    } else {
                        this.z--;
                    }
                }
            } else {
                this.ay++;
                if (this.dy > 0) {
                    this.y++;
                } else {
                    this.y--;
                }
            }
        } else {
            this.ax++;
            if (this.dx > 0) {
                this.x++;
            } else {
                this.x--;
            }
        }
        return true;
    }
    
    private static GalacticStructure loadFromFile(String filePath) {
        // check file name
        if (!filePath.endsWith(".gs")) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + filePath + "' that doesn't end with '.gs'!"
            );
            return ERROR;
        }

        // load
        try {
            return load(filePath.replace(".gs", ""), new FileInputStream(filePath));
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from file '" + filePath+ "' due to " +
                            e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
            );
            return ERROR;
        }
    }
    
    private static GalacticStructure loadFromPlugin(JavaPlugin plugin, String path) {
        
        // check name
        if (!path.endsWith(".gs")) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from resources of plugin '"
                            + plugin.getName() + "' in '" + path + "' that doesn't end with '.gs'!"
            );
            return ERROR;
        }

        // check already loaded
        String key = plugin.getName() + ":" + path;
        GalacticStructure loaded = STRUCTURES.get(key);
        if (loaded != null) {
            Galactifun.inst().log(Level.WARNING, plugin.getName() + " attempted to load the galactic structure at '" + path + "' which is already loaded!");
            return loaded;
        }

        // load
        try {
            loaded = load(path.substring(path.lastIndexOf('/') + 1, path.length() - 3), plugin.getResource(path));
        } catch (Exception e) {
            Galactifun.inst().log(Level.SEVERE,
                    "Failed to load galactic structure from resources of plugin '"
                            + plugin.getName() + "' in '" + path + "' due to " +
                            e.getClass().getSimpleName() + (e.getMessage() != null ? ": " + e.getMessage() : "")
            );
            return ERROR;
        }

        // save
        STRUCTURES.put(plugin.getName() + ":" + path, loaded);
        return loaded;
    }

    private static GalacticStructure load(String name, InputStream stream) {
        JsonArray array = Streams.parse(new JsonReader(new InputStreamReader(stream))).getAsJsonArray();
        JsonObject dimensions = array.get(0).getAsJsonObject();
        GalacticStructure structure = new GalacticStructure(name,
                dimensions.get("dx").getAsInt(),
                dimensions.get("dy").getAsInt(),
                dimensions.get("dz").getAsInt()
        );
        int i = 1;
        do {
            structure.setCurrentBlock(loadBlock(array.get(i++).getAsJsonObject()));
        } while (structure.loopDimensions());
        return structure;
    }

    private static StructureBlock loadBlock(JsonObject object) {
        if (object.size() == 0) {
            return StructureBlock.AIR;
        }

        Material material = Material.valueOf(object.get("m").getAsString());

        if (object.size() == 1) {
            return StructureBlock.get(material);
        }

        if (object.size() == 2) {
            return new PropertyStructureBlock(material, new DirectionalProperty(BlockFace.valueOf(object.get("d").getAsString())));
        }

        throw new IllegalArgumentException("Failed to load structure block from JsonObject " + object);
    }

    private static StructureBlock createBlock(Block block) {
        if (block.getType() == Material.AIR) {
            return StructureBlock.AIR;
        }

        Material material = block.getType();

        StructureBlockProperty property = null;

        BlockData data = block.getBlockData();

        if (data instanceof Directional) {
            property = new DirectionalProperty(((Directional) data).getFacing());
        }

        if (property == null) {
            return StructureBlock.get(material);
        } else {
            return new PropertyStructureBlock(material, property);
        }
    }
    
}
