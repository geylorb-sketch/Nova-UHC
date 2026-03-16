package net.novauhc.dandadan.roles.umbrella;

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

public class UmbrellaBoyRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability curse = new UmbrellaCurseAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability parasol = new ParasolAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability airStrike = new AirStrikeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability protection = new ProtectionAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "UMBRELLA_PASSIVE_NAME", type = VariableType.ABILITY)
    private Ability umbrellaPassive = new UmbrellaPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "UMBRELLA_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideUmbrella = new EspaceVideUmbrellaAbility();
public UmbrellaBoyRole() {
    }

    @Override public int getId()                { return 21; }
    @Override public String getName()           { return "Umbrella Boy"; }
    @Override public Material getIconMaterial() { return Material.DIAMOND_SWORD; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt2.UMBRA_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLangExt2.UMBRA_KILL_BONUS, bp);
    }

    // Passif : 20% absorption volée sur pomme

    // Malédiction : 2 épées flottantes + résistance 25%

    // Parasol : lance une épée

    // Air Strike : saut + 3 parasols

    // Protection : bouclier projectiles
}

// ════════════════════════════════════════════
//  Devilman (id 22)
// ════════════════════════════════════════════
