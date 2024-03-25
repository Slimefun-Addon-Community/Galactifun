package io.github.addoncommunity.galactifun.base;

import lombok.experimental.UtilityClass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.Relic;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuit;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitHelmet;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitStat;
import io.github.addoncommunity.galactifun.api.items.spacesuit.SpaceSuitUpgrade;
import io.github.addoncommunity.galactifun.api.universe.attributes.atmosphere.Gas;
import io.github.addoncommunity.galactifun.api.worlds.AlienWorld;
import io.github.addoncommunity.galactifun.base.items.AssemblyTable;
import io.github.addoncommunity.galactifun.base.items.AtmosphericHarvester;
import io.github.addoncommunity.galactifun.base.items.AutomaticDoor;
import io.github.addoncommunity.galactifun.base.items.CircuitPress;
import io.github.addoncommunity.galactifun.base.items.DiamondAnvil;
import io.github.addoncommunity.galactifun.base.items.Electrolyzer;
import io.github.addoncommunity.galactifun.base.items.FusionReactor;
import io.github.addoncommunity.galactifun.base.items.LaunchPadCore;
import io.github.addoncommunity.galactifun.base.items.LaunchPadFloor;
import io.github.addoncommunity.galactifun.base.items.OxygenFiller;
import io.github.addoncommunity.galactifun.base.items.SpaceSuitUpgrader;
import io.github.addoncommunity.galactifun.base.items.StargateController;
import io.github.addoncommunity.galactifun.base.items.StargateRing;
import io.github.addoncommunity.galactifun.base.items.TechnologicalSalvager;
import io.github.addoncommunity.galactifun.base.items.knowledge.Observatory;
import io.github.addoncommunity.galactifun.base.items.knowledge.PlanetaryAnalyzer;
import io.github.addoncommunity.galactifun.base.items.protection.CoolingUnit;
import io.github.addoncommunity.galactifun.base.items.protection.ForcefieldGenerator;
import io.github.addoncommunity.galactifun.base.items.protection.IonDisperser;
import io.github.addoncommunity.galactifun.base.items.protection.OxygenSealer;
import io.github.addoncommunity.galactifun.base.items.protection.SpaceHeater;
import io.github.addoncommunity.galactifun.base.items.rockets.ChemicalRocket;
import io.github.addoncommunity.galactifun.base.items.rockets.IonRocket;
import io.github.addoncommunity.galactifun.core.CoreItemGroup;
import io.github.mooy1.infinitylib.machines.MachineBlock;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;

/**
 * Holds the base machines and setup
 */
@UtilityClass
public final class BaseItems {

