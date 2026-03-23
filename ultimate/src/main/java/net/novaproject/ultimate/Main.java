package net.novaproject.ultimate;

import net.novaproject.novauhc.ability.AbilityManager;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.ultimate.beatthesanta.BeatTheSanta;
import net.novaproject.ultimate.fallenkigdom.FallenKingdom;
import net.novaproject.ultimate.flowerpower.FlowerPower;
import net.novaproject.ultimate.gonefish.GoneFish;
import net.novaproject.ultimate.king.King;
import net.novaproject.ultimate.legend.Legend;
import net.novaproject.ultimate.legend.roles.abilities.*;
import net.novaproject.ultimate.mysteryteam.MysteryTeam;
import net.novaproject.ultimate.netheribus.NetheriBus;
import net.novaproject.ultimate.random.RandomCraft;
import net.novaproject.ultimate.random.RandomDrop;
import net.novaproject.ultimate.skydef.SkyDef;
import net.novaproject.ultimate.skyhigt.SkyHigh;
import net.novaproject.ultimate.slavemarket.SlaveMarket;
import net.novaproject.ultimate.soulbrother.SoulBrother;
import net.novaproject.ultimate.superheros.SuperHeros;
import net.novaproject.ultimate.taupegun.TaupeGun;
import net.novaproject.ultimate.truelove.TrueLove;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ScenarioManager s = ScenarioManager.get();
                s.addScenario(new TaupeGun());
                s.addScenario(new TrueLove());
                s.addScenario(new FallenKingdom());
                s.addScenario(new SkyHigh());
                s.addScenario(new SuperHeros());
                s.addScenario(new SlaveMarket());
                s.addScenario(new King());
                s.addScenario(new RandomCraft());
                s.addScenario(new RandomDrop());
                s.addScenario(new NetheriBus());
                s.addScenario(new GoneFish());
                s.addScenario(new FlowerPower());
                s.addScenario(new BeatTheSanta());
                s.addScenario(new Legend());
                s.addScenario(new MysteryTeam());
                s.addScenario(new SoulBrother());
                s.addScenario(new SkyDef());
                AbilityManager a = AbilityManager.get();
                a.registerAbility(new ArcherBowPassive());
                a.registerAbility(new AssassinForcePassive());
                a.registerAbility(new CavalierHorseActive());
                a.registerAbility(new CorneMelodieAir());
                a.registerAbility(new CorneMelodieFeu());
                a.registerAbility(new CorneMelodieHeal());
                a.registerAbility(new CorneMelodieMetal());
                a.registerAbility(new CorneWeaknessPassive());
                a.registerAbility(new DragonFireballActive());
                a.registerAbility(new DragonFirePassive());
                a.registerAbility(new MagePotionPassive());
                a.registerAbility(new MarionnettistePuppetPassive());
                a.registerAbility(new MedecinHealPassive());
                a.registerAbility(new NainArmorActive());
                a.registerAbility(new NecroSummonActive());
                a.registerAbility(new OgrePassive());
                a.registerAbility(new PaladinBlessingActive());
                a.registerAbility(new PaladinLowHealthPassive());
                a.registerAbility(new PrincesseNoFallPassive());
                a.registerAbility(new PrisonnierChainActive());
                a.registerAbility(new PrisonnierSpeedPassive());
                a.registerAbility(new SoldatEquipmentPassive());
                a.registerAbility(new SuccubeAbsorptionActive());
                a.registerAbility(new SuccubeLifestealPassive());
                a.registerAbility(new TankResistancePassive());
                a.registerAbility(new ZeusEffectsActive());
                a.registerAbility(new ZeusLightningPassive());
            }
        }.runTaskLater(this, 20);

    }
}
