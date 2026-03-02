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
import net.novaproject.ultimate.legend.roles.abilities.NainArmorActive;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Nain extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "NAIN_ARMOR_NAME", type = VariableType.ABILITY)
    public Ability armorActive;

    public Nain() {
        this.armorActive = new NainArmorActive();
        getAbilities().add(armorActive);
    }

    @Override public int getId() { return 5; }
    @Override public String getName() { return "Nain"; }
    @Override public Material getIconMaterial() { return Material.GOLD_PICKAXE; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLD_PICKAXE); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_NAIN; }

    @Override
    public void onGive(UHCPlayer u) {
        super.onGive(u); Player p = u.getPlayer(); if (p == null) return;
        p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0, false, false));
    }
}