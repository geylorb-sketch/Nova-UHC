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
import net.novaproject.ultimate.legend.roles.abilities.NecroSummonActive;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Necromancien extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "NECROMANCIEN_SUMMON_NAME", type = VariableType.ABILITY)
    public Ability summonActive;

    public Necromancien() {
        this.summonActive = new NecroSummonActive();
    }

    @Override public int getId() { return 7; }
    @Override public String getName() { return "Nécromancien"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.SKULL_ITEM); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_NECROMANCIEN; }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
    }
}