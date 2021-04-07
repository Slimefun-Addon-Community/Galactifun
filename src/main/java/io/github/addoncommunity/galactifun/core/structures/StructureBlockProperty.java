package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.block.data.BlockData;

/**
 * A property of a structure block
 * 
 * @author Mooy1
 */
abstract class StructureBlockProperty {

    abstract void apply(BlockData data);
    
}
