package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;

public class BamoraRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_KAIJU_MAX_TIME_NAME", descKey = "BAMORA_KAIJU_MAX_TIME_DESC", type = VariableType.TIME)
    private int kaijuMaxTime = 600;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_KAIJU_KILL_BONUS_NAME", descKey = "BAMORA_KAIJU_KILL_BONUS_DESC", type = VariableType.TIME)
    private int kaijuKillBonus = 60;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_KAIJU_STRENGTH_PCT_NAME", descKey = "BAMORA_KAIJU_STRENGTH_PCT_DESC", type = VariableType.PERCENTAGE)
    private double kaijuStrengthPct = 0.15;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_KAIJU_NAME", type = VariableType.ABILITY)
    private Ability kaiju = new BamoraKaijuAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_INVIS_NAME", type = VariableType.ABILITY)
    private Ability invisibilite = new BamoraInvisAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_SYSTEME_NAME", type = VariableType.ABILITY)
    private Ability systeme = new SystemeAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_PASSIVE_PROJECTILE_NAME", type = VariableType.ABILITY)
    private Ability projectilePassive = new BamoraProjectilePassive();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "BAMORA_ABILITY_SLAUGHTER_NAME", type = VariableType.ABILITY)
    private Ability slaughter = new SlaughterModeAbility();

    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "BAMORA_ABILITY_VILLE_NAME", type = VariableType.ABILITY)
    private Ability ville = new VilleEspaceVideAbility();

public BamoraRole() {
    }

    @Override public int getId()                { return 3; }
    @Override public String getName()           { return "Bamora"; }
    @Override public Material getIconMaterial() { return Material.GOLD_BLOCK; }

    @Override
    public String getDescription(Player player) {
        return net.novaproject.novauhc.lang.LangManager.get()
                .get(DanDaDanLang.BAMORA_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        // Bonus sur Kaiju géré dans BamoraKaijuAbility via référence au role
        Player bp = killer.getPlayer();
        if (bp != null) net.novaproject.novauhc.lang.LangManager.get()
                .send(DanDaDanLang.BAMORA_KILL_BONUS, bp);
    }

    public int getKaijuMaxTime()    { return kaijuMaxTime; }
    public int getKaijuKillBonus()  { return kaijuKillBonus; }
    public double getKaijuStrengthPct() { return kaijuStrengthPct; }
}
