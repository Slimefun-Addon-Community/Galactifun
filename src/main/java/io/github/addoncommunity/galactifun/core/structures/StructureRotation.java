package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.block.BlockFace;

/**
 * Directions that a structure can be rotated in
 * 
 * @author Mooy1
 */
public enum StructureRotation {
    
    NORTH {
        @Override
        public BlockFace relativeTo(BlockFace face) {
            return face;
        }

        @Override
        public StructureRotation relativeTo(StructureRotation rotation) {
            return rotation;
        }
    },
    
    EAST  {
        @Override
        public BlockFace relativeTo(BlockFace face) {
            switch (face) {
                case NORTH: return BlockFace.EAST;
                case EAST: return BlockFace.SOUTH;
                case SOUTH: return BlockFace.WEST;
                case WEST: return BlockFace.NORTH;
                default: return face;
            }
        }
        @Override
        public StructureRotation relativeTo(StructureRotation rotation) {
            switch (rotation) {
                case NORTH: return EAST;
                case EAST: return SOUTH;
                case SOUTH: return WEST;
                case WEST: return NORTH;
                default: return rotation;
            }
        }
    },
    
    SOUTH {
        @Override
        public BlockFace relativeTo(BlockFace face) {
            switch (face) {
                case NORTH: return BlockFace.SOUTH;
                case EAST: return BlockFace.WEST;
                case SOUTH: return BlockFace.NORTH;
                case WEST: return BlockFace.EAST;
                default: return face;
            }
        }

        @Override
        public StructureRotation relativeTo(StructureRotation rotation) {
            switch (rotation) {
                case NORTH: return SOUTH;
                case EAST: return WEST;
                case SOUTH: return NORTH;
                case WEST: return EAST;
                default: return rotation;
            }
        }
    },
    
    WEST {
        @Override
        public BlockFace relativeTo(BlockFace face) {
            switch (face) {
                case NORTH: return BlockFace.WEST;
                case EAST: return BlockFace.NORTH;
                case SOUTH: return BlockFace.EAST;
                case WEST: return BlockFace.SOUTH;
                default: return face;
            }
        }

        @Override
        public StructureRotation relativeTo(StructureRotation rotation) {
            switch (rotation) {
                case NORTH: return WEST;
                case EAST: return NORTH;
                case SOUTH: return EAST;
                case WEST: return SOUTH;
                default: return rotation;
            }
        }
    };
    
    public abstract BlockFace relativeTo(BlockFace face);
    
    public abstract StructureRotation relativeTo(StructureRotation rotation);
    
    public static StructureRotation fromFace(BlockFace face) {
        switch (face) {
            case EAST: return EAST;
            case SOUTH: return SOUTH;
            case WEST: return WEST;
            default: return NORTH;
        }
    }
    
}
