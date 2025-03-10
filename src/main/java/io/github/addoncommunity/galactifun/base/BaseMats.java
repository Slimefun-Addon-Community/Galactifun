package io.github.addoncommunity.galactifun.base;

import lombok.experimental.UtilityClass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
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
import io.github.thebusybiscuit.slimefun4.implementation.items.electric.machines.HeatedPressureChamber;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import java.util.Arrays;

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
    public static final SlimefunItemStack MOON_ROCK = new SlimefunItemStack(
            "MOON_ROCK",
            Material.ANDESITE,
            "&7Moon Rock"
    );
    public static final SlimefunItemStack MARS_DUST = new SlimefunItemStack(
            "MARS_DUST",
            Material.RED_SAND,
            "&cMars Dust"
    );
    public static final SlimefunItemStack MARS_ROCK = new SlimefunItemStack(
            "MARS_ROCK",
            Material.TERRACOTTA,
            "&cMars Rock"
    );
    public static final SlimefunItemStack DRY_ICE = new SlimefunItemStack(
            "DRY_ICE",
            Material.PACKED_ICE,
            "&bDry Ice"
    );
    public static final SlimefunItemStack METHANE_ICE = new SlimefunItemStack(
            "METHANE_ICE",
            Material.BLUE_ICE,
            "&bMethane Ice"
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
            "AIR_FILTER",
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
    public static final SlimefunItemStack ION_ENGINE = new SlimefunItemStack(
            "ION_ENGINE",
            Material.FLINT_AND_STEEL,
            "&bIon Engine"
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
        worldItem(MOON_ROCK, BaseUniverse.THE_MOON);
        worldItem(MARS_DUST, BaseUniverse.MARS);
        worldItem(MARS_ROCK, BaseUniverse.MARS);
        worldItem(FALLEN_METEOR, BaseUniverse.MARS);
        worldItem(DRY_ICE, BaseUniverse.MARS, BaseUniverse.TITAN);
        worldItem(METHANE_ICE, BaseUniverse.TITAN);
        worldItem(SULFUR_BLOCK, BaseUniverse.VENUS, BaseUniverse.IO);
        worldItem(VENTSTONE, BaseUniverse.VENUS);
        worldItem(LASERITE_ORE, BaseUniverse.TITAN);
        component(VOLCANIC_INGOT, RecipeType.SMELTERY, VENTSTONE.item());
        component(ALUMINUM_COMPOSITE, RecipeType.SMELTERY,
                SlimefunItems.ALUMINUM_INGOT.item(), SlimefunItems.MAGNESIUM_DUST.item(), SlimefunItems.ZINC_DUST.item(),
                SlimefunItems.TIN_DUST.item(), SlimefunItems.ALUMINUM_DUST.item()
        );
        component(TUNGSTEN_INGOT, RecipeType.SMELTERY, FALLEN_METEOR.item());
        component(ALUMINUM_COMPOSITE_SHEET, RecipeType.COMPRESSOR, ALUMINUM_COMPOSITE.asQuantity(8));
        component(HEAVY_DUTY_SHEET, RecipeType.COMPRESSOR, ALUMINUM_COMPOSITE_SHEET.asQuantity(8));
        component(SPACE_GRADE_PLATE, RecipeType.HEATED_PRESSURE_CHAMBER, HEAVY_DUTY_SHEET.item(), TUNGSTEN_CARBIDE.item());
        ((HeatedPressureChamber) SlimefunItems.HEATED_PRESSURE_CHAMBER.getItem()).registerRecipe(
                20,
                new ItemStack[]{ HEAVY_DUTY_SHEET.item(), TUNGSTEN_CARBIDE.item()},
                new ItemStack[]{ SPACE_GRADE_PLATE.item() }
        );
        ((HeatedPressureChamber) SlimefunItems.HEATED_PRESSURE_CHAMBER_2.getItem()).registerRecipe(
                20,
                new ItemStack[]{ HEAVY_DUTY_SHEET.item(), TUNGSTEN_CARBIDE.item()},
                new ItemStack[]{ SPACE_GRADE_PLATE.item() }
        );
        component(ULTRA_DUTY_SHEET, RecipeType.COMPRESSOR, SPACE_GRADE_PLATE.asQuantity(4));
        component(GOLD_FOIL, RecipeType.COMPRESSOR, 4, SlimefunItems.GOLD_24K_BLOCK.item());
        component(REINFORCED_CHANNEL, RecipeType.ENHANCED_CRAFTING_TABLE, 8,
                ALUMINUM_COMPOSITE_SHEET.item(), null, ALUMINUM_COMPOSITE_SHEET.item(),
                ALUMINUM_COMPOSITE_SHEET.item(), null, ALUMINUM_COMPOSITE_SHEET.item(),
                ALUMINUM_COMPOSITE_SHEET.item(), null, ALUMINUM_COMPOSITE_SHEET.item()
        );
        component(FAN_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_INGOT.item(), null,
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item(),
                null, SlimefunItems.STEEL_INGOT.item(), null
        );
        component(NOZZLE, RecipeType.ENHANCED_CRAFTING_TABLE, 2,
                SlimefunItems.STEEL_INGOT.item(), null, SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), null, SlimefunItems.STEEL_INGOT.item(),
                null, new ItemStack(Material.IRON_TRAPDOOR), null
        );
        component(FILTER, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.CLOTH.item(), new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH.item(),
                SlimefunItems.CLOTH.item(), new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH.item(),
                SlimefunItems.CLOTH.item(), new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH.item()
        );
        component(OXYGEN_REGENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.ELECTRO_MAGNET.item(), REINFORCED_CHANNEL.item(), FILTER.item(),
                NOZZLE.item(), GOLD_FOIL.item(), FILTER.item(),
                SlimefunItems.ELECTRO_MAGNET.item(), REINFORCED_CHANNEL.item(), FILTER.item()
        );
        component(SPARK_PLUG, true, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE.item(), SlimefunItems.NICKEL_INGOT.item(),
                SlimefunItems.ALUMINUM_INGOT.item(), null, MUNPOWDER.item(),
                null, SlimefunItems.STEEL_PLATE.item(), SlimefunItems.NICKEL_INGOT.item()
        );
        component(SPARK_PLUG_2, true, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE.item(), SlimefunItems.NICKEL_INGOT.item(),
                TUNGSTEN_INGOT.item(), null, MUNPOWDER.item(),
                null, SlimefunItems.STEEL_PLATE.item(), SlimefunItems.NICKEL_INGOT.item()
        );
        assembly(ROCKET_ENGINE, true,
                null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null,
                null, null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null, null,
                null, DIAMOND_CIRCUIT.item(), NOZZLE.item(), NOZZLE.item(), DIAMOND_CIRCUIT.item(), null,
                null, SlimefunItems.REINFORCED_PLATE.item(), new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE.item(), null,
                SlimefunItems.REINFORCED_PLATE.item(), null, null, null, null, SlimefunItems.REINFORCED_PLATE.item(),
                SlimefunItems.REINFORCED_PLATE.item(), null, null, null, null, SlimefunItems.REINFORCED_PLATE.item()
        );
        assembly(ROCKET_ENGINE_2, true,
                null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null,
                null, null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null, null,
                null, DIAMOND_CIRCUIT.item(), NOZZLE.item(), NOZZLE.item(), DIAMOND_CIRCUIT.item(), null,
                null, SlimefunItems.REINFORCED_PLATE.item(), SPARK_PLUG.item(), SPARK_PLUG.item(), SlimefunItems.REINFORCED_PLATE.item(), null,
                SlimefunItems.REINFORCED_PLATE.item(), null, null, null, null, SlimefunItems.REINFORCED_PLATE.item(),
                SlimefunItems.REINFORCED_PLATE.item(), null, null, null, null, SlimefunItems.REINFORCED_PLATE.item()
        );
        assembly(ROCKET_ENGINE_3, true,
                null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null,
                null, null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null, null,
                null, DIAMOND_CIRCUIT.item(), NOZZLE.item(), NOZZLE.item(), DIAMOND_CIRCUIT.item(), null,
                null, SPACE_GRADE_PLATE.item(), SPARK_PLUG_2.item(), SPARK_PLUG_2.item(), SPACE_GRADE_PLATE.item(), null,
                SPACE_GRADE_PLATE.item(), null, null, null, null, SPACE_GRADE_PLATE.item(),
                SPACE_GRADE_PLATE.item(), null, null, null, null, SPACE_GRADE_PLATE.item()
        );
        assembly(ION_ENGINE, true,
                SlimefunItems.SOLAR_GENERATOR_4.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), SlimefunItems.SOLAR_GENERATOR_4.item(),
                null, null, REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), null, null,
                null, DIAMOND_CIRCUIT.item(), NOZZLE.item(), NOZZLE.item(), DIAMOND_CIRCUIT.item(), null,
                null, SPACE_GRADE_PLATE.item(), BLISTERING_VOLCANIC_INGOT.item(), BLISTERING_VOLCANIC_INGOT.item(), SPACE_GRADE_PLATE.item(), null,
                SPACE_GRADE_PLATE.item(), null, null, null, null, SPACE_GRADE_PLATE.item(),
                SPACE_GRADE_PLATE.item(), ALUMINUM_COMPOSITE.item(), null, null, ALUMINUM_COMPOSITE.item(), SPACE_GRADE_PLATE.item()
                );
        component(ADVANCED_PROCESSING_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE,
                REDSTONE_CIRCUIT.item(), GLOWSTONE_CIRCUIT.item(), REDSTONE_CIRCUIT.item(),
                DIAMOND_CIRCUIT.item(), SlimefunItems.ADVANCED_CIRCUIT_BOARD.item(), DIAMOND_CIRCUIT.item(),
                REDSTONE_CIRCUIT.item(), LAPIS_CIRCUIT.item(), REDSTONE_CIRCUIT.item()
        );
        assembly(LIFE_SUPPORT_MODULE,
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), ADVANCED_PROCESSING_UNIT.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), ADVANCED_PROCESSING_UNIT.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), LAPIS_CIRCUIT.item(), OXYGEN_REGENERATOR.item(), OXYGEN_REGENERATOR.item(), LAPIS_CIRCUIT.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), LAPIS_CIRCUIT.item(), OXYGEN_REGENERATOR.item(), OXYGEN_REGENERATOR.item(), LAPIS_CIRCUIT.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), ADVANCED_PROCESSING_UNIT.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), ADVANCED_PROCESSING_UNIT.item(), SlimefunItems.STEEL_INGOT.item(),
                SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item(), REINFORCED_CHANNEL.item(), REINFORCED_CHANNEL.item(), SlimefunItems.STEEL_INGOT.item(), SlimefunItems.STEEL_INGOT.item()
        );
        assembly(NOSE_CONE, true,
                null, null, new ItemStack(Material.REDSTONE_TORCH), new ItemStack(Material.REDSTONE_TORCH), null, null,
                null, null, ALUMINUM_COMPOSITE.item(), ALUMINUM_COMPOSITE.item(), null, null,
                null, ALUMINUM_COMPOSITE.item(), GLOWSTONE_CIRCUIT.item(), GLOWSTONE_CIRCUIT.item(), ALUMINUM_COMPOSITE.item(), null,
                ALUMINUM_COMPOSITE.item(), null, null, null, null, ALUMINUM_COMPOSITE.item(),
                ALUMINUM_COMPOSITE.item(), null, null, null, null, ALUMINUM_COMPOSITE.item(),
                ALUMINUM_COMPOSITE.item(), null, null, null, null, ALUMINUM_COMPOSITE.item()
        );
        component(FUEL_TANK, RecipeType.ENHANCED_CRAFTING_TABLE,
                HEAVY_DUTY_SHEET.item(), HEAVY_DUTY_SHEET.item(), HEAVY_DUTY_SHEET.item(),
                HEAVY_DUTY_SHEET.item(), null, HEAVY_DUTY_SHEET.item(),
                HEAVY_DUTY_SHEET.item(), HEAVY_DUTY_SHEET.item(), HEAVY_DUTY_SHEET.item()
        );
        component(FUEL_TANK_2, RecipeType.ENHANCED_CRAFTING_TABLE,
                ULTRA_DUTY_SHEET.item(), ULTRA_DUTY_SHEET.item(), ULTRA_DUTY_SHEET.item(),
                ULTRA_DUTY_SHEET.item(), null, ULTRA_DUTY_SHEET.item(),
                ULTRA_DUTY_SHEET.item(), ULTRA_DUTY_SHEET.item(), ULTRA_DUTY_SHEET.item()
        );
        component(DIAMOND_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.DIAMOND_BLOCK), SlimefunItems.SILICON.item());
        component(REDSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.REDSTONE_BLOCK), SlimefunItems.SILICON.item());
        component(LAPIS_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.LAPIS_BLOCK), SlimefunItems.SILICON.item());
        component(GLOWSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.GLOWSTONE), SlimefunItems.SILICON.item());

        component(MUNPOWDER, CoreRecipeType.ALIEN_DROP,
                null, null, null,
                null, CustomItemStack.create(Material.CREEPER_HEAD, "&fMutant Creeper")
        );
        component(ENDER_BLOCK, DiamondAnvil.TYPE, new ItemStack(Material.ENDER_PEARL, 16), BLISTERING_VOLCANIC_INGOT.item());
        component(LUNAR_GLASS, RecipeType.SMELTERY, new ItemStack(Material.SAND), MOON_DUST.item());
        component(TUNGSTEN_CARBIDE, RecipeType.SMELTERY, TUNGSTEN_INGOT.item(), SlimefunItems.COMPRESSED_CARBON.item());
        assembly(DIAMOND_ANVIL_CELL,
                TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(),
                SlimefunItems.SYNTHETIC_DIAMOND.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.SYNTHETIC_DIAMOND.item(),
                null, SlimefunItems.SYNTHETIC_DIAMOND.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.SYNTHETIC_DIAMOND.item(), null,
                null, SlimefunItems.SYNTHETIC_DIAMOND.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.SYNTHETIC_DIAMOND.item(), null,
                SlimefunItems.SYNTHETIC_DIAMOND.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.CARBONADO.item(), SlimefunItems.SYNTHETIC_DIAMOND.item(),
                TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item(), TUNGSTEN_CARBIDE.item()
        );

        component(BLISTERING_VOLCANIC_INGOT, DiamondAnvil.TYPE, VOLCANIC_INGOT.item(), SlimefunItems.BLISTERING_INGOT_3.item());
        new UnplaceableBlock(CoreItemGroup.ITEMS, FUSION_PELLET, DiamondAnvil.TYPE, new ItemStack[] {
                BLISTERING_VOLCANIC_INGOT.item(), MOON_DUST.asQuantity(8)
        }, FUSION_PELLET.asQuantity(8)).register(Galactifun.instance());

        component(LASERITE_DUST, true, RecipeType.ORE_CRUSHER, LASERITE_ORE.item());
        component(LASERITE, DiamondAnvil.TYPE, LASERITE_DUST.asQuantity(12));

        new MoonCheese(CoreItemGroup.ITEMS, MOON_CHEESE, CoreRecipeType.WORLD_GEN, new ItemStack[]{
                BaseUniverse.THE_MOON.item()
        }).register(Galactifun.instance());

        BaseUniverse.THE_MOON.addBlockMapping(Material.GOLD_ORE, MOON_CHEESE);

        // SlimefunWarfare integration
        SlimefunItem diode = SlimefunItem.getById("LASER_DIODE");
        if (diode != null) {
            DiamondAnvil.TYPE.register(new ItemStack[] { diode.getItem().asQuantity(12), null }, LASERITE.item());
        }

        RecipeType.GRIND_STONE.register(
                Arrays.copyOf(new ItemStack[] { SULFUR_BLOCK.item() }, 9),
                CustomItemStack.create(SlimefunItems.SULFATE.item(), 9)
        );
        RecipeType.GRIND_STONE.register(
                Arrays.copyOf(new ItemStack[] { MARS_ROCK.item() }, 9),
                MARS_DUST.asQuantity(4)
        );
        RecipeType.GRIND_STONE.register(
                Arrays.copyOf(new ItemStack[] { MOON_ROCK.item() }, 9),
                MOON_DUST.asQuantity(4)
        );

        RecipeType.SMELTERY.register(
                Arrays.copyOf(new ItemStack[] { METHANE_ICE.item() }, 9),
                CustomItemStack.create(Gas.METHANE.item().item(), 4)
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
        new SlimefunItem(CoreItemGroup.COMPONENTS, item, type, recipe, CustomItemStack.create(item.item(), output)).register(Galactifun.instance());
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
