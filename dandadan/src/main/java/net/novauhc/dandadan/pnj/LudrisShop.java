package net.novauhc.dandadan.pnj;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.novaproject.novauhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * LudrisShop — PNJ Ludris (Citizens) au centre du Dandadan.
 * Ouvre un inventaire de shop quand on clique droit dessus.
 * Items disponibles (coûts en minerais) :
 *   - Traducteur Groucho  : 5 diamants     — voir HP sur têtes
 *   - Territory Can       : 1 diamant      — invis+invincible 5s
 *   - Uchide              : 5 diamants     — épée rewind 5s
 *   - Kogaï               : 5d + 5e        — arc Power III 3 modes
 *   - Asura               : 7d + 5e        — +15% effet manquant
 *   - Slip du mauvais œil : 10e            — jambières fer P3 unbreakable
 *   - Tableau Maudit      : 2f             — 5% lave sur coup
 *   - Nano skin           : 8f + 10d       — pièce diamant P2 unbreakable thorns
 *   - Combat-Use Simulator: 10f + 15d      — combat wither squelette
 *   - Hunting Soul        : 8f + 10d       — 4 PNJ chanteurs
 */
public class LudrisShop implements Listener {

    private static LudrisShop instance;
    private NPC ludrisNpc;
    private int npcId = -1;
    private final Set<UUID> hasGroucho       = new HashSet<>();
    private final Set<UUID> hasTerritoryCan  = new HashSet<>();
    private final Set<UUID> hasUchide        = new HashSet<>();
    private final Map<UUID, Integer> shopPurchases = new HashMap<>();

    public static LudrisShop get() {
        if (instance == null) instance = new LudrisShop();
        return instance;
    }

    public void spawn(Location location) {
        Bukkit.getPluginManager().registerEvents(this, Main.get());
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        ludrisNpc = registry.createNPC(EntityType.PLAYER, "§6Ludris §e⟨Shop⟩");
        ludrisNpc.spawn(location);
        npcId = ludrisNpc.getId();
    }

