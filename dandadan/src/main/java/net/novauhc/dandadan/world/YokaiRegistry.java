package net.novauhc.dandadan.world;

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
import net.novauhc.dandadan.roles.rohan.RohanRole;
import net.novauhc.dandadan.roles.rokuro.RokuroSerpoRole;
import net.novauhc.dandadan.roles.seiko.SeikoRole;
import net.novauhc.dandadan.roles.tsuchinoko.TsuchinokoRole;
import net.novauhc.dandadan.roles.umbrella.UmbrellaBoyRole;

/**
 * Registre des 33 Yokai.
 *
 * Chaque Yokai a :
 *  - roleClass  : la classe du rôle
 *  - type       : NORMAL (activé par défaut) ou SPECIAL (désactivé par défaut)
 *  - trialLocation : position d'épreuve (chargée depuis dandadan.yml)
 *
 * Les ZONES de TP (où le joueur entre dans DanDaDan) sont dans une liste commune
 * gérée par YokaiZoneManager. Pas de positions dans l'enum.
 */
public enum YokaiRegistry {

    // ═══ NORMAUX ═══
    OKARUN      (OkarunRole.class,              YokaiType.NORMAL),
    MOMO        (MomoRole.class,                YokaiType.NORMAL),
    BAMORA      (BamoraRole.class,              YokaiType.NORMAL),
    SEIKO       (SeikoRole.class,               YokaiType.NORMAL),
    KINTA       (KintaRole.class,               YokaiType.NORMAL),
    KASHIMOTO   (KashimotoRole.class,           YokaiType.NORMAL),
    ENENRA      (EnenraRole.class,              YokaiType.NORMAL),
    PAYASE      (PayaseRole.class,              YokaiType.NORMAL),
    ROKURO      (RokuroSerpoRole.class,         YokaiType.NORMAL),
    MANTIS      (MantisRole.class,              YokaiType.NORMAL),
    JIANGSHI    (JiangshiRole.class,            YokaiType.NORMAL),
    TSUCHINOKO  (TsuchinokoRole.class,          YokaiType.NORMAL),
    OEIL        (OeilMalefiqueRole.class,       YokaiType.NORMAL),
    JIJI        (JijiRole.class,                YokaiType.NORMAL),
    FLATWOODS   (MonstreFlatwoodsRole.class,    YokaiType.NORMAL),
    REIKO       (ReikoKashimaRole.class,        YokaiType.NORMAL),
    JETBOOSTER  (JetBoosterKurRole.class,       YokaiType.NORMAL),
    ACROBATIQUE (AcrobatiqueSoyeuseRole.class,  YokaiType.NORMAL),
    NESSIE      (NessieRole.class,              YokaiType.NORMAL),
    MINOTAURE   (MinotaureRole.class,           YokaiType.NORMAL),
    UMBRELLA    (UmbrellaBoyRole.class,         YokaiType.NORMAL),
    DEVILMAN    (DevilmanRole.class,            YokaiType.NORMAL),

    // ═══ SPÉCIAUX ═══
    CSG         (CompteSaintGermainRole.class,  YokaiType.SPECIAL),
    CAESAR      (CaesarRole.class,             YokaiType.SPECIAL),
    JOSEPH      (JosephRole.class,             YokaiType.SPECIAL),
    DOOMSLAYER  (DoomslayerRole.class,         YokaiType.SPECIAL),
    DENJI       (DenjiRole.class,              YokaiType.SPECIAL),
    REZE        (RezeRole.class,               YokaiType.SPECIAL),
    JOTARO      (JotaroRole.class,             YokaiType.SPECIAL),
    DIO         (DioRole.class,                YokaiType.SPECIAL),
    KIRA        (KiraRole.class,               YokaiType.SPECIAL),
    POLNAREFF   (PolnareffRole.class,          YokaiType.SPECIAL),
    ROHAN       (RohanRole.class,              YokaiType.SPECIAL),
    ;

    public enum YokaiType { NORMAL, SPECIAL }

    private final Class<? extends DanDaDanRole> roleClass;
    private final YokaiType type;
    private int[] trialLocation;  // Chargé depuis dandadan.yml
    private boolean enabled;

    YokaiRegistry(Class<? extends DanDaDanRole> roleClass, YokaiType type) {
        this.roleClass = roleClass;
        this.type = type;
        this.trialLocation = new int[]{0, 100, 0}; // Défaut — écrasé par le config
        this.enabled = (type == YokaiType.NORMAL);
    }

    public Class<? extends DanDaDanRole> getRoleClass() { return roleClass; }
    public YokaiType getType()                          { return type; }
    public int[] getTrialLocation()                     { return trialLocation; }
    public boolean isEnabled()                          { return enabled; }

    public void setTrialLocation(int[] loc)             { this.trialLocation = loc; }
    public void setEnabled(boolean enabled)             { this.enabled = enabled; }

    /** Rayon de détection des zones */
    public static final int ZONE_RADIUS = 3;

    /** Y en dessous duquel = saut dans le vide */
    public static final int VOID_Y_THRESHOLD = 10;
}
