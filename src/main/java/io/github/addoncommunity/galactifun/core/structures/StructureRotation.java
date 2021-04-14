package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.block.BlockFace;

/**
 * Directions that a structure can be rotated in
 * 
 * @author Mooy1
 */
public enum StructureRotation {

    DEFAULT, CLOCKWISE, OPPOSITE, COUNTER_CLOCKWISE;

    private static final StructureRotation[] ROTATIONS = values();
    private static final BlockFace[] FACES = {
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST,
            BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH
    };
    
    public StructureRotation rotationTo(StructureRotation rotation) {
        return ROTATIONS[Math.abs(rotation.ordinal() - this.ordinal())];
    }
    
    public BlockFace rotateFace(BlockFace face) {
        switch (face) {
            case NORTH: return FACES[this.ordinal()];
            case EAST: return FACES[this.ordinal() + 1];
            case SOUTH: return FACES[this.ordinal() + 2];
            case WEST: return FACES[this.ordinal() + 3];
        }
        return face;
    }
    
    public static StructureRotation fromFace(BlockFace face) {
        switch (face) {
            case NORTH: return DEFAULT;
            case EAST: return CLOCKWISE;
            case SOUTH: return OPPOSITE;
            case WEST: return COUNTER_CLOCKWISE;
        }
        throw new IllegalArgumentException("BlockFace " + face + " cant be converted to StructureRotation!");
    }
    
}
