package io.github.addoncommunity.galactifun.base;

import java.util.Arrays;

import lombok.experimental.UtilityClass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.addoncommunity.galactifun.base.items.CircuitPress;
import io.github.addoncommunity.galactifun.base.items.DiamondAnvil;
import io.github.addoncommunity.galactifun.base.items.MoonCheese;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.addoncommunity.galactifun.core.CoreRecipeType;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

/**
 * A class containing commonly used items in recipes
 *
 * @author Seggan
 * @author Mooy1
 */
@UtilityClass
public final class BaseMats {

    //<editor-fold desc="Generated Blocks" defaultstate="collapsed">
    public static final SlimefunItemStack MOON_DUST = new SlimefunItemStack(
            "MOON_DUST",
            Material.LIGHT_GRAY_CONCRETE_POWDER,
            "&7Moon Dust"
    );
    public static final SlimefunItemStack MARS_DUST = new SlimefunItemStack(
            "MARS_DUST",
            Material.RED_SAND,
            "&cMars Dust"
    );
    public static final SlimefunItemStack DRY_ICE = new SlimefunItemStack(
            "DRY_ICE",
            Material.PACKED_ICE,
            "&bDry Ice"
    );
    public static final SlimefunItemStack SULFUR_BLOCK = new SlimefunItemStack(
            "SULFUR_BLOCK",
            Material.YELLOW_TERRACOTTA,
            "&6Sulfur Block"
    );
    public static final SlimefunItemStack VENTSTONE = new SlimefunItemStack(
            "VENTSTONE",
            Material.MAGMA_BLOCK,
            "&6Ventstone"
    );
    public static final SlimefunItemStack LASERITE_ORE = new SlimefunItemStack(
            "LASERITE_ORE",
            Material.REDSTONE_ORE,
            "&cLaserite Ore"
    );
    //</editor-fold>
    //<editor-fold desc="Ultra Duty" defaultstate="collapsed">
    public static final SlimefunItemStack ALUMINUM_COMPOSITE = new SlimefunItemStack(
            "ALUMINUM_COMPOSITE",
            Material.IRON_INGOT,
            "&fAluminum Composite",
            "",
            "&7You'll never guess how long it took us",
            "&7to name this material"
    );
    public static final SlimefunItemStack TUNGSTEN_INGOT = new SlimefunItemStack(
            "TUNGSTEN_INGOT",
            Material.NETHERITE_INGOT,
            "&bTungsten Ingot",
            "",
            "&7A strong metal obtained either from",
            "&7giving Martians reinforced plates or",
            "&7from smelting fallen meteors"
    );
    public static final SlimefunItemStack ALUMINUM_COMPOSITE_SHEET = new SlimefunItemStack(
            "ALUMINUM_COMPOSITE_SHEET",
            Material.PAPER,
            "&fAluminum Composite Sheet"
    );
    public static final SlimefunItemStack HEAVY_DUTY_SHEET = new SlimefunItemStack(
            "HEAVY_DUTY_SHEET",
            Material.PAPER,
            "&fHeavy Duty Sheet"
    );
    public static final SlimefunItemStack SPACE_GRADE_PLATE = new SlimefunItemStack(
            "SPACE_GRADE_PLATE",
            Material.PAPER,
            "&fSpace Grade Plate"
    );
    public static final SlimefunItemStack ULTRA_DUTY_SHEET = new SlimefunItemStack(
            "ULTRA_DUTY_SHEET",
            Material.PAPER,
            "&fUltra Duty Sheet"
    );
    //</editor-fold>
    //<editor-fold desc="Rocket Stuff" defaultstate="collapsed">
    public static final SlimefunItemStack GOLD_FOIL = new SlimefunItemStack(
            "GOLD_FOIL",
            Material.PAPER,
            "&6Gold Foil"
    );
    public static final SlimefunItemStack REINFORCED_CHANNEL = new SlimefunItemStack(
            "REINFORCED_CHANNEL",
            Material.BAMBOO,
            "&fReinforced Channel"
    );
    public static final SlimefunItemStack FAN_BLADE = new SlimefunItemStack(
            "FAN_BLADE",
            GalactifunHead.FAN,
            "&fFan Blade"
    );
    public static final SlimefunItemStack NOZZLE = new SlimefunItemStack(
            "NOZZLE",
            Material.IRON_TRAPDOOR,
            "&fNozzle"
    );
    public static final SlimefunItemStack FILTER = new SlimefunItemStack(
            "FILTER",
            Material.PAPER,
            "&fFilter"
    );
    public static final SlimefunItemStack OXYGEN_REGENERATOR = new SlimefunItemStack(
            "OXYGEN_REGENERATOR",
            GalactifunHead.OXYGEN_REGENERATOR,
            "&bOxygen Regenerator"
    );
    public static final SlimefunItemStack SPARK_PLUG = new SlimefunItemStack(
            "SPARK_PLUG",
            Material.FLINT_AND_STEEL,
            "&fSpark Plug"
    );
    public static final SlimefunItemStack SPARK_PLUG_2 = new SlimefunItemStack(
            "SPARK_PLUG_2",
            Material.FLINT_AND_STEEL,
            "&fSpark Plug Mk 2"
    );
    public static final SlimefunItemStack ROCKET_ENGINE = new SlimefunItemStack(
            "ROCKET_ENGINE",
            Material.FLINT_AND_STEEL,
            "&fRocket Engine"
    );
    public static final SlimefunItemStack ROCKET_ENGINE_2 = new SlimefunItemStack(
            "ROCKET_ENGINE_2",
            Material.FLINT_AND_STEEL,
            "&fRocket Engine Mk 2"
    );
    public static final SlimefunItemStack ROCKET_ENGINE_3 = new SlimefunItemStack(
            "ROCKET_ENGINE_3",
            Material.FLINT_AND_STEEL,
            "&fRocket Engine Mk 3"
    );
    public static final SlimefunItemStack ADVANCED_PROCESSING_UNIT = new SlimefunItemStack(
            "ADVANCED_PROCESSING_UNIT",
            GalactifunHead.CORE,
            "&4Advanced Processing Unit"
    );
    public static final SlimefunItemStack LIFE_SUPPORT_MODULE = new SlimefunItemStack(
            "LIFE_SUPPORT_MODULE",
            GalactifunHead.LIFE_SUPPORT_MODULE,
            "&4Life Support Module"
    );
    public static final SlimefunItemStack NOSE_CONE = new SlimefunItemStack(
            "NOSE_CONE",
            Material.REDSTONE_TORCH,
            "&fNose Cone"
    );
    public static final SlimefunItemStack FUEL_TANK = new SlimefunItemStack(
            "FUEL_TANK",
            GalactifunHead.CAN,
            "&6Fuel Tank"
    );
    public static final SlimefunItemStack FUEL_TANK_2 = new SlimefunItemStack(
            "FUEL_TANK_2",
            GalactifunHead.CAN,
            "&6Fuel Tank Mk 2"
    );
    public static final SlimefunItemStack DIAMOND_CIRCUIT = new SlimefunItemStack(
            "DIAMOND_CIRCUIT",
            Material.POWERED_RAIL,
            "&7Diamond Circuit"
    );
    public static final SlimefunItemStack REDSTONE_CIRCUIT = new SlimefunItemStack(
            "REDSTONE_CIRCUIT",
            Material.POWERED_RAIL,
            "&7Redstone Circuit"
    );
    public static final SlimefunItemStack LAPIS_CIRCUIT = new SlimefunItemStack(
            "LAPIS_CIRCUIT",
            Material.POWERED_RAIL,
            "&7Lapis Circuit"
    );
    public static final SlimefunItemStack GLOWSTONE_CIRCUIT = new SlimefunItemStack(
            "GLOWSTONE_CIRCUIT",
            Material.POWERED_RAIL,
            "&7Glowstone Circuit"
    );
    //</editor-fold>
    //<editor-fold desc="Misc" defaultstate="collapsed">
    public static final SlimefunItemStack MUNPOWDER = new SlimefunItemStack(
            "MUNPOWDER",
            Material.GUNPOWDER,
            "&7Munpowder",
            "",
            "&7The gunpowder of the moon"
    );
    public static final SlimefunItemStack FALLEN_METEOR = new SlimefunItemStack(
            "FALLEN_METEOR",
            Material.ANCIENT_DEBRIS,
            "&4Fallen Meteor",
            "",
            "&7These meteors contain Tungsten"
    );
    public static final SlimefunItemStack MOON_CHEESE = new SlimefunItemStack(
            "MOON_CHEESE",
            GalactifunHead.CHEESE,
            "&6Moon Cheese",
            "",
            "&7Ew"
    );
    public static final SlimefunItemStack ENDER_BLOCK = new SlimefunItemStack(
            "ENDER_BLOCK",
            Material.PRISMARINE_BRICKS,
            "&3Ender Block"
    );
    public static final SlimefunItemStack LUNAR_GLASS = new SlimefunItemStack(
            "LUNAR_GLASS",
            Material.GLASS,
            "&fLunar Glass",
            "",
            "&7For some reason adding moon dust to",
            "&7glass makes it clearer..."
    );
    public static final SlimefunItemStack VOLCANIC_INGOT = new SlimefunItemStack(
            "VOLCANIC_INGOT",
            Material.GOLD_INGOT,
            "&4Volcanic Ingot",
            "",
            "&7Forged in the depths of the closest",
            "&7thing we know to Hell, Volcanic Ingots",
            "&7are a valuable commodity"
    );
    public static final SlimefunItemStack BLISTERING_VOLCANIC_INGOT = new SlimefunItemStack(
            "BLISTERING_VOLCANIC_INGOT",
            Material.GOLD_INGOT,
            "&6Blistering Volcanic Ingot"
    );
    public static final SlimefunItemStack TUNGSTEN_CARBIDE = new SlimefunItemStack(
            "TUNGSTEN_CARBIDE",
            Material.IRON_INGOT,
            "&7Tungsten Carbide"
    );
    public static final SlimefunItemStack DIAMOND_ANVIL_CELL = new SlimefunItemStack(
            "DIAMOND_ANVIL_CELL",
            Material.DIAMOND,
            "&bDiamond Anvil Cell"
    );
    public static final SlimefunItemStack FUSION_PELLET = new SlimefunItemStack(
            "FUSION_PELLET",
            Material.STONE_BUTTON,
            "&fFusion Pellet"
    );
    public static final SlimefunItemStack LASERITE_DUST = new SlimefunItemStack(
            "LASERITE_DUST",
            Material.REDSTONE,
            "&cLaserite Dust",
            "",
            "&7I'm running out of names",
            Bukkit.getPluginManager().isPluginEnabled("SlimefunWarfare") ?
                    "&7Can be replaced by Laser Diode from Slimefun Warfare" :
                    ""
    );
    public static final SlimefunItemStack LASERITE = new SlimefunItemStack(
            "LASERITE",
            Material.RED_DYE,
            "&cLaserite"
    );
    //</editor-fold>

