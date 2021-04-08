package io.github.addoncommunity.galactifun.core.structures.properties;

import com.google.gson.JsonObject;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import org.bukkit.block.data.BlockData;

/**
 * A property of a structure block
 * 
 * @author Mooy1
 */
public abstract class StructureBlockProperty {

    public abstract void apply(BlockData data, StructureRotation rotation);
    
    public abstract void save(JsonObject object);
    
}
