package io.github.addoncommunity.galactifun.core.structures.blocks;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.EnumMap;

/**
 * A structure block with just a material, cached for each material
 * 
 * @author Mooy1
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StructureBlock {

    public static final StructureBlock AIR = new StructureBlock(Material.AIR);
    private static final EnumMap<Material, StructureBlock> CACHED = new EnumMap<>(Material.class);

    public static StructureBlock get(Material material) {
        return CACHED.computeIfAbsent(material, StructureBlock::new);
    }
    
    private final Material material;
    
    @OverridingMethodsMustInvokeSuper
    public void paste(Block block, StructureRotation rotation) {
        block.setType(this.material);
    }
    
    @OverridingMethodsMustInvokeSuper
    public void save(JsonObject object) {
        object.add("m", new JsonPrimitive(this.material.name()));
    }
    
}
