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
import net.novaproject.ultimate.legend.roles.abilities.MarionnettistePuppetPassive;
import org.bukkit.Material;
import java.util.UUID;

public class Marionnettiste extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "MARIONNETTISTE_PUPPET_NAME", type = VariableType.ABILITY)
    public Ability puppetPassive;

    public Marionnettiste() {
        this.puppetPassive = new MarionnettistePuppetPassive();
        getAbilities().add(puppetPassive);
    }

    @Override public int getId() { return 17; }
    @Override public String getName() { return "Marionnettiste"; }
    @Override public Material getIconMaterial() { return Material.STRING; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.STRING); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_MARIONNETTISTE; }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        if (getOwner() != null && getOwner().equals(killer)) {
            ((MarionnettistePuppetPassive) puppetPassive).createPuppet(victim, killer);
        }
    }

    public boolean isPuppet(UUID uuid) {
        return ((MarionnettistePuppetPassive) puppetPassive).isPuppet(uuid);
    }
}