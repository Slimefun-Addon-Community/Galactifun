package io.github.addoncommunity.galactifun.core.structures;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.EnumMap;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class SimpleStructureBlock implements StructureBlock {

    static final StructureBlock AIR = new SimpleStructureBlock(Material.AIR);
    private static final EnumMap<Material, SimpleStructureBlock> CACHED = new EnumMap<>(Material.class);

    static SimpleStructureBlock get(Material material) {
        return CACHED.computeIfAbsent(material, SimpleStructureBlock::new);
    }

    private final Material material;

    @OverridingMethodsMustInvokeSuper
    public void paste(Block block) {
        block.setType(this.material);
    }

}
