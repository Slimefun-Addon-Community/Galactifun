package io.github.addoncommunity.galactifun.base.aliens;

import javax.annotation.Nonnull;

import org.bukkit.entity.Evoker;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Spellcaster;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpellCastEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.addoncommunity.galactifun.api.aliens.Alien;
import io.github.addoncommunity.galactifun.api.aliens.BossAlien;
import io.github.addoncommunity.galactifun.api.aliens.BossBarStyle;

/**
 * Class for the Titan king, the boss bound on Titan
 *
 * @author Seggan
 */
public final class TitanKing extends BossAlien<Evoker> {

    private final Alien<?> leech;

    public TitanKing(String id, String name, double maxHealth, double spawnChance, BossBarStyle style, Alien<?> leech) {
        super(Evoker.class, id, name, maxHealth, spawnChance, style);
        this.leech = leech;
    }

    @Override
    public void onCastSpell(@Nonnull EntitySpellCastEvent e) {
        Mob entity = e.getEntity();
        if (e.getSpell() == Spellcaster.Spell.SUMMON_VEX) {
            for (int i = 0; i < 3; i++) {
                this.leech.spawn(entity.getLocation(), entity.getWorld()).setTarget(entity.getTarget());
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
    protected void onBossHit(@Nonnull EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            e.setCancelled(true);
        }
    }

}
