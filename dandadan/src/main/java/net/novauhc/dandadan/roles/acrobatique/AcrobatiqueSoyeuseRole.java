package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AcrobatiqueSoyeuseRole extends DanDaDanRole {

    public enum Upgrade { NONE, CHEVEUX, CORPS, JAMBES, PIED }
    private Upgrade chosen = Upgrade.NONE;
    private double speedStack = 0; // pour amélioration PIED
    private int corpsCounter = 0;  // pour amélioration CORPS

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability cheveux = new CheveuxAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability transformation = new TransformationAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability heat = new HeatCommand();

    // Passif Acrobate → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATIQUE_PASSIVE_ACROBATE_NAME", type = VariableType.ABILITY)
    private Ability acrobatePassive = new AcrobatePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "ACROBATIQUE_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideAcrobatique = new EspaceVideAcrobatiqueAbility();
public AcrobatiqueSoyeuseRole() {
    }

    @Override public int getId()                { return 19; }
    @Override public String getName()           { return "L'Acrobatique Soyeuse"; }
    @Override public Material getIconMaterial() { return Material.STRING; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt2.ACRO_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        // bonus speed PIED
        if (chosen == Upgrade.PIED) {
            speedStack = Math.min(0.40, speedStack + 0.02);
        }
    }

    public Upgrade getChosen() { return chosen; }
    public void setChosen(Upgrade u) { chosen = u; }
    public double getSpeedStack() { return speedStack; }
    public void decrementSpeed() { speedStack = Math.max(0, speedStack - 0.01); }
    public int getCorpsCounter() { return corpsCounter; }
    public void incrementCorps() { corpsCounter++; }
    public void resetCorps() { corpsCounter = 0; }

    // ── Passif Acrobate ───────────────────────────────────────

    // ── Cheveux (active) ──────────────────────────────────────

    // ── Transformation (choix amélioration) ───────────────────

    // ── /ddd heat ────────────────────────────────────────────
}
