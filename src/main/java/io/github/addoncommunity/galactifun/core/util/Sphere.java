package io.github.addoncommunity.galactifun.core.util;

import org.apache.commons.lang.Validate;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * A class for creating spheres of blocks
 * 
 * @author Mooy1
 */
public final class Sphere {

    private Block middle;
    private Random random;
    private final Material[] materials;
    
    public Sphere(@Nonnull Material... materials) {
        Validate.isTrue(materials.length != 0);
        this.materials = materials;
    }
    
    public void generate(@Nonnull Random random, @Nonnull Chunk chunk, int x, int y, int z, int min, int dev) {
        generate(chunk.getBlock(x, y, z), random, min, dev);
    }

    /**
     * Generates a sphere by looping through combos of x, y, z where no combo contains the same values
     *
     * Stuff for testing:
     * 
     * radius 5 max 25
     *  a 0 | 00000 | 0000 000 00 | 1111 111 1 222 2
     *  b 0 | 00000 | 1111 222 33 | 1111 222 3 222 3
     *  c 0 | 12345 | 1234 234 34 | 1234 234 3 234 3
     *
     * radius 4 max 16
     *  a 0 | 0000 | 000 00 | 111 11 2
     *  b 0 | 0000 | 111 22 | 111 22 2
     *  c 0 | 1234 | 123 23 | 123 23 2
     *
     * radius 3 max 9
     *  a 0 | 000 | 00 0 | 1 1 
     *  b 0 | 000 | 11 2 | 1 2 
     *  c 0 | 123 | 12 2 | 1 2 
     *  
     *  (a + 1)^2 == a^2 + 2a + 1
     *  
     *  (a + 1)^2 - a^2 == 2a + 1 == (a << 1) + 1
     *
     */
    public void generate(@Nonnull Block middle, @Nonnull Random random, int min, int dev) {
        Validate.notNull(middle);
        Validate.notNull(random);
        Validate.isTrue(min >= 3);
        Validate.isTrue(dev >= 0);
        
        this.middle = middle;
        this.random = random;
        
        // radius
        int radius = min + random.nextInt(dev + 1);
        int radiusSquared = radius * radius;
        // moves in any direction
        int a;
        int b;
        int c;
        // for keeping track of distance
        int aSquared;
        int aPlusBSquared;
        int aPlusBPlusCSquared;
        
        // 0 moves, always within radius
        gen(0, 0, 0, material());
        // 1 move, always within radius
        for (a = 1; a <= radius ; a++) {
            genThree(a, 0);
            genThree(-a, 0);
        }
        // 2 moves
        for (a = 1, aSquared = 1; a < radius ; a++) {
            for (b = a, aPlusBSquared = aSquared + b * b ; b < radius ; b++) {
                if (aPlusBSquared < radiusSquared) {
                    genTwelve(a, b);
                    if (a != b) {
                        genTwelve(b, a);
                    }
                }
                // increment
                aPlusBSquared += (b << 1) + 1;
            }
            // increment
            aSquared += (a << 1) + 1;
        }
        // 3 moves
        for (a = 1, aSquared = 1; a < radius - 1 ; a++) {
            for (b = a, aPlusBSquared = aSquared + b * b ; b < radius - 1 ; b++) {
                for (c = b, aPlusBPlusCSquared = aPlusBSquared + c * c ; c < radius ; c++) {
                    if (aPlusBPlusCSquared < radiusSquared) {
                        // ? ? ? -> always
                        genEight(a, b, c);
                        if (a != b) {
                            // a b ? -> swap ab ac
                            genEight(b, a, c);
                            genEight(c, b, a);
                            if (b != c) {
                                // a b c -> swap bc cycle abc
                                genEight(a, c, b);
                                genEight(c, a, b);
                                genEight(b, c, a);
                            }
                        } else if (a != c) {
                            // a a c -> swap ac bc
                            genEight(c, b, a);
                            genEight(a, c, b);
                        }
                    }
                    // increment
                    aPlusBPlusCSquared += (c << 1) + 1;
                }
                // increment
                aPlusBSquared += (b << 1) + 1;
            }
            // increment
            aSquared += (a << 1) + 1;
        }
    }
    
    private void genEight(int a, int b, int c) {
        Material material1 = material();
        Material material2 = material();
        Material material3 = material();
        gen(a, b, c, material1);
        gen(-a, b, c, material2);
        gen(a, -b, c, material3);
        gen(a, b, -c, material1);
        gen(-a, -b, c, material2);
        gen(a, -b, -c, material3);
        gen(-a, b, -c, material1);
        gen(-a, -b, -c, material2);
    }
    
    private void genTwelve(int a, int b) {
        genThree(a, b);
        genThree(a, -b);
        genThree(-a, b);
        genThree(-a, -b);
    }
    
    private void genThree(int a, int b) {
        Material material = material();
        gen(a, b, 0, material);
        gen(0, a, b, material);
        gen(b, 0, a, material());
    }
    
    private void gen(int x, int y, int z, Material material) {
        this.middle.getRelative(x, y, z).setType(material);
    }
    
    private Material material() {
        return this.materials[this.random.nextInt(this.materials.length)];
    }
    
}
