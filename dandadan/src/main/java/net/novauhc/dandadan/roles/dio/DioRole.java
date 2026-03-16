package net.novauhc.dandadan.roles.dio;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DioRole extends DanDaDanRole {

    private boolean standActive = false;
    private double  timeFreeze  = 9.0;
    private int     timeSkipUses = 3;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability couteau = new DioKnifeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability roadRoller = new RoadRollerAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability theWorld = new TheWorldAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability timeStop = new DioTimeStopAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability timeSkip = new TimeSkipAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_PASSIVE_VAMPIRE_NAME", type = VariableType.ABILITY)
    private Ability vampirePassive = new VampirePassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DIO_PASSIVE_ETOILE_JOESTAR_NAME", type = VariableType.ABILITY)
    private Ability etoileJoestarPassive = new EtoileJoestarPassive();

public DioRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 30; }
    @Override public String getName()           { return "Dio"; }
    @Override public Material getIconMaterial() { return Material.GOLD_SWORD; }
    @Override public String getDescription(Player player) { return LangManager.get().get(DanDaDanLangExt3.DIO_DESC, player); }

    @Override public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player p = killer.getPlayer(); if (p == null) return;
        p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 6));
        timeFreeze = Math.min(15.0, timeFreeze + 0.5);
    }

    public boolean isStandActive() { return standActive; }
    public void setStandActive(boolean b) { standActive = b; }
    public double getTimeFreeze() { return timeFreeze; }
    public int getTimeSkipUses() { return timeSkipUses; }
    public void useTimeSkip() { if (timeSkipUses > 0) timeSkipUses--; }
    public void resetTimeSkip() { timeSkipUses = 3; }

    // Passif Vampire : force de nuit + regen sur kill

    // Passif Étoile des Joestar : alerte quand joueur dans 100 blocs

    // Couteaux (5 boules de neige)

    // Road Roller

    // The World (Stand)

    // Arrêt du temps

    // Time Skip (3x TP à 15 blocs)
}

// ════════════════════════════════════════════
//  Kira (id 31)
// ════════════════════════════════════════════
