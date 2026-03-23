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
import net.novaproject.ultimate.legend.roles.abilities.MedecinHealPassive;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Medecin extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "MEDECIN_HEAL_NAME", type = VariableType.ABILITY)
    public Ability healPassive;

    public Medecin() {
        this.healPassive = new MedecinHealPassive();
    }

    @Override public int getId() { return 14; }
    @Override public String getName() { return "Médecin"; }
    @Override public Material getIconMaterial() { return Material.POTION; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.POTION); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_MEDECIN; }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, false, false));
    }
}