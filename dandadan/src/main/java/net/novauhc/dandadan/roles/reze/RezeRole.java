package net.novauhc.dandadan.roles.reze;

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

public class RezeRole extends DanDaDanRole {

    private boolean cursed = false;
    private int boumHits = 0;
    private boolean boumActive = false;
    private int torpilleFlames = 3;
    private boolean torpilleActive = false;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability malediction = new RezeMalediction();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability boum = new BoumAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability teteChercheuse = new TeteChercheuseAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_ABILITY_NAME_KEY_NAME", type = VariableType.ABILITY)
    private Ability torpille = new TorpilleAbility();

        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_PASSIVE_BOMBE_NAME", type = VariableType.ABILITY)
    private Ability bombePassive = new BombePassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "REZE_PASSIVE_EXPLOSION_DJ_NAME", type = VariableType.ABILITY)
    private Ability explosionDoubleJump = new ExplosionDoubleJump();

public RezeRole() {
        setCamp(DanDaDanCamps.SOLO);
    }

    @Override public int getId()                { return 28; }
    @Override public String getName()           { return "Reze"; }
    @Override public Material getIconMaterial() { return Material.TNT; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLangExt3.REZE_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player p = killer.getPlayer(); if (p == null) return;
        LangManager.get().send(DanDaDanLangExt3.REZE_KILL_BONUS, p);
    }

    public boolean isCursed() { return cursed; }
    public void setCursed(boolean c) { cursed = c; }
    public int getBoumHits() { return boumHits; }
    public void incrementBoumHits() { boumHits++; }
    public void resetBoumHits() { boumHits = 0; }
    public boolean isBoumActive() { return boumActive; }
    public void setBoumActive(boolean b) { boumActive = b; }
    public int getTorpilleFlames() { return torpilleFlames; }
    public void hitTorpille(Player reze) {
        if (!torpilleActive) return;
        torpilleFlames--;
        reze.setHealth(Math.min(reze.getMaxHealth(), reze.getHealth() + 4));
        reze.getWorld().getNearbyEntities(reze.getLocation(),3,3,3)
                .stream().filter(e->e instanceof Player&&!e.equals(reze))
                .forEach(e->((Player)e).damage(4.0, reze));
        if (torpilleFlames <= 0) triggerTorpilleExplosion(reze);
    }
    private void triggerTorpilleExplosion(Player reze) {
        torpilleActive = false;
        // Zone d'exclusion 20 blocs
        reze.getWorld().getNearbyEntities(reze.getLocation(),20,20,20)
                .stream().filter(e->e instanceof Player&&!e.equals(reze))
                .forEach(e->((Player)e).damage(6.0, reze));
        reze.sendMessage("§6★ §eTorpille finale déclenchée !");
    }
    public void setTorpilleActive(boolean t) { torpilleActive = t; torpilleFlames = 3; }

}
