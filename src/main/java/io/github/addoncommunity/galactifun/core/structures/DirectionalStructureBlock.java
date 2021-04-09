package io.github.addoncommunity.galactifun.core.structures;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

final class DirectionalStructureBlock extends StructureBlock {

    private final BlockFace direction;

    DirectionalStructureBlock(Material material, BlockFace direction) {
        super(material);
        this.direction = direction;
    }
    
    @Override
    void paste(Block block, StructureRotation rotation) {
        super.paste(block, rotation);
        BlockData data = block.getBlockData();
        ((Directional) data).setFacing(rotation.relativeTo(this.direction));
        block.setBlockData(data);
    }

    @Override
    public void save(JsonObject object) {
        super.save(object);
        object.add("d", new JsonPrimitive(this.direction.name()));
    }

}
