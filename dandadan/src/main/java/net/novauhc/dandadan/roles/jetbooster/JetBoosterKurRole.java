package net.novauhc.dandadan.roles.jetbooster;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JetBoosterKurRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability aviate = new AviateExosuitAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability supercharge = new SuperchargeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability boost = new BoostAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability aband = new AbandCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_PASSIVE_TETE_HAUTE_NAME", type = VariableType.ABILITY)
    private Ability teteHautePassive = new TeteHautePassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JETBOOSTER_PASSIVE_ESQUIVE_NAME", type = VariableType.ABILITY)
    private Ability esquivePassive = new EsquivePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "JETBOOSTER_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideJetBooster = new EspaceVideJetBoosterAbility();
public JetBoosterKurRole() {
    }

    @Override public int getId()                { return 17; }
    @Override public String getName()           { return "Jet Booster Kur"; }
    @Override public Material getIconMaterial() { return Material.FEATHER; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.KUR_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLangExt.KUR_KILL_BONUS, bp);
    }

    // Passif Tête haute : voit la vie des joueurs

    // Passif Esquive : tous les 10 coups reçus → recul + annule dernier coup

    // Aviate Exosuit

    // Supercharge

    // Boost

    // /ddd aband
}
