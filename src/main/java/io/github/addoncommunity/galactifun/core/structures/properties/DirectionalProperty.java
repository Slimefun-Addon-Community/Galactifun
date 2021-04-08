package io.github.addoncommunity.galactifun.core.structures.properties;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import lombok.AllArgsConstructor;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

/**
 * A structure block with a direction
 * 
 * @author Mooy1
 */
@AllArgsConstructor
public final class DirectionalProperty extends StructureBlockProperty {

    private final BlockFace direction;

    @Override
    public void apply(BlockData data, StructureRotation rotation) {
        ((Directional) data).setFacing(rotation.relativeTo(this.direction));
    }

    @Override
    public void save(JsonObject object) {
        object.add("d", new JsonPrimitive(this.direction.name()));
    }

}
