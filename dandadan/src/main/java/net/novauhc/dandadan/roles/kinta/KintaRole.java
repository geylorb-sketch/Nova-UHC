package net.novauhc.dandadan.roles.kinta;

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

public class KintaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_GREAT_KINTA_MAX_TIME_NAME", descKey = "KINTA_GREAT_KINTA_MAX_TIME_DESC", type = VariableType.TIME)
    private int greatKintaMaxTime = 600;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_GREAT_KINTA_NAME", type = VariableType.ABILITY)
    private Ability greatKinta = new GreatKintaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_ROCKET_PUNCH_NAME", type = VariableType.ABILITY)
    private Ability rocketPunch = new RocketPunchAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_NANOSKIN_NAME", type = VariableType.ABILITY)
    private Ability nanoskin = new NanoskinAbility();

    // Passif Lunette (voit les pommes en or) → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_PASSIVE_LUNETTE_NAME", type = VariableType.ABILITY)
    private Ability lunettePassive = new LunettePassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "KINTA_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideKinta = new EspaceVideKintaAbility();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "KINTA_ABILITY_DEDALE_NAME", type = VariableType.ABILITY)
    private Ability dedaleAcha = new DedaleAchaAbility();
public KintaRole() {
    }

    @Override public int getId()                { return 5; }
    @Override public String getName()           { return "Kinta"; }
    @Override public Material getIconMaterial() { return Material.PISTON_BASE; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.KINTA_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLang.KINTA_KILL_BONUS, bp);
    }

    public int getGreatKintaMaxTime() { return greatKintaMaxTime; }
}
