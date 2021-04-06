package io.github.addoncommunity.galactifun.core.structures;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.EnumMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class BasicStructureBlock extends StructureBlock {

    static final StructureBlock AIR = new BasicStructureBlock(Material.AIR);
    private static final EnumMap<Material, BasicStructureBlock> CACHED = new EnumMap<>(Material.class);

    static StructureBlock get(Material material) {
        return CACHED.computeIfAbsent(material, BasicStructureBlock::new);
    }
    
    private final Material material;

    @Override
    void paste(Block block) {
        block.setType(this.material);
    }
    
}
