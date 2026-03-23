package net.novaproject.novauhc.scenario;

import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Builder fluent pour créer des scénarios simples sans nécessiter une classe dédiée.
 *
 * <pre>
 * ScenarioManager.get().addScenario(
 *     ScenarioBuilder.named("NoFallDamage")
 *         .description(p -> "Pas de dégâts de chute")
 *         .item(new ItemCreator(Material.FEATHER).setName("NoFall"))
 *         .onDamage((player, event) -> {
 *             if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
 *         })
 *         .build()
 * );
 * </pre>
 */
public class ScenarioBuilder {

    private final String name;
    private Function<Player, String> description;
    private ItemCreator item;

    // Callbacks optionnels
    private TriConsumer<Player, Block, BlockBreakEvent> onBreak;
    private TriConsumer<Entity, Player, EntityDeathEvent> onEntityDeath;
    private Consumer<Player> onSec;
    private Consumer<Player> onStart;
    private Runnable onGameStart;
    private TriConsumer<UHCPlayer, UHCPlayer, PlayerDeathEvent> onDeath;
    private BiConsumer<ItemStack, CraftItemEvent> onCraft;
    private Consumer<PlayerDropItemEvent> onDrop;
    private BiConsumer<Player, PlayerInteractEvent> onPlayerInteract;
    private TriConsumer<Player, Block, BlockPlaceEvent> onPlace;
    private BiConsumer<Player, PlayerMoveEvent> onMove;
    private TriConsumer<Entity, Player, EntityShootBowEvent> onBow;
    private TriConsumer<Entity, Entity, EntityDamageByEntityEvent> onHit;
    private BiConsumer<Player, EntityDamageEvent> onDamage;
    private TriConsumer<Player, ItemStack, PlayerItemConsumeEvent> onConsume;
    private TriConsumer<Player, String, AsyncPlayerChatEvent> onChatSpeak;
    private BiConsumer<UHCPlayer, UHCPlayer> onKill;
    private TriConsumer<Player, Item, PlayerPickupItemEvent> onPickUp;
    private Consumer<FoodLevelChangeEvent> noFood;
    private Consumer<PlayerPortalEvent> onPortal;
    private Consumer<FurnaceBurnEvent> onFurnaceBurn;
    private BiConsumer<Player, PlayerInteractEntityEvent> onPlayerInteractEntity;

    private ScenarioBuilder(String name) {
        this.name = name;
        this.description = p -> name;

        this.item = new ItemCreator(org.bukkit.Material.PAPER).setName(name);
    }

    public static ScenarioBuilder named(String name) {
        return new ScenarioBuilder(name);
    }


    public ScenarioBuilder description(Function<Player, String> description) {
        this.description = description;
        return this;
    }

    public ScenarioBuilder description(String description) {
        this.description = p -> description;
        return this;
    }


    public ScenarioBuilder item(ItemCreator item) {
        this.item = item;
        return this;
    }

    public ScenarioBuilder onBreak(TriConsumer<Player, Block, BlockBreakEvent> handler) {
        this.onBreak = handler;
        return this;
    }

    public ScenarioBuilder onEntityDeath(TriConsumer<Entity, Player, EntityDeathEvent> handler) {
        this.onEntityDeath = handler;
        return this;
    }

    public ScenarioBuilder onStart(Consumer<Player> handler) {
        this.onStart = handler;
        return this;
    }

    public ScenarioBuilder onSec(Consumer<Player> handler) {
        this.onSec = handler;
        return this;
    }

    public ScenarioBuilder onGameStart(Runnable handler) {
        this.onGameStart = handler;
        return this;
    }

    public ScenarioBuilder onDeath(TriConsumer<UHCPlayer, UHCPlayer, PlayerDeathEvent> handler) {
        this.onDeath = handler;
        return this;
    }

    public ScenarioBuilder onCraft(BiConsumer<ItemStack, CraftItemEvent> handler) {
        this.onCraft = handler;
        return this;
    }

    public ScenarioBuilder onDrop(Consumer<PlayerDropItemEvent> handler) {
        this.onDrop = handler;
        return this;
    }

    public ScenarioBuilder onPlayerInteract(BiConsumer<Player, PlayerInteractEvent> handler) {
        this.onPlayerInteract = handler;
        return this;
    }

    public ScenarioBuilder onPlace(TriConsumer<Player, Block, BlockPlaceEvent> handler) {
        this.onPlace = handler;
        return this;
    }

    public ScenarioBuilder onMove(BiConsumer<Player, PlayerMoveEvent> handler) {
        this.onMove = handler;
        return this;
    }

    public ScenarioBuilder onBow(TriConsumer<Entity, Player, EntityShootBowEvent> handler) {
        this.onBow = handler;
        return this;
    }

    public ScenarioBuilder onHit(TriConsumer<Entity, Entity, EntityDamageByEntityEvent> handler) {
        this.onHit = handler;
        return this;
    }

    public ScenarioBuilder onDamage(BiConsumer<Player, EntityDamageEvent> handler) {
        this.onDamage = handler;
        return this;
    }

