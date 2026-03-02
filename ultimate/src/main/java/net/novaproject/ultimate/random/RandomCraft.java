package net.novaproject.ultimate.random;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomCraft extends Scenario {

    private final Map<Material, ItemStack> cache = new HashMap<>();
    private final Set<Material> alreadyUsed = new HashSet<>();

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_GIVE_STARTER_NAME",
            descKey = "RANDOMCRAFT_GIVE_STARTER_DESC",
            type = VariableType.BOOLEAN
    )
    private boolean giveStarterKit = true;

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_ALLOW_RARE_NAME",
            descKey = "RANDOMCRAFT_ALLOW_RARE_DESC",
            type = VariableType.BOOLEAN
    )
    private boolean allowRareItems = true;

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_MAX_INGOT_NAME",
            descKey = "RANDOMCRAFT_MAX_INGOT_DESC",
            type = VariableType.INTEGER
    )
    private int maxIngotAmount = 16;

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_GAPPLE_AMOUNT_NAME",
            descKey = "RANDOMCRAFT_GAPPLE_AMOUNT_DESC",
            type = VariableType.INTEGER
    )
    private int goldenAppleAmount = 3;

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_MAX_ENCHANT_NAME",
            descKey = "RANDOMCRAFT_MAX_ENCHANT_DESC",
            type = VariableType.INTEGER
    )
    private int maxEnchantLevel = 3;

    @ScenarioVariable(
            nameKey = "RANDOMCRAFT_ALLOW_DUPLICATE_NAME",
            descKey = "RANDOMCRAFT_ALLOW_DUPLICATE_DESC",
            type = VariableType.BOOLEAN
    )
    private boolean allowDuplicateDrops = false;

    @Override
    public String getName() {
        return "RandomCraft";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.RANDOMCRAFT, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.WORKBENCH);
    }

    @Override
    public void onStart(Player player) {
        if (!giveStarterKit) return;

        player.getInventory().addItem(new ItemStack(Material.WORKBENCH, 64));
        player.getInventory().addItem(new ItemStack(Material.FURNACE, 64));
        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
        player.getInventory().addItem(new ItemStack(Material.IRON_AXE));
        player.getInventory().addItem(new ItemStack(Material.IRON_SPADE));

    }

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        if (UHCManager.get().getTeam_size() != 1) {
            UHCTeamManager.get().scatterTeam(uhcPlayer, teamloc);
        } else {
            uhcPlayer.getPlayer().teleport(location);
        }
    }

    @Override
    public void onFurnace(ItemStack result, FurnaceSmeltEvent event) {
        if (result == null) return;
        event.setResult(getRandomized(result.getType()));
    }

    @Override
    public void onCraft(ItemStack result, CraftItemEvent event) {
        if (result == null) return;
        event.getInventory().setResult(getRandomized(result.getType()));
    }

    private ItemStack getRandomized(Material original) {
        if (cache.containsKey(original)) {
            return cache.get(original).clone();
        }

        ItemStack generated = generateRandomItem();
        cache.put(original, generated);
        return generated.clone();
    }

    private ItemStack generateRandomItem() {
        Material[] materials = Material.values();
        Material chosen;

        int safety = 0;
        do {
            chosen = materials[ThreadLocalRandom.current().nextInt(materials.length)];
            safety++;
        } while ((!isAcceptedMaterial(chosen) || (!allowDuplicateDrops && alreadyUsed.contains(chosen)))
                && safety < 500);

        alreadyUsed.add(chosen);

        int amount = 1;

        if (chosen == Material.GOLDEN_APPLE) amount = goldenAppleAmount;
        if (chosen == Material.IRON_INGOT || chosen == Material.GOLD_INGOT)
            amount = maxIngotAmount;

        ItemStack item = new ItemStack(chosen, amount);

        if (chosen == Material.ENCHANTED_BOOK && allowRareItems) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            Enchantment enchant = Enchantment.values()[ThreadLocalRandom.current().nextInt(Enchantment.values().length)];
            int level = ThreadLocalRandom.current().nextInt(maxEnchantLevel) + 1;
            meta.addStoredEnchant(enchant, level, true);
            item.setItemMeta(meta);
        }

        return item;
    }

    private boolean isAcceptedMaterial(Material type) {
        return type != Material.AIR
                && type != Material.BEDROCK
                && type != Material.COMMAND
                && type != Material.BARRIER
                && type != Material.PORTAL
                && type != Material.ENDER_PORTAL
                && type != Material.ENDER_PORTAL_FRAME
                && type != Material.WATER
                && type != Material.LAVA;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}