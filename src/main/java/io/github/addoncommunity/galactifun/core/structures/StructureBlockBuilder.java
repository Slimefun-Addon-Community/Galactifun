package io.github.addoncommunity.galactifun.core.structures;

import io.github.addoncommunity.galactifun.Galactifun;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.logging.Level;

/**
 * A helper class to create structure blocks with properties
 * 
 * @author Mooy1
 */
final class StructureBlockBuilder {
    
    @Setter
    private Material material;
    private StructureBlockProperty[] properties = new StructureBlockProperty[0];
    
    StructureBlock build() {
        if (this.properties.length == 0) {
            return BasicStructureBlock.get(this.material);
        }
        
        StructureBlockProperty[] properties = this.properties;
        
        this.properties = new StructureBlockProperty[0];
        
        if (properties.length == 1) {
            return new SinglePropertyBlock(this.material, properties[0]);
        }
        
        if (properties.length == 2) {
            return new DoublePropertyBlock(this.material, properties[0], properties[1]);
        }

        Galactifun.inst().log(Level.SEVERE, "Failed to build structure block with " + this.properties.length + " properties!");
        
        return BasicStructureBlock.AIR;
    }
    
    void addProperty(StructureBlockProperty property) {
        StructureBlockProperty[] old = this.properties;
        this.properties = new StructureBlockProperty[this.properties.length + 1];
        System.arraycopy(old, 0, this.properties, 0, old.length);
        this.properties[old.length] = property;
    }

    @AllArgsConstructor
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

    @AllArgsConstructor
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