    public ScenarioBuilder onConsume(TriConsumer<Player, ItemStack, PlayerItemConsumeEvent> handler) {
        this.onConsume = handler;
        return this;
    }

    public ScenarioBuilder onChatSpeak(TriConsumer<Player, String, AsyncPlayerChatEvent> handler) {
        this.onChatSpeak = handler;
        return this;
    }

    public ScenarioBuilder onKill(BiConsumer<UHCPlayer, UHCPlayer> handler) {
        this.onKill = handler;
        return this;
    }

    public ScenarioBuilder onPickUp(TriConsumer<Player, Item, PlayerPickupItemEvent> handler) {
        this.onPickUp = handler;
        return this;
    }

    public ScenarioBuilder noFood(Consumer<FoodLevelChangeEvent> handler) {
        this.noFood = handler;
        return this;
    }

    public ScenarioBuilder onPortal(Consumer<PlayerPortalEvent> handler) {
        this.onPortal = handler;
        return this;
    }

    public ScenarioBuilder onFurnaceBurn(Consumer<FurnaceBurnEvent> handler) {
        this.onFurnaceBurn = handler;
        return this;
    }

    public ScenarioBuilder onPlayerInteractEntity(BiConsumer<Player, PlayerInteractEntityEvent> handler) {
        this.onPlayerInteractEntity = handler;
        return this;
    }

    public Scenario build() {
        final ScenarioBuilder b = this;

        return new Scenario() {
            @Override public String getName() { return b.name; }
            @Override public String getDescription(Player player) { return b.description.apply(player); }
            @Override public ItemCreator getItem() { return new ItemCreator(b.item); }

            @Override
            public void onBreak(Player player, Block block, BlockBreakEvent event) {
                if (b.onBreak != null) b.onBreak.accept(player, block, event);
            }


            @Override
            public void onEntityDeath(Entity entity, Player killer, EntityDeathEvent event) {
                if (b.onEntityDeath != null) b.onEntityDeath.accept(entity, killer, event);
            }
            @Override
            public void onStart(Player player) {
                if (b.onStart != null) b.onStart.accept(player);
            }
            @Override
            public void onSec(Player p) {
                if (b.onSec != null) b.onSec.accept(p);
            }
            @Override
            public void onGameStart() {
                if (b.onGameStart != null) b.onGameStart.run();
            }
            @Override
            public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
                if (b.onDeath != null) b.onDeath.accept(uhcPlayer, killer, event);
            }
            @Override
            public void onCraft(ItemStack result, CraftItemEvent event) {
                if (b.onCraft != null) b.onCraft.accept(result, event);
            }
            @Override
            public void onDrop(PlayerDropItemEvent event) {
                if (b.onDrop != null) b.onDrop.accept(event);
            }
            @Override
            public void onPlayerInteract(Player player, PlayerInteractEvent event) {
                if (b.onPlayerInteract != null) b.onPlayerInteract.accept(player, event);
            }
            @Override
            public void onPlace(Player player, Block block, BlockPlaceEvent event) {
                if (b.onPlace != null) b.onPlace.accept(player, block, event);
            }
            @Override
            public void onMove(Player player, PlayerMoveEvent event) {
                if (b.onMove != null) b.onMove.accept(player, event);
            }
            @Override
            public void onBow(Entity entity, Player player, EntityShootBowEvent event) {
                if (b.onBow != null) b.onBow.accept(entity, player, event);
            }
            @Override
            public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
                if (b.onHit != null) b.onHit.accept(entity, dammager, event);
            }
            @Override
            public void onDamage(Player player, EntityDamageEvent event) {
                if (b.onDamage != null) b.onDamage.accept(player, event);
            }
            @Override
            public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
                if (b.onConsume != null) b.onConsume.accept(player, item, event);
            }
            @Override
            public void onChatSpeak(Player player, String message, AsyncPlayerChatEvent event) {
                if (b.onChatSpeak != null) b.onChatSpeak.accept(player, message, event);
            }
            @Override
            public void onKill(UHCPlayer killer, UHCPlayer victim) {
                if (b.onKill != null) b.onKill.accept(killer, victim);
            }
            @Override
            public void onPickUp(Player player, Item item, PlayerPickupItemEvent event) {
                if (b.onPickUp != null) b.onPickUp.accept(player, item, event);
            }
            @Override
            public void noFood(FoodLevelChangeEvent event) {
                if (b.noFood != null) b.noFood.accept(event);
            }
            @Override
            public void onPortal(PlayerPortalEvent event) {
                if (b.onPortal != null) b.onPortal.accept(event);
            }
            @Override
            public void onFurnaceBurn(FurnaceBurnEvent event) {
                if (b.onFurnaceBurn != null) b.onFurnaceBurn.accept(event);
            }
            @Override
            public void onPlayerInteractEntity(Player player, PlayerInteractEntityEvent event) {
                if (b.onPlayerInteractEntity != null) b.onPlayerInteractEntity.accept(player, event);
            }
        };
    }

    /** Interface fonctionnelle à 3 paramètres (manquante en Java standard). */
    @FunctionalInterface
    public interface TriConsumer<A, B, C> {
        void accept(A a, B b, C c);
    }
}
