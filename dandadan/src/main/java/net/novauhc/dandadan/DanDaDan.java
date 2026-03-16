package net.novauhc.dandadan;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.command.CommandManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.world.utils.LobbyCreator;
import net.novauhc.dandadan.events.MoriohRadio;
import net.novauhc.dandadan.events.RandomEventScheduler;
import net.novauhc.dandadan.lang.*;
import net.novauhc.dandadan.particularity.AmourManager;
import net.novauhc.dandadan.particularity.AuraManager;
import net.novauhc.dandadan.particularity.EspaceVideManager;
import net.novauhc.dandadan.particularity.KintamaManager;
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
import net.novauhc.dandadan.structure.StructureManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.novauhc.dandadan.world.DanDaDanWorldManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;

public class DanDaDan extends ScenarioRole<DanDaDanRole> {

    private static DanDaDan instance;
    private final Set<Class<? extends DanDaDanRole>> claimedRoles = new HashSet<>();

    // ── ScenarioVariables : activer/désactiver les fonctionnalités ──

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_DOOM_ARENA_NAME", descKey = "SCENARIO_ENABLE_DOOM_ARENA_DESC", type = VariableType.BOOLEAN)
    private boolean enableDoomArena = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_AURA_NAME", descKey = "SCENARIO_ENABLE_AURA_DESC", type = VariableType.BOOLEAN)
    private boolean enableAura = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_KINTAMA_NAME", descKey = "SCENARIO_ENABLE_KINTAMA_DESC", type = VariableType.BOOLEAN)
    private boolean enableKintama = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_ESPACE_VIDE_NAME", descKey = "SCENARIO_ENABLE_ESPACE_VIDE_DESC", type = VariableType.BOOLEAN)
    private boolean enableEspaceVide = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_AMOUR_NAME", descKey = "SCENARIO_ENABLE_AMOUR_DESC", type = VariableType.BOOLEAN)
    private boolean enableAmour = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_MORIOH_RADIO_NAME", descKey = "SCENARIO_ENABLE_MORIOH_RADIO_DESC", type = VariableType.BOOLEAN)
    private boolean enableMoriohRadio = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_TYPHOON_HUMAN_NAME", descKey = "SCENARIO_ENABLE_TYPHOON_HUMAN_DESC", type = VariableType.BOOLEAN)
    private boolean enableTyphoonHuman = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_ENABLE_WORLD_DANDADAN_NAME", descKey = "SCENARIO_ENABLE_WORLD_DANDADAN_DESC", type = VariableType.BOOLEAN)
    private boolean enableDanDaDanWorld = true;

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_DOOM_DELAY_NAME", descKey = "SCENARIO_DOOM_DELAY_DESC", type = VariableType.TIME)
    private int doomArenaDelay = 60; // secondes avant le lancement de l'arène Doom

    @ScenarioVariable(lang = DanDaDanVarLang.class, nameKey = "SCENARIO_PORTAL_RADIUS_NAME", descKey = "SCENARIO_PORTAL_RADIUS_DESC", type = VariableType.INTEGER)
    private int portalRadius = 60; // rayon de placement des portails autour du spawn

    // ── Monde dédié Doom (arène à 1 minute) ──────────────────
    private static final String DOOM_WORLD_TEMPLATE = "template_doom_arena";
    private static final String DOOM_WORLD_NAME     = "dandadan_doom";
    private World doomWorld;

    public static DanDaDan get() { return instance; }
    public World getDoomWorld()  { return doomWorld; }

    // ── Identité ─────────────────────────────────────────────
    @Override public String getName()                { return "DanDaDan UHC"; }
    @Override public String getDescription(Player p) { return "§5Explorez le Dandadan et réclamez la malédiction d'un yokai !"; }
    @Override public ItemCreator getItem()            { return new ItemCreator(Material.EYE_OF_ENDER); }
    @Override public Camps[] getCamps()               { return DanDaDanCamps.values(); }

