package net.novaproject.ultimate.legend;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.command.CommandManager;
import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;

import net.novaproject.novauhc.utils.VariableType;


import net.novaproject.ultimate.legend.roles.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class Legend extends ScenarioRole<LegendRole> {

    private static Legend instance;

    // ═══════════════════════════════════════════════════════════════════════════
    //  @ScenarioVariable
    // ═══════════════════════════════════════════════════════════════════════════

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "LEGEND_VAR_CHOOSE_TIME_NAME",
            descKey = "LEGEND_VAR_CHOOSE_TIME_DESC",
            type = VariableType.INTEGER)
    private int chooseTime = 3;

    // ═══════════════════════════════════════════════════════════════════════════
    //  State
    // ═══════════════════════════════════════════════════════════════════════════

    private boolean canChooseClass = true;

    /** Joueurs qui ont DÉJÀ choisi manuellement (avant giveRoles) */
    private final Set<UUID> manuallyChosen = new HashSet<>();

    public static Legend get() {
        return instance;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Camps — Un seul camp, victoire gérée manuellement
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public Camps[] getCamps() {
        return LegendCamps.values();
    }

    /**
     * Override : la victoire n'est PAS par camps.
     * On la gère via isWin() ci-dessous.
     */
    @Override
    public boolean isWin() {
        // Victoire quand il reste 1 équipe avec des joueurs vivants
        long activeTeams = UHCPlayerManager.get().getPlayingOnlineUHCPlayers().stream()
                .filter(p -> p.getTeam().isPresent())
                .map(p -> p.getTeam().get())
                .distinct()
                .count();
        return activeTeams <= 1;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Scenario Info
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public String getName() {
        return "UHC Legends";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.LEGEND, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.DRAGON_EGG);
    }


    // ═══════════════════════════════════════════════════════════════════════════
    //  Setup — Enregistrement des rôles
    // ═══════════════════════════════════════════════════════════════════════════

    @Override
    public void setup() {
        super.setup();
        instance = this;
        canChooseClass = true;
        manuallyChosen.clear();

        registerRoles();

        Bukkit.getLogger().info("[Legend] Scénario UHC Legends initialisé.");
    }

    private void registerRoles() {
        addRole(Assassin.class);
        addRole(Mage.class);
        addRole(Archer.class);
        addRole(Tank.class);
        addRole(Nain.class);
        addRole(Zeus.class);
        addRole(Necromancien.class);
        addRole(Succube.class);
        addRole(Soldat.class);
        addRole(Princesse.class);
        addRole(Cavalier.class);
        addRole(Ogre.class);
        addRole(Dragon.class);
        addRole(Medecin.class);
        addRole(Prisonnier.class);
        addRole(Corne.class);
        addRole(Marionnettiste.class);
        addRole(Paladin.class);
    }


    @Override
    public void incrementRole(Class<? extends LegendRole> roleClass) {
        if (getRoleAmount(roleClass) >= 1) return;
        super.incrementRole(roleClass);
    }

    @Override
    public void onStart(Player player) {


        // Message de bienvenue traduit
        String msg = LangManager.get().get(LegendLang.CHOOSE_CLASS_WELCOME, player)
                .replace("%choose_time%", String.valueOf(chooseTime));
        player.sendMessage(msg);
    }

    /**
     * Override onSec : à la fin du temps de choix, assigner les rôles
     * aux joueurs qui n'ont pas encore choisi via giveRoles().
     */
    @Override
    public void onSec(Player p) {
        super.onSec(p); // délègue aux abilities des rôles déjà assignés

        int timer = UHCManager.get().getTimer();

        // Quand le timer de choix expire
        if (canChooseClass && timer >= chooseTime * 60) {
            canChooseClass = false;
            giveRoles(); // assigne les rôles restants
        }
    }

    @Override
    public void onGameStart() {
        CommandManager.get().register("legend", new LdCMD(), "ld");
    }


    // ═══════════════════════════════════════════════════════════════════════════
    //  giveRoles — Override : assigne aléatoirement SANS regarder les counts
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Assigne un rôle aléatoire aux joueurs qui n'ont pas encore choisi.
     * Ignore le nombre configuré (0 ou 1) — tous les rôles activés (=1)
     * sont dans le pool. Si un rôle est à 0, il est exclu du pool.
     */
    @Override
    public void giveRoles() {
        LangManager.get().sendAll(LegendLang.CHOOSE_CLASS_TIME_EXPIRED);

        // Construire le pool de rôles activés (amount >= 1)
        List<LegendRole> pool = new ArrayList<>();
        getDefault_roles().forEach((role, amount) -> {
            if (amount >= 1) {
                pool.add(role);
            }
        });

        if (pool.isEmpty()) {
            Bukkit.getLogger().warning("[Legend] Aucun rôle activé dans le pool !");
            return;
        }

        // Assigner aux joueurs sans rôle
        for (UHCPlayer player : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            // Déjà un rôle (choisi manuellement) → skip
            if (getRoleByUHCPlayer(player) != null) continue;

            Player bp = player.getPlayer();
            if (bp == null) continue;

            // Choisir un rôle aléatoire non-pris par l'équipe
            LegendRole selected = pickRandomAvailable(player, pool);
            if (selected == null) {
                // Fallback : n'importe quel rôle du pool
                selected = pool.get(ThreadLocalRandom.current().nextInt(pool.size()));
            }

            // Clone + assign
            LegendRole clone = (LegendRole) selected.clone();
            giveRole(player, clone);

            String msg = LangManager.get().get(LegendLang.CLASS_ASSIGNED_RANDOM, bp)
                    .replace("%legend_name%", clone.getName());
            bp.sendMessage(msg);
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  Choix manuel (appelé par ChooseUi / LdCMD)
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Un joueur choisit manuellement un rôle.
     * @return true si le choix a réussi
     */
    public boolean chooseRole(UHCPlayer player, LegendRole role) {
        Player bp = player.getPlayer();
        if (bp == null) return false;

        // Déjà un rôle ?
        if (getRoleByUHCPlayer(player) != null) {
            LangManager.get().send(LegendLang.CLASS_ALREADY_CHOSEN, bp);
            return false;
        }

        // Rôle déjà pris dans l'équipe ?
        if (isRoleTakenInTeam(player, role)) {
            String msg = LangManager.get().get(LegendLang.CLASS_TAKEN_IN_TEAM, bp)
                    .replace("%legend_name%", role.getName());
            bp.sendMessage(msg);
            return false;
        }

        // Clone + assign
        LegendRole clone = (LegendRole) role.clone();
        giveRole(player, clone);
        manuallyChosen.add(bp.getUniqueId());

        String msg = LangManager.get().get(LegendLang.CLASS_ASSIGNED_SUCCESS, bp)
                .replace("%legend_name%", clone.getName());
        bp.sendMessage(msg);

        return true;
    }

    /**
     * Vérifie si un rôle est déjà pris par quelqu'un de la même équipe.
     */
    public boolean isRoleTakenInTeam(UHCPlayer player, LegendRole role) {
        if (!player.getTeam().isPresent()) return false;

        for (UHCPlayer teammate : player.getTeam().get().getPlayers()) {
            if (teammate.equals(player)) continue;

            LegendRole teammateRole = getRoleByUHCPlayer(teammate);
            if (teammateRole != null && teammateRole.getId() == role.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Choisit un rôle aléatoire non-pris dans l'équipe du joueur.
     */
    private LegendRole pickRandomAvailable(UHCPlayer player, List<LegendRole> pool) {
        List<LegendRole> available = new ArrayList<>();
        for (LegendRole role : pool) {
            if (!isRoleTakenInTeam(player, role)) {
                available.add(role);
            }
        }

        if (available.isEmpty()) return null;
        return available.get(ThreadLocalRandom.current().nextInt(available.size()));
    }

    /**
     * Récupère tous les rôles activés (amount >= 1) pour l'UI.
     */
    public List<LegendRole> getActivatedRoles() {
        List<LegendRole> roles = new ArrayList<>();
        getDefault_roles().forEach((role, amount) -> {
            if (amount >= 1) roles.add(role);
        });
        return roles;
    }
}