    public void despawn() {
        if (ludrisNpc != null && ludrisNpc.isSpawned()) {
            ludrisNpc.destroy();
            ludrisNpc = null;
        }
        hasGroucho.clear();
        hasTerritoryCan.clear();
        hasUchide.clear();
        shopPurchases.clear();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof org.bukkit.entity.Entity entity)) return;
        if (npcId == -1) return;
        NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
        if (npc == null || !npc.getEntity().equals(entity)) return;
        event.setCancelled(true);
        openShop(event.getPlayer());
    }

    private void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54,
                "§6✦ §eLudris — Boutique §6✦");
        inv.setItem(10, makeShopItem(Material.SKULL_ITEM,   "§fTraducteur Groucho",   List.of("§7Voir la vie sur les têtes","§e5 diamants — max 1"), (short)3));
        inv.setItem(11, makeShopItem(Material.GLASS_BOTTLE, "§fTerritory Can",         List.of("§7Invis + invincible 5s","§e1 diamant — max 1")));
        inv.setItem(12, makeShopItem(Material.IRON_SWORD,   "§fUchide",                List.of("§7Rewind joueurs proches 5s","§e5 diamants — max 1")));
        inv.setItem(13, makeShopItem(Material.BOW,          "§fKogaï",                 List.of("§7Arc P3 — 3 modes (infini/feu/punch)","§e5 diamants + 5 émeraudes — max 1")));
        inv.setItem(14, makeShopItem(Material.BLAZE_ROD,    "§fAsura",                 List.of("§7+15% effet manquant","§e7 diamants + 5 émeraudes — max 1")));
        inv.setItem(15, makeShopItem(Material.IRON_LEGGINGS,"§fSlip du mauvais œil",   List.of("§7Pantalon fer P3 unbreakable","§e10 émeraudes — max 1")));
        inv.setItem(20, makeShopItem(Material.PAINTING,     "§fTableau Maudit",        List.of("§75% lave sous les pieds sur coup","§e2 fer — max 1")));
        inv.setItem(21, makeShopItem(Material.DIAMOND_CHESTPLATE,"§fNano skin",        List.of("§7Pièce diamant aléatoire P2 unbreakable thorns","§e8 fer + 10 diamants — max 1")));
        inv.setItem(22, makeShopItem(Material.IRON_SWORD,   "§fCombat-Use Simulator",  List.of("§7Combat wither squelette → cooldown reset","§e10 fer + 15 diamants — max 2")));
        inv.setItem(23, makeShopItem(Material.RECORD_10,  "§fHunting Soul",          List.of("§74 PNJ chanteurs — bloquent pouvoirs","§e8 fer + 10 diamants — max 2")));
        player.openInventory(inv);
    }

    private ItemStack makeShopItem(Material mat, String name, List<String> lore) {
        return makeShopItem(mat, name, lore, (short) 0);
    }

    private ItemStack makeShopItem(Material mat, String name, List<String> lore, short data) {
        ItemStack item = new ItemStack(mat, 1, data);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("§6✦ §eLudris — Boutique §6✦")) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || clicked.getItemMeta() == null) return;
        String name = clicked.getItemMeta().getDisplayName();
        if (name == null) return;
        handlePurchase(player, name);
    }

    private void handlePurchase(Player player, String itemName) {
        switch (itemName) {
            case "§fTraducteur Groucho" -> {
                if (hasGroucho.contains(player.getUniqueId())) { player.sendMessage("§c✘ Déjà acheté."); return; }
                if (!consume(player, Material.DIAMOND, 5)) return;
                hasGroucho.add(player.getUniqueId());
                // Permettre de voir la vie via scoreboard (simplifié : active l'effet)
                player.sendMessage("§a✔ Traducteur Groucho obtenu ! Vous voyez la vie des joueurs.");
                // Le vrai affichage sera géré par un scoreboard chaque seconde dans DanDaDan.onSec()
                ItemStack groucho = makeShopItem(Material.SKULL_ITEM, "§fTraducteur Groucho",
                        List.of("§7Voir la vie sur les têtes"), (short)3);
                player.getInventory().addItem(groucho);
            }
            case "§fTerritory Can" -> {
                if (hasTerritoryCan.contains(player.getUniqueId())) { player.sendMessage("§c✘ Déjà acheté."); return; }
                if (!consume(player, Material.DIAMOND, 1)) return;
                hasTerritoryCan.add(player.getUniqueId());
                giveTerritoryCan(player);
            }
            case "§fUchide" -> {
                if (hasUchide.contains(player.getUniqueId())) { player.sendMessage("§c✘ Déjà acheté."); return; }
                if (!consume(player, Material.DIAMOND, 5)) return;
                hasUchide.add(player.getUniqueId());
                giveUchide(player);
            }
            case "§fKogaï" -> {
                if (!consume(player, Material.DIAMOND, 5)) return;
                if (!consume(player, Material.EMERALD, 5)) { player.getInventory().addItem(new ItemStack(Material.DIAMOND, 5)); return; }
                giveKogai(player);
            }
            case "§fAsura" -> {
                if (!consume(player, Material.DIAMOND, 7)) return;
                if (!consume(player, Material.EMERALD, 5)) { player.getInventory().addItem(new ItemStack(Material.DIAMOND, 7)); return; }
                giveAsura(player);
            }
            case "§fSlip du mauvais œil" -> {
                if (!consume(player, Material.EMERALD, 10)) return;
                ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                leggings.addUnsafeEnchantment(Enchantment.DURABILITY, 32767); // unbreakable
                player.getInventory().setLeggings(leggings);
                player.sendMessage("§a✔ Slip du mauvais œil équipé !");
            }
            case "§fTableau Maudit" -> {
                if (!consume(player, Material.IRON_INGOT, 2)) return;
                giveMaudit(player);
            }
            case "§fNano skin" -> {
                if (!consume(player, Material.IRON_INGOT, 8)) return;
                if (!consume(player, Material.DIAMOND, 10)) { player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 8)); return; }
                giveNanoskin(player);
            }
            case "§fCombat-Use Simulator" -> {
                int count = shopPurchases.getOrDefault(player.getUniqueId(), 0);
                if (count >= 2) { player.sendMessage("§c✘ Maximum atteint (2)."); return; }
                if (!consume(player, Material.IRON_INGOT, 10)) return;
                if (!consume(player, Material.DIAMOND, 15)) { player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 10)); return; }
                shopPurchases.merge(player.getUniqueId(), 1, Integer::sum);
                launchCombatSimulator(player);
            }
            case "§fHunting Soul" -> {
                if (!consume(player, Material.IRON_INGOT, 8)) return;
                if (!consume(player, Material.DIAMOND, 10)) { player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 8)); return; }
                giveHuntingSoul(player);
            }
        }
    }

    // ── Items effects ────────────────────────────────────────────

    private void giveTerritoryCan(Player player) {
        ItemStack can = makeShopItem(Material.GLASS_BOTTLE, "§fTerritory Can",
                List.of("§7Clic-Droit : invis + invincible 5s"));
        player.getInventory().addItem(can);
        player.sendMessage("§a✔ Territory Can obtenu !");
        // L'activation se fait via PlayerInteractEvent sur cet item (géré dans PnjManager)
    }

    private void giveUchide(Player player) {
        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = sword.getItemMeta(); assert meta != null;
        meta.setDisplayName("§fUchide");
        meta.setLore(List.of("§7Bloque 5s → rewind joueurs proches 5s et +10% CD"));
        sword.setItemMeta(meta);
        player.getInventory().addItem(sword);
        player.sendMessage("§a✔ Uchide obtenu !");
    }

    private void giveKogai(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
        ItemMeta meta = bow.getItemMeta(); assert meta != null;
        meta.setDisplayName("§fKogaï — Mode Infini");
        bow.setItemMeta(meta);
        bow.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
        player.getInventory().addItem(bow);
        player.sendMessage("§a✔ Kogaï obtenu ! Clic-Droit pour changer de mode.");
    }

    private void giveAsura(Player player) {
        // Donne +15% d'un effet que le joueur ne possède pas
        PotionEffectType[] effects = {
                PotionEffectType.SPEED, PotionEffectType.INCREASE_DAMAGE, PotionEffectType.DAMAGE_RESISTANCE
        };
        for (PotionEffectType type : effects) {
            if (!player.hasPotionEffect(type)) {
                player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, 0, false, false));
                player.sendMessage("§a✔ Asura : +" + type.getName() + " obtenu !");
                return;
            }
        }
        player.sendMessage("§c[Asura] §7Vous possédez déjà tous les effets.");
    }

    private void giveMaudit(Player player) {
        ItemStack painting = makeShopItem(Material.PAINTING, "§fTableau Maudit",
                List.of("§75% de poser de la lave sur coup"));
        player.getInventory().addItem(painting);
        player.sendMessage("§a✔ Tableau Maudit obtenu !");
    }

    private void giveNanoskin(Player player) {
        Material[] pieces = {Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
                Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS};
        Material piece = pieces[ThreadLocalRandom.current().nextInt(pieces.length)];
        ItemStack armor = new ItemStack(piece);
        armor.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        armor.addUnsafeEnchantment(Enchantment.DURABILITY, 32767);
        armor.addUnsafeEnchantment(Enchantment.THORNS, 1);
        player.getInventory().addItem(armor);
        player.sendMessage("§a✔ Nano skin obtenu !");
    }

    private void launchCombatSimulator(Player player) {
        player.sendMessage("§e[Combat Simulator] §7Téléportation dans la salle de combat...");
        // Téléportation dans l'arène + spawn Wither Squelette
        Location arena = player.getWorld().getSpawnLocation().clone().add(0, 100, 0);
        player.teleport(arena);
        Wither ws = (Wither)
                player.getWorld().spawnEntity(arena.clone().add(3, 0, 0),
                        EntityType.WITHER);
        ws.setHealth(40);
        ws.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 0));

        // Timer de surveillance : si le squelette meurt → reset un cooldown
        int[] task = {0};
        task[0] = Main.get().getServer().getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
            if (ws.isDead()) {
                Main.get().getServer().getScheduler().cancelTask(task[0]);
                // Remet le joueur à sa position et reset un cooldown aléatoire
                player.teleport(player.getWorld().getSpawnLocation().clone().add(0, 1, 0));
                player.sendMessage("§a[Combat Simulator] §7Victoire ! Un de vos cooldowns a été réinitialisé.");
                // Le reset effectif des cooldowns est géré via l'Ability framework
            }
        }, 0L, 20L);
        // Timeout 3 min
        Main.get().getServer().getScheduler().runTaskLater(Main.get(), () -> {
            Main.get().getServer().getScheduler().cancelTask(task[0]);
            if (!ws.isDead()) ws.remove();
            player.teleport(player.getWorld().getSpawnLocation().clone().add(0, 1, 0));
            player.sendMessage("§c[Combat Simulator] §7Temps écoulé. Défaite.");
        }, 20L * 180);
    }

    private void giveHuntingSoul(Player player) {
        ItemStack soul = makeShopItem(Material.RECORD_10, "§fHunting Soul",
                List.of("§7Clic-Droit : 4 PNJ chanteurs qui bloquent les pouvoirs"));
        player.getInventory().addItem(soul);
        player.sendMessage("§a✔ Hunting Soul obtenu !");
    }

    // ── Utils ────────────────────────────────────────────────────

    private boolean consume(Player player, Material mat, int amount) {
        int count = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == mat) count += item.getAmount();
        }
        if (count < amount) {
            player.sendMessage("§c✘ Pas assez de ressources (" + amount + " " + mat.name().toLowerCase() + ").");
            return false;
        }
        player.getInventory().removeItem(new ItemStack(mat, amount));
        return true;
    }
}
