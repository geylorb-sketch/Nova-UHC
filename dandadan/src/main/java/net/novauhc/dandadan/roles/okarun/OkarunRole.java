package net.novauhc.dandadan.roles.okarun;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;

public class OkarunRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_CURSE_MAX_TIME_NAME", descKey = "OKARUN_CURSE_MAX_TIME_DESC", type = VariableType.TIME)
    private int curseMaxTime = 600;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_CURSE_KILL_BONUS_NAME", descKey = "OKARUN_CURSE_KILL_BONUS_DESC", type = VariableType.TIME)
    private int killBonusTime = 60;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_MALEDICTION_NAME", type = VariableType.ABILITY)
    private Ability malediction = new MaledictionAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_ALLOUT_NAME", type = VariableType.ABILITY)
    private Ability allOut = new AllOutAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_ABILITY_COURSE_NAME", type = VariableType.ABILITY)
    private Ability course = new CourseAbility();

    private int remainingCurseTime;

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OKARUN_PASSIVE_MEME_NAME", type = VariableType.ABILITY)
    private Ability memePassive = new MemePassiveAbility();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "OKARUN_ABILITY_TOUBILLION_NAME", type = VariableType.ABILITY)
    private Ability toubillion = new ToubillionPassive();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "OKARUN_ABILITY_RYTHME_NAME", type = VariableType.ABILITY)
    private Ability rythme = new RythmeAbility();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "OKARUN_ABILITY_TUNNEL_NAME", type = VariableType.ABILITY)
    private Ability tunnel = new TunnelAbility();

public OkarunRole() {
    }

    @Override public int getId()                { return 1; }
    @Override public String getName()           { return "Okarun"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_POWDER; }

    @Override
    public String getDescription(Player player) {
        return net.novaproject.novauhc.lang.LangManager.get()
                .get(net.novauhc.dandadan.lang.DanDaDanLang.OKARUN_DESC, player);
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        remainingCurseTime = curseMaxTime;
        super.onGive(uhcPlayer);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        addCurseTime(killBonusTime);
        Player bp = killer.getPlayer();
        if (bp != null) net.novaproject.novauhc.lang.LangManager.get()
                .send(net.novauhc.dandadan.lang.DanDaDanLang.OKARUN_KILL_BONUS, bp);
    }

    public int getRemainingCurseTime()  { return remainingCurseTime; }
    public int getCurseMaxTime()        { return curseMaxTime; }
    public void decrementCurseTime()    { if (remainingCurseTime > 0) remainingCurseTime--; }
    public void addCurseTime(int sec)   { remainingCurseTime = Math.min(curseMaxTime, remainingCurseTime + sec); }
}
