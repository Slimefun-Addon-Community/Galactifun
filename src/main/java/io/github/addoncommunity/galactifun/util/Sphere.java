package io.github.addoncommunity.galactifun.util;

import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.generator.LimitedRegion;

/**
 * A class for optimized generation of spheres of blocks
 *
 * @author Mooy1
 */
public final class Sphere {

    public static final int MIN_RADIUS = 3;
    public static final int MAX_RADIUS = 125;

    private final Material[] materials;
    private Location currentMiddle;
    private int currentMaterial;
    private LimitedRegion currentRegion;

    public Sphere(@Nonnull Material... materials) {
        Validate.isTrue((this.materials = materials).length != 0);
    }

    public void generate(@Nonnull Location middle, @Nonnull LimitedRegion region, int min, int dev) {
        Validate.isTrue(min >= MIN_RADIUS && dev >= 0 && min + dev <= MAX_RADIUS, "Generation parameters out of bounds!");
        this.currentRegion = region;
        this.currentMiddle = middle;

        // radius
        int radius = min + ThreadLocalRandom.current().nextInt(dev + 1);
        int radiusSquared = radius * radius;

        // center block
        gen(0, 0, 0);

        // outer middle blocks, furthest from middle
        genMiddles(radius);

        for (int x = 1, vector1 = 1; x < radius; vector1 += (x++ << 1) + 1) {

            // middle blocks
            genMiddles(x);

            for (int y = x, vector2 = vector1 + y * y; y < radius; vector2 += (y++ << 1) + 1) {

                // check radius
                if (vector2 < radiusSquared) {

                    // edges
                    genEdges(x, y);
                    if (x != y) {
                        genEdges(y, x);
                    }

                } else {
                    break;
                }

                for (int z = y, vector3 = vector2 + z * z; z < radius; vector3 += (z++ << 1) + 1) {

                    // check within radius
                    if (vector3 < radiusSquared) {

                        // corners
                        genCorners(x, y, z);
                        if (x != y) {
                            genCorners(y, x, z);
                            genCorners(z, y, x);
                            if (y != z) {
                                genCorners(x, z, y);
                                genCorners(z, x, y);
                                genCorners(y, z, x);
                            }
                        } else if (x != z) {
                            genCorners(z, y, x);
                            genCorners(x, z, y);
                        }

                    } else {
                        break;
                    }
                }
            }
        }

        this.currentMiddle = null;
    }

    private void genMiddles(int a) {
        gen(a, 0, 0);
        gen(-a, 0, 0);
        gen(0, a, 0);
        randomize();
        gen(0, -a, 0);
        gen(0, 0, a);
        gen(0, 0, -a);
    }

    private void genEdges(int a, int b) {
        gen(a, b, 0);
        gen(-a, b, 0);
        randomize();
        gen(a, -b, 0);
        gen(-a, -b, 0);
        gen(0, a, b);
        gen(0, -a, b);
        randomize();
        gen(0, a, -b);
        gen(0, -a, -b);
        gen(a, 0, b);
        gen(-a, 0, b);
        randomize();
        gen(a, 0, -b);
        gen(-a, 0, -b);
    }

    private void genCorners(int a, int b, int c) {
        gen(a, b, c);
        gen(-a, b, c);
        gen(a, -b, c);
        gen(a, b, -c);
        randomize();
        gen(-a, -b, c);
        gen(a, -b, -c);
        gen(-a, b, -c);
        gen(-a, -b, -c);
    }

    private void gen(int x, int y, int z) {
        this.currentRegion.setType(currentMiddle.clone().add(x, y, z), this.materials[this.currentMaterial++]);
        if (this.currentMaterial == this.materials.length) {
            this.currentMaterial = 0;
        }
    }

    private void randomize() {
        this.currentMaterial = ThreadLocalRandom.current().nextInt(this.materials.length);
    }

}
