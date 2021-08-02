package io.github.addoncommunity.galactifun.api.structures;

import java.util.EnumMap;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * A structure block with just a material, cached for each material
 *
 * @author Mooy1
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class StructureBlock {

    static final StructureBlock AIR = new StructureBlock(Material.AIR) {
        @Override
        public String save() {
            return "";
        }
    };

    private static final EnumMap<Material, StructureBlock> CACHE = new EnumMap<>(Material.class);

    static StructureBlock get(Material material) {
        return CACHE.computeIfAbsent(material, StructureBlock::new);
    }

    private final Material material;

    void paste(Block block, StructureRotation rotation) {
        block.setType(this.material);
    }

    String save() {
        return this.material.name();
    }

}
