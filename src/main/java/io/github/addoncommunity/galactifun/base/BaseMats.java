package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.addoncommunity.galactifun.base.items.CircuitPress;
import io.github.addoncommunity.galactifun.core.CoreRecipeType;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * A class containing commonly used items in recipes
 *
 * @author Seggan
 * @author Mooy1
 */
@UtilityClass
public final class BaseMats {

    public static final SlimefunItemStack ALUMINUM_COMPOSITE = new SlimefunItemStack(
            "ALUMINUM_COMPOSITE",
            Material.IRON_INGOT,
            "&fAluminum Composite",
            "&7You'll never guess how long it took us",
            "&7to name this material"
    );
    public static final SlimefunItemStack TUNGSTEN = new SlimefunItemStack(
            "TUNGSTEN_INGOT",
            Material.NETHERITE_INGOT,
            "&bTungsten Ingot",
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

    public static void setup() {
        createComponent(ALUMINUM_COMPOSITE, RecipeType.SMELTERY,
                SlimefunItems.ALUMINUM_INGOT, SlimefunItems.MAGNESIUM_DUST, SlimefunItems.ZINC_DUST,
                SlimefunItems.TIN_DUST, SlimefunItems.ALUMINUM_DUST
        );
        createComponent(TUNGSTEN, RecipeType.SMELTERY, FALLEN_METEOR);
        createComponent(ALUMINUM_COMPOSITE_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(ALUMINUM_COMPOSITE, 8));
        createComponent(HEAVY_DUTY_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(ALUMINUM_COMPOSITE_SHEET, 8));
        createComponent(SPACE_GRADE_PLATE, RecipeType.COMPRESSOR, HEAVY_DUTY_SHEET, TUNGSTEN);
        createComponent(ULTRA_DUTY_SHEET, RecipeType.COMPRESSOR, new SlimefunItemStack(SPACE_GRADE_PLATE, 4));
        createComponent(GOLD_FOIL, RecipeType.COMPRESSOR, 4, SlimefunItems.GOLD_24K_BLOCK);
        createComponent(REINFORCED_CHANNEL, RecipeType.ENHANCED_CRAFTING_TABLE, 8,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET,
                ALUMINUM_COMPOSITE_SHEET, null, ALUMINUM_COMPOSITE_SHEET
        );
        createComponent(FAN_BLADE, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_INGOT, null,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
                null, SlimefunItems.STEEL_INGOT, null
        );
        createComponent(NOZZLE, RecipeType.ENHANCED_CRAFTING_TABLE, 2,
                SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
                null, new ItemStack(Material.IRON_TRAPDOOR), null
        );
        createComponent(FILTER, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
                SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH
        );
        createComponent(OXYGEN_REGENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE,
                SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL, FILTER,
                NOZZLE, GOLD_FOIL, FILTER,
                SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL, FILTER
        );
        createComponent(SPARK_PLUG, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
                SlimefunItems.ALUMINUM_INGOT, null, MUNPOWDER,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
        );
        createComponent(SPARK_PLUG_2, RecipeType.ENHANCED_CRAFTING_TABLE,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
                TUNGSTEN, null, MUNPOWDER,
                null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
        );
        createAssembly(ROCKET_ENGINE,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE, null,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE
        );
        createAssembly(ROCKET_ENGINE_2,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SlimefunItems.REINFORCED_PLATE, SPARK_PLUG, SPARK_PLUG, SlimefunItems.REINFORCED_PLATE, null,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE
        );
        createAssembly(ROCKET_ENGINE_3,
                null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null,
                null, null, REINFORCED_CHANNEL, REINFORCED_CHANNEL, null, null,
                null, DIAMOND_CIRCUIT, NOZZLE, NOZZLE, DIAMOND_CIRCUIT, null,
                null, SPACE_GRADE_PLATE, SPARK_PLUG_2, SPARK_PLUG_2, SPACE_GRADE_PLATE, null,
                SPACE_GRADE_PLATE, null, null, null, null, SPACE_GRADE_PLATE,
                SPACE_GRADE_PLATE, null, null, null, null, SPACE_GRADE_PLATE
        );
        createComponent(ADVANCED_PROCESSING_UNIT, RecipeType.ENHANCED_CRAFTING_TABLE,
                REDSTONE_CIRCUIT, GLOWSTONE_CIRCUIT, REDSTONE_CIRCUIT,
                DIAMOND_CIRCUIT, SlimefunItems.ADVANCED_CIRCUIT_BOARD, DIAMOND_CIRCUIT,
                REDSTONE_CIRCUIT, LAPIS_CIRCUIT, REDSTONE_CIRCUIT
        );
        createAssembly(LIFE_SUPPORT_MODULE,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, ADVANCED_PROCESSING_UNIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, LAPIS_CIRCUIT, OXYGEN_REGENERATOR, OXYGEN_REGENERATOR, LAPIS_CIRCUIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, LAPIS_CIRCUIT, OXYGEN_REGENERATOR, OXYGEN_REGENERATOR, LAPIS_CIRCUIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, ADVANCED_PROCESSING_UNIT, SlimefunItems.STEEL_INGOT,
                SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL, REINFORCED_CHANNEL, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT
        );
        createAssembly(NOSE_CONE,
                null, null, new ItemStack(Material.REDSTONE_TORCH), new ItemStack(Material.REDSTONE_TORCH), null, null,
                null, null, ALUMINUM_COMPOSITE, ALUMINUM_COMPOSITE, null, null,
                null, ALUMINUM_COMPOSITE, GLOWSTONE_CIRCUIT, GLOWSTONE_CIRCUIT, ALUMINUM_COMPOSITE, null,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE,
                ALUMINUM_COMPOSITE, null, null, null, null, ALUMINUM_COMPOSITE
        );
        createComponent(FUEL_TANK, RecipeType.ENHANCED_CRAFTING_TABLE,
                HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET,
                HEAVY_DUTY_SHEET, null, HEAVY_DUTY_SHEET,
                HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET, HEAVY_DUTY_SHEET
        );
        createComponent(FUEL_TANK_2, RecipeType.ENHANCED_CRAFTING_TABLE,
                ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET,
                ULTRA_DUTY_SHEET, null, ULTRA_DUTY_SHEET,
                ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET, ULTRA_DUTY_SHEET
        );
        createComponent(DIAMOND_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.DIAMOND_BLOCK));
        createComponent(REDSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.REDSTONE_BLOCK));
        createComponent(LAPIS_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.LAPIS_BLOCK));
        createComponent(GLOWSTONE_CIRCUIT, CircuitPress.TYPE, new ItemStack(Material.GLOWSTONE));
        
        createComponent(MUNPOWDER, CoreRecipeType.ALIEN_DROP,             
                null, null, null,
                null, new CustomItem(Material.CREEPER_HEAD, "&fMutant Creeper")
        );
        createComponent(FALLEN_METEOR, CoreRecipeType.WORLD_GEN, BaseRegistry.MARS.getItem());
    }

    private static void createComponent(SlimefunItemStack item, RecipeType type, ItemStack... recipe) {
        new SlimefunItem(CoreCategory.COMPONENTS, item, type, Arrays.copyOf(recipe, 9)).register(Galactifun.getInstance());
    }

    private static void createComponent(SlimefunItemStack item, RecipeType type, int output, ItemStack... recipe) {
        new SlimefunItem(CoreCategory.COMPONENTS, item, type, Arrays.copyOf(recipe, 9), new SlimefunItemStack(item, output)).register(Galactifun.getInstance());
    }

    private static void createAssembly(SlimefunItemStack item, ItemStack... recipe) {
        new SlimefunItem(CoreCategory.MAIN_CATEGORY, item, AssemblyTable.TYPE, Arrays.copyOf(recipe, 36)).register(Galactifun.getInstance());
    }

}
