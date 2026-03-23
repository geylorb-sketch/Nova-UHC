package net.novauhc.dandadan;

import net.novaproject.novauhc.ability.AbilityManager;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novauhc.dandadan.roles.acrobatique.AcrobatePassive;
import net.novauhc.dandadan.roles.acrobatique.CheveuxAbility;
import net.novauhc.dandadan.roles.caesar.BandanaPassive;
import net.novauhc.dandadan.roles.caesar.SavonCutterAbility;
import net.novauhc.dandadan.roles.caesar.SavonLauncherAbility;
import net.novauhc.dandadan.roles.caesar.SavonLensesAbility;
import net.novauhc.dandadan.roles.csg.CarteAbility;
import net.novauhc.dandadan.roles.csg.FioleAbility;
import net.novauhc.dandadan.roles.denji.ChaineAbility;
import net.novauhc.dandadan.roles.denji.ChainsawManAbility;
import net.novauhc.dandadan.roles.denji.SangPassive;
import net.novauhc.dandadan.roles.devilman.ChaleurAbility;
import net.novauhc.dandadan.roles.devilman.CrocAbility;
import net.novauhc.dandadan.roles.devilman.FlammePassive;
import net.novauhc.dandadan.roles.dio.RoadRollerAbility;
import net.novauhc.dandadan.roles.dio.TheWorldAbility;
import net.novauhc.dandadan.roles.dio.TimeSkipAbility;
import net.novauhc.dandadan.roles.dio.VampirePassive;
import net.novauhc.dandadan.roles.doomslayer.DoomPassive;
import net.novauhc.dandadan.roles.enenra.ComboPassive;
import net.novauhc.dandadan.roles.enenra.NinjaAbility;
import net.novauhc.dandadan.roles.enenra.SettaAbility;
import net.novauhc.dandadan.roles.flatwoods.FlatwoodsPassive;
import net.novauhc.dandadan.roles.jetbooster.BoostAbility;
import net.novauhc.dandadan.roles.jetbooster.SuperchargeAbility;
import net.novauhc.dandadan.roles.jetbooster.TeteHautePassive;
import net.novauhc.dandadan.roles.joseph.ClackerAbility;
import net.novauhc.dandadan.roles.joseph.HamonOverdriveAbility;
import net.novauhc.dandadan.roles.joseph.PredictionPassive;
import net.novauhc.dandadan.roles.joseph.RebuffAbility;
import net.novauhc.dandadan.roles.jotaro.OraOraAbility;
import net.novauhc.dandadan.roles.jotaro.ReactionPassive;
import net.novauhc.dandadan.roles.jotaro.StarFingerAbility;
import net.novauhc.dandadan.roles.jotaro.StarPlatinumAbility;
import net.novauhc.dandadan.roles.kashimoto.AphoomZhahAbility;
import net.novauhc.dandadan.roles.kashimoto.EspritProtecteurAbility;
import net.novauhc.dandadan.roles.kashimoto.FlammeGlaceAbility;
import net.novauhc.dandadan.roles.kashimoto.ProtecteurPassive;
import net.novauhc.dandadan.roles.kinta.GreatKintaAbility;
import net.novauhc.dandadan.roles.kinta.LunettePassive;
import net.novauhc.dandadan.roles.kira.BitesDustAbility;
import net.novauhc.dandadan.roles.kira.KillerQueenAbility;
import net.novauhc.dandadan.roles.kira.MainPassive;
import net.novauhc.dandadan.roles.kira.StrayCatAbility;
import net.novauhc.dandadan.roles.mantis.BoxeAbility;
import net.novauhc.dandadan.roles.mantis.CrabeAbility;
import net.novauhc.dandadan.roles.mantis.JetWaterAbility;
import net.novauhc.dandadan.roles.mantis.UppercutAbility;
import net.novauhc.dandadan.roles.minotaure.KungFuAbility;
import net.novauhc.dandadan.roles.minotaure.OxydationPassive;
import net.novauhc.dandadan.roles.momo.MoeMoeAbility;
import net.novauhc.dandadan.roles.nessie.DelugeAbility;
import net.novauhc.dandadan.roles.nessie.JetEauAbility;
import net.novauhc.dandadan.roles.nessie.PoissonPassive;
import net.novauhc.dandadan.roles.okarun.AllOutAbility;
import net.novauhc.dandadan.roles.okarun.MaledictionAbility;
import net.novauhc.dandadan.roles.payase.DarknessFormAbility;
import net.novauhc.dandadan.roles.payase.OmbreAbility;
import net.novauhc.dandadan.roles.payase.PermutationAbility;
import net.novauhc.dandadan.roles.polnareff.FrancaisPassive;
import net.novauhc.dandadan.roles.polnareff.HoraRushAbility;
import net.novauhc.dandadan.roles.polnareff.SilverChariotAbility;
import net.novauhc.dandadan.roles.polnareff.SwordLaunchAbility;
import net.novauhc.dandadan.roles.reiko.EmprisonnementAbility;
import net.novauhc.dandadan.roles.reiko.MiroirPassive;
import net.novauhc.dandadan.roles.reiko.ThornsAbility;
import net.novauhc.dandadan.roles.reze.BombePassive;
import net.novauhc.dandadan.roles.reze.BoumAbility;
import net.novauhc.dandadan.roles.reze.TorpilleAbility;
import net.novauhc.dandadan.roles.rohan.EcrivainPassive;
import net.novauhc.dandadan.roles.rohan.HeavensDoorAbility;
import net.novauhc.dandadan.roles.rohan.LivreAbility;
import net.novauhc.dandadan.roles.rokuro.ZoneIncroyableAbility;
import net.novauhc.dandadan.roles.rokuro.ZoneIntouchableAbility;
import net.novauhc.dandadan.roles.seiko.BarriereMystiqueAbility;
import net.novauhc.dandadan.roles.seiko.DieuRegionPassive;
import net.novauhc.dandadan.roles.tsuchinoko.OndesAbility;
import net.novauhc.dandadan.roles.tsuchinoko.SuicideAbility;
import net.novauhc.dandadan.roles.tsuchinoko.VeninAbility;
import net.novauhc.dandadan.roles.tsuchinoko.VerMortPassive;
import net.novauhc.dandadan.roles.umbrella.AirStrikeAbility;
import net.novauhc.dandadan.roles.umbrella.ParasolAbility;
import net.novauhc.dandadan.roles.umbrella.UmbrellaPassive;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                ScenarioManager s = ScenarioManager.get();
                s.addScenario(new DanDaDan());

                AbilityManager a = AbilityManager.get();

                a.registerAbility(new AcrobatePassive());

                a.registerAbility(new AirStrikeAbility());
                a.registerAbility(new AllOutAbility());
                a.registerAbility(new AphoomZhahAbility());

                a.registerAbility(new BandanaPassive());

                a.registerAbility(new BarriereMystiqueAbility());
                a.registerAbility(new BitesDustAbility());

                a.registerAbility(new BombePassive());
                a.registerAbility(new BoostAbility());
                a.registerAbility(new BoumAbility());
                a.registerAbility(new BoxeAbility());

                a.registerAbility(new CarteAbility());
                a.registerAbility(new ChaineAbility());
                a.registerAbility(new ChainsawManAbility());
                a.registerAbility(new ChaleurAbility());
                a.registerAbility(new CheveuxAbility());
                a.registerAbility(new ClackerAbility());
                a.registerAbility(new ComboPassive());

                a.registerAbility(new CrabeAbility());
                a.registerAbility(new CrocAbility());

                a.registerAbility(new DarknessFormAbility());
                a.registerAbility(new DelugeAbility());

                a.registerAbility(new DieuRegionPassive());

                a.registerAbility(new DoomPassive());

                a.registerAbility(new EcrivainPassive());
                a.registerAbility(new EmprisonnementAbility());

                a.registerAbility(new EspritProtecteurAbility());

                a.registerAbility(new FioleAbility());
                a.registerAbility(new FlammeGlaceAbility());
                a.registerAbility(new FlammePassive());
                a.registerAbility(new FlatwoodsPassive());

                a.registerAbility(new FrancaisPassive());

                a.registerAbility(new GreatKintaAbility());
                a.registerAbility(new HamonOverdriveAbility());

                a.registerAbility(new HeavensDoorAbility());

                a.registerAbility(new HoraRushAbility());

                a.registerAbility(new JetEauAbility());
                a.registerAbility(new JetWaterAbility());



                a.registerAbility(new KillerQueenAbility());

                a.registerAbility(new KungFuAbility());
                a.registerAbility(new LivreAbility());
                a.registerAbility(new LunettePassive());
                a.registerAbility(new MainPassive());
                a.registerAbility(new MaledictionAbility());


                a.registerAbility(new MiroirPassive());
                a.registerAbility(new MoeMoeAbility());


                a.registerAbility(new NinjaAbility());

                a.registerAbility(new OmbreAbility());
                a.registerAbility(new OndesAbility());
                a.registerAbility(new OraOraAbility());


                a.registerAbility(new OxydationPassive());
                a.registerAbility(new ParasolAbility());


                a.registerAbility(new PermutationAbility());
                a.registerAbility(new PoissonPassive());


                a.registerAbility(new PredictionPassive());
                a.registerAbility(new ProtecteurPassive());

                a.registerAbility(new ReactionPassive());
                a.registerAbility(new RebuffAbility());

                a.registerAbility(new RoadRollerAbility());

                a.registerAbility(new SangPassive());
                a.registerAbility(new SavonCutterAbility());
                a.registerAbility(new SavonLauncherAbility());
                a.registerAbility(new SavonLensesAbility());

                a.registerAbility(new SettaAbility());

                a.registerAbility(new SilverChariotAbility());
                a.registerAbility(new StarFingerAbility());
                a.registerAbility(new StarPlatinumAbility());
                a.registerAbility(new StrayCatAbility());
                a.registerAbility(new SuicideAbility());

                a.registerAbility(new SuperchargeAbility());
                a.registerAbility(new SwordLaunchAbility());
                a.registerAbility(new TeteHautePassive());
                a.registerAbility(new TheWorldAbility());

                a.registerAbility(new ThornsAbility());
                a.registerAbility(new TimeSkipAbility());

                a.registerAbility(new TorpilleAbility());
                a.registerAbility(new UmbrellaPassive());
                a.registerAbility(new UppercutAbility());
                a.registerAbility(new VampirePassive());
                a.registerAbility(new VeninAbility());
                a.registerAbility(new VerMortPassive());

                a.registerAbility(new ZoneIncroyableAbility());
                a.registerAbility(new ZoneIntouchableAbility());
            }
        }.runTaskLater(this, 20);
    }
}
