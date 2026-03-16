package net.novauhc.dandadan.roles.minotaure;

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

public class MinotaureRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability durabilite = new DurabiliteImmenAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability kungFu = new KungFuAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability fer = new FerCommand();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MINOTAURE_PASSIVE_OXYDATION_NAME", type = VariableType.ABILITY)
    private Ability oxydationPassive = new OxydationPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "MINOTAURE_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideMinotaure = new EspaceVideMinotaureAbility();
public MinotaureRole() {
    }

    @Override public int getId()                { return 20; }
    @Override public String getName()           { return "Minotaure"; }
    @Override public Material getIconMaterial() { return Material.IRON_CHESTPLATE; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt2.MINO_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLangExt2.MINO_KILL_BONUS, bp);
    }

    // Passif Oxydation : 80% réduction feu

    // Durabilité Immense

    // Kung-Fu

    // /ddd fer
}

// ════════════════════════════════════════════
//  Umbrella Boy (id 21) — dans le même fichier pour compacité
// ════════════════════════════════════════════
