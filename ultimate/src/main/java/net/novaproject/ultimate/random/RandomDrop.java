package net.novaproject.ultimate.random;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.*;

public class RandomDrop extends Scenario {

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_GIVE_STARTER_NAME", descKey = "RANDOMDROP_VAR_GIVE_STARTER_DESC", type = VariableType.BOOLEAN)
    private boolean giveStarter = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_ALLOW_RARE_NAME", descKey = "RANDOMDROP_VAR_ALLOW_RARE_DESC", type = VariableType.BOOLEAN)
    private boolean allowRare = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_MAX_INGOT_NAME", descKey = "RANDOMDROP_VAR_MAX_INGOT_DESC", type = VariableType.INTEGER)
    private int maxIngotAmount = 16;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_GAPPLE_AMOUNT_NAME", descKey = "RANDOMDROP_VAR_GAPPLE_AMOUNT_DESC", type = VariableType.INTEGER)
    private int gappleAmount = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_MAX_ENCHANT_NAME", descKey = "RANDOMDROP_VAR_MAX_ENCHANT_DESC", type = VariableType.INTEGER)
    private int maxEnchantLevel = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_ALLOW_DUPLICATE_NAME", descKey = "RANDOMDROP_VAR_ALLOW_DUPLICATE_DESC", type = VariableType.BOOLEAN)
    private boolean allowDuplicate = false;

    private final Map<String, ItemStack> cacheblock = new HashMap<>();
    private final Set<Material> alreadyUsed = new HashSet<>();
    private final Random random = new Random();

    @Override
    public String getName() {
        return "RandomDrop";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.RANDOMDROP, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.ROTTEN_FLESH);
    }

    @Override
    public void onStart(Player player) {
        if (!giveStarter) return;
        player.getInventory().addItem(new ItemStack(Material.WORKBENCH, 64));
        player.getInventory().addItem(new ItemStack(Material.FURNACE, 64));
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
    public void onEntityDeath(Entity entity, Player killer, EntityDeathEvent event) {
        if (event.getDrops().isEmpty()) return;

        Location loc = entity.getLocation();

        for (ItemStack drop : event.getDrops()) {
            String key = drop.getType() + ":" + drop.getDurability();
            ItemStack result = cacheblock.computeIfAbsent(key, this::generateDrop);
            if (result != null) entity.getWorld().dropItem(loc, result.clone());
        }

        event.getDrops().clear();
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (block.getDrops().isEmpty()) return;

        event.setCancelled(true);

        String key = block.getType() + ":" + (isDirectionable(block.getType()) ? 0 : block.getData());
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);

        ItemStack result = cacheblock.computeIfAbsent(key, this::generateDrop);
        if (result != null) block.getWorld().dropItem(loc, result.clone());

        block.setType(Material.AIR);
    }

    private ItemStack generateDrop(String key) {
        Material material;
        int safety = 0;

        do {
            material = Material.values()[random.nextInt(Material.values().length)];
            safety++;
            if (safety > 500) return null;
        } while (!isAcceptedMaterial(material) || (!allowDuplicate && alreadyUsed.contains(material)));

        if (!allowDuplicate) alreadyUsed.add(material);

        int amount = 1;

        if (material == Material.GOLDEN_APPLE) amount = gappleAmount;
        if (material == Material.IRON_INGOT || material == Material.GOLD_INGOT) amount = maxIngotAmount;

        ItemStack item = new ItemStack(material, Math.max(1, amount));

        if (allowRare && material == Material.ENCHANTED_BOOK) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            Enchantment enchant = Enchantment.values()[random.nextInt(Enchantment.values().length)];
            int level = random.nextInt(Math.max(1, maxEnchantLevel)) + 1;
            meta.addStoredEnchant(enchant, level, true);
            item.setItemMeta(meta);
        }

        return item;
    }

    private boolean isDirectionable(Material type) {
        return type == Material.FENCE_GATE || type == Material.FURNACE || type == Material.TRAP_DOOR
                || type == Material.TRAPPED_CHEST || type == Material.CHEST || type == Material.DROPPER
                || type == Material.HOPPER || type == Material.SIGN;
    }

    private boolean isAcceptedMaterial(Material type) {
        if (type == null) return false;

        if (!allowRare && isRare(type)) return false;

        return type != Material.AIR
                && type != Material.BEDROCK
                && type != Material.COMMAND
                && type != Material.PORTAL
                && type != Material.ENDER_PORTAL
                && type != Material.ENDER_PORTAL_FRAME
                && type != Material.BARRIER
                && type != Material.MOB_SPAWNER
                && type != Material.WATER
                && type != Material.LAVA
                && type != Material.STATIONARY_WATER
                && type != Material.STATIONARY_LAVA;
    }

    private boolean isRare(Material type) {
        return type == Material.DIAMOND || type == Material.DIAMOND_BLOCK
                || type == Material.GOLDEN_APPLE
                || type == Material.ENCHANTED_BOOK
                || type == Material.BEACON
                || type == Material.DRAGON_EGG;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}