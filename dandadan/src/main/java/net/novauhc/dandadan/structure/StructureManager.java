package net.novauhc.dandadan.structure;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.pnj.LudrisShop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.io.File;
import java.util.*;

/**
 * StructureManager — Charge les schematics des structures d'obtention
 * de yokai sur la map DanDaDan, et gère les interactions avec les PNJs.
 *
 * Structures :
 *  - Usine (Acrobatique Soyeuse) : résoudre puzzle redstone
 *  - Restaurant (Minotaure)      : PNJ Minotaure, préparer des plats
 *  - Tunnel (Reze)               : jump + clic droit sur Reze
 *  - Portail Nether (Devilman)   : crafter un portail nether
 *  - Rivière (Umbrella Boy)      : TP en hauteur, lâcher une enclume sur PNJ
 *  - Hangar Pochita (Denji)      : trouver Pochita, mourir devant lui
 *
 * Le placement des structures est semi-aléatoire autour du spawn.
 */
public class StructureManager implements Listener {

    private static StructureManager instance;

    private World gameWorld;
    private final Map<String, Location> structureLocations = new HashMap<>();
    private final Map<Integer, Class<? extends net.novauhc.dandadan.DanDaDanRole>> npcToRole = new HashMap<>();
    private final Set<Integer> spawnedNpcIds = new HashSet<>();

    public static StructureManager get() {
        if (instance == null) instance = new StructureManager();
        return instance;
    }

    public void init(World world) {
        this.gameWorld = world;
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        placeAllStructures();
    }

