package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JiangshiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_REVIVE_COOLDOWN_NAME", descKey = "JIANGSHI_REVIVE_COOLDOWN_DESC", type = VariableType.TIME)
    private int reviveCooldown = 1200; // 20 minutes

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_BASE_ZOMBIES_NAME", descKey = "JIANGSHI_BASE_ZOMBIES_DESC", type = VariableType.INTEGER)
    private int baseZombies = 10;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_BASE_SKELETONS_NAME", descKey = "JIANGSHI_BASE_SKELETONS_DESC", type = VariableType.INTEGER)
    private int baseSkeletons = 2;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_SUMMON_NAME", type = VariableType.ABILITY)
    private Ability invocation = new JiangshiInvocationAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_ALLOUT_NAME", type = VariableType.ABILITY)
    private Ability allOut = new JiangshiAllOutAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_ABILITY_ALLOUT_NAME", type = VariableType.ABILITY)
    private Ability revivePassive = new JiangshiRevivePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "JIANGSHI_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideJiangshi = new EspaceVideJiangshiAbility();
    private int ki = 10;
    private int kills = 0;

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIANGSHI_PASSIVE_REVIVE", type = VariableType.ABILITY)
    private Ability kiRegenPassive = new KiRegenPassive();

public JiangshiRole() {
    }

    @Override public int getId()                { return 11; }
    @Override public String getName()           { return "Jiangshi"; }
    @Override public Material getIconMaterial() { return Material.SKULL_ITEM; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.JIANGSHI_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        kills++;
        ki += 10;
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLang.JIANGSHI_KILL_BONUS, bp);
    }

    // ── Accesseurs pour les abilities ──
    public int getKi()           { return ki; }
    public boolean spendKi(int cost) {
        if (ki < cost) return false;
        ki -= cost; return true;
    }
    public int getKills()         { return kills; }
    public int getBaseZombies()   { return baseZombies + (kills >= 1 ? 8 : 0) + (kills >= 2 ? 10 : 0) + (kills >= 3 ? 12 : 0); }
    public int getBaseSkeletons() { return baseSkeletons + (kills >= 1 ? 2 : 0) + (kills >= 2 ? 3 : 0) + (kills >= 3 ? 6 : 0); }
    public int getReviveCooldown(){ return reviveCooldown; }
}
