package net.novauhc.dandadan.roles.flatwoods;

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

public class MonstreFlatwoodsRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability fumee = new FumeeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability sumoForme = new SumoFormeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability sumoCmd = new SumoCommand();

    // Passif → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "FLATWOODS_PASSIVE_NAME", type = VariableType.ABILITY)
    private Ability flatwoodsPassive = new FlatwoodsPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "FLATWOODS_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideFlatwoods = new EspaceVideFlatwoodsAbility();
public MonstreFlatwoodsRole() {
    }

    @Override public int getId()                { return 15; }
    @Override public String getName()           { return "Monstre de Flatwoods"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.FLATWOODS_DESC, player);
    }

    // ── Passif : 15% KB amplifié sous 2❤ ─────────────────────

    // ── Fumée (compteur 0-5) ──────────────────────────────────

    // ── Sumo Forme (attaquant/défensif) ───────────────────────

    // ── /ddd sumo ────────────────────────────────────────────
}
