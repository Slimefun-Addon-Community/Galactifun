package io.github.addoncommunity.galactifun.core.structures.blocks;

import com.google.gson.JsonObject;
import io.github.addoncommunity.galactifun.core.structures.StructureRotation;
import io.github.addoncommunity.galactifun.core.structures.properties.StructureBlockProperty;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class PropertyStructureBlock extends StructureBlock {

    private final StructureBlockProperty property;

    public PropertyStructureBlock(Material material, StructureBlockProperty property) {
        super(material);
        this.property = property;
    }

    @Override
    public void paste(Block block, StructureRotation rotation) {
        super.paste(block, rotation);
        BlockData data = block.getBlockData();
        this.property.apply(data, rotation);
        block.setBlockData(data);
    }

    @Override
    public void save(JsonObject object) {
        super.save(object);
        this.property.save(object);
    }

}
