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

    // -----------------------------------------------------------------------
    // Caches statiques – évite de recréer les tableaux à chaque appel
    // -----------------------------------------------------------------------
    private static final Material[]    ALL_MATERIALS    = Material.values();
    private static final Enchantment[] ALL_ENCHANTMENTS = Enchantment.values();

    /** Blocs dont la data-value n'est pas directionnelle (clé normalisée à 0). */
    private static final EnumSet<Material> DIRECTIONABLE = EnumSet.of(
            Material.FENCE_GATE, Material.FURNACE, Material.TRAP_DOOR,
            Material.TRAPPED_CHEST, Material.CHEST, Material.DROPPER,
            Material.HOPPER, Material.SIGN
    );

    /** Matériaux toujours exclus, quelle que soit la config. */
    private static final EnumSet<Material> ALWAYS_BLOCKED = EnumSet.of(
            Material.AIR, Material.BEDROCK, Material.COMMAND,
            Material.PORTAL, Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME,
            Material.BARRIER, Material.MOB_SPAWNER,
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
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_GIVE_STARTER_NAME",   descKey = "RANDOMDROP_VAR_GIVE_STARTER_DESC",   type = VariableType.BOOLEAN)
    private boolean giveStarter = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_ALLOW_RARE_NAME",     descKey = "RANDOMDROP_VAR_ALLOW_RARE_DESC",     type = VariableType.BOOLEAN)
    private boolean allowRare = true;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_MAX_INGOT_NAME",      descKey = "RANDOMDROP_VAR_MAX_INGOT_DESC",      type = VariableType.INTEGER)
    private int maxIngotAmount = 16;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_GAPPLE_AMOUNT_NAME",  descKey = "RANDOMDROP_VAR_GAPPLE_AMOUNT_DESC",  type = VariableType.INTEGER)
    private int gappleAmount = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_MAX_ENCHANT_NAME",    descKey = "RANDOMDROP_VAR_MAX_ENCHANT_DESC",    type = VariableType.INTEGER)
    private int maxEnchantLevel = 3;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "RANDOMDROP_VAR_ALLOW_DUPLICATE_NAME", descKey = "RANDOMDROP_VAR_ALLOW_DUPLICATE_DESC", type = VariableType.BOOLEAN)
    private boolean allowDuplicate = false;

    // -----------------------------------------------------------------------
    // État interne
    // -----------------------------------------------------------------------
    /** Cache clé→drop : évite de recalculer un drop déjà attribué. */
    private final Map<String, ItemStack> cacheBlock = new HashMap<>();
    private final Random random = new Random();

    /**
     * Pool de matériaux disponibles.
     * – Mode sans doublons : on pioche en bout de liste (swap-remove O(1)).
     * – Mode avec doublons : on pioche aléatoirement sans supprimer.
     * Initialisée paresseusement au premier appel de generateDrop().
     */
    private List<Material> materialPool = null;

    // -----------------------------------------------------------------------
    // API Scenario
    // -----------------------------------------------------------------------
    @Override
    public String getName() { return "RandomDrop"; }

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

    // -----------------------------------------------------------------------
    // Événements
    // -----------------------------------------------------------------------
    @Override
    public void onEntityDeath(Entity entity, Player killer, EntityDeathEvent event) {
        if (event.getDrops().isEmpty()) return;

        Location loc = entity.getLocation();

        for (ItemStack drop : event.getDrops()) {
            String key = drop.getType().name() + ':' + drop.getDurability();
            ItemStack result = cacheBlock.computeIfAbsent(key, k -> generateDrop());
            if (result != null) entity.getWorld().dropItem(loc, result.clone());
        }

        event.getDrops().clear();
    }

    @Override
    public void onBreak(Player player, Block block, BlockBreakEvent event) {
        if (block.getDrops().isEmpty()) return;

        event.setCancelled(true);

        String key = block.getType().name() + ':' + (DIRECTIONABLE.contains(block.getType()) ? 0 : block.getData());
        Location loc = block.getLocation().add(0.5, 0.5, 0.5);

        ItemStack result = cacheBlock.computeIfAbsent(key, k -> generateDrop());
        if (result != null) block.getWorld().dropItem(loc, result.clone());

        block.setType(Material.AIR);
    }

    // -----------------------------------------------------------------------
    // Logique de génération
    // -----------------------------------------------------------------------

    /**
     * Initialise la pool la première fois.
     * On filtre une seule fois tous les matériaux acceptés et on mélange.
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
     * Génère un drop aléatoire.
     *
     * – Sans doublons : swap-remove sur la pool pré-mélangée → O(1), pas de boucle de retry.
     * – Avec doublons  : pioche directement dans la pool sans la modifier.
     *
     * @return l'ItemStack généré, ou null si la pool est épuisée.
     */
    private ItemStack generateDrop() {
        initPoolIfNeeded();

        if (materialPool.isEmpty()) return null;

        Material material;
        if (!allowDuplicate) {
            // Swap-remove : échange l'élément choisi avec le dernier puis retire le dernier → O(1)
            int idx = materialPool.size() - 1;
            material = materialPool.remove(idx);
        } else {
            material = materialPool.get(random.nextInt(materialPool.size()));
        }

        return buildItem(material);
    }

    /** Construit l'ItemStack final pour un matériau donné. */
    private ItemStack buildItem(Material material) {
        int amount = 1;
        if (material == Material.GOLDEN_APPLE)                                  amount = gappleAmount;
        if (material == Material.IRON_INGOT || material == Material.GOLD_INGOT) amount = maxIngotAmount;

        ItemStack item = new ItemStack(material, Math.max(1, amount));

        if (material == Material.ENCHANTED_BOOK && allowRare) {
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
        if (!allowRare && RARE_MATERIALS.contains(type)) return false;
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}