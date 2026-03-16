package net.novauhc.dandadan.roles.tsuchinoko;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TsuchinokoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability venin = new VeninAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability ondes = new OndesAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability suicide = new SuicideAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_REGEN", type = VariableType.ABILITY)
    private Ability regen = new RegenCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "TSUCHINOKO_PASSIVE_VER_MORT_NAME", type = VariableType.ABILITY)
    private Ability verMortPassive = new VerMortPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "TSUCHINOKO_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideTsuchinoko = new EspaceVideTsuchinokoAbility();
public TsuchinokoRole() {
    }

    @Override public int getId()                { return 12; }
    @Override public String getName()           { return "Tsuchinoko"; }
    @Override public Material getIconMaterial() { return Material.SLIME_BALL; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.TSUCHINOKO_DESC, player);
    }

    // ── Passif : Ver de la mort mongole ──────────────────────

    // ── Venin ─────────────────────────────────────────────────

    // ── Ondes psychiques ──────────────────────────────────────

    // ── Suicide ───────────────────────────────────────────────

    // ── Regen (commande) ──────────────────────────────────────
}
