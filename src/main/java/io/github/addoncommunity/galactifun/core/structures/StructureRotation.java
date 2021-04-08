package io.github.addoncommunity.galactifun.core.structures;

import org.bukkit.block.BlockFace;

public enum StructureRotation {
    
    NORTH {
        @Override
        public BlockFace relativeTo(BlockFace face) {
            return face;
        }
    },
    EAST  {
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
    },
    WEST {
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
    };
    
    public abstract BlockFace relativeTo(BlockFace face);
        
    public static StructureRotation fromFace(BlockFace face) {
        switch (face) {
            case EAST: return EAST;
            case SOUTH: return SOUTH;
            case WEST: return WEST;
            default: return NORTH;
        }
    }
    
}
