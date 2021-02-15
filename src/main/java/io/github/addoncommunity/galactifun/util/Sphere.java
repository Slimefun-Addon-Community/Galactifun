package io.github.addoncommunity.galactifun.util;

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
     * Generates a sphere by looping throughout sets of 1, 2, and 3 moves (a, b, c),
     * comparing to radius, and generating blocks at all possible corresponding locations
     */
    public void generate(@Nonnull Block middle, @Nonnull Random random, int min, int dev) {
        Validate.notNull(middle);
        Validate.notNull(random);
        
        Validate.isTrue(min >= 3);
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
        
        // 0 moves, middle
        gen(0, 0, 0, material());
        
        // 1 move, on radius
        genThree(radius, 0);
        genThree(-radius, 0);
        
        for (a = 1, aSquared = 1; a < radius ; aSquared += (a << 1) + 1, a++) {
            
            // 1 move, always within radius
            genThree(a, 0);
            genThree(-a, 0);

            for (b = a, aPlusBSquared = aSquared + b * b; b < radius ; aPlusBSquared += (b << 1) + 1, b++) {
                
                // 2 moves, check within radius
                if (aPlusBSquared < radiusSquared) {
                    genTwelve(a, b);
                    if (a != b) {
                        genTwelve(b, a);
                    }
                } else {
                    // any past this are outside radius
                    break;
                }

                for (c = b, aPlusBPlusCSquared = aPlusBSquared + c * c ; c < radius ; aPlusBPlusCSquared += (c << 1) + 1, c++) {
                    
                    // 3 moves, check within radius
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
                        // any past this are outside radius
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
        gen(a, b, 0, material());
        gen(0, a, b, material());
        gen(b, 0, a, material());
    }
    
    private void gen(int x, int y, int z, Material material) {
        this.middle.getRelative(x, y, z).setType(material);
    }
    
    private Material material() {
        return this.materials[this.random.nextInt(this.materials.length)];
    }
    
}
