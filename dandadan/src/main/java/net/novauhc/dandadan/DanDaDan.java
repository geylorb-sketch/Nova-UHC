package net.novauhc.dandadan;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.command.CommandManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.utils.YokaiConfig;
import net.novauhc.dandadan.utils.YokaiRegistry;
import net.novauhc.dandadan.world.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * DanDaDan — ScenarioRole principal.
 *
 * Système d'obtention des Yokai :
 *  GameWorld → Portails → DanDaDan → Zones Yokai → Épreuve → Pacte → Retour
 *
 * Avant de déclencher un événement lié à un Yokai (arène Doom, Time Freeze, etc.),
 * on vérifie TOUJOURS que le Yokai est activé via YokaiRegistry.isEnabled().
 */
public class DanDaDan extends ScenarioRole<DanDaDanRole> {

    private static DanDaDan instance;
    private final Set<Class<? extends DanDaDanRole>> claimedRoles = new HashSet<>();

    // ── ScenarioVariables (visibles dans le menu UI du framework) ──────

    @ScenarioVariable(lang = DanDaDanVarLang.class,
            nameKey = "PORTAL_COUNT_NAME", descKey = "PORTAL_COUNT_DESC",
            type = VariableType.INTEGER)
    private int portalCount = 4;

    @ScenarioVariable(lang = DanDaDanVarLang.class,
            nameKey = "PORTAL_RADIUS_NAME", descKey = "PORTAL_RADIUS_DESC",
            type = VariableType.INTEGER)
    private int portalRadius = 60;

    @ScenarioVariable(lang = DanDaDanVarLang.class,
            nameKey = "ZONE_DETECT_RADIUS_NAME", descKey = "ZONE_DETECT_RADIUS_DESC",
            type = VariableType.INTEGER)
    private int zoneDetectRadius = 3;

    @ScenarioVariable(lang = DanDaDanVarLang.class,
            nameKey = "INVOCATION_TIME_NAME", descKey = "INVOCATION_TIME_DESC",
            type = VariableType.TIME)
    private int invocationTime = 3;

    public static DanDaDan get() { return instance; }

    // ── Identité ─────────────────────────────────────────────
    @Override public String getName()                { return "DanDaDan UHC"; }
    @Override public String getDescription(Player p) { return "§5Explorez le DanDaDan et réclamez la malédiction d'un Yokai !"; }
    @Override public ItemCreator getItem()            { return new ItemCreator(Material.EYE_OF_ENDER); }
    @Override public Camps[] getCamps()               { return DanDaDanCamps.values(); }

    // ── Lifecycle ────────────────────────────────────────────


    @Override
    public String getPath() {
        return "special/dandadan";
    }

    @Override
    public void setup() {
        super.setup();
        instance = this;
        claimedRoles.clear();
        registerRoles();

    }

    private void registerRoles() {
        for (YokaiRegistry y : YokaiRegistry.values()) {
            addRole(y.getRoleClass());
            incrementRole(y.getRoleClass());
        }
    }

    @Override
    public void incrementRole(Class<? extends DanDaDanRole> roleClass) {
        if (getRoleAmount(roleClass) >= 1) return;
        if(YokaiRegistry.forClass(roleClass) != null && !YokaiRegistry.forClass(roleClass).isEnabled()){
            YokaiRegistry.forClass(roleClass).setEnabled(true);
        }
        super.incrementRole(roleClass);
    }

    @Override
    public void decrementRole(Class<? extends DanDaDanRole> roleClass) {
        if (getRoleAmount(roleClass) <= 0) return;
        if(YokaiRegistry.forClass(roleClass) != null && YokaiRegistry.forClass(roleClass).isEnabled()){
            YokaiRegistry.forClass(roleClass).setEnabled(false);
        }
        super.decrementRole(roleClass);
    }