    //<editor-fold desc="Space Suits, Oxygen, Upgrades" defaultstate="collapsed">
    public static final SlimefunItemStack OXYGEN_FILLER = new SlimefunItemStack(
            "OXYGEN_FILLER",
            new ItemStack(Material.QUARTZ_BLOCK),
            "&fOxygen Filler",
            "",
            "&7Fills space suits with oxygen",
            "",
            LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(100),
            LoreBuilder.speed(1)
    );
    public static final SlimefunItemStack SPACE_SUIT_UPGRADER = new SlimefunItemStack(
            "SPACE_SUIT_UPGRADER",
            new ItemStack(Material.IRON_BLOCK),
            "&fSpace Suit Upgrader",
            "",
            "&7Adds upgrades to your space suit",
            "",
            LoreBuilder.machine(MachineTier.AVERAGE, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(200),
            LoreBuilder.speed(1)
    );
    public static final SlimefunItemStack HEAT_RESISTANCE_UPGRADE = new SlimefunItemStack(
            "HEAT_RESISTANCE_UPGRADE",
            Material.IRON_BLOCK,
            "&7Heat Resistance Upgrade",
            "",
            "&8Add to your space suit to increase heat resistance"
    );
    public static final SlimefunItemStack COLD_RESISTANCE_UPGRADE = new SlimefunItemStack(
            "COLD_RESISTANCE_UPGRADE",
            Material.IRON_BLOCK,
            "&7Cold Resistance Upgrade",
            "",
            "&8Add to your space suit to increase cold resistance"
    );
    public static final SlimefunItemStack RADIATION_RESISTANCE_UPGRADE = new SlimefunItemStack(
            "RADIATION_RESISTANCE_UPGRADE",
            Material.IRON_BLOCK,
            "&7Radiation Resistance Upgrade",
            "",
            "&8Add to your space suit to increase radiation resistance"
    );
    public static final SlimefunItemStack SPACE_SUIT_HELMET = new SlimefunItemStack(
            "SPACE_SUIT_HELMET",
            Material.GLASS,
            "&fSpace Suit Helmet",
            "",
            "&7A basic space suit helmet",
            LoreBuilder.RIGHT_CLICK_TO_USE
    );
    public static final SlimefunItemStack SPACE_SUIT_CHEST = new SlimefunItemStack(
            "SPACE_SUIT_CHEST",
            Material.IRON_CHESTPLATE,
            "&fSpace Suit Chest",
            "",
            "&7A basic space suit chest",
            "",
            SpaceSuit.oxygenLore(0, 3600)
    );
    public static final SlimefunItemStack SPACE_SUIT_PANTS = new SlimefunItemStack(
            "SPACE_SUIT_PANTS",
            Material.IRON_LEGGINGS,
            "&fSpace Suit Pants",
            "",
            "&7Basic pair of space suit pants"
    );
    public static final SlimefunItemStack SPACE_SUIT_BOOTS = new SlimefunItemStack(
            "SPACE_SUIT_BOOTS",
            Material.IRON_BOOTS,
            "&fSpace Suit Boots",
            "",
            "&7Basic pair of space suit boots"
    );
    //</editor-fold>
    //<editor-fold desc="Random Stuff" defaultstate="collapsed">
    public static final SlimefunItemStack LAUNCH_PAD_CORE = new SlimefunItemStack(
            "LAUNCH_PAD_CORE",
            Material.STONE,
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
            "",
            "&7Creates circuits",
            LoreBuilder.machine(MachineTier.ADVANCED, MachineType.MACHINE),
            LoreBuilder.powerBuffer(512),
            LoreBuilder.powerPerSecond(256)
    );
    public static final SlimefunItemStack ASSEMBLY_TABLE = new SlimefunItemStack(
            "ASSEMBLY_TABLE",
            Material.SMITHING_TABLE,
            "&fAssembly Table",
            "",
            "&7Used to construct many things",
            "",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.power(2048, "/Craft")
    );
    public static final SlimefunItemStack STARGATE_RING = new SlimefunItemStack(
            "STARGATE_RING",
            Material.QUARTZ_BLOCK,
            "&9Stargate Ring",
            "",
            "&7Used to construct a stargate"
    );
    public static final SlimefunItemStack STARGATE_CONTROLLER = new SlimefunItemStack(
            "STARGATE_CONTROLLER",
            Material.CHISELED_QUARTZ_BLOCK,
            "&9Stargate Controller",
            "",
            "&7Used to control a stargate"
    );
    public static final SlimefunItemStack OBSERVATORY = new SlimefunItemStack(
            "OBSERVATORY",
            Material.GLASS,
            "&fObservatory",
            "",
            "&7Allows you to discover basic info about",
            "&7a world remotely. Only works on worlds",
            "&7closer than 0.25 ly (2,365,200,000,000 km)"
    );
    public static final SlimefunItemStack PLANETARY_ANALYZER = new SlimefunItemStack(
            "PLANETARY_ANALYZER",
            Material.SEA_LANTERN,
            "&fPlanetary Analyzer",
            "",
            "&7Allows you to discover advanced info",
            "&7about the current planet"
    );
    public static final SlimefunItemStack DIAMOND_ANVIL = new SlimefunItemStack(
            "DIAMOND_ANVIL",
            Material.PISTON,
            "&fDiamond Anvil",
            "",
            "&7Compresses material so hard",
            "&7it becomes something else entirely",
            LoreBuilder.machine(MachineTier.END_GAME, MachineType.MACHINE),
            LoreBuilder.powerPerSecond(1024),
            LoreBuilder.powerBuffer(2048)
    );
    public static final SlimefunItemStack FUSION_REACTOR = new SlimefunItemStack(
            "FUSION_REACTOR",
            HeadTexture.NUCLEAR_REACTOR.getAsItemStack(),
            "&fFusion Reactor",
            "",
            "&7Uses lasers to fuse Helium-3 pellets",
            "&7and generate a whale a lot of energy",
            "&7in the process",
            "",
            LoreBuilder.powerPerSecond(65_536),
            LoreBuilder.powerBuffer(65_536)
    );
    public static final SlimefunItemStack ATMOSPHERIC_HARVESTER = new SlimefunItemStack(
            "ATMOSPHERIC_HARVESTER",
            GalactifunHead.ATMOSPHERIC_HARVESTER,
            "&fAtmospheric Harvester",
            "",
            "&7Collects gases from the atmosphere",
            LoreBuilder.powerPerSecond(64),
            LoreBuilder.powerBuffer(128),
            "",
            "&f&oTexture by haseir"
    );
    public static final SlimefunItemStack CHEMICAL_REACTOR = new SlimefunItemStack(
            "CHEMICAL_REACTOR",
            Material.SEA_LANTERN,
            "&fChemical Reactor",
            "",
            "&7Reacts chemicals together",
            "&7to create new ones",
            LoreBuilder.powerPerSecond(256),
            LoreBuilder.powerBuffer(512)
    );
    public static final SlimefunItemStack ELECTROLYZER = new SlimefunItemStack(
            "ELECTROLYZER",
            Material.LANTERN,
            "&fElectrolyzer",
            "",
            "&7Uses electricity to split",
            "&7chemicals into their constituents",
            LoreBuilder.powerPerSecond(256),
            LoreBuilder.powerBuffer(512)
    );
    //</editor-fold>
    //<editor-fold desc="Protecting Blocks" defaultstate="collapsed">
    public static final SlimefunItemStack COOLING_UNIT_1 = new SlimefunItemStack(
            "COOLING_UNIT_1",
            HeadTexture.COOLING_UNIT.getTexture(),
            "&bCooling Unit I",
            "",
            "&7Effect: Heat",
            "&7Protection: 2",
            "&7Range: 1000 blocks",
            LoreBuilder.powerPerSecond(1024),
            LoreBuilder.powerBuffer(2048)
    );
    public static final SlimefunItemStack COOLING_UNIT_2 = new SlimefunItemStack(
            "COOLING_UNIT_2",
            HeadTexture.COOLING_UNIT.getTexture(),
            "&bCooling Unit II",
            "",
            "&7Effect: Heat",
            "&7Protection: 4",
            "&7Range: 1500 blocks",
            LoreBuilder.powerPerSecond(2048),
            LoreBuilder.powerBuffer(4096)
    );
    public static final SlimefunItemStack COOLING_UNIT_3 = new SlimefunItemStack(
            "COOLING_UNIT_3",
            HeadTexture.COOLING_UNIT.getTexture(),
            "&bCooling Unit III",
            "",
            "&7Effect: Heat",
            "&7Protection: 6",
            "&7Range: 2000 blocks",
            LoreBuilder.powerPerSecond(3072),
            LoreBuilder.powerBuffer(6144)
    );
    public static final SlimefunItemStack SPACE_HEATER_1 = new SlimefunItemStack(
            "SPACE_HEATER_1",
            Material.SHROOMLIGHT,
            "&6Space Heater I",
            "",
            "&7Effect: Cold",
            "&7Protection: 2",
            "&7Range: 1000 blocks",
            LoreBuilder.powerPerSecond(512),
            LoreBuilder.powerBuffer(1024)
    );
    public static final SlimefunItemStack SPACE_HEATER_2 = new SlimefunItemStack(
            "SPACE_HEATER_2",
            Material.SHROOMLIGHT,
            "&6Space Heater II",
            "",
            "&7Effect: Cold",
            "&7Protection: 4",
            "&7Range: 1500 blocks",
            LoreBuilder.powerPerSecond(1024),
            LoreBuilder.powerBuffer(2048)
    );
    public static final SlimefunItemStack SPACE_HEATER_3 = new SlimefunItemStack(
            "SPACE_HEATER_3",
            Material.SHROOMLIGHT,
            "&6Space Heater III",
            "",
            "&7Effect: Cold",
            "&7Protection: 6",
            "&7Range: 2000 blocks",
            LoreBuilder.powerPerSecond(2048),
            LoreBuilder.powerBuffer(4096)
    );
    public static final SlimefunItemStack ION_DISPERSER_1 = new SlimefunItemStack(
            "ION_DISPERSER_1",
            Material.PRISMARINE,
            "&fIon Disperser I",
            "",
            "&7Effect: Radiation",
            "&7Protection: 2",
            "&7Range: 1000 blocks",
            LoreBuilder.powerPerSecond(512),
            LoreBuilder.powerBuffer(1024)
    );
    public static final SlimefunItemStack ION_DISPERSER_2 = new SlimefunItemStack(
            "ION_DISPERSER_2",
            Material.PRISMARINE,
            "&fIon Disperser II",
            "",
            "&7Effect: Radiation",
            "&7Protection: 4",
            "&7Range: 1500 blocks",
            LoreBuilder.powerPerSecond(1024),
            LoreBuilder.powerBuffer(2048)
    );
    public static final SlimefunItemStack OXYGEN_SEALER = new SlimefunItemStack(
            "OXYGEN_SEALER",
            Material.FURNACE,
            "&fOxygen Sealer",
            "",
            "&7Fills a sealed area with oxygen. Range 1000 blocks"
    );
    public static final SlimefunItemStack LANDING_HATCH = new SlimefunItemStack(
            "LANDING_HATCH",
            Material.IRON_TRAPDOOR,
            "&fLanding Hatch",
            "",
            "&7Rockets ignore this block when",
            "&7landing; they'll land on the",
            "&7highest block below it. It is",
            "&7considered impassable by air",
            "&7so it can be used to seal spaces"
    );
    public static final SlimefunItemStack SUPER_FAN = new SlimefunItemStack(
            "SUPER_FAN",
            Material.WHITE_WOOL,
            "&7Super Fan",
            "",
            "&7Place this next to a block that needs",
            "&7a sealed area to extend the range by 15%"
    );
    public static final SlimefunItemStack AUTOMATIC_DOOR = new SlimefunItemStack(
            "AUTOMATIC_DOOR",
            Material.OBSERVER,
            "&fAutomatic Door",
            "",
            "&7Automatically places/removes the blocks inside",
            "&7when you go to/away from it"
    );
    public static final SlimefunItemStack ENVIRONMENTAL_FORCEFIELD_GENERATOR = new SlimefunItemStack(
            "ENVIRONMENTAL_FORCEFIELD_GENERATOR",
            Material.DISPENSER,
            "&fEnvironmental Forcefield Generator",
            "",
            "&7Produces a forcefield that keeps air from",
            "&7going through, but allows entities"
    );
    //</editor-fold>
    //<editor-fold desc="Relics" defaultstate="collapsed">
    public static final SlimefunItemStack ENGINE_RELIC = new SlimefunItemStack(
            "ENGINE_RELIC",
            Material.SEA_LANTERN,
            "&fRelic: 'Letagivd Yynmukav",
            "",
            "&7Dorepv 'lbarok 'tagivd yboths amukav",
            "&7uyigrene teuz'lopsi 'letagivd tote"
    );
    public static final SlimefunItemStack REACTOR_RELIC = new SlimefunItemStack(
            "REACTOR_RELIC",
            Material.BEACON,
            "&fRelic: Rotarenegortkele Yynmukav",
            "",
            "&7Ovehcin zi 'taribos onhzom uyigrene",
            "&7oths lamud otk?"
    );
    public static final SlimefunItemStack COMPUTER_RELIC = new SlimefunItemStack(
            "COMPUTER_RELIC",
            GalactifunHead.CORE,
            "&fRelic: Retuypmok Yyntnavk",
            "",
            "&7Aretupmoc eyenhcybo mehc eyertsyb ogonman",
            "&7einelsihcsar 'taled tugom aretuypmok eyntnavk"
    );
    public static final SlimefunItemStack BROKEN_SOLAR_PANEL_RELIC = new SlimefunItemStack(
            "BROKEN_SOLAR_PANEL_RELIC",
            Material.DAYLIGHT_DETECTOR,
            "&7Relic: 'Lenap Ayanhcenlos Ayanamolop",
            "",
            "&7'Lenap ayanhcenlos ayarats 'nehco"
    );
    public static final SlimefunItemStack FALLEN_SATELLITE_RELIC = new SlimefunItemStack(
            "FALLEN_SATELLITE_RELIC",
            Material.CHISELED_QUARTZ_BLOCK,
            "&7Relic: Kintups Yyhsdap",
            "",
            "&7Aben s lapu yyrotok, kintups"
    );
    public static final SlimefunItemStack TECHNOLOGICAL_SALVAGER = new SlimefunItemStack(
            "TECHNOLOGICAL_SALVAGER",
            GalactifunHead.CORE,
            "&fTechnological Salvager",
            "",
            "&7Salvages anything salvageable from relics",
            LoreBuilder.powerPerSecond(64),
            LoreBuilder.powerBuffer(64)
    );
    //</editor-fold>
    //<editor-fold desc="Rock It" defaultstate="collapsed">
    private static final int TIER_ONE_FUEL = 10;
    private static final int TIER_ONE_STORAGE = 9;
    public static final SlimefunItemStack TIER_ONE = new SlimefunItemStack(
            "ROCKET_TIER_ONE",
            GalactifunHead.ROCKET,
            "&4Chemical Rocket Tier 1",
            "",
            "&7Fuel Capacity: " + TIER_ONE_FUEL,
            "&7Cargo Capacity: " + TIER_ONE_STORAGE
    );
    private static final int TIER_TWO_FUEL = 100;
    private static final int TIER_TWO_STORAGE = 18;
    public static final SlimefunItemStack TIER_TWO = new SlimefunItemStack(
            "ROCKET_TIER_TWO",
            GalactifunHead.ROCKET,
            "&4Chemical Rocket Tier 2",
            "",
            "&7Fuel Capacity: " + TIER_TWO_FUEL,
            "&7Cargo Capacity: " + TIER_TWO_STORAGE
    );
    private static final int TIER_THREE_FUEL = 500;
    private static final int TIER_THREE_STORAGE = 36;
    public static final SlimefunItemStack TIER_THREE = new SlimefunItemStack(
            "ROCKET_TIER_THREE",
            GalactifunHead.ROCKET,
            "&4Chemical Rocket Tier 3",
            "",
            "&7Fuel Capacity: " + TIER_THREE_FUEL,
            "&7Cargo Capacity: " + TIER_THREE_STORAGE
    );
    public static final SlimefunItemStack ION_ROCKET = new SlimefunItemStack(
            "ION_ROCKET",
            GalactifunHead.ION_ROCKET,
            "&bIon Rocket",
            "",
            "&7Uses a specialized engine that",
            "&7expels ions at great speed, granting",
            "&7extreme fuel efficiency",
            "",
            "&7Fuel Capacity: 500",
            "&7Cargo Capacity: 18"
    );
    //</editor-fold>

    public static void setup(Galactifun galactifun) {
        new OxygenFiller(CoreItemGroup.MACHINES, OXYGEN_FILLER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE, BaseMats.REINFORCED_CHANNEL, BaseMats.ALUMINUM_COMPOSITE,
                BaseMats.ALUMINUM_COMPOSITE, BaseMats.FAN_BLADE, BaseMats.ALUMINUM_COMPOSITE,
                BaseMats.ALUMINUM_COMPOSITE, null, BaseMats.ALUMINUM_COMPOSITE
        }).setCapacity(200).setEnergyConsumption(100).setProcessingSpeed(1).register(galactifun);
        new SpaceSuitUpgrader(CoreItemGroup.MACHINES, SPACE_SUIT_UPGRADER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE, SlimefunItems.HARDENED_GLASS, BaseMats.ALUMINUM_COMPOSITE,
                BaseMats.ALUMINUM_COMPOSITE, null, BaseMats.ALUMINUM_COMPOSITE,
                BaseMats.ALUMINUM_COMPOSITE, SlimefunItems.STEEL_INGOT, BaseMats.ALUMINUM_COMPOSITE
        }).setCapacity(400).setEnergyConsumption(200).setProcessingSpeed(1).register(galactifun);

