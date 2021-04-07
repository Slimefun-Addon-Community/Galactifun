package io.github.addoncommunity.galactifun.core.structures;

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
final class DirectionalProperty extends StructureBlockProperty {

    private final BlockFace direction;

    @Override
    void apply(BlockData data) {
        ((Directional) data).setFacing(this.direction);
    }

}
