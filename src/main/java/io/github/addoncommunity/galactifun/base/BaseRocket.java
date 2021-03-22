package io.github.addoncommunity.galactifun.base;

import io.github.addoncommunity.galactifun.Galactifun;
import io.github.addoncommunity.galactifun.api.items.Rocket;
import io.github.addoncommunity.galactifun.base.items.Components;
import io.github.addoncommunity.galactifun.core.categories.CoreCategories;
import lombok.experimental.UtilityClass;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public final class BaseRocket {

    private static final int TIER_ONE_FUEL = 10;
    private static final int TIER_ONE_STORAGE = 9;
    private static final int TIER_TWO_FUEL = 100;
    private static final int TIER_TWO_STORAGE = 18;
    private static final int TIER_THREE_FUEL = 500;
    private static final int TIER_THREE_STORAGE = 36;

    public static final SlimefunItemStack TIER_ONE = new SlimefunItemStack(
            "ROCKET_TIER_ONE",
            GalactifunHeads.ROCKET,
            "&4Rocket Tier 1",
            "",
            "&7Fuel Capacity: " + TIER_ONE_FUEL,
            "&7Cargo Capacity: " + TIER_ONE_STORAGE
    );
    public static final SlimefunItemStack TIER_TWO = new SlimefunItemStack(
            "ROCKET_TIER_TWO",
            GalactifunHeads.ROCKET,
            "&4Rocket Tier 2",
            "",
            "&7Fuel Capacity: " + TIER_TWO_FUEL,
            "&7Cargo Capacity: " + TIER_TWO_STORAGE
    );
    public static final SlimefunItemStack TIER_THREE = new SlimefunItemStack(
            "ROCKET_TIER_THREE",
            GalactifunHeads.ROCKET,
            "&4Rocket Tier 3",
            "",
            "&7Fuel Capacity: " + TIER_THREE_FUEL,
            "&7Cargo Capacity: " + TIER_THREE_STORAGE
    );

    public static void setup(Galactifun galactifun) {
        new Rocket(CoreCategories.MAIN_CATEGORY, TIER_ONE, RecipeTypes.ASSEMBLY_TABLE, new ItemStack[] {
                null, null, Components.NOSE_CONE.getItem(), Components.NOSE_CONE.getItem(), null, null,
                null, null, Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), null, null,
                null, Components.HEAVY_DUTY_SHEET.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), null,
                Components.HEAVY_DUTY_SHEET.getItem(), Components.FUEL_TANK.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.FUEL_TANK.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
                Components.HEAVY_DUTY_SHEET.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
                Components.HEAVY_DUTY_SHEET.getItem(), null, Components.ROCKET_ENGINE.getItem(), Components.ROCKET_ENGINE.getItem(), null, Components.HEAVY_DUTY_SHEET.getItem()
        }, TIER_ONE_FUEL, TIER_ONE_STORAGE).register(galactifun);
        new Rocket(CoreCategories.MAIN_CATEGORY, TIER_TWO, RecipeTypes.ASSEMBLY_TABLE, new ItemStack[] {
                null, null, Components.NOSE_CONE.getItem(), Components.NOSE_CONE.getItem(), null, null,
                null, null, Components.HEAVY_DUTY_SHEET.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), null, null,
                null, Components.HEAVY_DUTY_SHEET.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.HEAVY_DUTY_SHEET.getItem(), null,
                Components.HEAVY_DUTY_SHEET.getItem(), Components.FUEL_TANK.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.FUEL_TANK.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
                Components.HEAVY_DUTY_SHEET.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.HEAVY_DUTY_SHEET.getItem(),
                Components.HEAVY_DUTY_SHEET.getItem(), null, Components.ROCKET_ENGINE_2.getItem(), Components.ROCKET_ENGINE_2.getItem(), null, Components.HEAVY_DUTY_SHEET.getItem()
        }, TIER_TWO_FUEL, TIER_TWO_STORAGE).register(galactifun);
        new Rocket(CoreCategories.MAIN_CATEGORY, TIER_THREE, RecipeTypes.ASSEMBLY_TABLE, new ItemStack[] {
                null, null, Components.NOSE_CONE.getItem(), Components.NOSE_CONE.getItem(), null, null,
                null, null, Components.ULTRA_DUTY_SHEET.getItem(), Components.ULTRA_DUTY_SHEET.getItem(), null, null,
                null, Components.HEAVY_DUTY_SHEET.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.ADVANCED_PROCESSING_UNIT.getItem(), Components.ULTRA_DUTY_SHEET.getItem(), null,
                Components.ULTRA_DUTY_SHEET.getItem(), Components.FUEL_TANK_2.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.LIFE_SUPPORT_MODULE.getItem(), Components.FUEL_TANK_2.getItem(), Components.ULTRA_DUTY_SHEET.getItem(),
                Components.ULTRA_DUTY_SHEET.getItem(), Components.FUEL_TANK_2.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK.getItem(), Components.FUEL_TANK_2.getItem(), Components.ULTRA_DUTY_SHEET.getItem(),
                Components.ULTRA_DUTY_SHEET.getItem(), null, Components.ROCKET_ENGINE_3.getItem(), Components.ROCKET_ENGINE_3.getItem(), null, Components.ULTRA_DUTY_SHEET.getItem()
        }, TIER_THREE_FUEL, TIER_THREE_STORAGE).register(galactifun);
    }

}
