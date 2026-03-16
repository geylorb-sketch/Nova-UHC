package net.novauhc.dandadan.roles.kashimoto;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KashimotoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_FLAME_NAME", type = VariableType.ABILITY)
    private Ability flammeGlace = new FlammeGlaceAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_ESPRIT_NAME", type = VariableType.ABILITY)
    private Ability espritProtecteur = new EspritProtecteurAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_APHOOM_NAME", type = VariableType.ABILITY)
    private Ability aphoomZhah = new AphoomZhahAbility();

    // Passif Protecteur → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_PASSIVE_PROTECTEUR_NAME", type = VariableType.ABILITY)
    private Ability protecteurPassive = new ProtecteurPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "KASHIMOTO_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideKashimoto = new EspaceVideKashimotoAbility();
public KashimotoRole() {
    }

    @Override public int getId()                { return 6; }
    @Override public String getName()           { return "Kashimoto"; }
    @Override public Material getIconMaterial() { return Material.ICE; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.KASHIMOTO_DESC, player);
    }
}
