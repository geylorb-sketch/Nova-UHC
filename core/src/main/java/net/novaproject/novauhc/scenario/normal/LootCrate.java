package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;

import net.novaproject.novauhc.Common;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.scenario.LootCrateLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class LootCrate extends Scenario {

    private final List<Location> activeCrates = new ArrayList<>();
    private final Random random = new Random();
    private BukkitRunnable lootCrateTask;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_SPAWN_INTERVAL_NAME", descKey = "LOOTCRATE_VAR_SPAWN_INTERVAL_DESC", type = VariableType.TIME)
    private int spawnInterval = 600;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_MIN_CRATES_NAME", descKey = "LOOTCRATE_VAR_MIN_CRATES_DESC", type = VariableType.INTEGER)
    private int minCrates = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_MAX_CRATES_NAME", descKey = "LOOTCRATE_VAR_MAX_CRATES_DESC", type = VariableType.INTEGER)
    private int maxCrates = 5;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_ENABLE_DIAMONDS_NAME", descKey = "LOOTCRATE_VAR_ENABLE_DIAMONDS_DESC", type = VariableType.BOOLEAN)
    private boolean enableDiamonds = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_ENABLE_GOLDEN_APPLES_NAME", descKey = "LOOTCRATE_VAR_ENABLE_GOLDEN_APPLES_DESC", type = VariableType.BOOLEAN)
    private boolean enableGoldenApples = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_ENABLE_ENCHANTED_ITEMS_NAME", descKey = "LOOTCRATE_VAR_ENABLE_ENCHANTED_ITEMS_DESC", type = VariableType.BOOLEAN)
    private boolean enableEnchantedItems = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_ENABLE_POTIONS_NAME", descKey = "LOOTCRATE_VAR_ENABLE_POTIONS_DESC", type = VariableType.BOOLEAN)
    private boolean enablePotions = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "LOOTCRATE_VAR_ENABLE_FOOD_NAME", descKey = "LOOTCRATE_VAR_ENABLE_FOOD_DESC", type = VariableType.BOOLEAN)
    private boolean enableFood = true;

    @Override
    public String getName() {
        return "LootCrate";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.LOOT_CRATE, player)
                .replace("%minutes%", String.valueOf(spawnInterval / 60));
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.CHEST);
    }



    @Override
    public void onGameStart() {
        startLootCrateTask();
    }

    private void startLootCrateTask() {
        if (lootCrateTask != null) lootCrateTask.cancel();

        lootCrateTask = new BukkitRunnable() {
            private int timer = 0;

            @Override
            public void run() {
                if (!isActive()) {
                    cancel();
                    return;
                }

                timer++;
                if (timer >= spawnInterval) {
                    spawnLootCrates();
                    timer = 0;
                }

                int timeUntilNext = spawnInterval - timer;
                if (timeUntilNext == 60) LangManager.get().sendAll(LootCrateLang.CRATES_WARNING_1MIN);
                else if (timeUntilNext == 10) LangManager.get().sendAll(LootCrateLang.CRATES_WARNING_10SEC);
            }
        };

        lootCrateTask.runTaskTimer(Main.get(), 0, 20);
    }

    private void spawnLootCrates() {
        removeAllCrates();

        List<UHCPlayer> playingPlayers = UHCPlayerManager.get().getPlayingOnlineUHCPlayers();
        if (playingPlayers.isEmpty()) return;

        int crateCount = minCrates + random.nextInt(maxCrates - minCrates + 1);

        for (int i = 0; i < crateCount; i++) {
            Location loc = findSuitableCrateLocation();
            if (loc != null) spawnLootCrate(loc);
        }

        Map<String, Object> placeholders = new HashMap<>();
        placeholders.put("%count%", String.valueOf(activeCrates.size()));
        LangManager.get().sendAll(LootCrateLang.CRATES_SPAWNED, placeholders);
        LangManager.get().sendAll(LootCrateLang.CRATES_ANNOUNCEMENT);

        for (UHCPlayer uhcPlayer : playingPlayers) {
            Player player = uhcPlayer.getPlayer();
            player.getWorld().playSound(player.getLocation(), org.bukkit.Sound.ENDERDRAGON_GROWL, 1.0f, 1.5f);
        }
    }

    private Location findSuitableCrateLocation() {
        World world = Common.get().getArena();
        if (world == null) return null;

        for (int attempts = 0; attempts < 20; attempts++) {
            int x = random.nextInt(2000) - 1000;
            int z = random.nextInt(2000) - 1000;
            int y = world.getHighestBlockYAt(x, z) + 1;

            Location testLoc = new Location(world, x, y, z);

            if (testLoc.getBlock().getType() == Material.AIR &&
                    testLoc.clone().subtract(0, 1, 0).getBlock().getType().isSolid() &&
                    !testLoc.clone().subtract(0, 1, 0).getBlock().getType().equals(Material.LAVA) &&
                    !testLoc.clone().subtract(0, 1, 0).getBlock().getType().equals(Material.WATER)) {
                return testLoc;
            }
        }

        return null;
    }

    private void spawnLootCrate(Location location) {
        Block block = location.getBlock();
        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();

        fillChestWithLoot(inv);
        activeCrates.add(location);

        
        Location beaconLoc = location.clone().subtract(0, 1, 0);
        beaconLoc.getBlock().setType(Material.IRON_BLOCK);
        location.clone().add(0, 1, 0).getBlock().setType(Material.BEACON);

        new BukkitRunnable() {
            @Override
            public void run() {
                beaconLoc.getBlock().setType(Material.STONE);
                location.clone().add(0, 1, 0).getBlock().setType(Material.AIR);
            }
        }.runTaskLater(Main.get(), 600);

        announceNearbyPlayers(location);
    }

    private void fillChestWithLoot(Inventory inventory) {
        List<ItemStack> possibleLoot = new ArrayList<>();

        if (enableEnchantedItems) {
            possibleLoot.add(createEnchantedItem(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 2));
            possibleLoot.add(createEnchantedItem(Material.DIAMOND_PICKAXE, Enchantment.DIG_SPEED, 3));
            possibleLoot.add(createEnchantedItem(Material.BOW, Enchantment.ARROW_DAMAGE, 3));
        }
        if (enableDiamonds) possibleLoot.add(new ItemStack(Material.DIAMOND, 3 + random.nextInt(5)));
        if (enableGoldenApples) possibleLoot.add(new ItemStack(Material.GOLDEN_APPLE, 2 + random.nextInt(3)));
        if (enablePotions) possibleLoot.add(createPotion(Material.POTION, 8201));
        if (enableFood) possibleLoot.add(new ItemStack(Material.COOKED_BEEF, 8 + random.nextInt(8)));

        int itemCount = 5 + random.nextInt(5);
        Set<Integer> usedSlots = new HashSet<>();

        for (int i = 0; i < itemCount; i++) {
            if (possibleLoot.isEmpty()) break;
            ItemStack loot = possibleLoot.get(random.nextInt(possibleLoot.size())).clone();

            int slot;
            do {
                slot = random.nextInt(27);
            } while (usedSlots.contains(slot));

            usedSlots.add(slot);
            inventory.setItem(slot, loot);
        }
    }

    private ItemStack createEnchantedItem(Material mat, Enchantment ench, int lvl) {
        ItemStack item = new ItemStack(mat);
        item.addUnsafeEnchantment(ench, lvl);
        return item;
    }

    private ItemStack createPotion(Material mat, int data) {
        ItemStack potion = new ItemStack(mat, 1);
        potion.setDurability((short) data);
        return potion;
    }

    private void announceNearbyPlayers(Location loc) {
        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Player player = uhcPlayer.getPlayer();
            if (player.getLocation().distance(loc) <= 100) {
                LangManager.get().send(LootCrateLang.CRATE_SPAWNED, player);
            }
        }
    }

    private void removeAllCrates() {
        for (Location loc : activeCrates) {
            Block block = loc.getBlock();
            if (block.getType() == Material.CHEST) block.setType(Material.AIR);
            Block iron = loc.clone().subtract(0, 1, 0).getBlock();
            if (iron.getType() == Material.IRON_BLOCK) iron.setType(Material.STONE);
            Block beacon = loc.clone().add(0, 1, 0).getBlock();
            if (beacon.getType() == Material.BEACON) beacon.setType(Material.AIR);
        }
        activeCrates.clear();
    }
}
