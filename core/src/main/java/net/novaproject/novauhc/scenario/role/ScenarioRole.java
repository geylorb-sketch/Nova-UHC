package net.novaproject.novauhc.scenario.role;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.ability.template.ReviveAbility;
import net.novaproject.novauhc.lang.lang.CommonLang;
import net.novaproject.novauhc.scenario.RoleVariableProcessor;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import net.novaproject.novauhc.scenario.role.ui.ScenarioCampUi;
import net.novaproject.novauhc.scenario.role.ui.ScenarioRoleDispatcherUi;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.utils.ui.CustomInventory;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;
import java.util.stream.Collectors;

public abstract class ScenarioRole<T extends Role> extends Scenario {

    @ScenarioVariable(lang = net.novaproject.novauhc.lang.lang.ScenarioVarLang.class, nameKey = "ROLEREVIVE_VAR_DELAY_NAME", descKey = "ROLEREVIVE_VAR_DELAY_DESC", type = VariableType.TIME)
    private int reviveDelay = 10;


    private final Map<Class<? extends T>, Integer> default_roles = new HashMap<>();
    private final Map<Class<? extends T>, T> roleConfigs = new HashMap<>();


    private final Map<UHCPlayer, T> players_roles = new HashMap<>();
    private boolean isgived = false;
    private WinCondition<T> winCondition = null;


    private final Map<UUID, PendingReviveData> pendingRevives = new HashMap<>();

    private static class PendingReviveData {
        final Location location;
        final Collection<PotionEffect> effects;
        final List<ItemStack> items;
        final int taskId;
        final UHCPlayer uhcPlayer;
        final UHCPlayer killer;

        PendingReviveData(Location location, Collection<PotionEffect> effects, List<ItemStack> items,
                          int taskId, UHCPlayer uhcPlayer, UHCPlayer killer) {
            this.location = location;
            this.effects = effects;
            this.items = items;
            this.taskId = taskId;
            this.uhcPlayer = uhcPlayer;
            this.killer = killer;
        }
    }

    public abstract Camps[] getCamps();

    public void setWinCondition(WinCondition<T> condition) { this.winCondition = condition; }


    public void addRole(Class<? extends T> roleClass) {
        try {
            T role = roleClass.getDeclaredConstructor().newInstance();
            roleConfigs.put(roleClass, role);
            default_roles.put(roleClass, 0);
        } catch (Exception e) {
            throw new RuntimeException("Cannot register roles " + roleClass.getName(), e);
        }
    }

    public int getRoleAmount(Class<? extends T> roleClass) {
        return default_roles.getOrDefault(roleClass, 0);
    }

    public void incrementRole(Class<? extends T> roleClass) {
        default_roles.put(roleClass, getRoleAmount(roleClass) + 1);
    }

    public void decrementRole(Class<? extends T> roleClass) {
        int current = getRoleAmount(roleClass);
        if (current > 0) {
            default_roles.put(roleClass, current - 1);
        }
    }

    public void setRoleAmout(Class<? extends T> roleClass,int amont){
        default_roles.replace(roleClass,amont);
    }

    public Map<T, Integer> getDefault_roles() {
        Map<T, Integer> result = new TreeMap<>(Comparator.comparing(Role::getName));
        default_roles.forEach((clazz, amount) ->
                result.put(roleConfigs.get(clazz), amount)
        );
        return result;
    }

    public Document getRolesDocument() {
        Document doc = new Document();
        Map<String, Integer> rolesList = new HashMap<>();
        default_roles.forEach((clazz, amount) -> {
            T role = roleConfigs.get(clazz);
            if(amount > 0) rolesList.put(role.getName(),amount);

        });
        if(!rolesList.isEmpty()) doc.append("roles", rolesList);

        Map<String,Document> roleConfigsDoc = new HashMap<>();
        roleConfigs.forEach((clazz, role) -> {
            Document roleDoc = role.roleToDoc();
            if(!roleDoc.isEmpty()){
                roleConfigsDoc.put(role.getName(),roleDoc);
            }
        });
        if(!roleConfigsDoc.isEmpty()) doc.append("roleConfigs",roleConfigsDoc);



        return doc;
    }

    public void loadRolesDocument(Document doc) {
        if (doc == null) return;

        if(doc.containsKey("roles")){
            Document rolesDoc = (Document) doc.get("roles");
            rolesDoc.forEach((roleName, amountObj) -> {
                int amount;
                if(amountObj instanceof Integer i) amount = i;
                else if(amountObj instanceof Double d) amount = d.intValue();
                else {
                    amount = 0;
                }

                roleConfigs.forEach((clazz, role) -> {
                    if(role.getName().equals(roleName)){
                        default_roles.put(clazz, amount);
                    }
                });
            });
        }

        if(doc.containsKey("roleConfigs")){
            Document roleConfigsDoc = (Document) doc.get("roleConfigs");
            roleConfigsDoc.forEach((roleName, roleDocObj) -> {
                if(roleDocObj instanceof Document roleDoc){
                    roleConfigs.forEach((clazz, role) -> {
                        if(role.getName().equals(roleName)){
                            role.docToRole(roleDoc);
                        }
                    });
                }
            });
        }
    }

