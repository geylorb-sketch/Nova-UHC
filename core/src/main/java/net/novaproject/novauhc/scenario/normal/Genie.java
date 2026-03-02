package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.scenario.Scenario;

import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.lang.scenario.GenieLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class Genie extends Scenario {

    private final Map<UUID, Integer> playerWishes = new HashMap<>();
    private final Map<UUID, Integer> playerKills = new HashMap<>();

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_MAX_WISHES_NAME", descKey = "GENIE_VAR_MAX_WISHES_DESC", type = VariableType.INTEGER)
    private final int maxWishes = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_SPEED_DURATION_NAME", descKey = "GENIE_VAR_SPEED_DURATION_DESC", type = VariableType.INTEGER)
    private final int speedDuration = 20 * 60 * 5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_STRENGTH_DURATION_NAME", descKey = "GENIE_VAR_STRENGTH_DURATION_DESC", type = VariableType.INTEGER)
    private final int strengthDuration = 20 * 60 * 5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_BASIC_KILL_REQUIREMENT_NAME", descKey = "GENIE_VAR_BASIC_KILL_REQUIREMENT_DESC", type = VariableType.INTEGER)
    private final int basicKillRequirement = 0;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_MEDIUM_KILL_REQUIREMENT_NAME", descKey = "GENIE_VAR_MEDIUM_KILL_REQUIREMENT_DESC", type = VariableType.INTEGER)
    private final int mediumKillRequirement = 1;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_ADVANCED_KILL_REQUIREMENT_NAME", descKey = "GENIE_VAR_ADVANCED_KILL_REQUIREMENT_DESC", type = VariableType.INTEGER)
    private final int advancedKillRequirement = 2;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GENIE_VAR_LEGENDARY_KILL_REQUIREMENT_NAME", descKey = "GENIE_VAR_LEGENDARY_KILL_REQUIREMENT_DESC", type = VariableType.INTEGER)
    private final int legendaryKillRequirement = 3;

    @Override
    public String getName() {
        return "Genie";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.GENIE, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.NETHER_STAR);
    }



    @Override
    public void onStart(Player player) {
        if (!isActive()) return;

        playerWishes.put(player.getUniqueId(), maxWishes);
        playerKills.put(player.getUniqueId(), 0);

        LangManager.get().send(GenieLang.WISHES_RECEIVED, player);
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (!isActive()) return;

        if (killer != null) {
            UUID killerUuid = killer.getPlayer().getUniqueId();
            playerKills.put(killerUuid, playerKills.getOrDefault(killerUuid, 0) + 1);
            LangManager.get().send(GenieLang.WISHES_IMPROVED, killer.getPlayer());
        }
    }

    public boolean makeWish(Player player, String wishType) {
        if (!isActive()) return false;

        UUID playerUuid = player.getUniqueId();
        int wishesLeft = playerWishes.getOrDefault(playerUuid, 0);

        if (wishesLeft <= 0) {
            LangManager.get().send(GenieLang.NO_WISHES_LEFT, player);
            return false;
        }

        int kills = playerKills.getOrDefault(playerUuid, 0);

        if (!canMakeWish(wishType, kills)) {
            LangManager.get().send(GenieLang.NOT_ENOUGH_KILLS, player);
            return false;
        }

        boolean success = grantWish(player, wishType);

        if (success) {
            playerWishes.put(playerUuid, wishesLeft - 1);
            Map<String, Object> placeholders = new HashMap<>();
            placeholders.put("%remaining%", String.valueOf(wishesLeft - 1));
            LangManager.get().send(GenieLang.WISH_GRANTED, player, placeholders);

            Map<String, Object> broadcastPlaceholders = new HashMap<>();
            broadcastPlaceholders.put("%player%", player.getName());
            LangManager.get().sendAll(GenieLang.WISH_ANNOUNCED, broadcastPlaceholders);
        }

        return success;
    }

    private boolean canMakeWish(String wishType, int kills) {
        switch (wishType.toLowerCase()) {
            case "heal":
            case "food":
            case "speed":
                return kills >= basicKillRequirement;
            case "strength":
            case "resistance":
            case "invisibility":
            case "arrows":
                return kills >= mediumKillRequirement;
            case "diamond":
            case "enchanted_book":
            case "golden_apple":
            case "teleport":
                return kills >= advancedKillRequirement;
            case "full_diamond":
            case "enchanted_sword":
            case "notch_apple":
            case "flight":
                return kills >= legendaryKillRequirement;
            default:
                return false;
        }
    }

    private boolean grantWish(Player player, String wishType) {
        switch (wishType.toLowerCase()) {
            case "heal":
                player.setHealth(player.getMaxHealth());
                LangManager.get().send(GenieLang.HEAL_GRANTED, player);
                return true;
            case "food":
                player.setFoodLevel(20);
                player.setSaturation(20);
                LangManager.get().send(GenieLang.FOOD_GRANTED, player);
                return true;
            case "speed":
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, speedDuration, 1));
                Map<String, Object> speedPlaceholders = new HashMap<>();
                speedPlaceholders.put("%duration%", String.valueOf(speedDuration / 20 / 60));
                LangManager.get().send(GenieLang.SPEED_GRANTED, player, speedPlaceholders);
                return true;
            case "strength":
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, strengthDuration, 0));
                Map<String, Object> strengthPlaceholders = new HashMap<>();
                strengthPlaceholders.put("%duration%", String.valueOf(strengthDuration / 20 / 60));
                LangManager.get().send(GenieLang.STRENGTH_GRANTED, player, strengthPlaceholders);
                return true;
            case "resistance":
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 6000, 0));
                LangManager.get().send(GenieLang.RECEIVED_RESISTANCE, player);
                return true;
            case "invisibility":
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1200, 0));
                LangManager.get().send(GenieLang.RECEIVED_INVISIBILITY, player);
                return true;
            case "arrows":
                player.getInventory().addItem(new ItemStack(Material.ARROW, 32));
                LangManager.get().send(GenieLang.RECEIVED_ARROWS, player);
                return true;
            default:
                return false;
        }
    }

    public int getRemainingWishes(Player player) {
        return playerWishes.getOrDefault(player.getUniqueId(), 0);
    }

    public int getPlayerKills(Player player) {
        return playerKills.getOrDefault(player.getUniqueId(), 0);
    }
}