    public static void setup() {
        worldItem(MOON_DUST, BaseUniverse.THE_MOON);
        worldItem(MARS_DUST, BaseUniverse.MARS);
        worldItem(FALLEN_METEOR, BaseUniverse.MARS);
        worldItem(DRY_ICE, BaseUniverse.MARS, BaseUniverse.TITAN);
        worldItem(SULFUR_BLOCK, BaseUniverse.VENUS, BaseUniverse.IO);
        worldItem(VENTSTONE, BaseUniverse.VENUS);
        worldItem(LASERITE_ORE, BaseUniverse.TITAN);
        component(VOLCANIC_INGOT, RecipeType.SMELTERY, VENTSTONE);
        component(ALUMINUM_COMPOSITE, RecipeType.SMELTERY,
                SlimefunItems.ALUMINUM_INGOT, SlimefunItems.MAGNESIUM_DUST, SlimefunItems.ZINC_DUST,
                SlimefunItems.TIN_DUST, SlimefunItems.ALUMINUM_DUST
        );
        component(TUNGSTEN_INGOT, RecipeType.SMELTERY, FALLEN_METEOR);
        component(ALUMINUM_COMPOSITE_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(ALUMINUM_COMPOSITE, 8));
        component(HEAVY_DUTY_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(ALUMINUM_COMPOSITE_SHEET, 8));
        component(SPACE_GRADE_PLATE, RecipeType.COMPRESSOR, HEAVY_DUTY_SHEET, TUNGSTEN_CARBIDE);
        component(ULTRA_DUTY_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(SPACE_GRADE_PLATE, 4));
        component(GOLD_FOIL, RecipeType.COMPRESSOR, 4, SlimefunItems.GOLD_24K_BLOCK);
        component(REINFORCED_CHANNEL, RecipeType.ENHANCED_CRAFTING_TABLE, 8,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET
        );
        component(FAN_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_INGOT, null,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
                null, SlimefunItems.STEEL_INGOT, null
        );
        component(NOZZLE, RecipeType.ENHANCED_CRAFTING_TABLE, 2,
                SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
                null, new ItemStack(Material.IRON_TRAPDOOR), null
        );
        component(FILTER, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH
        );
        component(OXYGEN_REGENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL, FILTER,
                NOZZLE, GOLD_FOIL, FILTER,
                SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL, FILTER
        );
        component(SPARK_PLUG, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
                SlimefunItems.ALUMINUM_INGOT, null, MUNPOWDER,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
        );
        component(SPARK_PLUG_2, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
                TUNGSTEN_INGOT, null, MUNPOWDER,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
        );
        assembly(ROCKET_ENGINE, true,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE, null,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE
        );
        assembly(ROCKET_ENGINE_2, true,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SlimefunItems.REINFORCED_PLATE, SPARK_PLUG, SPARK_PLUG, SlimefunItems.REINFORCED_PLATE, null,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE
        );
        assembly(ROCKET_ENGINE_3, true,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SPACE_GRADE_PLATE, SPARK_PLUG_2, SPARK_PLUG_2, SPACE_GRADE_PLATE, null,
                SPACE_GRADE_PLATE, null, null, null, null, SPACE_GRADE_PLATE,
                SPACE_GRADE_PLATE, null, null, null, null, SPACE_GRADE_PLATE
        );
        component(ADVANCED_PROCESSING_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE,
                REDSTONE_CIRCUIT, GLOWSTONE_CIRCUIT, REDSTONE_CIRCUIT,
                DIAMOND_CIRCUIT, SlimefunItems.ADVANCED_CIRCUIT_BOARD, DIAMOND_CIRCUIT,
                REDSTONE_CIRCUIT, LAPIS_CIRCUIT, REDSTONE_CIRCUIT
        );
        assembly(LIFE_SUPPORT_MODULE,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, ADVANCED_PROCESSING_UNIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, LAPIS_CIRCUIT, OXYGEN_REGENERATOR, OXYGEN_REGENERATOR, LAPIS_CIRCUIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, LAPIS_CIRCUIT, OXYGEN_REGENERATOR, OXYGEN_REGENERATOR, LAPIS_CIRCUIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, ADVANCED_PROCESSING_UNIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT
        );
        assembly(NOSE_CONE, true,
                null, null, new ItemStack(Material.REDSTONE_TORCH), new ItemStack(Material.REDSTONE_TORCH), null, null,
                null, null, ALUMINUM_COMPOSITE, ALUMINUM_COMPOSITE, null, null,
                null, ALUMINUM_COMPOSITE, GLOWSTONE_CIRCUIT, GLOWSTONE_CIRCUIT, ALUMINUM_COMPOSITE, null,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE
        );
        component(FUEL_TANK, RecipeType.ENHANCED_CRAFTING_TABLE,
                HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET,
                HEAVY_DUTY_SHEET, null, HEAVY_DUTY_SHEET,
                HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET
        );
        component(FUEL_TANK_2, RecipeType.ENHANCED_CRAFTING_TABLE,
                ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET,
                ULTRA_DUTY_SHEET, null, ULTRA_DUTY_SHEET,
                ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET
        );
        component(DIAMOND_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.DIAMOND_BLOCK), SlimefunItems.SILICON);
        component(REDSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SILICON);
        component(LAPIS_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.LAPIS_BLOCK), SlimefunItems.SILICON);
        component(GLOWSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.GLOWSTONE), SlimefunItems.SILICON);

        component(MUNPOWDER, CoreRecipeType.ALIEN_DROP,
                null, null, null,
                null, new CustomItemStack(Material.CREEPER_HEAD, "&fMutant Creeper")
        );
        component(ENDER_BLOCK, DiamondAnvil.TYPE, new ItemStack(Material.ENDER_PEARL, 16), BLISTERING_VOLCANIC_INGOT);
        component(LUNAR_GLASS, RecipeType.SMELTERY, new ItemStack(Material.SAND), MOON_DUST);
        component(TUNGSTEN_CARBIDE, RecipeType.SMELTERY, TUNGSTEN_INGOT, SlimefunItems.COMPRESSED_CARBON);
        assembly(DIAMOND_ANVIL_CELL,
                TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE,
                SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.SYNTHETIC_DIAMOND,
                null, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.SYNTHETIC_DIAMOND, null,
                null, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.SYNTHETIC_DIAMOND, null,
                SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.CARBONADO, SlimefunItems.SYNTHETIC_DIAMOND,
                TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE, TUNGSTEN_CARBIDE
        );

        component(BLISTERING_VOLCANIC_INGOT, DiamondAnvil.TYPE, VOLCANIC_INGOT, SlimefunItems.BLISTERING_INGOT_3);
        new UnplaceableBlock(CoreItemGroup.ITEMS, FUSION_PELLET, DiamondAnvil.TYPE, new ItemStack[] {
                BLISTERING_VOLCANIC_INGOT, new SlimefunItemStack(MOON_DUST, 8)
        }, new SlimefunItemStack(FUSION_PELLET, 8)).register(Galactifun.instance());

        component(LASERITE_DUST, true, RecipeType.ORE_CRUSHER, LASERITE_ORE);
        component(LASERITE, DiamondAnvil.TYPE, new SlimefunItemStack(LASERITE_DUST, 12));

        new MoonCheese(CoreItemGroup.ITEMS, MOON_CHEESE, CoreRecipeType.WORLD_GEN, new ItemStack[]{
                BaseUniverse.THE_MOON.item()
        }).register(Galactifun.instance());

        BaseUniverse.THE_MOON.addBlockMapping(Material.GOLD_ORE, MOON_CHEESE);

        // SlimefunWarfare integration
        SlimefunItem diode = SlimefunItem.getById("LASER_DIODE");
        if (diode != null) {
            DiamondAnvil.TYPE.register(new ItemStack[] { diode.getItem().asQuantity(12), null }, LASERITE);
        }

        RecipeType.GRIND_STONE.register(
                Arrays.copyOf(new ItemStack[] { SULFUR_BLOCK }, 9),
                new SlimefunItemStack(SlimefunItems.SULFATE, 9)
        );
    }

    private static void component(SlimefunItemStack item, boolean unplaceable, RecipeType type, ItemStack... recipe) {
        if (unplaceable) {
            new UnplaceableBlock(CoreItemGroup.COMPONENTS, item, type, recipe).register(Galactifun.instance());
        } else {
            new SlimefunItem(CoreItemGroup.COMPONENTS, item, type, recipe).register(Galactifun.instance());
        }
    }

    private static void component(SlimefunItemStack item, RecipeType type, ItemStack... recipe) {
        component(item, false, type, recipe);
    }

    private static void component(SlimefunItemStack item, RecipeType type, int output, ItemStack... recipe) {
        new SlimefunItem(CoreItemGroup.COMPONENTS, item, type, recipe, new SlimefunItemStack(item, output)).register(Galactifun.instance());
    }

    private static void assembly(SlimefunItemStack item, boolean unplaceable, ItemStack... recipe) {
        if (unplaceable) {
            new UnplaceableBlock(CoreItemGroup.ITEMS, item, AssemblyTable.TYPE, recipe).register(Galactifun.instance());
        } else {
            new SlimefunItem(CoreItemGroup.ITEMS, item, AssemblyTable.TYPE, recipe).register(Galactifun.instance());
        }
    }

    private static void assembly(SlimefunItemStack item, ItemStack... recipe) {
        assembly(item, false, recipe);
    }

    private static void worldItem(SlimefunItemStack item, AlienWorld... worlds) {
        ItemStack[] recipe = new ItemStack[worlds.length];
        for (int i = 0; i < worlds.length; i++) {
            AlienWorld world = worlds[i];
            recipe[i] = world.item();
            world.addBlockMapping(item.getType(), item);
        }
        new SlimefunItem(CoreItemGroup.BLOCKS, item, CoreRecipeType.WORLD_GEN, recipe).register(Galactifun.instance());
    }

}
