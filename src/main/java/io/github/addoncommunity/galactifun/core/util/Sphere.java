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
     * min 5, max 125
     *
     * Stuff for thinking about how to implement:
     * 
     * radius 6 max 36
     *  a 0e | 000000e | 00000e 0000e 000e 0x | 11111e 1111e 111e 1x 2222e 2222x 33x x
     *  b 0e | 000000e | 11111e 2222e 333e 4x | 11111e 2222e 333e 4x 2222e 3334x 33x x
     *  c 0e | 123456e | 12345e 2345e 345e 4x | 12345e 2345e 345e 4x 2345e 3454x 34x x
     * 
     * radius 5 max 25
     *  a 0e | 00000e | 0000e 000e 00e x | 1111e 111e 1x 222e 2x e
     *  b 0e | 00000e | 1111e 222e 33e x | 1111e 222e 3x 222e 3x e
     *  c 0e | 12345e | 1234e 234e 34e x | 1234e 234e 3x 234e 3x e
     *
     * radius 4 max 16
     *  a 0e | 0000e | 000e 00e x | 111e 11e 2x
     *  b 0e | 0000e | 111e 22e x | 111e 22e 2x
     *  c 0e | 1234e | 123e 23e x | 123e 23e 2x
     *  
     *  (a + 1)^2 == a^2 + 2a + 1
     *  (a + 1)^2 - a^2 == 2a + 1 == (a << 1) + 1
     *
     */
    public void generate(@Nonnull Block middle, @Nonnull Random random, int min, int dev) {
        Validate.notNull(middle);
        Validate.notNull(random);
        Validate.isTrue(min >= 5);
        Validate.isTrue(dev >= 0);
        Validate.isTrue(min + dev <= 125);
        
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
        
        // 0 moves
        gen(0, 0, 0, material());
        // 1 move
        for (a = 1; a <= radius ; a++) {
            // always within radius
            genThree(a, 0);
            genThree(-a, 0);
        }
        // 2 moves
        for (a = 1, aSquared = 1; a < radius - 1 ; aSquared += (a << 1) + 1, a++) {
            // 2nd move is >= 1st move
            for (b = a, aPlusBSquared = aSquared + b * b; b < radius ; aPlusBSquared += (b << 1) + 1, b++) {
                // check within radius
                if (aPlusBSquared < radiusSquared) {
                    genTwelve(a, b);
                    if (a != b) {
                        genTwelve(b, a);
                    }
                } else {
                    // any b past this are outside radius
                    break;
                }
            }
        }
        // 3 moves
        for (a = 1, aSquared = 1; a < radius - 2 ; aSquared += (a << 1) + 1, a++) {
            // 2nd move is >= 1st move
            for (b = a, aPlusBSquared = aSquared + b * b ; b < radius - 1 ; aPlusBSquared += (b << 1) + 1, b++) {
                // 3rd move is >= 2nd move
                for (c = b, aPlusBPlusCSquared = aPlusBSquared + c * c ; c < radius ; aPlusBPlusCSquared += (c << 1) + 1, c++) {
                    // check within radius
                    if (aPlusBPlusCSquared < radiusSquared) {
                        // ? ? ? -> no swaps
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
                    } else {
                        // any c past this are outside radius
                        break;
                    }
                }
            }
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
