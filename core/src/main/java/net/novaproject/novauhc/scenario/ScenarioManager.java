package net.novaproject.novauhc.scenario;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.scenario.NoEndLang;
import net.novaproject.novauhc.lang.scenario.NoNetherLang;
import net.novaproject.novauhc.scenario.normal.*;
import net.novaproject.novauhc.scenario.role.scenario.mhdragonfall.DragonFall;
import net.novaproject.novauhc.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ScenarioManager {

    public static ScenarioManager get(){
        return UHCManager.get().getScenarioManager();
    }

    private final List<Scenario> scenarios = new ArrayList<>();

    public void setup(){

        addScenario(new Cutclean());
        addScenario(new HasteyBoy());
        addScenario(new Rodless());
        addScenario(new Timber());
        addScenario(new DoubleOre());
        addScenario(ScenarioBuilder.named("FireLess")
                .description(p -> LangManager.get().get(ScenarioDescLang.FIRE_LESS, p))
                .item(new ItemCreator(Material.MAGMA_CREAM))
                .onDamage((player, event) -> {
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE
                            || event.getCause() == EntityDamageEvent.DamageCause.LAVA
                            || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        event.setCancelled(true);
                    }
                })
                .build());
        addScenario(new BloodDiamonds());
        addScenario(ScenarioBuilder.named("No Fall")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_FALL, p))
                .item(new ItemCreator(Material.DIAMOND_BOOTS))
                .onDamage((player, event) -> {
                    if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                        event.setCancelled(true);
                    }
                })
                .build());
        addScenario(new TpMeetup());
        addScenario(ScenarioBuilder.named("WooltoString")
                .description(p -> LangManager.get().get(ScenarioDescLang.WOOL_TO_STRING, p))
                .item(new ItemCreator(Material.WOOL))
                .onGameStart(() -> {
                    for (byte i = 0; i < 16; i++) {
                        MaterialData woolData = new MaterialData(Material.WOOL, i);
                        ShapedRecipe r1 = new ShapedRecipe(new ItemStack(Material.STRING, 1));
                        r1.shape("WW ", "WW ", "   ");
                        r1.setIngredient('W', woolData);
                        Bukkit.getServer().addRecipe(r1);
                        ShapedRecipe r2 = new ShapedRecipe(new ItemStack(Material.STRING, 1));
                        r2.shape(" WW", " WW", "   ");
                        r2.setIngredient('W', woolData);
                        Bukkit.getServer().addRecipe(r2);
                        ShapedRecipe r3 = new ShapedRecipe(new ItemStack(Material.STRING, 1));
                        r3.shape("   ", "WW ", "WW ");
                        r3.setIngredient('W', woolData);
                        Bukkit.getServer().addRecipe(r3);
                        ShapedRecipe r4 = new ShapedRecipe(new ItemStack(Material.STRING, 1));
                        r4.shape("   ", " WW", " WW");
                        r4.setIngredient('W', woolData);
                        Bukkit.getServer().addRecipe(r4);
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("BetaZombie")
                .description(p -> LangManager.get().get(ScenarioDescLang.BETA_ZOMBIE, p))
                .item(new ItemCreator(Material.SKULL_ITEM))
                .onEntityDeath((entity, killer, e) -> {
                    if (e.getEntityType() == EntityType.ZOMBIE) {
                        e.getDrops().clear();
                        e.getDrops().add(new ItemStack(Material.FEATHER, 3));
                        e.setDroppedExp(10);
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("CatEye")
                .description(p -> LangManager.get().get(ScenarioDescLang.CAT_EYE, p))
                .item(new ItemCreator(Material.EYE_OF_ENDER))
                .onSec(player -> {
                    boolean hasNightVision = false;
                    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                        if (potionEffect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                            hasNightVision = true;
                            break;
                        }
                    }
                    if (hasNightVision) return;
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
                })
                .build());
        addScenario(new Meetup());
        
        addScenario(ScenarioBuilder.named("Starter-Tools")
                .description(p -> LangManager.get().get(ScenarioDescLang.STARTER_TOOLS, p))
                .item(new ItemCreator(Material.BOOK))
                .onStart(player -> {
                    player.setSaturation(100);
                    ItemCreator gap = new ItemCreator(Material.APPLE).setAmount(64);
                    ItemCreator eau = new ItemCreator(Material.WATER_BUCKET).setAmount(1);
                    ItemCreator block = new ItemCreator(Material.WOOD).setAmount(256);
                    ItemCreator shovel = new ItemCreator(Material.IRON_SPADE).setUnbreakable(true).addEnchantment(Enchantment.DIG_SPEED, 3);
                    ItemCreator pick = new ItemCreator(Material.IRON_PICKAXE).setUnbreakable(true).addEnchantment(Enchantment.DIG_SPEED, 3);
                    ItemCreator axe = new ItemCreator(Material.IRON_AXE).setUnbreakable(true).addEnchantment(Enchantment.DIG_SPEED, 3);
                    ItemCreator food = new ItemCreator(Material.GOLDEN_CARROT).setAmount(64);
                    ItemCreator book = new ItemCreator(Material.BOOK).setAmount(7);
                    PlayerInventory inventory = player.getInventory();
                    inventory.addItem(food.getItemstack());
                    inventory.addItem(gap.getItemstack());
                    inventory.addItem(eau.getItemstack());
                    inventory.addItem(axe.getItemstack());
                    inventory.addItem(pick.getItemstack());
                    inventory.addItem(shovel.getItemstack());
                    inventory.addItem(book.getItemstack());
                    inventory.addItem(block.getItemstack());
                })
                .build());
        addScenario(ScenarioBuilder.named("InfiniteEnchanter")
                .description(p -> LangManager.get().get(ScenarioDescLang.INFINITE_ENCHANTER, p))
                .item(new ItemCreator(Material.ENCHANTMENT_TABLE))
                .onStart(player -> player.setLevel(10000000))
                .build());

        addScenario(new FinalHeal());
        addScenario(ScenarioBuilder.named("BlackArrow")
                .description(p -> LangManager.get().get(ScenarioDescLang.BLACK_ARROW, p))
                .item(new ItemCreator(Material.ARROW))
                .onBreak((player, block, event) -> {
                    Location loc = block.getLocation().clone().add(0.5D, 0.5D, 0.5D);
                    if (block.getType() == Material.COAL_ORE) {
                        loc.getWorld().dropItemNaturally(loc, new ItemStack(Material.ARROW));
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("NoFood")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_FOOD, p))
                .item(new ItemCreator(Material.GOLDEN_CARROT))
                .noFood(event -> {
                    event.setCancelled(true);
                    event.setFoodLevel(20);
                })
                .build());

        addScenario(new BatRoulette());
        addScenario(new WebCage());
        addScenario(new TimeBombe());

        addScenario(ScenarioBuilder.named("SwitchArrow")
                .description(p -> LangManager.get().get(ScenarioDescLang.ARROW_SWITCH, p))
                .item(new ItemCreator(Material.ARROW))
                .onHit((entity, dammager, event) -> {
                    if (entity.getType() != EntityType.PLAYER) return;
                    if (dammager instanceof Arrow f) {
                        Player taper = (Player) event.getEntity();
                        if (f.getShooter() instanceof Player shooter) {
                            Location personne_taper = taper.getLocation();
                            Location personne_tappant = shooter.getLocation();
                            taper.teleport(personne_tappant);
                            shooter.teleport(personne_taper);
                        }
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("NetherLess")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_NETHER, p))
                .item(new ItemCreator(Material.NETHERRACK))
                .onPortal(event -> {
                    Optional<Scenario> netheriBus = ScenarioManager.get().getScenarioByName("NetheriBus");
                    if (netheriBus.isPresent() && ScenarioManager.get().getActiveScenarios().contains(netheriBus.get())) return;
                    if (event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(LangManager.get().get(NoNetherLang.BLOCKED, event.getPlayer()));
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("EndLess")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_END, p))
                .item(new ItemCreator(Material.ENDER_STONE))
                .onPortal(event -> {
                    if (event.getCause() == PlayerPortalEvent.TeleportCause.END_PORTAL) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(LangManager.get().get(NoEndLang.BLOCKED, event.getPlayer()));
                    }
                })
                .build());
        addScenario(new LongShoot());
        addScenario(new BloodCycle());
        addScenario(new GapRoulette());

        addScenario(ScenarioBuilder.named("NoWoodenTool")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_WOODEN_TOOL, p))
                .item(new ItemCreator(Material.WOOD_SPADE))
                .onCraft((result, event) -> {
                    ItemStack item = event.getInventory().getResult();
                    if (item.getType() == Material.WOOD_SPADE) {
                        event.getInventory().setResult(new ItemStack(Material.IRON_SPADE));
                    }
                    if (item.getType() == Material.WOOD_PICKAXE) {
                        event.getInventory().setResult(new ItemStack(Material.IRON_PICKAXE));
                    }
                    if (item.getType() == Material.WOOD_AXE) {
                        event.getInventory().setResult(new ItemStack(Material.IRON_AXE));
                    }
                    if (item.getType() == Material.WOOD_SWORD) {
                        event.getInventory().setResult(new ItemStack(Material.IRON_SWORD));
                    }
                })
                .build());
        addScenario(ScenarioBuilder.named("No Notch Gap")
                .item(new ItemCreator(Material.GOLDEN_APPLE).setDurability((short) 1))

                .onCraft((result, event) -> {
                    if(result.getType() == Material.GOLDEN_APPLE){
                        if(result.getDurability() == 1){
                            event.setCancelled(true);
                        }
                    }
                }).build()
        );
        addScenario(new AutoRevive());
        addScenario(ScenarioBuilder.named("FastFurnace")
                .description(p -> LangManager.get().get(ScenarioDescLang.FAST_FURNACE, p))
                .item(new ItemCreator(Material.FURNACE))
                .onFurnaceBurn(event -> {
                    Furnace block = (Furnace) event.getBlock().getState();
                    new BukkitRunnable() {
                        public void run() {
                            if ((block.getCookTime() > 0) || (block.getBurnTime() > 0)) {
                                block.setCookTime((short) (block.getCookTime() + 4));
                                block.update();
                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimer(Main.get(), 1L, 1L);
                })
                .build());
        addScenario(new NoCleanUp());
        addScenario(ScenarioBuilder.named("Chat PvP")
                .description(p -> LangManager.get().get(ScenarioDescLang.CHAT_PVP, p))
                .item(new ItemCreator(Material.BOOK_AND_QUILL))
                .onSec(p -> {
                    int timer = UHCManager.get().getTimer();
                    int timerpvp = UHCManager.get().getTimerpvp();
                    if (timer == timerpvp) {
                        UHCManager.get().setChatdisbale(true);
                        LangManager.get().send(CommonLang.CHAT_DISABLED, p);
                    }
                })
                .build());
        addScenario(new SafeMiner());
        addScenario(new TeamInv());
        addScenario(ScenarioBuilder.named("HorseLess")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_HORSE, p))
                .item(new ItemCreator(Material.SADDLE))
                .onPlayerInteractEntity((player, event) -> {
                    if (event.getRightClicked().getType() == EntityType.HORSE) {
                        event.setCancelled(true);
                        LangManager.get().send(CommonLang.DISABLE_ACTION, player);
                    }
                })
                .build());
        
        addScenario(ScenarioBuilder.named("NoNameTag")
                .description(p -> LangManager.get().get(ScenarioDescLang.NO_NAME_TAG, p))
                .item(new ItemCreator(Material.NAME_TAG))
                .onStart(player -> player.setCustomNameVisible(false))
                .build());
        addScenario(new BestPvE());
        addScenario(new HeathCharity());
        addScenario(new Compensation());
        addScenario(new Cripple());
        addScenario(new DeathEmerauld());
        addScenario(new XpSansue());
        addScenario(ScenarioBuilder.named("FastMiner")
                .description(p -> LangManager.get().get(ScenarioDescLang.FAST_MINER, p))
                .item(new ItemCreator(Material.IRON_PICKAXE))
                .onSec(p -> {
                    int timer = UHCManager.get().getTimer();
                    if (timer < UHCManager.get().getTimerpvp()) {
                        PotionEffect[] getEff = new PotionEffect[]{
                                new PotionEffect(PotionEffectType.SATURATION, 80, 0, false, false),
                                new PotionEffect(PotionEffectType.FAST_DIGGING, 80, 1, false, false),
                                new PotionEffect(PotionEffectType.SPEED, 80, 1, false, false),
                        };
                        for (PotionEffect activeEffect : getEff) {
                            p.removePotionEffect(activeEffect.getType());
                        }
                        for (PotionEffect effect : getEff) {
                            effect.apply(p);
                        }
                    }
                })
                .build());
        addScenario(new GoldenHead());
        addScenario(new GoldenDrop());
        addScenario(new HasteyBabie());
        addScenario(new HasteyAlpha());
        addScenario(new BuffKiller());
        addScenario(new TeamArrow());
        

        addScenario(new Vampire());
        addScenario(new Gladiator());
        addScenario(new BloodLust());
        addScenario(new WeakestLink());


        addScenario(new OreSwap());
        addScenario(new OreRoulette());
        addScenario(new Magnet());
        addScenario(new LuckyOre());
        addScenario(new Transmutation());

        addScenario(new AcidRain());
        addScenario(new Blizzard());

        addScenario(new ParkourMaster());
        addScenario(new SimonSays());
        addScenario(new Democracy());

        addScenario(new Inventors());
        addScenario(new BlockRush());

        addScenario(new NineSlot());
        addScenario(new Fallout());



        addScenario(new Genie());
        addScenario(new PotentialPermanent());
        addScenario(new LootCrate());



        
        
    }


    public Optional<Scenario> getScenarioByName(String name){

        for(Scenario s : scenarios){
            if(s.getName().equalsIgnoreCase(name)){
                return Optional.of(s);
            }
        }

        return Optional.empty();
    }

    public List<Scenario> getScenarios(){
        return scenarios;
    }

    public List<Scenario> getActiveScenarios(){
        List<Scenario> result = new ArrayList<>();

        for(Scenario s : scenarios){
            if(s.isActive()){
                result.add(s);
            }
        }

        return result;
    }

    public boolean isScenarioActive(String scenarioName) {
        return getScenarioByName(scenarioName).map(Scenario::isActive).orElse(false);
    }

    public List<Scenario> getSpecialScenarios() {
        List<Scenario> result = new ArrayList<>();

        for (Scenario s : scenarios) {
            if (s.isSpecial()) {
                result.add(s);
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends Scenario> T getScenario(Class<T> clazz) {
        for (Scenario scenario : scenarios) {
            if (clazz.isInstance(scenario)) {
                return (T) scenario;
            }
        }
        return null;
    }


    public List<Scenario> getActiveSpecialScenarios() {

        List<Scenario> result = new ArrayList<>();

        for (Scenario s : getSpecialScenarios()) {
            if (s.isActive()) {
                result.add(s);
            }
        }

        return result;

    }

    public void addScenario(Scenario scenario){

        scenarios.add(scenario);
        scenario.setup();

    }
}
