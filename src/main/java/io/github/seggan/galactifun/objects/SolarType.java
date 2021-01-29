package io.github.seggan.galactifun.objects;

import org.bukkit.GameRule;
import org.bukkit.World;

import javax.annotation.Nonnull;

public enum SolarType {

    ETERNAL_DAY {
        @Override
        void apply(@Nonnull World world) {
            world.setTime(6000L); // need to check these values
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    },
    
    ETERNAL_NIGHT {
        @Override
        void apply(@Nonnull World world) {
            world.setTime(18000L); // need to check these values
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        }
    },
    
    NORMAL {
        @Override
        void apply(@Nonnull World world) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
        }
    };

    abstract void apply(@Nonnull World world);
    
}
