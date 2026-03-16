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

public class RandomCraft extends Scenario {

    // -----------------------------------------------------------------------
    // Caches statiques
    // -----------------------------------------------------------------------
    private static final Material[]    ALL_MATERIALS    = Material.values();
    private static final Enchantment[] ALL_ENCHANTMENTS = Enchantment.values();

    /** Matériaux toujours exclus. */
    private static final EnumSet<Material> ALWAYS_BLOCKED = EnumSet.of(
            Material.AIR, Material.BEDROCK, Material.COMMAND,
            Material.BARRIER, Material.PORTAL,
            Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME,
            Material.WATER, Material.LAVA,
            Material.STATIONARY_WATER, Material.STATIONARY_LAVA
    );

    /** Matériaux considérés comme rares. */
    private static final EnumSet<Material> RARE_MATERIALS = EnumSet.of(
            Material.DIAMOND, Material.DIAMOND_BLOCK,
            Material.GOLDEN_APPLE, Material.ENCHANTED_BOOK,
            Material.BEACON, Material.DRAGON_EGG
    );

    // -----------------------------------------------------------------------
    // Variables de scénario
    // -----------------------------------------------------------------------
    @ScenarioVariable(nameKey = "RANDOMCRAFT_GIVE_STARTER_NAME",   descKey = "RANDOMCRAFT_GIVE_STARTER_DESC",   type = VariableType.BOOLEAN)
    private boolean giveStarterKit = true;

    @ScenarioVariable(nameKey = "RANDOMCRAFT_ALLOW_RARE_NAME",     descKey = "RANDOMCRAFT_ALLOW_RARE_DESC",     type = VariableType.BOOLEAN)
    private boolean allowRareItems = true;

    @ScenarioVariable(nameKey = "RANDOMCRAFT_MAX_INGOT_NAME",      descKey = "RANDOMCRAFT_MAX_INGOT_DESC",      type = VariableType.INTEGER)
    private int maxIngotAmount = 16;

    @ScenarioVariable(nameKey = "RANDOMCRAFT_GAPPLE_AMOUNT_NAME",  descKey = "RANDOMCRAFT_GAPPLE_AMOUNT_DESC",  type = VariableType.INTEGER)
    private int goldenAppleAmount = 3;

    @ScenarioVariable(nameKey = "RANDOMCRAFT_MAX_ENCHANT_NAME",    descKey = "RANDOMCRAFT_MAX_ENCHANT_DESC",    type = VariableType.INTEGER)
    private int maxEnchantLevel = 3;

    @ScenarioVariable(nameKey = "RANDOMCRAFT_ALLOW_DUPLICATE_NAME", descKey = "RANDOMCRAFT_ALLOW_DUPLICATE_DESC", type = VariableType.BOOLEAN)
    private boolean allowDuplicateDrops = false;

    // -----------------------------------------------------------------------
    // État interne
    // -----------------------------------------------------------------------
    /** Cache matériau craftable/fondable → résultat aléatoire. */
    private final Map<Material, ItemStack> cache = new HashMap<>();
    private final Random random = new Random();

    /**
     * Pool de matériaux disponibles, initialisée paresseusement.
     * – Sans doublons : swap-remove O(1).
     * – Avec doublons : pioche sans suppression.
     */
    private List<Material> materialPool = null;

    // -----------------------------------------------------------------------
    // API Scenario
    // -----------------------------------------------------------------------
    @Override
    public String getName() { return "RandomCraft"; }

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

    // -----------------------------------------------------------------------
    // Événements
    // -----------------------------------------------------------------------
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

    // -----------------------------------------------------------------------
    // Logique de génération
    // -----------------------------------------------------------------------

    private ItemStack getRandomized(Material original) {
        return cache.computeIfAbsent(original, k -> generateRandomItem());
    }

    /**
     * Initialise la pool la première fois, en filtrant une seule fois tous les matériaux
     * valides selon la config courante (allowRareItems).
     */
    private void initPoolIfNeeded() {
        if (materialPool != null) return;

        materialPool = new ArrayList<>();
        for (Material m : ALL_MATERIALS) {
            if (isAcceptedMaterial(m)) materialPool.add(m);
        }
        Collections.shuffle(materialPool, random);
    }

    /**
     * Génère un item aléatoire.
     *
     * – Sans doublons : swap-remove → O(1), zéro retry.
     * – Avec doublons  : pioche directement.
     *
     * @return l'ItemStack généré, ou null si la pool est épuisée.
     */
    private ItemStack generateRandomItem() {
        initPoolIfNeeded();

        if (materialPool.isEmpty()) return null;

        Material chosen;
        if (!allowDuplicateDrops) {
            chosen = materialPool.remove(materialPool.size() - 1);
        } else {
            chosen = materialPool.get(random.nextInt(materialPool.size()));
        }

        return buildItem(chosen);
    }

    /** Construit l'ItemStack final pour un matériau donné. */
    private ItemStack buildItem(Material chosen) {
        int amount = 1;
        if (chosen == Material.GOLDEN_APPLE)                                    amount = goldenAppleAmount;
        if (chosen == Material.IRON_INGOT || chosen == Material.GOLD_INGOT)     amount = maxIngotAmount;

        ItemStack item = new ItemStack(chosen, Math.max(1, amount));

        if (chosen == Material.ENCHANTED_BOOK && allowRareItems) {
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
            Enchantment enchant = ALL_ENCHANTMENTS[random.nextInt(ALL_ENCHANTMENTS.length)];
            int level = random.nextInt(Math.max(1, maxEnchantLevel)) + 1;
            meta.addStoredEnchant(enchant, level, true);
            item.setItemMeta(meta);
        }

        return item;
    }

    // -----------------------------------------------------------------------
    // Helpers
    // -----------------------------------------------------------------------
    private boolean isAcceptedMaterial(Material type) {
        if (type == null) return false;
        if (ALWAYS_BLOCKED.contains(type)) return false;
        if (!allowRareItems && RARE_MATERIALS.contains(type)) return false;
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}