package net.novaproject.ultimate.legend.roles.abilities;

import net.novaproject.novauhc.ability.template.UseAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class DragonFireballActive extends UseAbility {

    @AbilityVariable(lang = ScenarioVarLang.class, nameKey = "DRAGON_FIREBALL_YIELD_NAME", descKey = "DRAGON_FIREBALL_YIELD_DESC", type = VariableType.DOUBLE)
    private double yield = 1.5;

    public DragonFireballActive() { setCooldown(300); setMaxUse(-1); }

    @Override public String getName() { return "Boule de Feu"; }
    @Override public Material getMaterial() { return Material.FIREBALL; }

    @Override
    public boolean onEnable(Player player) {
        Fireball fb = player.launchProjectile(Fireball.class);
        fb.setYield((float) yield); fb.setIsIncendiary(true);
        return true;
    }
}