        new SpaceSuitUpgrade(CoreItemGroup.EQUIPMENT, HEAT_RESISTANCE_UPGRADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT,
                SlimefunItems.COOLING_UNIT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.COOLING_UNIT,
                SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT, SlimefunItems.COOLING_UNIT
        }, SpaceSuitStat.HEAT_RESISTANCE, 1).register(galactifun);
        new SpaceSuitUpgrade(CoreItemGroup.EQUIPMENT, COLD_RESISTANCE_UPGRADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL,
                SlimefunItems.HEATING_COIL, new ItemStack(Material.IRON_BLOCK), SlimefunItems.HEATING_COIL,
                SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL, SlimefunItems.HEATING_COIL
        }, SpaceSuitStat.COLD_RESISTANCE, 1).register(galactifun);
        new SpaceSuitUpgrade(CoreItemGroup.EQUIPMENT, RADIATION_RESISTANCE_UPGRADE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.LEAD_INGOT, SlimefunItems.LEAD_INGOT, SlimefunItems.LEAD_INGOT,
                SlimefunItems.LEAD_INGOT, new ItemStack(Material.IRON_BLOCK), SlimefunItems.LEAD_INGOT,
                SlimefunItems.LEAD_INGOT, SlimefunItems.LEAD_INGOT, SlimefunItems.LEAD_INGOT
        }, SpaceSuitStat.RADIATION_RESISTANCE, 1).register(galactifun);

        new SpaceSuitHelmet(CoreItemGroup.EQUIPMENT, SPACE_SUIT_HELMET, RecipeType.ARMOR_FORGE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.GOLD_FOIL, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                SlimefunItems.REINFORCED_CLOTH, SlimefunItems.SCUBA_HELMET, SlimefunItems.REINFORCED_CLOTH,
                BaseMats.REINFORCED_CHANNEL, BaseMats.OXYGEN_REGENERATOR, BaseMats.FAN_BLADE
        }, 3, 0).register(galactifun);
        new SpaceSuit(CoreItemGroup.EQUIPMENT, SPACE_SUIT_CHEST, RecipeType.ARMOR_FORGE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.REINFORCED_CLOTH, BaseMats.REINFORCED_CHANNEL,
                BaseMats.FAN_BLADE, SlimefunItems.HAZMAT_CHESTPLATE, BaseMats.FAN_BLADE,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 3, 3600).register(galactifun);
        new SpaceSuit(CoreItemGroup.EQUIPMENT, SPACE_SUIT_PANTS, RecipeType.ARMOR_FORGE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.HAZMAT_LEGGINGS, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, null, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, null, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 2, 0).register(galactifun);
        new SpaceSuit(CoreItemGroup.EQUIPMENT, SPACE_SUIT_BOOTS, RecipeType.ARMOR_FORGE, new ItemStack[] {
                null, null, null,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, null, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.HAZMAT_BOOTS, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 2, 0).register(galactifun);

        new CircuitPress(CoreItemGroup.MACHINES, CIRCUIT_PRESS, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, null, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                SlimefunItems.HEATING_COIL, new ItemStack(Material.PISTON), SlimefunItems.HEATING_COIL
        }).setCapacity(512).setEnergyConsumption(128).setProcessingSpeed(1).register(galactifun);

        new AssemblyTable(ASSEMBLY_TABLE, new ItemStack[] {
                SlimefunItems.STEEL_PLATE, SlimefunItems.ENHANCED_AUTO_CRAFTER, SlimefunItems.STEEL_PLATE,
                SlimefunItems.CARGO_MOTOR, BaseMats.ADVANCED_PROCESSING_UNIT, SlimefunItems.CARGO_MOTOR,
                SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE, SlimefunItems.REINFORCED_PLATE
        }, 2048).register(galactifun);

        new StargateRing(CoreItemGroup.COMPONENTS, STARGATE_RING, AssemblyTable.TYPE, new ItemStack[] {
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ENDER_BLOCK, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET
        }).register(galactifun);
        new StargateController(CoreItemGroup.COMPONENTS, STARGATE_CONTROLLER, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                STARGATE_RING, BaseMats.GLOWSTONE_CIRCUIT, STARGATE_RING,
                BaseMats.REDSTONE_CIRCUIT, BaseMats.DIAMOND_CIRCUIT, BaseMats.REDSTONE_CIRCUIT,
                STARGATE_RING, BaseMats.LAPIS_CIRCUIT, STARGATE_RING
        }).register(galactifun);

        new ChemicalRocket(CoreItemGroup.ITEMS, TIER_ONE, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.HEAVY_DUTY_SHEET, null,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE, BaseMats.ROCKET_ENGINE, null, BaseMats.HEAVY_DUTY_SHEET
        }, TIER_ONE_FUEL, TIER_ONE_STORAGE).register(galactifun);
        new ChemicalRocket(CoreItemGroup.ITEMS, TIER_TWO, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.HEAVY_DUTY_SHEET, null,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE_2, BaseMats.ROCKET_ENGINE_2, null, BaseMats.HEAVY_DUTY_SHEET
        }, TIER_TWO_FUEL, TIER_TWO_STORAGE).register(galactifun);
        new ChemicalRocket(CoreItemGroup.ITEMS, TIER_THREE, AssemblyTable.TYPE, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, null,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.FUEL_TANK_2, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.FUEL_TANK_2, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.FUEL_TANK_2, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK_2, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, null, BaseMats.ROCKET_ENGINE_3, BaseMats.ROCKET_ENGINE_3, null, BaseMats.ULTRA_DUTY_SHEET
        }, TIER_THREE_FUEL, TIER_THREE_STORAGE).register(galactifun);

        new SlimefunItem(CoreItemGroup.ITEMS, LANDING_HATCH, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BaseMats.HEAVY_DUTY_SHEET, new ItemStack(Material.IRON_TRAPDOOR), BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.SPACE_GRADE_PLATE, null, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.SPACE_GRADE_PLATE, null, BaseMats.SPACE_GRADE_PLATE
        }).register(galactifun);

        new LaunchPadFloor(CoreItemGroup.ITEMS, BaseItems.LAUNCH_PAD_FLOOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                null, null, null,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET, BaseMats.HEAVY_DUTY_SHEET,
        }).register(galactifun);

        new LaunchPadCore(CoreItemGroup.ITEMS, BaseItems.LAUNCH_PAD_CORE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                SlimefunItems.REINFORCED_PLATE, BaseMats.NOZZLE, SlimefunItems.REINFORCED_PLATE,
                SlimefunItems.CARGO_MOTOR, SlimefunItems.OIL_PUMP, SlimefunItems.CARGO_MOTOR,
                SlimefunItems.REINFORCED_PLATE, BaseMats.ADVANCED_PROCESSING_UNIT, SlimefunItems.REINFORCED_PLATE,
        }).register(galactifun);

        new CoolingUnit(COOLING_UNIT_1, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.COOLING_UNIT, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                SlimefunItems.COOLING_UNIT, BaseMats.FAN_BLADE, SlimefunItems.COOLING_UNIT,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.COOLING_UNIT, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 1).register(galactifun);

        new CoolingUnit(COOLING_UNIT_2, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, BaseMats.DRY_ICE, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.DRY_ICE, COOLING_UNIT_1, BaseMats.DRY_ICE,
                BaseMats.SPACE_GRADE_PLATE, BaseMats.DRY_ICE, BaseMats.SPACE_GRADE_PLATE
        }, 2).register(galactifun);

        new CoolingUnit(COOLING_UNIT_3, new ItemStack[] {
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.DRY_ICE, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.DRY_ICE, COOLING_UNIT_2, BaseMats.DRY_ICE,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.DRY_ICE, BaseMats.HEAVY_DUTY_SHEET
        }, 3).register(galactifun);

        new SpaceHeater(SPACE_HEATER_1, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.HEATING_COIL, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                SlimefunItems.HEATING_COIL, BaseMats.FAN_BLADE, SlimefunItems.HEATING_COIL,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.HEATING_COIL, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 1).register(galactifun);

        new SpaceHeater(SPACE_HEATER_2, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, new ItemStack(Material.LAVA_BUCKET), BaseMats.SPACE_GRADE_PLATE,
                BaseMats.VENTSTONE, SPACE_HEATER_1, BaseMats.VENTSTONE,
                BaseMats.SPACE_GRADE_PLATE, SlimefunItems.HEATING_COIL, BaseMats.SPACE_GRADE_PLATE
        }, 2).register(galactifun);

        new SpaceHeater(SPACE_HEATER_3, new ItemStack[] {
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.VOLCANIC_INGOT, BaseMats.HEAVY_DUTY_SHEET,
                BaseMats.VOLCANIC_INGOT, SPACE_HEATER_2, BaseMats.VOLCANIC_INGOT,
                BaseMats.HEAVY_DUTY_SHEET, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.HEAVY_DUTY_SHEET
        }, 3).register(galactifun);

        new IonDisperser(ION_DISPERSER_1, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.FAN_BLADE, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                new ItemStack(Material.PRISMARINE_CRYSTALS), BaseMats.SULFUR_BLOCK, new ItemStack(Material.PRISMARINE_CRYSTALS),
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.VENTSTONE, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 1).register(galactifun);

        new IonDisperser(ION_DISPERSER_2, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, BaseMats.FAN_BLADE, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.SULFUR_BLOCK, ION_DISPERSER_1, BaseMats.SULFUR_BLOCK,
                BaseMats.SPACE_GRADE_PLATE, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.SPACE_GRADE_PLATE
        }, 2).register(galactifun);

        new Observatory(OBSERVATORY, new ItemStack[] {
                new ItemStack(Material.IRON_BLOCK), new ItemStack(Material.PISTON), new ItemStack(Material.IRON_BLOCK),
                new ItemStack(Material.PISTON), BaseMats.LUNAR_GLASS, new ItemStack(Material.PISTON),
                new ItemStack(Material.IRON_BLOCK), new ItemStack(Material.PISTON), new ItemStack(Material.IRON_BLOCK)
        }).register(galactifun);
        new PlanetaryAnalyzer(PLANETARY_ANALYZER, new ItemStack[] {
                BaseMats.TUNGSTEN_INGOT, SlimefunItems.GPS_TRANSMITTER_4, BaseMats.TUNGSTEN_INGOT,
                BaseMats.SPACE_GRADE_PLATE, SlimefunItems.ENERGIZED_CAPACITOR, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.TUNGSTEN_INGOT, BaseMats.VOLCANIC_INGOT, BaseMats.TUNGSTEN_INGOT
        }).register(galactifun);
        new DiamondAnvil(DIAMOND_ANVIL, new ItemStack[] {
                BaseMats.ULTRA_DUTY_SHEET, new ItemStack(Material.GLASS), BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.DIAMOND_ANVIL_CELL, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, new ItemStack(Material.ANVIL), BaseMats.ULTRA_DUTY_SHEET
        }).setCapacity(2048).setEnergyConsumption(512).setProcessingSpeed(1).register(galactifun);

        new OxygenSealer(OXYGEN_SEALER, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.FAN_BLADE, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.REINFORCED_CHANNEL, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.OXYGEN_REGENERATOR, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }, 1000).register(galactifun);

        new SlimefunItem(CoreItemGroup.ITEMS, SUPER_FAN, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.FAN_BLADE, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.VENTSTONE, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }).register(galactifun);

        new AutomaticDoor(AUTOMATIC_DOOR, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, new ItemStack(Material.OBSERVER), BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, SlimefunItems.BLOCK_PLACER, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }).register(galactifun);

        new ForcefieldGenerator(ENVIRONMENTAL_FORCEFIELD_GENERATOR, new ItemStack[] {
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.ENDER_BLOCK, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, ION_DISPERSER_1, BaseMats.ALUMINUM_COMPOSITE_SHEET,
                BaseMats.ALUMINUM_COMPOSITE_SHEET, BaseMats.SPACE_GRADE_PLATE, BaseMats.ALUMINUM_COMPOSITE_SHEET
        }).register(galactifun);

        new FusionReactor(FUSION_REACTOR, new ItemStack[] {
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.LASERITE, BaseMats.LASERITE, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.LASERITE, BaseMats.FUSION_PELLET, BaseMats.FUSION_PELLET, BaseMats.LASERITE, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.LASERITE, BaseMats.FUSION_PELLET, BaseMats.FUSION_PELLET, BaseMats.LASERITE, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.LASERITE, BaseMats.LASERITE, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET,
        }).register(galactifun);

        new AtmosphericHarvester(ATMOSPHERIC_HARVESTER, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, BaseMats.FAN_BLADE, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.SPACE_GRADE_PLATE, null, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.SPACE_GRADE_PLATE, BaseMats.SPACE_GRADE_PLATE, BaseMats.SPACE_GRADE_PLATE
        }).register(galactifun);

        new IonRocket(ION_ROCKET, new ItemStack[] {
                null, null, BaseMats.NOSE_CONE, BaseMats.NOSE_CONE, null, null,
                null, null, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ULTRA_DUTY_SHEET, null, null,
                null, BaseMats.HEAVY_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, null,
                BaseMats.ULTRA_DUTY_SHEET, FUSION_REACTOR, BaseMats.LIFE_SUPPORT_MODULE, BaseMats.LIFE_SUPPORT_MODULE, SlimefunItems.ENERGIZED_CAPACITOR, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.FUEL_TANK_2, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK, BaseMats.FUEL_TANK_2, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ULTRA_DUTY_SHEET, null, BaseMats.ION_ENGINE, BaseMats.ION_ENGINE, null, BaseMats.ULTRA_DUTY_SHEET
        }, 500, 18).register(galactifun);

        MachineBlock chemicalReactor = new MachineBlock(CoreItemGroup.MACHINES, CHEMICAL_REACTOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, null, BaseMats.SPACE_GRADE_PLATE,
                BaseMats.ULTRA_DUTY_SHEET, BaseMats.BLISTERING_VOLCANIC_INGOT, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT
        });

        chemicalReactor.addRecipe(new ItemStack(Material.WATER_BUCKET), Gas.WATER.item(), new ItemStack(Material.BUCKET));
        chemicalReactor.addRecipe(Gas.WATER.item(), Gas.OXYGEN.item(), Gas.HYDROGEN.item().asQuantity(2));

        chemicalReactor.addRecipe(Gas.CARBON_DIOXIDE.item(), SlimefunItems.CARBON, Gas.OXYGEN.item().asQuantity(2));
        chemicalReactor.addRecipe(Gas.METHANE.item(), SlimefunItems.CARBON, Gas.HYDROGEN.item().asQuantity(4));
        chemicalReactor.addRecipe(Gas.HYDROCARBONS.item(), Gas.METHANE.item().asQuantity(6));
        chemicalReactor.addRecipe(SlimefunItems.OIL_BUCKET, Gas.HYDROCARBONS.item(), new ItemStack(Material.BUCKET));

        chemicalReactor.addRecipe(Gas.AMMONIA.item(), Gas.NITROGEN.item(), Gas.HYDROGEN.item().asQuantity(3));

        chemicalReactor.energyCapacity(512);
        chemicalReactor.energyPerTick(128);
        chemicalReactor.ticksPerOutput(20);
        chemicalReactor.register(galactifun);

        new Electrolyzer(ELECTROLYZER, new ItemStack[] {
                BaseMats.SPACE_GRADE_PLATE, null, BaseMats.SPACE_GRADE_PLATE,
                SlimefunItems.SILVER_INGOT, BaseMats.BLISTERING_VOLCANIC_INGOT, SlimefunItems.SILVER_INGOT,
                BaseMats.ADVANCED_PROCESSING_UNIT, BaseMats.ULTRA_DUTY_SHEET, BaseMats.ADVANCED_PROCESSING_UNIT
        }).register(galactifun);

        // relics
        new Relic(BROKEN_SOLAR_PANEL_RELIC, new Relic.RelicSettings()
                .addRequired(SlimefunItems.SILICON, 2, 5)
                .addRequired(SlimefunItems.SOLAR_PANEL, 1, 3)
                .addOptional(SlimefunItems.SOLAR_GENERATOR, 0.20f)
                .addOptional(SlimefunItems.SOLAR_GENERATOR_2, 0.10f)
                .addOptional(SlimefunItems.SOLAR_GENERATOR_3, 0.02f),
                Galactifun.worldManager().alienWorlds().stream()
                        .filter(a -> a.getSetting("generate-fallen-satellites", Boolean.class, true))
                        .toArray(AlienWorld[]::new)).register(galactifun);

        new Relic(FALLEN_SATELLITE_RELIC, new Relic.RelicSettings()
                .addRequired(BaseMats.HEAVY_DUTY_SHEET, 3, 4)
                .addRequired(BaseMats.SPACE_GRADE_PLATE, 1, 3)
                .addRequired(SlimefunItems.BASIC_CIRCUIT_BOARD, 0, 2)
                .addOptional(SlimefunItems.ADVANCED_CIRCUIT_BOARD, 0.15f)
                .addOptional(BaseMats.ADVANCED_PROCESSING_UNIT, 0.10f),
                Galactifun.worldManager().alienWorlds().stream()
                        .filter(a -> a.getSetting("generate-fallen-satellites", Boolean.class, true))
                        .toArray(AlienWorld[]::new)).register(galactifun);

        new TechnologicalSalvager(TECHNOLOGICAL_SALVAGER, new ItemStack[] {
                BaseMats.ULTRA_DUTY_SHEET, null, BaseMats.ULTRA_DUTY_SHEET,
                BaseMats.ADVANCED_PROCESSING_UNIT, null, BaseMats.ADVANCED_PROCESSING_UNIT,
                BaseMats.ULTRA_DUTY_SHEET, new ItemStack(Material.STICKY_PISTON), BaseMats.ULTRA_DUTY_SHEET
        }).register(galactifun);
    }

}