    // ── Lifecycle ────────────────────────────────────────────
    @Override
    public void setup() {
        super.setup();
        instance = this;
        claimedRoles.clear();
        registerRoles();

        // ── Monde Doom ───────────────────────────────────────────
        LobbyCreator.cloneWorld(DOOM_WORLD_TEMPLATE, DOOM_WORLD_NAME);
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            doomWorld = Bukkit.getWorld(DOOM_WORLD_NAME);
        }, 40L);

        // ── Dimension DanDaDan (portails + monde) ──────────────────
        if (enableDanDaDanWorld) DanDaDanWorldManager.get().init();

        // ── Monde Espace Vide ────────────────────────────────────
        if (enableEspaceVide) EspaceVideManager.get().init();

        // ── Monde Café (système Amour) ───────────────────────────
        // (LobbyCreator.cloneWorld déclenché dans AmourManager.init())
    }

    private void registerRoles() {
        addRole(OkarunRole.class);
        addRole(MomoRole.class);
        addRole(BamoraRole.class);
        addRole(SeikoRole.class);
        addRole(KintaRole.class);
        addRole(KashimotoRole.class);
        addRole(EnenraRole.class);
        addRole(PayaseRole.class);
        addRole(RokuroSerpoRole.class);
        addRole(MantisRole.class);
        addRole(JiangshiRole.class);
        addRole(TsuchinokoRole.class);
        addRole(OeilMalefiqueRole.class);
        addRole(JijiRole.class);
        addRole(MonstreFlatwoodsRole.class);
        addRole(ReikoKashimaRole.class);
        addRole(JetBoosterKurRole.class);
        addRole(NessieRole.class);
        addRole(AcrobatiqueSoyeuseRole.class);
        addRole(MinotaureRole.class);
        addRole(UmbrellaBoyRole.class);
        addRole(DevilmanRole.class);
        // ── Malédictions spéciales ──────────────────────────────
        addRole(CompteSaintGermainRole.class);
        addRole(CaesarRole.class);
        addRole(JosephRole.class);
        addRole(DoomslayerRole.class);
        addRole(DenjiRole.class);
        addRole(RezeRole.class);
        addRole(JotaroRole.class);
        addRole(DioRole.class);
        addRole(KiraRole.class);
        addRole(PolnareffRole.class);
        addRole(RohanRole.class);
    }

    @Override
    public void onGameStart() {
        CommandManager.get().register("ddd", new DanDaDanCMD(), "dandadan");
        LangManager.get().register(DanDaDanLang.values());
        LangManager.get().register(DanDaDanVarLang.values());
        LangManager.get().register(DanDaDanLangExt.values());
        LangManager.get().register(DanDaDanLangExt2.values());
        LangManager.get().register(DanDaDanLangExt3.values());
        LangManager.get().register(DanDaDanVarLangExt4.values());
        LangManager.get().register(DanDaDanVarLangExt5.values());
        LangManager.get().register(DanDaDanVarLangExt6.values());
        LangManager.get().register(DanDaDanVarLangExt7.values());

        // ── Arène Doom à 60 secondes ─────────────────────────────
//        if (enableDoomArena) Main.get().getServer().getScheduler().runTaskLater(Main.get(),
//                this::launchDoomArena, 20L * doomArenaDelay);

        // ── Portails custom sur la map principale ────────────────
        World gameWorld = Common.get().getArena();
        if (enableDanDaDanWorld) DanDaDanWorldManager.get().spawnPortals(gameWorld);

        // ── Structures d'obtention de yokai DANS la dimension ────
        World dimWorld = DanDaDanWorldManager.get().getDandadanWorld();
        StructureManager.get().init(dimWorld != null ? dimWorld : gameWorld);
//
//        // ── Kintama (spawn entre 20-40min) ───────────────────────
//        if (enableKintama) KintamaManager.get().start(gameWorld);
//
//        // ── Aura (pastilles couleur) ─────────────────────────────
//        if (enableAura) AuraManager.get().start();

        // ── Événements aléatoires ─────────────────────────────────
        //RandomEventScheduler.get().setEnableRadio(enableMoriohRadio);
        //RandomEventScheduler.get().setEnableTyphoon(enableTyphoonHuman);
        //RandomEventScheduler.get().start();

        // ── Système Amour (Denji/Reze + Okarun/Momo) ────────────
        // (enableAmour) AmourManager.get().init();

        // ── Compte de Saint-Germain : donner le rôle aléatoirement
        //giveCompteSaintGermain();
    }

    /** Donne le rôle Compte de Saint-Germain à un joueur aléatoire. */
    private void giveCompteSaintGermain() {
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            var players = UHCPlayerManager.get().getPlayingOnlineUHCPlayers().stream()
                    .filter(p -> getRoleByUHCPlayer(p) == null && p.getPlayer() != null)
                    .toList();
            if (players.isEmpty()) return;
            UHCPlayer chosen = players.get(
                    (int)(Math.random() * players.size()));
            claimRole(chosen, CompteSaintGermainRole.class);
        }, 20L * 5); // 5 secondes après le start
    }


    /*
     * Lance l'arène Doom : téléporte tous les joueurs,
     * équipe chacun en fer + épée sharp II + arc + pommes.
     * Le dernier en vie reçoit le rôle Doomslayer.
     */
    private void launchDoomArena() {
        if (doomWorld == null) return;
        List<UHCPlayer> players = UHCPlayerManager.get().getPlayingOnlineUHCPlayers();
        if (players.isEmpty()) return;

        // Centre de l'arène au spawn du monde Doom
        Location spawnLoc = doomWorld.getSpawnLocation().clone().add(0, 1, 0);

        players.forEach(up -> {
            Player p = up.getPlayer();
            if (p == null) return;

            // Téléportation + équipement arène
            p.teleport(spawnLoc.clone().add(
                    (Math.random() - 0.5) * 20, 0, (Math.random() - 0.5) * 20));
            UHCUtils.clearPlayerInventory(p);
            p.getInventory().setHelmet    (new ItemCreator(Material.IRON_HELMET).getItemstack());
            p.getInventory().setChestplate(new ItemCreator(Material.IRON_CHESTPLATE).getItemstack());
            p.getInventory().setLeggings  (new ItemCreator(Material.IRON_LEGGINGS).getItemstack());
            p.getInventory().setBoots     (new ItemCreator(Material.IRON_BOOTS).getItemstack());

            org.bukkit.inventory.ItemStack sword = new org.bukkit.inventory.ItemStack(Material.IRON_SWORD);
            sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 2);
            p.getInventory().addItem(sword);

            org.bukkit.inventory.ItemStack bow = new org.bukkit.inventory.ItemStack(Material.BOW);
            p.getInventory().addItem(bow);
            p.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.ARROW, 24));
            p.getInventory().addItem(new org.bukkit.inventory.ItemStack(Material.GOLDEN_APPLE, 10));

            LangManager.get().send(DanDaDanLangExt3.DOOM_ARENA_START, p);
        });
    }

    /** Appelé par DoomslayerRole quand il détecte la fin de l'arène. */
    public void endDoomArena(Player winner) {
        if (winner != null) LangManager.get().send(DanDaDanLangExt3.DOOM_ARENA_WIN, winner);

        // Renvoyer les joueurs survivants dans le monde principal
        World mainWorld = Bukkit.getWorlds().get(0);
        Location mainSpawn = mainWorld.getSpawnLocation().clone().add(0, 1, 0);
        doomWorld.getPlayers().forEach(p -> p.teleport(mainSpawn));
    }


    // ── Rôle on-demand ──────────────────────────────────────
    @Override
    public void giveRoles() { /* no-op : rôles obtenus en jeu */ }

    public boolean claimRole(UHCPlayer player, Class<? extends DanDaDanRole> roleClass) {
        Player bp = player.getPlayer();
        if (getRoleByUHCPlayer(player) != null) {
            if (bp != null) LangManager.get().send(DanDaDanLang.YOKAI_ALREADY_HAVE, bp);
            return false;
        }
        if (claimedRoles.contains(roleClass)) {
            if (bp != null) LangManager.get().send(DanDaDanLang.YOKAI_ALREADY_CLAIMED, bp);
            return false;
        }
        DanDaDanRole role;
        try {
            role = roleClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Impossible de créer le rôle " + roleClass.getName(), e);
        }
        claimedRoles.add(roleClass);
        giveRole(player, role);
        // ── Libérer de la dimension DanDaDan ─────────────────────
        if (player.getPlayer() != null) {
            DanDaDanWorldManager.get().onYokaiObtained(player.getPlayer());
        }

        // ── Radio : annonce l'obtention du yokai ─────────────────
        if (player.getPlayer() != null) {
            MoriohRadio.get().onYokaiObtained(player.getPlayer().getName(), role.getName());
        }
        return true;
    }

    // ── Victoire ─────────────────────────────────────────────
    @Override
    public boolean isWin() {
        // Solo : dernier en vie gagne
        return UHCPlayerManager.get().getPlayingOnlineUHCPlayers().size() <= 1;
    }

    @Override public boolean overridesVictory() { return true; }
    /**
     * Hook onDeath : vérifie si le joueur possède 2 Kintamas (passif Yokai — survie).
     */

    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {
        if(!(entity instanceof Player)) return;
        Player p = (Player) entity;
        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(p);
        if (uhcPlayer.getPlayer() == null || event == null) return;
        if (KintamaManager.get().tryYokaiSurvive(uhcPlayer.getPlayer())) {
            event.setCancelled(true);
        }
        // Denji/Pochita : mort devant Pochita
        if (net.novaproject.novauhc.utils.ShortCooldownManager
                .get(uhcPlayer.getPlayer(), "PochitaInteracted") > 0
                && DanDaDan.get() != null) {
            UHCPlayer uhcp = uhcPlayer;
            Main.get().getServer().getScheduler().runTaskLater(
                    Main.get(),
                    () -> claimRole(uhcp, DenjiRole.class), 20L);
        }
    }

    // ── Mort custom ──────────────────────────────────────────
    @Override public boolean hascustomDeathMessage() { return true; }

    @Override
    public void sendCustomDeathMessage(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        DanDaDanRole role    = getRoleByUHCPlayer(uhcPlayer);
        String yokaiName     = (role != null) ? role.getName() : "Aucun yokai";
        String victimName    = uhcPlayer.getPlayer() != null ? uhcPlayer.getPlayer().getName() : "???";
        String killerPart    = "";
        if (killer != null && killer.getPlayer() != null) {
            killerPart = LangManager.get().get(DanDaDanLang.DEATH_MESSAGE_KILLER)
                    .replace("%killer%", killer.getPlayer().getName());
        }
        String msg = LangManager.get().get(DanDaDanLang.DEATH_MESSAGE)
                .replace("%player%", victimName)
                .replace("%killer%", killerPart)
                .replace("%yokai%",  yokaiName);
        UHCPlayerManager.get().getOnlineUHCPlayers()
                .forEach(p -> { if (p.getPlayer() != null) p.getPlayer().sendMessage(msg); });
        event.setDeathMessage(null);
        // ── Morioh-Cho Radio ─────────────────────────────────────
        String killerName = (killer != null && killer.getPlayer() != null)
                ? killer.getPlayer().getName() : "le monde";
        MoriohRadio.get().onPlayerDeath(victimName, killerName);

        // ── Denji/Pochita : mort devant Pochita = obtention du rôle
        if (uhcPlayer.getPlayer() != null
                && net.novaproject.novauhc.utils.ShortCooldownManager
                   .get(uhcPlayer.getPlayer(), "PochitaInteracted") > 0) {
            UHCPlayer uhcp = uhcPlayer;
            Main.get().getServer().getScheduler().runTaskLater(Main.get(), () ->
                    claimRole(uhcp, DenjiRole.class), 20L);
        }
    }

    // ── Helpers ──────────────────────────────────────────────
    public boolean isRoleClaimed(Class<? extends DanDaDanRole> roleClass) {
        return claimedRoles.contains(roleClass);
    }
    public Set<Class<? extends DanDaDanRole>> getClaimedRoles() { return claimedRoles; }

    // ── Cleanup : supprimer les mondes dédiés en fin de partie ──

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        if (UHCManager.get().getTeam_size() != 1) {
            UHCTeamManager.get().scatterTeam(uhcPlayer, teamloc);
        } else {
            uhcPlayer.getPlayer().teleport(location);
        }
    }
}
