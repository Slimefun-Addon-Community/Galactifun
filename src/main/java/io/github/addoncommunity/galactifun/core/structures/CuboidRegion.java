package io.github.addoncommunity.galactifun.core.structures;

/*
 * WorldEdit, a Minecraft world manipulation toolkit
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldEdit team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An axis-aligned cuboid. It can be defined using two corners of the cuboid. All credits go to the WorldEdit team
 *
 * @author WorldEdit team
 */
public class CuboidRegion implements Iterable<BlockVector3> {

    @Getter
    @Setter
    private BlockVector3 pos1;
    @Getter
    @Setter
    private BlockVector3 pos2;

    /**
     * Construct a new instance of this cuboid using two corners of the cuboid.
     *
     * @param pos1  the first position
     * @param pos2  the second position
     */
    public CuboidRegion(BlockVector3 pos1, BlockVector3 pos2) {
        checkNotNull(pos1);
        checkNotNull(pos2);
        this.pos1 = pos1;
        this.pos2 = pos2;
        recalculate();
    }

    /**
     * Clamps the cuboid according to boundaries of the world.
     */
    private void recalculate() {
        pos1 = pos1.clampY(0, 255);
        pos2 = pos2.clampY(0, 255);
    }

    public BlockVector3 getMinimumPoint() {
        return pos1.getMinimum(pos2);
    }

    public BlockVector3 getMaximumPoint() {
        return pos1.getMaximum(pos2);
    }

    public CuboidRegion getBoundingBox() {
        return this;
    }

    public int getMinimumY() {
        return Math.min(pos1.getBlockY(), pos2.getBlockY());
    }

    public int getMaximumY() {
        return Math.max(pos1.getBlockY(), pos2.getBlockY());
    }

    public void expand(BlockVector3... changes) {
        checkNotNull(changes);

        for (BlockVector3 change : changes) {
            if (change.getX() > 0) {
                if (Math.max(pos1.getX(), pos2.getX()) == pos1.getX()) {
                    pos1 = pos1.add(change.getX(), 0, 0);
                } else {
                    pos2 = pos2.add(change.getX(), 0, 0);
                }
            } else {
                if (Math.min(pos1.getX(), pos2.getX()) == pos1.getX()) {
                    pos1 = pos1.add(change.getX(), 0, 0);
                } else {
                    pos2 = pos2.add(change.getX(), 0, 0);
                }
            }

            if (change.getY() > 0) {
                if (Math.max(pos1.getY(), pos2.getY()) == pos1.getY()) {
                    pos1 = pos1.add(0, change.getY(), 0);
                } else {
                    pos2 = pos2.add(0, change.getY(), 0);
                }
            } else {
                if (Math.min(pos1.getY(), pos2.getY()) == pos1.getY()) {
                    pos1 = pos1.add(0, change.getY(), 0);
                } else {
                    pos2 = pos2.add(0, change.getY(), 0);
                }
            }

            if (change.getZ() > 0) {
                if (Math.max(pos1.getZ(), pos2.getZ()) == pos1.getZ()) {
                    pos1 = pos1.add(0, 0, change.getZ());
                } else {
                    pos2 = pos2.add(0, 0, change.getZ());
                }
            } else {
                if (Math.min(pos1.getZ(), pos2.getZ()) == pos1.getZ()) {
                    pos1 = pos1.add(0, 0, change.getZ());
                } else {
                    pos2 = pos2.add(0, 0, change.getZ());
                }
            }
        }

        recalculate();
    }

    public void contract(BlockVector3... changes) {
        checkNotNull(changes);

        for (BlockVector3 change : changes) {
            if (change.getX() < 0) {
                if (Math.max(pos1.getX(), pos2.getX()) == pos1.getX()) {
                    pos1 = pos1.add(change.getX(), 0, 0);
                } else {
                    pos2 = pos2.add(change.getX(), 0, 0);
                }
            } else {
                if (Math.min(pos1.getX(), pos2.getX()) == pos1.getX()) {
                    pos1 = pos1.add(change.getX(), 0, 0);
                } else {
                    pos2 = pos2.add(change.getX(), 0, 0);
                }
            }

            if (change.getY() < 0) {
                if (Math.max(pos1.getY(), pos2.getY()) == pos1.getY()) {
                    pos1 = pos1.add(0, change.getY(), 0);
                } else {
                    pos2 = pos2.add(0, change.getY(), 0);
                }
            } else {
                if (Math.min(pos1.getY(), pos2.getY()) == pos1.getY()) {
                    pos1 = pos1.add(0, change.getY(), 0);
                } else {
                    pos2 = pos2.add(0, change.getY(), 0);
                }
            }

            if (change.getZ() < 0) {
                if (Math.max(pos1.getZ(), pos2.getZ()) == pos1.getZ()) {
                    pos1 = pos1.add(0, 0, change.getZ());
                } else {
                    pos2 = pos2.add(0, 0, change.getZ());
                }
            } else {
                if (Math.min(pos1.getZ(), pos2.getZ()) == pos1.getZ()) {
                    pos1 = pos1.add(0, 0, change.getZ());
                } else {
                    pos2 = pos2.add(0, 0, change.getZ());
                }
            }
        }

        recalculate();
    }

    public void shift(BlockVector3 change) {
        pos1 = pos1.add(change);
        pos2 = pos2.add(change);

        recalculate();
    }

    public boolean contains(@Nonnull BlockVector3 position) {
        BlockVector3 min = getMinimumPoint();
        BlockVector3 max = getMaximumPoint();

        return position.containedWithin(min, max);
    }

    @Nonnull
    public Iterator<BlockVector3> iterator() {
        return new Iterator<BlockVector3>() {
            private final BlockVector3 min = getMinimumPoint();
            private final BlockVector3 max = getMaximumPoint();
            private int nextX = min.getBlockX();
            private int nextY = min.getBlockY();
            private int nextZ = min.getBlockZ();

            @Override
            public boolean hasNext() {
                return (nextX != Integer.MIN_VALUE);
            }

            @Override
            public BlockVector3 next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                BlockVector3 answer = BlockVector3.at(nextX, nextY, nextZ);
                if (++nextX > max.getBlockX()) {
                    nextX = min.getBlockX();
                    if (++nextZ > max.getBlockZ()) {
                        nextZ = min.getBlockZ();
                        if (++nextY > max.getBlockY()) {
                            nextX = Integer.MIN_VALUE;
                        }
                    }
                }
                return answer;
            }
        };
    }

    @Override
    public String toString() {
        return getMinimumPoint() + " - " + getMaximumPoint();
    }

    @Override
    public CuboidRegion clone() {
        return new CuboidRegion(this.pos1, this.pos2);
    }

    /**
     * Make a cuboid from the center.
     *
     * @param origin the origin
     * @param apothem the apothem, where 0 is the minimum value to make a 1x1 cuboid
     * @return a cuboid region
     */
    public static CuboidRegion fromCenter(BlockVector3 origin, int apothem) {
        checkNotNull(origin);
        checkArgument(apothem >= 0, "apothem => 0 required");
        BlockVector3 size = BlockVector3.ONE.multiply(apothem);
        return new CuboidRegion(origin.subtract(size), origin.add(size));
    }
}

