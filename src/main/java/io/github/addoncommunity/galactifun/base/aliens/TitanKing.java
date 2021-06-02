package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Spellcaster;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.addoncommunity.galactifun.api.aliens.BossBarStyle;
import io.github.addoncommunity.galactifun.base.BaseRegistry;

/**
 * Class for the Titan king, the boss bound on Titan
 *
 * @author Seggan
 */
public final class TitanKing extends BossAlien {

    public TitanKing() {
        super("TITAN_KING", "Titan King", EntityType.EVOKER);
    }

    @Override
    public void onCastSpell(@Nonnull EntitySpellCastEvent e) {
        Mob entity = e.getEntity();
        if (e.getSpell() == Spellcaster.Spell.SUMMON_VEX) {
            for (int i = 0; i < 3; i++) {
                ((Mob) BaseRegistry.LEECH.spawn(entity.getLocation(), entity.getWorld())).setTarget(entity.getTarget());
            }
        } else if (e.getSpell() == Spellcaster.Spell.FANGS) {
            entity.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY,
                100,
                1,
                false,
                false
            ));
        }
    }

    @Override
    protected int getMaxHealth() {
        return 300;
    }


    @Override
    protected void onBossHit(@Nonnull EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            e.setCancelled(true);
        }
    }

    @Nonnull
    @Override
    protected BossBarStyle createBossBarStyle() {
        return new BossBarStyle("Titan King", BarColor.BLUE, BarStyle.SOLID, BarFlag.CREATE_FOG, BarFlag.DARKEN_SKY);
    }

    @Override
    protected int getSpawnChance() {
        return 1;
    }
    
}
