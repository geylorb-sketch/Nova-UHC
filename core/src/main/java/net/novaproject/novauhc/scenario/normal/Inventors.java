package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.InventorsLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Inventors extends Scenario {

    private final Map<Material, UUID> firstCrafters = new HashMap<>();
    private final Map<UUID, Integer> playerInventions = new HashMap<>();

    @ScenarioVariable(name="Bonus pour outils en bois", description="Pommes pour le premier crafteur bois", type=VariableType.INTEGER)
    private int woodToolBonus = 2;
    @ScenarioVariable(name="Bonus pour outils en pierre", description="Pains pour le premier crafteur pierre", type=VariableType.INTEGER)
    private int stoneToolBonus = 3;
    @ScenarioVariable(name="Bonus pour outils en fer", description="Lingots de fer pour le premier crafteur fer", type=VariableType.INTEGER)
    private int ironToolBonus = 2;
    @ScenarioVariable(name="Bonus pour outils en diamant", description="Diamants pour le premier crafteur diamant", type=VariableType.INTEGER)
    private int diamondToolBonus = 1;
    @ScenarioVariable(name="Récompense milestone 5", description="Objet donné au palier 5", type=VariableType.STRING)
    private String milestone5Reward = "ENCHANTED_BOOK";
    @ScenarioVariable(name="Récompense milestone 10", description="Objet donné au palier 10", type=VariableType.STRING)
    private String milestone10Reward = "NETHER_STAR";

    private String t(InventorsLang key, Map<String,Object> p) { return LangManager.get().get(key, p); }

    @Override public String getName() { return "Inventors"; }
    @Override public String getDescription() { return "Le premier à crafter un objet est annoncé et reçoit un bonus !"; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.WORKBENCH); }

    @Override
    public void onCraft(ItemStack result, CraftItemEvent event) {
        if (!isActive()) return;
        Player player = (Player) event.getWhoClicked();
        Material craftedItem = result.getType();
        UUID playerUuid = player.getUniqueId();
        if (!firstCrafters.containsKey(craftedItem)) {
            firstCrafters.put(craftedItem, playerUuid);
            playerInventions.put(playerUuid, playerInventions.getOrDefault(playerUuid, 0) + 1);
            String itemName = craftedItem.name().replace("_", " ");
            Bukkit.broadcastMessage(t(InventorsLang.FIRST_CRAFT, Map.of("%player%", player.getName(), "%item%", itemName)));
            giveInventorBonus(player, craftedItem);
            int count = playerInventions.get(playerUuid);
            if (count == 5) {
                Bukkit.broadcastMessage(t(InventorsLang.MILESTONE_5, Map.of("%player%", player.getName())));
                giveMilestoneReward(player, milestone5Reward);
            } else if (count == 10) {
                Bukkit.broadcastMessage(t(InventorsLang.MILESTONE_10, Map.of("%player%", player.getName())));
                giveMilestoneReward(player, milestone10Reward);
            }
        }
    }

    private void giveInventorBonus(Player player, Material craftedItem) {
        ItemStack bonus;
        String bonusDescription;
        switch (craftedItem) {
            case WOOD_PICKAXE, WOOD_AXE, WOOD_SWORD, WOOD_SPADE, WOOD_HOE -> { bonus = new ItemStack(Material.APPLE, woodToolBonus); bonusDescription = woodToolBonus + " Pommes"; }
            case STONE_PICKAXE, STONE_AXE, STONE_SWORD, STONE_SPADE, STONE_HOE -> { bonus = new ItemStack(Material.BREAD, stoneToolBonus); bonusDescription = stoneToolBonus + " Pains"; }
            case IRON_PICKAXE, IRON_AXE, IRON_SWORD, IRON_SPADE, IRON_HOE -> { bonus = new ItemStack(Material.IRON_INGOT, ironToolBonus); bonusDescription = ironToolBonus + " Lingots de Fer"; }
            case DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_SWORD, DIAMOND_SPADE, DIAMOND_HOE -> { bonus = new ItemStack(Material.DIAMOND, diamondToolBonus); bonusDescription = diamondToolBonus + " Diamants"; }
            default -> { bonus = new ItemStack(Material.GOLD_NUGGET, 3); bonusDescription = "3 Pépites d'Or"; }
        }
        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(bonus);
        } else {
            player.getWorld().dropItemNaturally(player.getLocation(), bonus);
            player.sendMessage(LangManager.get().get(InventorsLang.INVENTORY_FULL, player));
        }
        player.sendMessage(t(InventorsLang.BONUS_REWARD, Map.of("%bonus%", bonusDescription)));
    }

    private void giveMilestoneReward(Player player, String rewardType) {
        Material material = Material.getMaterial(rewardType);
        if (material == null) return;
        ItemStack reward = new ItemStack(material, 1);
        if (player.getInventory().firstEmpty() != -1) player.getInventory().addItem(reward);
        else player.getWorld().dropItemNaturally(player.getLocation(), reward);
    }

    public void resetInventions() {
        firstCrafters.clear();
        playerInventions.clear();
        Bukkit.broadcastMessage(LangManager.get().get(InventorsLang.RESET_BROADCAST));
    }

    public Map<UUID, Integer> getInventorLeaderboard() { return new HashMap<>(playerInventions); }
    public boolean hasBeenInvented(Material material) { return firstCrafters.containsKey(material); }
    public Map<Material, UUID> getAllInventions() { return new HashMap<>(firstCrafters); }
    public int getPlayerInventions(Player player) { return playerInventions.getOrDefault(player.getUniqueId(), 0); }
}
