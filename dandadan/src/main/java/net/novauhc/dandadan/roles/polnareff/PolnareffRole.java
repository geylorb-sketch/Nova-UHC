package net.novauhc.dandadan.roles.polnareff;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PolnareffRole extends DanDaDanRole {

    private boolean standActive     = false;
    private boolean autoAimActive   = false;
    private boolean horaRushActive  = false;
    private double  totalDmgReceived = 0; // pour le switch force/speed

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability swordLaunch = new SwordLaunchAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability silverChariot = new SilverChariotAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability horaRush = new HoraRushAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability imageRemanente = new ImageRemanteAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_PASSIVE_FRANCAIS_NAME", type = VariableType.ABILITY)
    private Ability francaisPassive = new FrancaisPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "POLNAREFF_PASSIVE_PRECISION_NAME", type = VariableType.ABILITY)
    private Ability precisionPassive = new PrecisionPassive();

public PolnareffRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 32; }
    @Override public String getName()           { return "Jean-Pierre Polnareff"; }
    @Override public Material getIconMaterial() { return Material.IRON_SWORD; }
    @Override public String getDescription(Player player) { return LangManager.get().get(DanDaDanLangExt3.POLNA_DESC, player); }

    public boolean isStandActive()   { return standActive; }
    public void setStandActive(boolean b) { standActive = b; }
    public boolean isAutoAimActive() { return autoAimActive; }
    public void setAutoAimActive(boolean b) { autoAimActive = b; }
    public boolean isHoraRushActive(){ return horaRushActive; }
    public void setHoraRushActive(boolean b) { horaRushActive = b; }
    public double getTotalDmgReceived() { return totalDmgReceived; }
    public void addDmgReceived(double d) { totalDmgReceived += d; }
    public void resetDmgReceived() { totalDmgReceived = 0; }

    // Passif Français : pomme = nourriture pleine

    // Passif Précision : 25% auto-aim

    // Sword Launch

    // Silver Chariot (Stand)

    // Hora Rush

    // Image rémanente : 6 PNJ zombies
}

// ════════════════════════════════════════════
//  Rohan (id 33)
// ════════════════════════════════════════════
