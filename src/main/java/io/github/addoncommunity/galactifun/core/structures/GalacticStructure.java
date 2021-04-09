package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.block.Block;

/**
 * A structure that can be pasted
 *
 * @author Seggan
 * @author Mooy1
 */
public final class GalacticStructure {

    /**
     * The 3d array of structure blocks
     */
    private final StructureBlock[][][] structure;
    
    /**
     * The name of this structure
     */
    final String path;

    /**
     * The default rotation of this structure
     */
    final StructureRotation rotation;

    /**
     * The dimensions of this structure
     */
    final int dx;
    final int dy;
    final int dz;
    
    GalacticStructure(String path, StructureRotation rotation, int dx, int dy, int dz) {
        this.path = path;
        this.rotation = rotation;
        this.structure = new StructureBlock[Math.abs(this.dx = dx) + 1][Math.abs(this.dy = dy) + 1][Math.abs(this.dz = dz) + 1];
    }
    
    public void paste(Block pos, StructureRotation rotation) {
        rotation = rotation.relativeTo(this.rotation);
        StructureIterator iterator = new StructureIterator();
        while (iterator.hasNext()) {
            iterator.getNextBlock().paste(pos.getRelative(iterator.x, iterator.y, iterator.z), rotation);
        }
    }
    
    final class StructureIterator {
        
        private boolean loop = GalacticStructure.this.structure.length != 0;
        
        private int ax;
        private int ay;
        private int az;
        
        int x;
        int y;
        int z;
    
        StructureBlock getNextBlock() {
            StructureBlock block = GalacticStructure.this.structure[this.ax][this.ay][this.az];
            next();
            return block;
        }
    
        void setNextBlock(StructureBlock block) {
            GalacticStructure.this.structure[this.ax][this.ay][this.az] = block;
            next();
        }

        boolean hasNext() {
            return this.loop;
        }
    
        void next() {
            if (this.x == GalacticStructure.this.dx) {
                this.x = 0;
                this.ax = 0;
                if (this.y == GalacticStructure.this.dy) {
                    this.y = 0;
                    this.ay = 0;
                    if (this.z == GalacticStructure.this.dz) {
                        this.loop = false;
                    } else {
                        this.az++;
                        if (GalacticStructure.this.dz > 0) {
                            this.z++;
                        } else {
                            this.z--;
                        }
                    }
                } else {
                    this.ay++;
                    if (GalacticStructure.this.dy > 0) {
                        this.y++;
                    } else {
                        this.y--;
                    }
                }
            } else {
                this.ax++;
                if (GalacticStructure.this.dx > 0) {
                    this.x++;
                } else {
                    this.x--;
                }
            }
        }

    }

}
