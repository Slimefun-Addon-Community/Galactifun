package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

/**
 * The class for managing the Galactifun Structure Format (GSF)
 *
 * @author Seggan
 */
public class GalacticStructure {

    @Getter
    private final Set<SimpleBlock> blocks;

    public GalacticStructure(@Nonnull World world, @Nonnull BlockVector3 pos1, @Nonnull BlockVector3 pos2) {
        this(world, new CuboidRegion(pos1, pos2));
    }

    public GalacticStructure(@Nonnull World world, @Nonnull CuboidRegion region) {
        this(getFromRegion(region, world));
    }

    private GalacticStructure(Set<SimpleBlock> blocks) {
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

            if (!mat.isAir()) {
                blockSet.add(new SimpleBlock(mat, loc.subtract(origin)));
            }
        }

        return blockSet;
    }

    /**
     * Serializes the current GSF to a string that can be deserialized with {@link #deserialize(String)}
     *
     * @return a string that is the serialized GSF
     */
    @Nonnull
    public String serialize() {
        JsonArray array = new JsonArray();
        for (SimpleBlock block : this.blocks) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.add("m", new JsonPrimitive(block.getMaterial().name()));

            BlockVector3 loc = block.getLocation();
            jsonObject.add("x", new JsonPrimitive(loc.getX()));
            jsonObject.add("y", new JsonPrimitive(loc.getY()));
            jsonObject.add("z", new JsonPrimitive(loc.getZ()));

            array.add(jsonObject);
        }

        return array.toString();
    }

    /**
     * Saves the current GSF to the specified file
     *
     * @param file the file too save the GSF to
     */
    public void save(@Nonnull File file) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        try {
            Files.writeString(file.toPath(), serialize(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Loads a GSF file and deserializes it
     *
     * @param file the file to load
     * @return a new {@link GalacticStructure}
     * @throws FileNotFoundException if the file was not found
     */
    @Nonnull
    public static GalacticStructure load(@Nonnull File file) throws FileNotFoundException {
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }

        try {
            return deserialize(Files.readString(file.toPath()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Deserializes a GSF string
     *
     * @param serialized the serialized string
     * @return a new {@link GalacticStructure}
     */
    @Nonnull
    public static GalacticStructure deserialize(@Nonnull String serialized) {
        Set<SimpleBlock> blocks = new HashSet<>();

        JsonArray array = new JsonParser().parse(serialized).getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();

            Material material = Material.valueOf(object.get("m").getAsString());
            int x = object.get("x").getAsInt();
            int y = object.get("y").getAsInt();
            int z = object.get("z").getAsInt();

            blocks.add(new SimpleBlock(material, BlockVector3.at(x, y, z)));
        }

        return new GalacticStructure(blocks);
    }

    public void paste(Location location) {
        for (SimpleBlock b : this.blocks) {
            BlockVector3 pos = b.getLocation();

            location.clone().add(pos.getX(), pos.getY(), pos.getZ()).getBlock().setType(b.getMaterial());
        }
    }
    
}
