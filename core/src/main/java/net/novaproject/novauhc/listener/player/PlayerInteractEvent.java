package net.novaproject.novauhc.listener.player;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.normal.GoldenHead;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.ui.DefaultUi;
import net.novaproject.novauhc.ui.inGameScenario;
import net.novaproject.novauhc.ui.player.inGameTeamUi;
import net.novaproject.novauhc.ui.world.CenterUi;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.world.generation.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerInteractEvent implements Listener {


    @EventHandler
    public void onPlayerInteract(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (UHCManager.get().isLobby()) {
            ItemStack item = event.getItem();
            if (item != null) {
                if ((player.hasPermission("novauhc.host") || player.hasPermission("novauhc.cohost"))) {
                    if (item.isSimilar(Common.get().getConfigItem().getItemstack())) {
                        new DefaultUi(player).open();
                        return;
                    }
                    if (item.isSimilar(Common.get().getReglesItem().getItemstack())) {
                        if (UHCManager.get().getWaitState().equals(UHCManager.WaitState.WAIT_STATE)) {
                            UHCManager.get().setWaitState(UHCManager.WaitState.LOBBY_STATE);
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.teleport(Common.get().getLobbySpawn());
                            }
                        } else {
                            UHCManager.get().setWaitState(UHCManager.WaitState.WAIT_STATE);
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.teleport(Common.get().getRulesSpawn());
                            }
                        }
                        return;
                    }
                    if (item.getType() == Material.WOOD_DOOR && player.getWorld().equals(Common.get().getArena())) {
                        player.getInventory().clear();
                        player.teleport(Common.get().getLobbySpawn());
                        UHCUtils.giveLobbyItems(player);
                    }
                    if (item.isSimilar(Common.get().getChangeSpawn().getItemstack())) {
                        new CenterUi(player.getPlayer(), null).open();
                    }
                    if (item.isSimilar(Common.get().getRegenArena().getItemstack())) {
                        new WorldGenerator(Main.get(), Common.get().getArenaName());
                        UHCUtils.giveLobbyItems(player);
                    }
                }
                if (item.isSimilar(Common.get().getTeamItem().getItemstack())) {
                    new inGameTeamUi(player).open();
                    return;
                }
                if (item.isSimilar(Common.get().getActiveRole().getItemstack())) {
                    new inGameScenario(player, true).open();
                    return;
                }
                if (item.isSimilar(Common.get().getActiveScenario().getItemstack())) {
                    new inGameScenario(player).open();
                    return;
                }
            }
        }

        ItemStack item = event.getItem();
        if (isDiamondArmor(item)) {
            UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);
            int currentDiamondPieces = countDiamondArmor(player);

            if (currentDiamondPieces >= uhcPlayer.getDiamondArmor()) {
                event.setCancelled(true);
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                LangManager.get().send(CommonLang.EXEDED_LIMITE, player);
            }
        }

        ScenarioManager.get().getActiveScenarios().forEach(scenario -> {
            scenario.onPlayerInteract(player, event);
        });
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (UHCManager.get().isLobby()) {
            event.setCancelled(true);
            return;
        }
        ScenarioManager.get().getActiveScenarios().forEach(scenario -> {
            scenario.onDrop(event);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        UHCPlayer uhcPlayer = UHCPlayerManager.get().getPlayer(player);

        if (UHCManager.get().isLobby()) {
            // FIX : || remplacé par && — avec ||, même un host était bloqué
            // car il n'a pas forcément les DEUX permissions simultanément
            if (!player.hasPermission("novauhc.host") && !player.hasPermission("novauhc.cohost")) {
                event.setCancelled(true);
                player.updateInventory();
            }
            return;
        }

        if (event.isShiftClick()) {
            ItemStack clicked = event.getCurrentItem();
            if (isDiamondArmor(clicked)) {
                // FIX : vérifie si l'item irait réellement occuper un slot armure supplémentaire
                int armorIndex = getArmorIndex(clicked);
                ItemStack[] armorContents = player.getInventory().getArmorContents();
                boolean slotAlreadyOccupied = armorIndex >= 0
                        && armorContents[armorIndex] != null
                        && armorContents[armorIndex].getType() != Material.AIR;

                // Si le slot cible est déjà occupé par une pièce diamant,
                // le shift-click va la remplacer → le count ne change pas
                boolean wouldIncreaseCount = !slotAlreadyOccupied || !isDiamondArmor(armorIndex >= 0 ? armorContents[armorIndex] : null);

                if (wouldIncreaseCount && exceedsDiamondLimit(player, uhcPlayer)) {
                    event.setCancelled(true);
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                    LangManager.get().send(CommonLang.EXEDED_LIMITE, player);
                    player.updateInventory();
                }
            }
            // FIX : pas de return ici — on laisse passer les shift-clicks non-diamant
            // normalement sans court-circuiter la suite du handler.
            // (Le return était trop tôt et empêchait d'autres vérifications.)
            return;
        }

        if (event.getSlotType() == InventoryType.SlotType.ARMOR) {
            ItemStack cursor = event.getCursor();
            if (isDiamondArmor(cursor)) {
                ItemStack current = event.getCurrentItem();
                int currentPieces = countDiamondArmor(player);

                // Si le slot armure ciblé contenait déjà une pièce diamant, on ne compte pas double
                if (isDiamondArmor(current)) {
                    currentPieces--;
                }

                if (currentPieces >= uhcPlayer.getDiamondArmor()) {
                    event.setCancelled(true);
                    player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                    LangManager.get().send(CommonLang.EXEDED_LIMITE, player);
                    player.updateInventory();
                }
            }
        }
    }

    private boolean isDiamondArmor(ItemStack item) {
        if (item == null) return false;
        Material type = item.getType();
        return type == Material.DIAMOND_HELMET ||
                type == Material.DIAMOND_CHESTPLATE ||
                type == Material.DIAMOND_LEGGINGS ||
                type == Material.DIAMOND_BOOTS;
    }

    private int countDiamondArmor(Player player) {
        int count = 0;
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (isDiamondArmor(item)) count++;
        }
        return count;
    }

    private boolean exceedsDiamondLimit(Player player, UHCPlayer uhcPlayer) {
        return countDiamondArmor(player) >= uhcPlayer.getDiamondArmor();
    }

    /**
     * Retourne l'index dans getArmorContents() correspondant au type de pièce :
     * 0 = boots, 1 = leggings, 2 = chestplate, 3 = helmet
     */
    private int getArmorIndex(ItemStack item) {
        if (item == null) return -1;
        return switch (item.getType()) {
            case DIAMOND_BOOTS -> 0;
            case DIAMOND_LEGGINGS -> 1;
            case DIAMOND_CHESTPLATE -> 2;
            case DIAMOND_HELMET -> 3;
            default -> -1;
        };
    }


    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        ScenarioManager.get().getActiveScenarios().forEach(scenario -> {
            scenario.onConsume(player, item, event);
        });

        GoldenHead goldenHeadScenario = ScenarioManager.get().getScenario(GoldenHead.class);
        if (goldenHeadScenario != null && !goldenHeadScenario.isActive()) {
            if (item.getType() == Material.GOLDEN_APPLE
                    && item.hasItemMeta()
                    && item.getItemMeta().hasDisplayName()
                    && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Golden Head")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMountHorse(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ScenarioManager.get().getActiveScenarios().forEach(scenario -> {
            scenario.onPlayerInteractEntity(player, event);
        });
    }

}