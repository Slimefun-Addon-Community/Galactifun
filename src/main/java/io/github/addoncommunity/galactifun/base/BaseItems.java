package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

@UtilityClass
public final class BaseItems {

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
            GalactifunHeads.FAN,
            "&fFan Blade"
    );

    public static void setup() {
        createComponent(ALUMINUM_COMPOSITE, RecipeType.SMELTERY,
                SlimefunItems.ALUMINUM_INGOT, SlimefunItems.MAGNESIUM_DUST, SlimefunItems.ZINC_DUST,
                SlimefunItems.TIN_DUST, SlimefunItems.ALUMINUM_DUST
        );
        createComponent(TUNGSTEN, RecipeType.SMELTERY, GalactifunItems.FALLEN_METEOR);
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
        
    }

    private static void createComponent(SlimefunItemStack item, RecipeType type, ItemStack... recipe) {
        new SlimefunItem(CoreCategories.COMPONENTS, item, type, Arrays.copyOf(recipe, 9)).register(Galactifun.getInstance());
    }

    private static void createComponent(SlimefunItemStack item, RecipeType type, int output, ItemStack... recipe) {
        new SlimefunItem(CoreCategories.COMPONENTS, item, type, Arrays.copyOf(recipe, 9), new SlimefunItemStack(item, output)).register(Galactifun.getInstance());
    }

    private static void createAssembly(SlimefunItemStack item, ItemStack[] recipe) {
        // TODO add a cheat category for assembly table
        new SlimefunItem(CoreCategories.MAIN_CATEGORY, item, RecipeTypes.ASSEMBLY_TABLE, recipe).register(Galactifun.getInstance());
    }

}
