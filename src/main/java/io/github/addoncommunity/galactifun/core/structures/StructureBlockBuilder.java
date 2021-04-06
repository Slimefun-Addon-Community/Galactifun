package io.github.addoncommunity.galactifun.core.structures;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

final class StructureBlockBuilder {
    
    private final Material material;
    private StructureBlockProperty[] properties;

    StructureBlockBuilder(Material material) {
        this.material = material;
        this.properties = new StructureBlockProperty[0];
    }
    
    StructureBlockBuilder(Material material, int size) {
        this.material = material;
        this.properties = new StructureBlockProperty[size];
    }
    
    StructureBlock build() {
        switch (this.properties.length) {
            case 0: return BasicStructureBlock.get(this.material);
            case 1: return new SinglePropertyBlock(this.material, this.properties[0]);
            case 2: return new DoublePropertyBlock(this.material, this.properties[0], this.properties[1]);
        }
        throw new IllegalStateException("Failed to build structure block with " + this.properties.length + " properties!");
    }
    
    void addProperty(StructureBlockProperty property) {
        StructureBlockProperty[] old = this.properties;
        this.properties = new StructureBlockProperty[this.properties.length + 1];
        System.arraycopy(old, 0, this.properties, 0, old.length);
        this.properties[old.length] = property;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class SinglePropertyBlock extends StructureBlock {
    
        private final Material material;
        private final StructureBlockProperty property1;

        @Override
        void paste(Block block) {
            block.setType(this.material);
            BlockData data = block.getBlockData();
            this.property1.apply(data);
            block.setBlockData(data);
        }
    
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private static final class DoublePropertyBlock extends StructureBlock {

        private final Material material;
        private final StructureBlockProperty property1;
        private final StructureBlockProperty property2;

        @Override
        void paste(Block block) {
            block.setType(this.material);
            BlockData data = block.getBlockData();
            this.property1.apply(data);
            this.property2.apply(data);
            block.setBlockData(data);
        }

    }
    
}
