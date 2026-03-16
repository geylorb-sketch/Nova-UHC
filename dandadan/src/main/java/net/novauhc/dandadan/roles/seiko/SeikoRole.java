package net.novauhc.dandadan.roles.seiko;

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

public class SeikoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIER_MYSTIQUE_NAME", type = VariableType.ABILITY)
    private Ability barriereMystique = new BarriereMystiqueAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_ABILITY_BARRIER_INTERIEUR_NAME", type = VariableType.ABILITY)
    private Ability barriereInterieur = new BarriereInterieurAbility();

    // Passif pur → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "SEIKO_PASSIVE_DIEU_REGION_NAME", type = VariableType.ABILITY)
    private Ability dieuRegionPassive = new DieuRegionPassive();

    // Passif non-configurable → ajouté manuellement
    private final PouvoirDesMotsPassive pouvoirDesMots = new PouvoirDesMotsPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "SEIKO_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideSeiko = new EspaceVideSeikoAbility();
public SeikoRole() {
            getAbilities().add(pouvoirDesMots);
}

    @Override public int getId()                { return 4; }
    @Override public String getName()           { return "Seiko"; }
    @Override public Material getIconMaterial() { return Material.ENCHANTED_BOOK; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.SEIKO_DESC, player);
    }
}
