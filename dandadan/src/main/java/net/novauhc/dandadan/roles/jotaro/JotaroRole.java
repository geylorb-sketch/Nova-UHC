package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JotaroRole extends DanDaDanRole {

    private boolean standActive = false;
    private double timeFreeze   = 5.0;    // secondes disponibles
    private int    starFingerPierce = 0;  // coups qui passent la résistance
    private int    oraOraTarget = -1;
    private int    kills = 0;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability starFinger = new StarFingerAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability starPlatinum = new StarPlatinumAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability timeStop = new TimeStopAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability oraOra = new OraOraAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_PASSIVE_REACTION_NAME", type = VariableType.ABILITY)
    private Ability reactionPassive = new ReactionPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOTARO_PASSIVE_DETECTIVE_NAME", type = VariableType.ABILITY)
    private Ability detectivePassive = new DetectivePassive();

    // Passif non-configurable → ajouté manuellement
    private final CasquettePassive casquette = new CasquettePassive();

public JotaroRole() {
        setCamp(DanDaDanCamps.SOLO);
            getAbilities().add(casquette);
}

    @Override public int getId()                { return 29; }
    @Override public String getName()           { return "Jotaro"; }
    @Override public Material getIconMaterial() { return Material.NETHER_STAR; }
    @Override public String getDescription(Player player) { return LangManager.get().get(DanDaDanLangExt3.JOTARO_DESC, player); }

    @Override public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        kills++;
        timeFreeze = Math.min(9.0, timeFreeze + 0.5);
    }

    public boolean isStandActive() { return standActive; }
    public void setStandActive(boolean b) { standActive = b; }
    public double getTimeFreeze() { return timeFreeze; }
    public int getStarFingerPierce() { return starFingerPierce; }
    public void useStarFingerPierce() { if (starFingerPierce > 0) starFingerPierce--; }
    public void setStarFingerPierce(int n) { starFingerPierce = n; }

    // Passif Réaction

    // Passif Détective : voit le nom du yokai de chaque joueur

    // Star Finger

    // Star Platinum (Stand)

    // Arrêt du temps

    // Ora Ora
}

// ════════════════════════════════════════════
//  Dio (id 30)
// ════════════════════════════════════════════
