package io.github.addoncommunity.galactifun.api.structures;

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
     * The default rotation of this structure
     */
    final StructureRotation rotation;

    /**
     * The dimensions of this structure
     */
    final int dx;
    final int dy;
    final int dz;
    
    GalacticStructure(StructureRotation rotation, int dx, int dy, int dz) {
        this.rotation = rotation;
        this.structure = new StructureBlock[Math.abs(this.dx = dx) + 1][Math.abs(this.dy = dy) + 1][Math.abs(this.dz = dz) + 1];
    }
    
    public void paste(Block pos, StructureRotation rotation) {
        StructureRotation dif = this.rotation.rotationTo(rotation);
        getAll((block, x, y, z) -> block.paste(pos.getRelative(x, y, z), dif));
    }
    
    void setAll(Setter setter) {
        iterate((x, y, z, ax, ay, az) -> this.structure[ax][ay][az] = setter.set(x, y, z));
    }

    void getAll(Getter getter) {
        iterate((x, y, z, ax, ay, az) -> getter.get(this.structure[ax][ay][az], x, y, z));
    }
    
    private void iterate(Iterator iterator) {
        boolean loop = this.structure.length != 0;
        int ax = 0;
        int ay = 0;
        int az = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        while (loop) {
            iterator.iterate(x, y, z, ax, ay, az);
            if (x == this.dx) {
                x = 0;
                ax = 0;
                if (y == this.dy) {
                    y = 0;
                    ay = 0;
                    if (z == this.dz) {
                        loop = false;
                    } else {
                        az++;
                        if (this.dz > 0) {
                            z++;
                        } else {
                            z--;
                        }
                    }
                } else {
                    ay++;
                    if (this.dy > 0) {
                        y++;
                    } else {
                        y--;
                    }
                }
            } else {
                ax++;
                if (this.dx > 0) {
                    x++;
                } else {
                    x--;
                }
            }
        }
    }

    @FunctionalInterface
    private interface Iterator {
        void iterate(int x, int y, int z, int ax, int ay, int az);
    }
    
    @FunctionalInterface
    interface Getter {
        void get(StructureBlock block, int x, int y, int z);
    }

    @FunctionalInterface
    interface Setter {
        StructureBlock set(int x, int y, int z);
    }
    
}
