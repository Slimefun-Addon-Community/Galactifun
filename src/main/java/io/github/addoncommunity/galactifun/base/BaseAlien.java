package io.github.addoncommunity.galactifun.base;

import lombok.experimental.UtilityClass;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;

import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.aliens.AlienManager;
import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.addoncommunity.galactifun.api.aliens.BossBarStyle;
import io.github.addoncommunity.galactifun.base.aliens.Leech;
import io.github.addoncommunity.galactifun.base.aliens.Martian;
import io.github.addoncommunity.galactifun.base.aliens.MutantCreeper;
import io.github.addoncommunity.galactifun.base.aliens.Skywhale;
import io.github.addoncommunity.galactifun.base.aliens.TitanAlien;
import io.github.addoncommunity.galactifun.base.aliens.TitanKing;

@UtilityClass
public final class BaseAlien {

    public static final Alien<?> MUTANT_CREEPER = new MutantCreeper("MUTANT_CREEPER", "Mutant Creeper", 40, 40);
    public static final Alien<?> MARTIAN = new Martian("MARTIAN", "&4Martian", 32, 50);
    public static final Alien<?> LEECH = new Leech("LEECH", "&eLeech", 10, 1);
    public static final Alien<?> SKYWHALE = new Skywhale("SKYWHALE", "&fSkywhale", 100, 3);
    public static final Alien<?> TITAN = new TitanAlien("TITAN", "Titan", 32, 40);
    public static final BossAlien<?> TITAN_KING = new TitanKing("TITAN_KING", "Titan King", 300, 1,
            new BossBarStyle(BarColor.BLUE, BarStyle.SOLID, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY), LEECH);

    public static void setup(AlienManager alienManager) {
        MUTANT_CREEPER.register(alienManager);
        MARTIAN.register(alienManager);
        LEECH.register(alienManager);
        SKYWHALE.register(alienManager);
        TITAN_KING.register(alienManager);
        TITAN.register(alienManager);
    }

}
