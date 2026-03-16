package net.novauhc.dandadan.roles.oeilmalefique;

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

public class OeilMalefiqueRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEIL_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability malediction = new OeilMaledictionAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEIL_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability balle = new BalleRancuneAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEIL_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability transfo = new TransfoCommand();

    // Passifs → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEIL_PASSIVE_ENVIE_MEURTR_NAME", type = VariableType.ABILITY)
    private Ability envieMeurtrPassive = new EnvieMeurtrPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEIL_PASSIVE_JIJI_DETECT_NAME", type = VariableType.ABILITY)
    private Ability jijiDetectPassive = new JijiDetectPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "OEIL_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideOeil = new EspaceVideOeilAbility();
public OeilMalefiqueRole() {
    }

    @Override public int getId()                { return 13; }
    @Override public String getName()           { return "L'Œil maléfique"; }
    @Override public Material getIconMaterial() { return Material.EYE_OF_ENDER; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.OEIL_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        // Passif "Envie de meurtre" : notifie l'Œil des kills récents
        // géré dans EnvieMeurtrPassive via un flag static
        if (killer.getPlayer() != null)
            EnvieMeurtrPassive.markKill(killer.getPlayer().getName(), victim.getPlayer() != null ? victim.getPlayer().getName() : "?");
    }

    // ── Passif : Envie de meurtre ─────────────────────────────

    // ── Passif : Détection Jiji ───────────────────────────────

    // ── Malédiction (40% effets aléatoires) ──────────────────

    // ── Balle de rancune ──────────────────────────────────────

    // ── /ddd transfo ─────────────────────────────────────────
}
