package net.novauhc.dandadan.particularity;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.roles.acrobatique.AcrobatiqueSoyeuseRole;
import net.novauhc.dandadan.roles.bamora.BamoraRole;
import net.novauhc.dandadan.roles.caesar.CaesarRole;
import net.novauhc.dandadan.roles.csg.CompteSaintGermainRole;
import net.novauhc.dandadan.roles.denji.DenjiRole;
import net.novauhc.dandadan.roles.devilman.DevilmanRole;
import net.novauhc.dandadan.roles.dio.DioRole;
import net.novauhc.dandadan.roles.doomslayer.DoomslayerRole;
import net.novauhc.dandadan.roles.enenra.EnenraRole;
import net.novauhc.dandadan.roles.flatwoods.MonstreFlatwoodsRole;
import net.novauhc.dandadan.roles.jetbooster.JetBoosterKurRole;
import net.novauhc.dandadan.roles.jiangshi.JiangshiRole;
import net.novauhc.dandadan.roles.jiji.JijiRole;
import net.novauhc.dandadan.roles.joseph.JosephRole;
import net.novauhc.dandadan.roles.jotaro.JotaroRole;
import net.novauhc.dandadan.roles.kashimoto.KashimotoRole;
import net.novauhc.dandadan.roles.kinta.KintaRole;
import net.novauhc.dandadan.roles.kira.KiraRole;
import net.novauhc.dandadan.roles.mantis.MantisRole;
import net.novauhc.dandadan.roles.minotaure.MinotaureRole;
import net.novauhc.dandadan.roles.momo.MomoRole;
import net.novauhc.dandadan.roles.nessie.NessieRole;
import net.novauhc.dandadan.roles.oeilmalefique.OeilMalefiqueRole;
import net.novauhc.dandadan.roles.okarun.OkarunRole;
import net.novauhc.dandadan.roles.payase.PayaseRole;
import net.novauhc.dandadan.roles.polnareff.PolnareffRole;
import net.novauhc.dandadan.roles.reiko.ReikoKashimaRole;
import net.novauhc.dandadan.roles.reze.RezeRole;
import net.novauhc.dandadan.roles.rokuro.RokuroSerpoRole;
import net.novauhc.dandadan.roles.seiko.SeikoRole;
import net.novauhc.dandadan.roles.tsuchinoko.TsuchinokoRole;
import net.novauhc.dandadan.roles.umbrella.UmbrellaBoyRole;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * AuraManager — affiche une pastille de couleur (§x) à côté du pseudo
 * de chaque joueur en fonction de son yokai.
 * Mapping conforme au gitbook (page Aura).
 */
public class AuraManager {

    private static AuraManager instance;
    private int taskId = -1;

    // Couleur → code §
    public static final String VIOLET     = "§5";
    public static final String VERT       = "§a";
    public static final String ROUGE      = "§c";
    public static final String ORANGE     = "§6";
    public static final String JAUNE      = "§e";
    public static final String BLEU       = "§b";
    public static final String MARRON     = "§8";
    public static final String BLANC      = "§f";
    public static final String BLEU_FONCE = "§9";

    private static final Map<Class<? extends DanDaDanRole>, String> AURA_MAP = new HashMap<>();

    static {
        // 🟣 Violet
        AURA_MAP.put(OkarunRole.class,            VIOLET);
        AURA_MAP.put(EnenraRole.class,            VIOLET);
        AURA_MAP.put(OeilMalefiqueRole.class,     VIOLET);
        AURA_MAP.put(MonstreFlatwoodsRole.class,  VIOLET);
        AURA_MAP.put(ReikoKashimaRole.class,      VIOLET);
        AURA_MAP.put(AcrobatiqueSoyeuseRole.class, VIOLET);
        AURA_MAP.put(MinotaureRole.class,          VIOLET);
        AURA_MAP.put(UmbrellaBoyRole.class,        VIOLET);
        AURA_MAP.put(JotaroRole.class,             VIOLET);
        // 🟢 Vert
        AURA_MAP.put(MomoRole.class,      VERT);
        AURA_MAP.put(BamoraRole.class,    VERT);
        AURA_MAP.put(SeikoRole.class,     VERT);
        AURA_MAP.put(KintaRole.class,     VERT);
        AURA_MAP.put(KashimotoRole.class, VERT);
        AURA_MAP.put(PayaseRole.class,    VERT);
        AURA_MAP.put(JijiRole.class,      VERT);
        AURA_MAP.put(PolnareffRole.class, VERT);
        AURA_MAP.put(JosephRole.class,    VERT);
        // 🔴 Rouge
        AURA_MAP.put(DevilmanRole.class, ROUGE);
        // 🟠 Orange
        AURA_MAP.put(MantisRole.class,           ORANGE);
        AURA_MAP.put(JetBoosterKurRole.class,    ORANGE);
        AURA_MAP.put(CompteSaintGermainRole.class, ORANGE);
        // 🟡 Jaune
        AURA_MAP.put(RokuroSerpoRole.class, JAUNE);
        AURA_MAP.put(DioRole.class,         JAUNE);
        AURA_MAP.put(KiraRole.class,        JAUNE);
        // 🔵 Bleu
        AURA_MAP.put(JiangshiRole.class,  BLEU);
        AURA_MAP.put(NessieRole.class,    BLEU);
        AURA_MAP.put(TsuchinokoRole.class, BLEU);
        // 🟤 Marron
        AURA_MAP.put(DoomslayerRole.class, MARRON);
        // 🔵 Bleu foncé
        AURA_MAP.put(CaesarRole.class, BLEU_FONCE);
        // Reze & Denji → pas de couleur spécifique dans le doc → orange
        AURA_MAP.put(DenjiRole.class, ORANGE);
        AURA_MAP.put(RezeRole.class,  ORANGE);
    }

    public static AuraManager get() {
        if (instance == null) instance = new AuraManager();
        return instance;
    }

    /** Démarre la tâche récurrente d'affichage des auras (toutes les 2s). */
    public void start() {
        if (taskId != -1) return;
        taskId = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(
                Main.get(), this::updateAllAuras, 20L, 40L);
    }

    /** Arrête la tâche et remet les noms normaux. */
    public void stop() {
        if (taskId != -1) {
            Main.get().getServer().getScheduler().cancelTask(taskId);
            taskId = -1;
        }
        // Réinitialiser les custom names
        UHCPlayerManager.get().getOnlineUHCPlayers().forEach(uhcp -> {
            Player p = uhcp.getPlayer();
            if (p != null) p.setCustomName(null);
        });
    }

    private void updateAllAuras() {
        if (DanDaDan.get() == null) return;
        UHCPlayerManager.get().getPlayingOnlineUHCPlayers().forEach(uhcp -> {
            Player p = uhcp.getPlayer();
            if (p == null) return;
            DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(uhcp);
            String color = role != null
                    ? AURA_MAP.getOrDefault(role.getClass(), "§7")
                    : "§7";
            // Affiche : [●] Pseudo en couleur
            p.setCustomName(color + "● §f" + p.getName());
            p.setCustomNameVisible(true);
        });
    }

    /**
     * Retourne la couleur d'aura d'un rôle.
     */
    public static String getAuraColor(DanDaDanRole role) {
        if (role == null) return "§7";
        return AURA_MAP.getOrDefault(role.getClass(), "§7");
    }
}
