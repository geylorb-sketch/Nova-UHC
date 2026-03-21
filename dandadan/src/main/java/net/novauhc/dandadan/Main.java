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

                a.regesiterAbility(new AcrobatePassive());

                a.regesiterAbility(new AirStrikeAbility());
                a.regesiterAbility(new AllOutAbility());
                a.regesiterAbility(new AphoomZhahAbility());

                a.regesiterAbility(new BandanaPassive());

                a.regesiterAbility(new BarriereMystiqueAbility());
                a.regesiterAbility(new BitesDustAbility());

                a.regesiterAbility(new BombePassive());
                a.regesiterAbility(new BoostAbility());
                a.regesiterAbility(new BoumAbility());
                a.regesiterAbility(new BoxeAbility());

                a.regesiterAbility(new CarteAbility());
                a.regesiterAbility(new ChaineAbility());
                a.regesiterAbility(new ChainsawManAbility());
                a.regesiterAbility(new ChaleurAbility());
                a.regesiterAbility(new CheveuxAbility());
                a.regesiterAbility(new ClackerAbility());
                a.regesiterAbility(new ComboPassive());

                a.regesiterAbility(new CrabeAbility());
                a.regesiterAbility(new CrocAbility());

                a.regesiterAbility(new DarknessFormAbility());
                a.regesiterAbility(new DelugeAbility());

                a.regesiterAbility(new DieuRegionPassive());

                a.regesiterAbility(new DoomPassive());

                a.regesiterAbility(new EcrivainPassive());
                a.regesiterAbility(new EmprisonnementAbility());

                a.regesiterAbility(new EspritProtecteurAbility());

                a.regesiterAbility(new FioleAbility());
                a.regesiterAbility(new FlammeGlaceAbility());
                a.regesiterAbility(new FlammePassive(null));
                a.regesiterAbility(new FlatwoodsPassive());

                a.regesiterAbility(new FrancaisPassive());

                a.regesiterAbility(new GreatKintaAbility());
                a.regesiterAbility(new HamonOverdriveAbility());

                a.regesiterAbility(new HeavensDoorAbility());

                a.regesiterAbility(new HoraRushAbility());

                a.regesiterAbility(new JetEauAbility());
                a.regesiterAbility(new JetWaterAbility());



                a.regesiterAbility(new KillerQueenAbility());

                a.regesiterAbility(new KungFuAbility());
                a.regesiterAbility(new LivreAbility());
                a.regesiterAbility(new LunettePassive());
                a.regesiterAbility(new MainPassive());
                a.regesiterAbility(new MaledictionAbility());


                a.regesiterAbility(new MiroirPassive());
                a.regesiterAbility(new MoeMoeAbility());


                a.regesiterAbility(new NinjaAbility());

                a.regesiterAbility(new OmbreAbility());
                a.regesiterAbility(new OndesAbility());
                a.regesiterAbility(new OraOraAbility());


                a.regesiterAbility(new OxydationPassive());
                a.regesiterAbility(new ParasolAbility());


                a.regesiterAbility(new PermutationAbility());
                a.regesiterAbility(new PoissonPassive());


                a.regesiterAbility(new PredictionPassive());
                a.regesiterAbility(new ProtecteurPassive());

                a.regesiterAbility(new ReactionPassive());
                a.regesiterAbility(new RebuffAbility());

                a.regesiterAbility(new RoadRollerAbility());

                a.regesiterAbility(new SangPassive());
                a.regesiterAbility(new SavonCutterAbility());
                a.regesiterAbility(new SavonLauncherAbility());
                a.regesiterAbility(new SavonLensesAbility());

                a.regesiterAbility(new SettaAbility());

                a.regesiterAbility(new SilverChariotAbility());
                a.regesiterAbility(new StarFingerAbility());
                a.regesiterAbility(new StarPlatinumAbility());
                a.regesiterAbility(new StrayCatAbility());
                a.regesiterAbility(new SuicideAbility());

                a.regesiterAbility(new SuperchargeAbility());
                a.regesiterAbility(new SwordLaunchAbility());
                a.regesiterAbility(new TeteHautePassive());
                a.regesiterAbility(new TheWorldAbility());

                a.regesiterAbility(new ThornsAbility());
                a.regesiterAbility(new TimeSkipAbility());

                a.regesiterAbility(new TorpilleAbility());
                a.regesiterAbility(new UmbrellaPassive());
                a.regesiterAbility(new UppercutAbility());
                a.regesiterAbility(new VampirePassive());
                a.regesiterAbility(new VeninAbility());
                a.regesiterAbility(new VerMortPassive());

                a.regesiterAbility(new ZoneIncroyableAbility());
                a.regesiterAbility(new ZoneIntouchableAbility());
            }
        }.runTaskLater(this, 20);
    }
}
