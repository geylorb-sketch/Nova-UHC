package net.novauhc.dandadan.roles.jiji;

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

public class JijiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIJI_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability pouvoirs = new PouvoirSpiralesAbility();

    // Passif Adaptation → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIJI_PASSIVE_ADAPTATION_NAME", type = VariableType.ABILITY)
    private Ability adaptationPassive = new AdaptationPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "JIJI_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideJiji = new EspaceVideJijiAbility();
public JijiRole() {
    }

    @Override public int getId()                { return 14; }
    @Override public String getName()           { return "Jiji"; }
    @Override public Material getIconMaterial() { return Material.NETHER_STAR; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt.JIJI_DESC, player);
    }

    // ── Passif Adaptation ────────────────────────────────────

    // ── Pouvoir des Spirales (selon slot hotbar) ─────────────
}