    @Override
    public void toggleActive() {
        super.toggleActive();
        if(isActive()) {
            DanDaDanWorldManager.get().init();
            YokaiConfig.get().init();
            YokaiConfig.get().loadTrialLocations();
            Bukkit.getPluginManager().registerEvents(TrialNpcManager.get(), Main.get());
            CourseManager.get().loadConfig();
            return;
        }
        cleanup();
    }
    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        uhcPlayer.getPlayer().teleport(location);
    }
    @Override
    public void onGameStart() {
        CommandManager.get().register("ddd", new DanDaDanCMD(), "dandadan");
        LangManager.get().register(DanDaDanLang.values());
        LangManager.get().register(DanDaDanVarLang.values());
        LangManager.get().register(DanDaDanDescLang.values());

        // Portails GameWorld → DanDaDan
        World gameWorld = Common.get().getArena();
        if (gameWorld != null) {
            PortalManager.get().generatePortals(gameWorld, portalRadius, portalCount);
        }

        // ── Construire les zones Yokai dans DanDaDan ──
        YokaiZoneManager.get().buildZones();
    }

    // ── Tick chaque seconde ─────────────────────────────────
    @Override
    public void onSec(Player p) {
        super.onSec(p);

        World dandadanWorld = DanDaDanWorldManager.get().getDandadanWorld();

        // Portails : détection + particules
        PortalManager.get().tick();
        PortalManager.get().tickParticles();

        // Zones Yokai : détection joueurs dans DanDaDan
        YokaiZoneManager.get().tick(dandadanWorld);

        // Épreuves en cours : détection vide (refus du pacte)
        YokaiZoneManager.get().tickTrials(dandadanWorld);
    }

    // ── Rôle on-demand ──────────────────────────────────────
    @Override
    public void giveRoles() { /* no-op : rôles obtenus via le système d'acquisition */ }

    /**
     * Attribue un rôle Yokai à un joueur.
     * Vérifie que le Yokai est activé avant d'attribuer.
     */
    public boolean claimRole(UHCPlayer player, Class<? extends DanDaDanRole> roleClass) {
        if (getRoleByUHCPlayer(player) != null) return false;
        if (claimedRoles.contains(roleClass)) return false;

        // ── GUARD : vérifier que le Yokai est activé ──
        YokaiRegistry yokai = YokaiRegistry.forClass(roleClass);
        if (yokai == null || !yokai.isEnabled()) return false;

        DanDaDanRole role;
        try {
            role = roleClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer le rôle " + roleClass.getName(), e);
        }

        claimedRoles.add(roleClass);
        role.onGive(player);

        // Annonce globale
        String roleName = role.getName();
        String playerName = player.getPlayer() != null ? player.getPlayer().getName() : "???";
        LangManager.get().sendAll(DanDaDanLang.YOKAI_CLAIMED_BROADCAST,
                java.util.Map.of("%player%", playerName, "%yokai%", roleName));

        return true;
    }



    // ══════════════════════════════════════════════════════════
    //  GUARD : Vérifier qu'un Yokai est actif avant un événement
    // ══════════════════════════════════════════════════════════

    /**
     * Vérifie si un Yokai est activé dans la partie.
     * À appeler AVANT de déclencher un événement lié à un Yokai.
     *
     * Exemples :
     *   if (!DanDaDan.get().isYokaiActive(YokaiRegistry.DOOMSLAYER)) return;
     *   if (!DanDaDan.get().isYokaiActive(YokaiRegistry.JOTARO)) return;
     */
    public boolean isYokaiActive(YokaiRegistry yokai) {
        return yokai.isEnabled();
    }

    /**
     * Vérifie si un Yokai est activé par sa classe de rôle.
     */
    public boolean isYokaiActive(Class<? extends DanDaDanRole> roleClass) {
        YokaiRegistry y = YokaiRegistry.forClass(roleClass);
        return y != null && y.isEnabled();
    }

    @Override
    public boolean isWin() {
        return UHCPlayerManager.get().getPlayingOnlineUHCPlayers().size() <= 1;
    }

    @Override public boolean overridesVictory() { return true; }

    // ── Mort custom ──────────────────────────────────────────
    @Override public boolean hascustomDeathMessage() { return true; }

    @Override
    public void sendCustomDeathMessage(UHCPlayer victim, UHCPlayer killer, PlayerDeathEvent event) {
        DanDaDanRole role = getRoleByUHCPlayer(victim);
        String yokaiName = (role != null) ? role.getName() : "Aucun Yokai";
        String victimName = victim.getPlayer() != null ? victim.getPlayer().getName() : "???";

        String msg = LangManager.get().get(DanDaDanLang.DEATH_MESSAGE)
                .replace("%player%", victimName)
                .replace("%yokai%", yokaiName);

        UHCPlayerManager.get().getOnlineUHCPlayers()
                .forEach(p -> { if (p.getPlayer() != null) p.getPlayer().sendMessage(msg); });
        event.setDeathMessage(null);
    }

    // ── Cleanup ──────────────────────────────────────────────
    public void cleanup() {
        TrialNpcManager.get().cleanupAll();
        Location fallback = Common.get().getLobbySpawn();
        DanDaDanWorldManager.get().cleanup(fallback);
    }

    // ── Helpers ──────────────────────────────────────────────
    public boolean isRoleClaimed(Class<? extends DanDaDanRole> roleClass) {
        return claimedRoles.contains(roleClass);
    }

    public int getZoneDetectRadius()  { return zoneDetectRadius; }
    public int getInvocationTime()    { return invocationTime; }
}
