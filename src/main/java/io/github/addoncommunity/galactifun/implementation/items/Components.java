package io.github.addoncommunity.galactifun.implementation.items;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.implementation.lists.Categories;
import io.github.addoncommunity.galactifun.implementation.lists.GalactifunItems;
import io.github.addoncommunity.galactifun.implementation.lists.Heads;
import io.github.addoncommunity.galactifun.implementation.lists.RecipeTypes;
import io.github.addoncommunity.galactifun.util.ItemChoice;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.items.blocks.UnplaceableBlock;
import lombok.AccessLevel;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@Getter
public enum Components {
    ALUMINUM_COMPOSITE_SHEET("Aluminum Composite Sheet", new ItemChoice(Material.PAPER), RecipeType.COMPRESSOR, new ItemStack[]{
        new SlimefunItemStack(Metals.ALUMINUM_COMPOSITE.getItem(), 8), null, null,
        null, null, null,
        null, null, null
    }),
    HEAVY_DUTY_SHEET("Heavy Duty Sheet", new ItemChoice(Material.PAPER), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem(),
        ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem(),
        ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem(), ALUMINUM_COMPOSITE_SHEET.getItem()
    }),
    SPACE_GRADE_PLATE("Space Grade Plate", new ItemChoice(Material.PAPER), RecipeType.COMPRESSOR, new ItemStack[]{
        ALUMINUM_COMPOSITE_SHEET.getItem(), Metals.TUNGSTEN.getItem(), null,
        null, null, null,
        null, null, null
    }),
    ULTRA_DUTY_SHEET("Ultra Duty Sheet", new ItemChoice(Material.PAPER), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SPACE_GRADE_PLATE.getItem(), SPACE_GRADE_PLATE.getItem(), null,
        SPACE_GRADE_PLATE.getItem(), SPACE_GRADE_PLATE.getItem(), null,
        null, null, null
    }),
    GOLD_FOIL("&6Gold Foil", new ItemChoice(Material.PAPER), RecipeType.COMPRESSOR, new ItemStack[]{
        SlimefunItems.GOLD_24K_BLOCK, null, null,
        null, null, null,
        null, null, null
    }, 4),
    REINFORCED_CHANNEL("Reinforced Channel", new ItemChoice(Material.BAMBOO), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        ALUMINUM_COMPOSITE_SHEET.getItem(), null, ALUMINUM_COMPOSITE_SHEET.getItem(),
        ALUMINUM_COMPOSITE_SHEET.getItem(), null, ALUMINUM_COMPOSITE_SHEET.getItem(),
        ALUMINUM_COMPOSITE_SHEET.getItem(), null, ALUMINUM_COMPOSITE_SHEET.getItem(),
    }, 8),
    FAN_BLADE("Fan Blade", new ItemChoice(Heads.FAN.getAsItemStack()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        null, SlimefunItems.STEEL_INGOT, null,
        SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
        null, SlimefunItems.STEEL_INGOT, null
    }),
    NOZZLE("Nozzle", new ItemChoice(Material.IRON_TRAPDOOR), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, null, SlimefunItems.STEEL_INGOT,
        null, new ItemStack(Material.IRON_TRAPDOOR), null
    }, 2),
    AIR_FILTER("Air Filter", new ItemChoice(Material.PAPER), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
        SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
        SlimefunItems.CLOTH, new ItemStack(Material.CHARCOAL), SlimefunItems.CLOTH,
    }),
    OXYGEN_REGENERATOR("&bOxygen Regenerator", new ItemChoice(Heads.OXYGEN_REGENERATOR.getAsItemStack()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL.getItem(), AIR_FILTER.getItem(),
        NOZZLE.getItem(), GOLD_FOIL.getItem(), AIR_FILTER.getItem(),
        SlimefunItems.ELECTRO_MAGNET, REINFORCED_CHANNEL.getItem(), AIR_FILTER.getItem()
    }),
    SPARK_PLUG("Spark Plug", new ItemChoice(Material.FLINT_AND_STEEL), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
        SlimefunItems.ALUMINUM_INGOT, null, GalactifunItems.MUNPOWDER,
        null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
    }),
    SPARK_PLUG_2("Spark Plug Mk 2", new ItemChoice(Material.FLINT_AND_STEEL), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT,
        Metals.TUNGSTEN.getItem(), null, GalactifunItems.MUNPOWDER,
        null, SlimefunItems.STEEL_PLATE, SlimefunItems.NICKEL_INGOT
    }),
    ROCKET_ENGINE("Rocket Engine", new ItemChoice(Material.FLINT_AND_STEEL), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null,
        null, null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null, null,
        null, Circuits.DIAMOND.getItem(), NOZZLE.getItem(), NOZZLE.getItem(), Circuits.DIAMOND.getItem(), null,
        null, SlimefunItems.REINFORCED_PLATE, new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FLINT_AND_STEEL), SlimefunItems.REINFORCED_PLATE, null,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
    }),
    ROCKET_ENGINE_2("Rocket Engine Mk 2", new ItemChoice(Material.FLINT_AND_STEEL), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null,
        null, null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null, null,
        null, Circuits.DIAMOND.getItem(), NOZZLE.getItem(), NOZZLE.getItem(), Circuits.DIAMOND.getItem(), null,
        null, SlimefunItems.REINFORCED_PLATE, SPARK_PLUG.getItem(), SPARK_PLUG.getItem(), SlimefunItems.REINFORCED_PLATE, null,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
        SlimefunItems.REINFORCED_PLATE, null, null, null, null, SlimefunItems.REINFORCED_PLATE,
    }),
    ROCKET_ENGINE_3("Rocket Engine Mk 3", new ItemChoice(Material.FLINT_AND_STEEL), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null,
        null, null, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), null, null,
        null, Circuits.DIAMOND.getItem(), NOZZLE.getItem(), NOZZLE.getItem(), Circuits.DIAMOND.getItem(), null,
        null, SPACE_GRADE_PLATE.getItem(), SPARK_PLUG_2.getItem(), SPARK_PLUG_2.getItem(), SPACE_GRADE_PLATE.getItem(), null,
        SPACE_GRADE_PLATE.getItem(), null, null, null, null, SPACE_GRADE_PLATE.getItem(),
        SPACE_GRADE_PLATE.getItem(), null, null, null, null, SPACE_GRADE_PLATE.getItem(),
    }),
    ADVANCED_PROCESSING_UNIT("&4Advanced Processing Unit", new ItemChoice(Heads.CORE.getAsItemStack()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        Circuits.REDSTONE.getItem(), Circuits.GLOWSTONE.getItem(), Circuits.REDSTONE.getItem(),
        Circuits.DIAMOND.getItem(), SlimefunItems.ADVANCED_CIRCUIT_BOARD, Circuits.DIAMOND.getItem(),
        Circuits.REDSTONE.getItem(), Circuits.LAPIS.getItem(), Circuits.REDSTONE.getItem()
    }),
    LIFE_SUPPORT_MODULE("&4Life Support Module", new ItemChoice(Heads.LIFE_SUPPORT_MODULE.getAsItemStack()), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), ADVANCED_PROCESSING_UNIT.getItem(), SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, Circuits.LAPIS.getItem(), OXYGEN_REGENERATOR.getItem(), OXYGEN_REGENERATOR.getItem(), Circuits.LAPIS.getItem(), SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, Circuits.LAPIS.getItem(), OXYGEN_REGENERATOR.getItem(), OXYGEN_REGENERATOR.getItem(), Circuits.LAPIS.getItem(), SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, ADVANCED_PROCESSING_UNIT.getItem(), REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), ADVANCED_PROCESSING_UNIT.getItem(), SlimefunItems.STEEL_INGOT,
        SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT, REINFORCED_CHANNEL.getItem(), REINFORCED_CHANNEL.getItem(), SlimefunItems.STEEL_INGOT, SlimefunItems.STEEL_INGOT,
    }),
    NOSE_CONE("Nose Cone", new ItemChoice(Material.REDSTONE_TORCH), RecipeTypes.ASSEMBLY_TABLE, new ItemStack[]{
        null, null, new ItemStack(Material.REDSTONE_TORCH), new ItemStack(Material.REDSTONE_TORCH), null, null,
        null, null, Metals.ALUMINUM_COMPOSITE.getItem(), Metals.ALUMINUM_COMPOSITE.getItem(), null, null,
        null, Metals.ALUMINUM_COMPOSITE.getItem(), Circuits.GLOWSTONE.getItem(), Circuits.GLOWSTONE.getItem(), Metals.ALUMINUM_COMPOSITE.getItem(), null,
        Metals.ALUMINUM_COMPOSITE.getItem(), null, null, null, null, Metals.ALUMINUM_COMPOSITE.getItem(),
        Metals.ALUMINUM_COMPOSITE.getItem(), null, null, null, null, Metals.ALUMINUM_COMPOSITE.getItem(),
        Metals.ALUMINUM_COMPOSITE.getItem(), null, null, null, null, Metals.ALUMINUM_COMPOSITE.getItem(),
    }),
    FUEL_TANK("&6Fuel Tank", new ItemChoice(Heads.CAN.getAsItemStack()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        HEAVY_DUTY_SHEET.getItem(), HEAVY_DUTY_SHEET.getItem(), HEAVY_DUTY_SHEET.getItem(),
        HEAVY_DUTY_SHEET.getItem(), null, HEAVY_DUTY_SHEET.getItem(),
        HEAVY_DUTY_SHEET.getItem(), HEAVY_DUTY_SHEET.getItem(), HEAVY_DUTY_SHEET.getItem()
    }),
    FUEL_TANK_2("&6Fuel Tank Mk 2", new ItemChoice(Heads.CAN.getAsItemStack()), RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
        ULTRA_DUTY_SHEET.getItem(), ULTRA_DUTY_SHEET.getItem(), ULTRA_DUTY_SHEET.getItem(),
        ULTRA_DUTY_SHEET.getItem(), null, ULTRA_DUTY_SHEET.getItem(),
        ULTRA_DUTY_SHEET.getItem(), ULTRA_DUTY_SHEET.getItem(), ULTRA_DUTY_SHEET.getItem()
    }),
    ;
    private final String name;
    private final RecipeType recipeType;
    private final ItemStack[] recipe;
    private final SlimefunItemStack item;
    @Getter(AccessLevel.NONE)
    private final int amount;

    @ParametersAreNonnullByDefault
    Components(String name, ItemChoice material, RecipeType recipeType, ItemStack[] recipe, int amount) {
        Validate.notNull(name);
        Validate.notNull(material);
        Validate.notNull(recipeType);
        Validate.notNull(recipe);

        // makes name white if no color codes
        if (name.contains("&")) {
            this.name = ChatColor.translateAlternateColorCodes('&', name);
        } else {
            this.name = ChatColor.translateAlternateColorCodes('&', "&f" + name);
        }

        this.recipeType = recipeType;
        this.recipe = recipe;
        this.item = new SlimefunItemStack(
            this.name(),
            material.getItem(),
            this.name,
            "",
            "&7A component in construction"
        );
        this.amount = amount;
    }

    @ParametersAreNonnullByDefault
    Components(@Nonnull String name, @Nonnull ItemChoice material, @Nonnull RecipeType recipeType, @Nonnull ItemStack[] recipe) {
        this(name, material, recipeType, recipe, 1);
    }

    public static void setup(@Nonnull Galactifun addon) {
        for (Components component : Components.values()) {
            SlimefunItem item = new UnplaceableBlock(Categories.COMPONENTS, component.item, component.recipeType, component.recipe, new SlimefunItemStack(component.item, component.amount));
            item.register(addon);
            if (component.recipeType.equals(RecipeTypes.ASSEMBLY_TABLE)) {
                item.setHidden(true);
            }
        }
    }
}
