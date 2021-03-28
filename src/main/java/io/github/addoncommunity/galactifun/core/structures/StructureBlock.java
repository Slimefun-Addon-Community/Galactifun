package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * Represents a simple block, with a {@link Material}
 *
 * @author Seggan
 * @author Mooy1
 */ // TODO add rotation and other data and improve save/load
@AllArgsConstructor
public class StructureBlock {

    private final Material material;
    
    // load
    public StructureBlock(JsonObject object) {
        this.material = Material.valueOf(object.get("m").getAsString());
    }
    
    public void paste(Block b) {
        b.setType(this.material);
    }
    
    public void save(JsonObject object) {
        object.add("m", new JsonPrimitive(this.material.name()));
    }

}
