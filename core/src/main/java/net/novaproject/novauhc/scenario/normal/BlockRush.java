package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.BlockRushLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BlockRush extends Scenario {

    private final Map<Material, UUID> firstMiners = new HashMap<>();
    private final Map<UUID, Set<Material>> playerMinedBlocks = new HashMap<>();
    private final Map<UUID, Integer> playerRewards = new HashMap<>();

    @ScenarioVariable(name="base_reward_amount", description="Quantité de lingot d'or par nouveau bloc", type=VariableType.INTEGER)
    private int baseRewardAmount = 1;
    @ScenarioVariable(name="diamond_bonus", description="Diamants pour le premier miner un diamant", type=VariableType.INTEGER)
    private int diamondBonus = 2;
    @ScenarioVariable(name="emerald_bonus", description="Émeraudes pour le premier miner une émeraude", type=VariableType.INTEGER)
    private int emeraldBonus = 2;
    @ScenarioVariable(name="gold_bonus", description="Lingots d'or pour le premier miner de l'or", type=VariableType.INTEGER)
    private int goldBonus = 3;
    @ScenarioVariable(name="milestone_10_reward", description="Pommes d'or pour le palier 10", type=VariableType.INTEGER)
    private int milestone10Reward = 3;
    @ScenarioVariable(name="milestone_25_reward", description="Livres enchantés pour le palier 25", type=VariableType.INTEGER)
    private int milestone25Reward = 2;
    @ScenarioVariable(name="milestone_50_reward", description="Étoiles du Nether pour le palier 50", type=VariableType.INTEGER)
    private int milestone50Reward = 1;

    private String t(BlockRushLang key, Map<String,Object> p) { return LangManager.get().get(key, p); }

    @Override public String getName() { return "BlockRush"; }
    @Override public String getDescription() { return "Miner un type de bloc pour la première fois donne " + baseRewardAmount + " lingot(s) d'or."; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLD_INGOT); }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (!isActive()) return;
        Material blockType = block.getType();
        UUID playerUuid = player.getUniqueId();
        playerMinedBlocks.putIfAbsent(playerUuid, new HashSet<>());
        Set<Material> playerMined = playerMinedBlocks.get(playerUuid);
        if (!playerMined.contains(blockType) && isRewardableBlock(blockType)) {
            playerMined.add(blockType);
            giveOrDrop(player, new ItemStack(Material.GOLD_INGOT, baseRewardAmount), null);
            playerRewards.put(playerUuid, playerRewards.getOrDefault(playerUuid, 0) + 1);
            String blockName = getBlockDisplayName(blockType);
            if (!firstMiners.containsKey(blockType)) {
                firstMiners.put(blockType, playerUuid);
                ItemStack bonusReward = getBonusReward(blockType);
                giveOrDrop(player, bonusReward, null);
                Bukkit.broadcastMessage(t(BlockRushLang.FIRST_MINE, Map.of("%player%", player.getName(), "%block%", blockName)));
                player.sendMessage(t(BlockRushLang.FIRST_BONUS, Map.of("%bonus%", getBonusDescription(blockType))));
            } else {
                player.sendMessage(t(BlockRushLang.NEW_BLOCK, Map.of("%block%", blockName, "%amount%", baseRewardAmount)));
            }
            int total = playerRewards.get(playerUuid);
            if (total == 10) giveMilestone(player, 10, milestone10Reward, Material.GOLDEN_APPLE);
            else if (total == 25) giveMilestone(player, 25, milestone25Reward, Material.ENCHANTED_BOOK);
            else if (total == 50) giveMilestone(player, 50, milestone50Reward, Material.NETHER_STAR);
        }
    }

    private void giveOrDrop(Player player, ItemStack item, String msg) {
        if (item == null) return;
        if (player.getInventory().firstEmpty() != -1) player.getInventory().addItem(item);
        else if (msg != null) { player.getWorld().dropItemNaturally(player.getLocation(), item); player.sendMessage(msg); }
    }

    private void giveMilestone(Player player, int milestone, int amount, Material material) {
        giveOrDrop(player, new ItemStack(material, amount), null);
        player.sendMessage(t(BlockRushLang.STEP_REWARD, Map.of("%amount%", amount, "%item%", material.name().replace("_"," "))));
        Bukkit.broadcastMessage(t(BlockRushLang.MILESTONE, Map.of("%player%", player.getName(), "%count%", milestone)));
    }

    private boolean isRewardableBlock(Material m) {
        return switch (m) {
            case COAL_ORE, IRON_ORE, GOLD_ORE, DIAMOND_ORE, EMERALD_ORE, LAPIS_ORE, REDSTONE_ORE, QUARTZ_ORE,
                 STONE, DIRT, GRASS, SAND, GRAVEL, CLAY, MYCEL, LOG, LOG_2, LEAVES, LEAVES_2,
                 NETHERRACK, SOUL_SAND, NETHER_BRICK, NETHER_WARTS, GLOWSTONE, ENDER_STONE, OBSIDIAN,
                 PRISMARINE, SEA_LANTERN, SPONGE, MOSSY_COBBLESTONE, MONSTER_EGG, ICE, PACKED_ICE,
                 SNOW_BLOCK, CACTUS, SUGAR_CANE_BLOCK, PUMPKIN, MELON_BLOCK, VINE, WATER_LILY -> true;
            default -> false;
        };
    }

    private ItemStack getBonusReward(Material m) {
        return switch (m) {
            case DIAMOND_ORE -> new ItemStack(Material.DIAMOND, diamondBonus);
            case EMERALD_ORE -> new ItemStack(Material.EMERALD, emeraldBonus);
            case GOLD_ORE -> new ItemStack(Material.GOLD_INGOT, goldBonus);
            case GLOWSTONE -> new ItemStack(Material.GLOWSTONE_DUST, 8);
            case NETHER_WARTS -> new ItemStack(Material.NETHER_WARTS, 4);
            case QUARTZ_ORE -> new ItemStack(Material.QUARTZ, 4);
            case ENDER_STONE -> new ItemStack(Material.ENDER_PEARL, 2);
            case OBSIDIAN -> new ItemStack(Material.OBSIDIAN, 2);
            case PRISMARINE -> new ItemStack(Material.PRISMARINE_SHARD, 4);
            case SEA_LANTERN -> new ItemStack(Material.PRISMARINE_CRYSTALS, 3);
            case IRON_ORE -> new ItemStack(Material.IRON_INGOT, 2);
            case COAL_ORE -> new ItemStack(Material.COAL, 3);
            case LAPIS_ORE -> new ItemStack(Material.INK_SACK, 6, (short) 4);
            case REDSTONE_ORE -> new ItemStack(Material.REDSTONE, 6);
            default -> new ItemStack(Material.GOLD_NUGGET, 5);
        };
    }

    private String getBonusDescription(Material blockType) {
        ItemStack bonus = getBonusReward(blockType);
        return bonus != null ? bonus.getAmount() + "x " + bonus.getType().name().replace("_"," ") : "?";
    }

    private String getBlockDisplayName(Material m) {
        return switch (m) {
            case COAL_ORE -> "Minerai de Charbon"; case IRON_ORE -> "Minerai de Fer"; case GOLD_ORE -> "Minerai d'Or";
            case DIAMOND_ORE -> "Minerai de Diamant"; case EMERALD_ORE -> "Minerai d'Émeraude";
            case LAPIS_ORE -> "Minerai de Lapis"; case REDSTONE_ORE -> "Minerai de Redstone";
            case QUARTZ_ORE -> "Minerai de Quartz"; case OBSIDIAN -> "Obsidienne"; case ENDER_STONE -> "Pierre de l'End";
            case GLOWSTONE -> "Pierre Lumineuse"; case PRISMARINE -> "Prismarine"; case SEA_LANTERN -> "Lanterne de Mer";
            default -> m.name().replace("_"," ");
        };
    }
}
