package net.novauhc.dandadan.roles.devilman;

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

public class DevilmanRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability malediction = new DevilMaledictionAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability croc = new CrocAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability chaleur = new ChaleurAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability crybaby = new CrybabyAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_PASSIVE_FLAMME_NAME", type = VariableType.ABILITY)
    private Ability flammePassive = new FlammePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "DEVILMAN_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideDevilman = new EspaceVideDevilmanAbility();
public DevilmanRole() {
    }

    @Override public int getId()                { return 22; }
    @Override public String getName()           { return "Devilman"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_ROD; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt2.DEVIL_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLangExt2.DEVIL_KILL_BONUS, bp);
    }

    // Passif Flamme

    // Malédiction : 20% force + résistance + fire res

    // Croc : dévoré 1min → +15% dégâts + ne peut pas utiliser de pouvoir

    // Chaleur : cercle de feu 30s

    // Crybaby : ailes de feu + élimination auto sous 2❤
}
