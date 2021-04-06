package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

final class ComplexStructureBlock implements StructureBlock {

    private final Material material;
    private final StructureBlockProperty<?, ?>[] properties;

    ComplexStructureBlock(Material material, StructureBlockProperty<?, ?>... properties) {
        this.material = material;
        this.properties = properties;
    }

    @Override
    public void paste(Block block) {
        block.setType(this.material);
        BlockData data = block.getBlockData();
        for (StructureBlockProperty<?, ?> property : this.properties) {
            property.paste(data);
        }
        block.setBlockData(data);
    }

}