    public void giveRoles() {

        Bukkit.broadcastMessage(LangManager.get().get(CommonLang.GIVING_ROLES));

        List<T> pool = new ArrayList<>();

        default_roles.forEach((clazz, amount) -> {
            T base = roleConfigs.get(clazz);
            for (int i = 0; i < amount; i++) {
                pool.add((T) base.clone());
            }
        });

        Collections.shuffle(pool, new Random());

        for (UHCPlayer player : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            if (pool.isEmpty()) break;

            T role = pool.remove(0);
            players_roles.put(player, role);
            role.setOwner(player);
            RoleVariableProcessor.process(role,player,this);
            role.onGive(player);
        }

        isgived = true;
    }

    public void giveRole(UHCPlayer player, T role) {
        players_roles.put(player, role);
        RoleVariableProcessor.process(role,player,this);
        role.onGive(player);
    }

    public T getRoleByUHCPlayer(UHCPlayer player) {
        return players_roles.get(player);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public CustomInventory getMenu(Player player) {
        for (java.lang.reflect.Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(net.novaproject.novauhc.scenario.ScenarioVariable.class)) {
                return new ScenarioRoleDispatcherUi<>(player, this);
            }
        }
        return new ScenarioCampUi<>(player, this);
    }

    @Override
    public void onSec(Player p) {
        int timer = UHCManager.get().getTimer();

        players_roles.forEach((player, role) -> role.onSec(p));

        if (!isgived && timer == UHCManager.get().getTimerpvp()) {
            giveRoles();
        }
    }

    @Override
    public void setup() {
        super.setup();
        players_roles.forEach((player, role) -> role.onSetup());
    }

