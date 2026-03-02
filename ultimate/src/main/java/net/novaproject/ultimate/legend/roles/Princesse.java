package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.PrincesseNoFallPassive;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Princesse extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "PRINCESSE_EXTRA_HEARTS_NAME", descKey = "PRINCESSE_EXTRA_HEARTS_DESC", type = VariableType.INTEGER)
    private int extraHearts = 4;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "PRINCESSE_NOFALL_NAME", type = VariableType.ABILITY)
    public Ability noFallPassive;

    public Princesse() {
        this.noFallPassive = new PrincesseNoFallPassive();
        getAbilities().add(noFallPassive);
    }

    @Override public int getId() { return 10; }
    @Override public String getName() { return "Princesse"; }
    @Override public Material getIconMaterial() { return Material.GOLD_HELMET; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLD_HELMET); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_PRINCESSE; }
    @Override protected String applyPlaceholders(String d) { return d.replace("%extra_hearts%", String.valueOf(extraHearts / 2)); }
    public boolean hasNoFall() { return true; }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.setMaxHealth(20 + extraHearts); p.setHealth(20 + extraHearts);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0, false, false));
    }
}