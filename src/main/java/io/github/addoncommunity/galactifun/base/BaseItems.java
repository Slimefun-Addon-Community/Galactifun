package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.Rocket;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.addoncommunity.galactifun.base.items.CircuitPress;
import io.github.addoncommunity.galactifun.base.items.LaunchPadCore;
import io.github.addoncommunity.galactifun.core.CoreCategory;
import io.github.mooy1.infinitylib.slimefun.presets.LorePreset;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Holds the base machines and setup
 */
@UtilityClass
public final class BaseItems {
    
    public static final SlimefunItemStack LAUNCH_PAD_CORE = new SlimefunItemStack(
            "LAUNCH_PAD_CORE",
            Material.SEA_LANTERN,
            "&fLaunch Pad Core",
            "",
            "&7Surround with 8 &fLaunch Pad Floor&7s",
            "&7to use"
    );

    public static final SlimefunItemStack LAUNCH_PAD_FLOOR = new SlimefunItemStack(
            "LAUNCH_PAD_FLOOR",
            Material.STONE_SLAB,
            "&fLaunch Pad Floor",
            "",
            "&7Used in constructing the Launch Pad"
    );
    
    public static final SlimefunItemStack CIRCUIT_PRESS = new SlimefunItemStack(
            "CIRCUIT_PRESS",
            Material.PISTON,
            "&fCircuit Press",
            "&7Creates circuits",
            "",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            LorePreset.energyBuffer(1024)
    );
    public static final SlimefunItemStack ASSEMBLY_TABLE = new SlimefunItemStack(
            "ASSEMBLY_TABLE",
            Material.SMITHING_TABLE,
            "&fAssembly Table",
            "",
            "&7Used to construct many things",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LorePreset.energyPerSecond(2048)
    );

    private static final int TIER_ONE_FUEL = 10;
    private static final int TIER_ONE_STORAGE = 9;
    private static final int TIER_TWO_FUEL = 100;
    private static final int TIER_TWO_STORAGE = 18;
    private static final int TIER_THREE_FUEL = 500;
    private static final int TIER_THREE_STORAGE = 36;

    public static final SlimefunItemStack TIER_ONE = new SlimefunItemStack(
            "ROCKET_TIER_ONE",
            GalactifunHead.ROCKET,
            "&4Rocket Tier 1",
            "",
            "&7Fuel Capacity: " + TIER_ONE_FUEL,
            "&7Cargo Capacity: " + TIER_ONE_STORAGE
    );
    public static final SlimefunItemStack TIER_TWO = new SlimefunItemStack(
            "ROCKET_TIER_TWO",
            GalactifunHead.ROCKET,
            "&4Rocket Tier 2",
            "",
            "&7Fuel Capacity: " + TIER_TWO_FUEL,
            "&7Cargo Capacity: " + TIER_TWO_STORAGE
    );
    public static final SlimefunItemStack TIER_THREE = new SlimefunItemStack(
            "ROCKET_TIER_THREE",
            GalactifunHead.ROCKET,
            "&4Rocket Tier 3",
            "",
            "&7Fuel Capacity: " + TIER_THREE_FUEL,
            "&7Cargo Capacity: " + TIER_THREE_STORAGE
    );
    
    public static void setup(Galactifun galactifun) {
        new CircuitPress(CoreCategory.MACHINES, CIRCUIT_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL,
                SlimefunItems.STEEL_PLATE, null, SlimefunItems.STEEL_PLATE,
                SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL
        }).setCapacity(1024).setEnergyConsumption(512).setProcessingSpeed(1).register(galactifun);
        
        new AssemblyTable(CoreCategory.MACHINES, ASSEMBLY_TABLE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.STEEL_PLATE, SlimefunItems.AUTOMATED_CRAFTING_CHAMBER, SlimefunItems.STEEL_PLATE,
                SlimefunItems.CARGO_MOTOR, BaseMats.ADVANCED_PROCESSING_UNIT, SlimefunItems.CARGO_MOTOR,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
        }).register(galactifun);
        
        new Rocket(CoreCategory.ITEMS, TIER_ONE, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.HEAVY_DUTY_SHEET, null,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE, BaseMats.ROCKET_ENGINE, null, BaseMats.HEAVY_DUTY_SHEET
        }, TIER_ONE_FUEL, TIER_ONE_STORAGE).register(galactifun);
        new Rocket(CoreCategory.ITEMS, TIER_TWO, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.HEAVY_DUTY_SHEET, null,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE_2, BaseMats.ROCKET_ENGINE_2, null, BaseMats.HEAVY_DUTY_SHEET
        }, TIER_TWO_FUEL, TIER_TWO_STORAGE).register(galactifun);
        new Rocket(CoreCategory.ITEMS, TIER_THREE, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, null,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.FUEL_TANK_2, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK_2, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.FUEL_TANK_2, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK_2, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE_3, BaseMats.ROCKET_ENGINE_3, null, BaseMats.ULTRA_DUTY_SHEET
        }, TIER_THREE_FUEL, TIER_THREE_STORAGE).register(galactifun);

        new SlimefunItem(CoreCategory.ITEMS, LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                null, null, null,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE,
        }).register(galactifun);
        
        new LaunchPadCore(CoreCategory.ITEMS, BaseItems.LAUNCH_PAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                SlimefunItems.REINFORCED_PLATE, BaseMats.NOZZLE, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.CARGO_MOTOR, SlimefunItems.OIL_PUMP, SlimefunItems.CARGO_MOTOR,
                SlimefunItems.REINFORCED_PLATE, BaseMats.ADVANCED_PROCESSING_UNIT, SlimefunItems.REINFORCED_PLATE,
        }).register(galactifun);
        
    }
    
}
