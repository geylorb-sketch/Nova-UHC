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
                a.regesiterAbility(new ArcherBowPassive());
                a.regesiterAbility(new AssassinForcePassive());
                a.regesiterAbility(new CavalierHorseActive());
                a.regesiterAbility(new CorneMelodieAir());
                a.regesiterAbility(new CorneMelodieFeu());
                a.regesiterAbility(new CorneMelodieHeal());
                a.regesiterAbility(new CorneMelodieMetal());
                a.regesiterAbility(new CorneWeaknessPassive());
                a.regesiterAbility(new DragonFireballActive());
                a.regesiterAbility(new DragonFirePassive());
                a.regesiterAbility(new MagePotionPassive());
                a.regesiterAbility(new MarionnettistePuppetPassive());
                a.regesiterAbility(new MedecinHealPassive());
                a.regesiterAbility(new NainArmorActive());
                a.regesiterAbility(new NecroSummonActive());
                a.regesiterAbility(new OgrePassive());
                a.regesiterAbility(new PaladinBlessingActive());
                a.regesiterAbility(new PaladinLowHealthPassive());
                a.regesiterAbility(new PrincesseNoFallPassive());
                a.regesiterAbility(new PrisonnierChainActive());
                a.regesiterAbility(new PrisonnierSpeedPassive());
                a.regesiterAbility(new SoldatEquipmentPassive());
                a.regesiterAbility(new SuccubeAbsorptionActive());
                a.regesiterAbility(new SuccubeLifestealPassive());
                a.regesiterAbility(new TankResistancePassive());
                a.regesiterAbility(new ZeusEffectsActive());
                a.regesiterAbility(new ZeusLightningPassive());
            }
        }.runTaskLater(this, 20);

    }
}
