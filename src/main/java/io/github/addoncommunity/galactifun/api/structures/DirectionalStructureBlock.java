package io.github.addoncommunity.galactifun.api.structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

public final class DirectionalStructureBlock extends StructureBlock {

    private final BlockFace direction;

    public DirectionalStructureBlock(Material material, BlockFace direction) {
        super(material);
        this.direction = direction;
    }

    @Override
    public void paste(Block block, StructureRotation rotation) {
        super.paste(block, rotation);
        BlockData data = block.getBlockData();
        ((Directional) data).setFacing(rotation.rotateFace(this.direction));
        block.setBlockData(data);
    }

    @Override
    public String save() {
        return super.save() + ',' + this.direction.name();
    }

}
