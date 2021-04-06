package io.github.addoncommunity.galactifun.core.structures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.block.Block;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
abstract class StructureBlock {
    
    abstract void paste(Block block);
    
}