    @Override
    public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
        players_roles.forEach((p, role) -> role.onConsume(player, item, event));
    }

    @Override
    public void onDrop(PlayerDropItemEvent event) {
        players_roles.forEach((p, role) -> role.onDrop(p,event));
    }

    @Override
    public void onPlayerInteract(Player player, PlayerInteractEvent event) {
        players_roles.forEach((p, role) -> role.onInteract(player, event));
    }

    @Override
    public void onMove(Player player, PlayerMoveEvent event) {
        players_roles.forEach((p, role) -> role.onMove(p, event));
    }

    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        players_roles.forEach((p, role) -> role.onHit(entity, dammager, event));
    }

    @Override
    public void onBow(Entity entity, Player player, EntityShootBowEvent event) {
        players_roles.forEach((p,role) -> role.onBow(entity,player,event));
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        players_roles.forEach((p, role) -> role.onKill(killer, victim));
    }

    public List<UHCPlayer> getPlayersByRoleName(String name) {
        List<UHCPlayer> list = new ArrayList<>();
        players_roles.forEach((player, role) -> {
            if (role.getName().equals(name)) list.add(player);
        });
        return list;
    }

    public Camps getWinningCamp() {
        Map<Camps, Integer> counts = new HashMap<>();

        players_roles.forEach((player, role) -> {
            Camps camp = role.getCamp();
            counts.put(camp, counts.getOrDefault(camp, 0) + 1);
        });

        return counts.size() == 1 ? counts.keySet().iterator().next() : null;
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        players_roles.forEach((p, role) -> role.onDeath(uhcPlayer, killer, event));
    }

    @Override
    public boolean isWin() {
        if (winCondition != null) return winCondition.isWin(players_roles);
        Map<Camps, Integer> campCounts = new HashMap<>();

        for (UHCPlayer uhcPlayer : UHCPlayerManager.get().getPlayingOnlineUHCPlayers()) {
            Role role = getRoleByUHCPlayer(uhcPlayer);
            if (role == null) continue;

            Camps camp = role.getCamp();
            campCounts.put(camp, campCounts.getOrDefault(camp, 0) + 1);
        }

        return campCounts.size() <= 1;
    }

    @Override
    public boolean hascustomDeathMessage() {
        return isgived;
    }

    @Override
    public void sendCustomDeathMessage(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        // Revive window countdown handles the death message on expiry.
        // If no pending revive exists (e.g. scenario was just given but somehow bypassed),
        // broadcast immediately.
        if (!pendingRevives.containsKey(uhcPlayer.getUniqueId())) {
            broadcastRoleDeathMessage(uhcPlayer, killer);
            UHCManager.get().checkVictory();
        }
//         Otherwise the countdown task or completeRevive will call checkVictory.
    }

    @Override
    public void onAfterDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        if (!isgived || UHCManager.get().getGameState() != UHCManager.GameState.INGAME) return;

        Player player = uhcPlayer.getPlayer();
        if (player == null) return;

        // Save state
        Location location = player.getLocation();
        Collection<PotionEffect> effects = new ArrayList<>(player.getActivePotionEffects());
        List<ItemStack> items = new ArrayList<>(event.getDrops());
        event.getDrops().clear();

        List<UHCPlayer> eligibleRevivers = players_roles.keySet().stream()
                .filter(p -> !p.equals(uhcPlayer) && p.isPlaying() && p.getPlayer() != null)
                .filter(p -> {
                    Role r = players_roles.get(p);
                    if (r == null) return false;
                    return r.getAbilities().stream()
                            .anyMatch(a -> a instanceof ReviveAbility ra && ra.canRevive(p, uhcPlayer));
                })
                .collect(Collectors.toList());

        for (Player online : Bukkit.getOnlinePlayers()) {
            String baseMsg = LangManager.get().get(CommonLang.REVIVE_PENDING_MSG, online)
                    .replace("%player%", player.getName());

            UHCPlayer onlineUHC = UHCPlayerManager.get().getPlayer(online);
            boolean isReviver = onlineUHC != null && eligibleRevivers.contains(onlineUHC);

            if (isReviver) {
                TextComponent base = new TextComponent(TextComponent.fromLegacyText(baseMsg));
                TextComponent btn = new TextComponent(TextComponent.fromLegacyText(
                        LangManager.get().get(CommonLang.REVIVE_PENDING_BTN, online)
                ));
                btn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/revive " + player.getName()));
                btn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder(LangManager.get().get(CommonLang.REVIVE_PENDING_HOVER, online)
                                .replace("%player%", player.getName())).create()));
                base.addExtra(btn);
                online.spigot().sendMessage(base);
            }
        }

        int taskId = Bukkit.getScheduler().runTaskLater(Main.get(), () -> {
            PendingReviveData data = pendingRevives.remove(uhcPlayer.getUniqueId());
            if (data == null) return;

            // Drop saved items at death location
            for (ItemStack item : data.items) {
                if (item != null && item.getType() != org.bukkit.Material.AIR) {
                    data.location.getWorld().dropItemNaturally(data.location, item);
                }
            }

            broadcastRoleDeathMessage(data.uhcPlayer, data.killer);
            UHCManager.get().checkVictory();
        }, reviveDelay * 20L).getTaskId();

        pendingRevives.put(uhcPlayer.getUniqueId(), new PendingReviveData(
                location, effects, items, taskId, uhcPlayer, killer
        ));
    }

    public PendingReviveData getPendingRevive(UUID uuid) {
        return pendingRevives.get(uuid);
    }

    public boolean completeRevive(UHCPlayer target, UHCPlayer reviver, ReviveAbility ability) {
        PendingReviveData data = pendingRevives.remove(target.getUniqueId());
        if (data == null) return false;

        Bukkit.getScheduler().cancelTask(data.taskId);

        Player targetPlayer = target.getPlayer();
        if (targetPlayer != null) {
            target.setPlaying(true);
            targetPlayer.setGameMode(GameMode.SURVIVAL);
            targetPlayer.teleport(data.location);
            for (PotionEffect effect : data.effects) {
                targetPlayer.addPotionEffect(effect);
            }
            for (ItemStack item : data.items) {
                if (item != null && item.getType() != org.bukkit.Material.AIR) {
                    targetPlayer.getInventory().addItem(item);
                }
            }
        }

        ability.onRevive(reviver, target);

        Player reviverPlayer = reviver.getPlayer();
        String reviverName = reviverPlayer != null ? reviverPlayer.getName() : "?";
        String targetName = targetPlayer != null ? targetPlayer.getName() : "?";
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(LangManager.get().get(CommonLang.REVIVE_SUCCESS, online)
                    .replace("%player%", targetName)
                    .replace("%reviver%", reviverName));
        }

        UHCManager.get().checkVictory();
        return true;
    }

    private void broadcastRoleDeathMessage(UHCPlayer uhcPlayer, UHCPlayer killer) {
        String message = "§8§m---------" + ChatColor.MAGIC + "jdsqjlkmsqjsqjml§8§m----------§r\n" +
                ChatColor.AQUA + " Quelqu'un est mort : §6" + uhcPlayer.getPlayer().getDisplayName() + "\n" +
                "Il etait : " + getRoleByUHCPlayer(uhcPlayer).getColor() + getRoleByUHCPlayer(uhcPlayer).getName() + "\n" +
                "§8§m---------" + ChatColor.MAGIC + "jdsqjlkmsqjsqjml§8§m----------§r";

        Bukkit.broadcastMessage(message);
    }

    @Override
    public void onStop() {
        pendingRevives.values().forEach(d -> Bukkit.getScheduler().cancelTask(d.taskId));
        pendingRevives.clear();
    }
}