    public void cleanup() {
        spawnedNpcIds.forEach(id -> {
            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc != null) npc.destroy();
        });
        spawnedNpcIds.clear();
        structureLocations.clear();
        npcToRole.clear();
    }

    // ── Placement ───────────────────────────────────────────────

    private void placeAllStructures() {
        // Positions autour du spawn (espacement de ~80 blocs)
        List<int[]> offsets = List.of(
                new int[]{ 80,  0},  // Est
                new int[]{-80,  0},  // Ouest
                new int[]{  0, 80},  // Sud
                new int[]{  0,-80},  // Nord
                new int[]{ 60, 60},  // Sud-Est
                new int[]{-60, 60}   // Sud-Ouest
        );

        String[] schematicNames = {
            "structure_usine",      // 0 - Acrobatique Soyeuse
            "structure_restaurant", // 1 - Minotaure
            "structure_tunnel",     // 2 - Reze
            "structure_portail",    // 3 - Devilman
            "structure_riviere",    // 4 - Umbrella Boy
            "structure_hangar",     // 5 - Denji / Pochita
        };

        Class<?>[] roleClasses = {
            net.novauhc.dandadan.roles.acrobatique.AcrobatiqueSoyeuseRole.class,
            net.novauhc.dandadan.roles.minotaure.MinotaureRole.class,
            net.novauhc.dandadan.roles.reze.RezeRole.class,
            net.novauhc.dandadan.roles.devilman.DevilmanRole.class,
            net.novauhc.dandadan.roles.umbrella.UmbrellaBoyRole.class,
            net.novauhc.dandadan.roles.denji.DenjiRole.class,
        };

        String[] npcNames = {
            null,           // usine : pas de PNJ, interaction avec redstone
            "§cMinotaure",  // restaurant
            "§eReze",       // tunnel
            null,           // portail : pas de PNJ
            "§bUmbrella Boy", // rivière
            "§5Pochita",    // hangar
        };

        for (int i = 0; i < schematicNames.length; i++) {
            if (i >= offsets.size()) break;
            int dx = offsets.get(i)[0];
            int dz = offsets.get(i)[1];
            int x  = dx;
            int z  = dz;
            int y  = gameWorld.getHighestBlockYAt(x, z) + 1;
            Location loc = new Location(gameWorld, x, y, z);
            structureLocations.put(schematicNames[i], loc);

            // Charger le schematic
            loadSchematic(schematicNames[i], loc);
            System.out.println(loc);
            // Spawner le PNJ si nécessaire
            if (npcNames[i] != null) {
                @SuppressWarnings("unchecked")
                Class<? extends net.novauhc.dandadan.DanDaDanRole> roleClass =
                        (Class<? extends net.novauhc.dandadan.DanDaDanRole>) roleClasses[i];
                spawnStructureNpc(loc.clone().add(0, 1, 0), npcNames[i], roleClass);
            }
        }

        // Spawner Ludris au centre
        Location center = new Location(gameWorld, 0, gameWorld.getHighestBlockYAt(0, 0) + 2, 0);
        LudrisShop.get().spawn(center);
    }

    private void loadSchematic(String name, Location loc) {
        File schematic = new File(Main.get().getDataFolder(),
                "api/schem/" + name + ".schematic");
        if (schematic.exists()) {
            UHCUtils.loadSchematic(Main.get(), schematic, loc);
            Bukkit.getLogger().info("[DanDaDan] Schematic chargé : " + name);
        } else {
            Bukkit.getLogger().warning("[DanDaDan] Schematic manquant : " + schematic.getAbsolutePath());
        }
    }

    private void spawnStructureNpc(Location loc, String name,
                                   Class<? extends net.novauhc.dandadan.DanDaDanRole> roleClass) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
        npc.spawn(loc);
        npcToRole.put(npc.getId(), roleClass);
        spawnedNpcIds.add(npc.getId());
    }

    // ── Interactions ────────────────────────────────────────────

    /** Clic droit sur un PNJ de structure → tente de donner le rôle correspondant. */
    @EventHandler
    public void onNpcClick(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof org.bukkit.entity.Entity entity)) return;
        NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
        if (npc == null) return;
        int npcId = npc.getId();
        if (!npcToRole.containsKey(npcId)) return;
        event.setCancelled(true);
        Player player = event.getPlayer();
        handleNpcInteraction(player, npcToRole.get(npcId), npc.getName());
    }

    private void handleNpcInteraction(Player player,
                                      Class<? extends net.novauhc.dandadan.DanDaDanRole> roleClass,
                                      String npcName) {
        if (DanDaDan.get() == null) return;
        var uhcp = UHCPlayerManager.get().getPlayer(player);
        if (uhcp == null) return;

        // Vérifie que le rôle n'est pas déjà pris
        if (DanDaDan.get().isRoleClaimed(roleClass)) {
            player.sendMessage(LangManager.get().get(DanDaDanLang.YOKAI_ALREADY_CLAIMED, player));
            return;
        }

        // Selon la structure, il peut y avoir une condition spéciale
        String npcNameClean = npcName.replaceAll("§.", "").trim();
        switch (npcNameClean) {
            case "Pochita" -> {
                // Denji doit mourir devant Pochita — géré dans DanDaDan.onDeath
                player.sendMessage("§5[Pochita] §7Vous devez mourir devant moi pour obtenir son pouvoir...");
                net.novaproject.novauhc.utils.ShortCooldownManager.put(player, "PochitaInteracted", 30_000L);
            }
            case "Umbrella Boy" -> {
                // TP en hauteur + lâcher enclume
                Location high = player.getLocation().clone().add(0, 15, 0);
                player.teleport(high);
                player.sendMessage("§b[Umbrella Boy] §7Faites tomber une enclume pile sur moi !");
                net.novaproject.novauhc.utils.ShortCooldownManager.put(player, "UmbrellaInteracted", 60_000L);
            }
            case "Minotaure" -> {
                // Affiche les plats à reproduire
                player.sendMessage("§c[Minotaure] §7Reproduisez les plats dans l'ordre :");
                player.sendMessage("§71. §fSoupe de champignon  §72. §fPain  §73. §fTarte aux pommes");
                net.novaproject.novauhc.utils.ShortCooldownManager.put(player, "MinotaureInteracted", 120_000L);
            }
            case "Reze" -> {
                // La structure tunnel → cliquetez sur Reze après le jump
                boolean passed = net.novaproject.novauhc.utils.ShortCooldownManager
                        .get(player, "TunnelJumpDone") > 0;
                if (!passed) {
                    player.sendMessage("§e[Reze] §7Réussissez d'abord le jump dans le tunnel !");
                    return;
                }
                DanDaDan.get().claimRole(uhcp, roleClass);
            }
            default -> {
                // Interaction simple
                DanDaDan.get().claimRole(uhcp, roleClass);
            }
        }
    }

    /** Gère le placement réussi d'une enclume (pour Umbrella Boy). */
    @EventHandler
    public void onAnvilPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        if (event.getBlock().getType() != Material.ANVIL) return;
        Player player = event.getPlayer();
        if (net.novaproject.novauhc.utils.ShortCooldownManager.get(player, "UmbrellaInteracted") <= 0) return;

        // Cherche un PNJ Umbrella Boy proche au sol
        Location below = event.getBlock().getLocation().subtract(0, 1, 0);
        spawnedNpcIds.stream()
                .map(id -> CitizensAPI.getNPCRegistry().getById(id))
                .filter(npc -> npc != null
                        && npc.getName().contains("Umbrella")
                        && npc.getEntity() != null
                        && npc.getEntity().getLocation().distance(below) < 2)
                .findFirst()
                .ifPresent(npc -> {
                    var uhcp = UHCPlayerManager.get().getPlayer(player);
                    if (uhcp == null || DanDaDan.get() == null) return;
                    DanDaDan.get().claimRole(uhcp,
                            net.novauhc.dandadan.roles.umbrella.UmbrellaBoyRole.class);
                    player.sendMessage(LangManager.get().get(DanDaDanLang.YOKAI_OBTAINED, player)
                            .replace("%yokai%", "Umbrella Boy"));
                });
    }

    public Map<String, Location> getStructureLocations() {
        return Collections.unmodifiableMap(structureLocations);
    }
}
