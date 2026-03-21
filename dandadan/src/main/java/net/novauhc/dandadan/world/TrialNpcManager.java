package net.novauhc.dandadan.world;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrialNpcManager implements Listener {

    private static TrialNpcManager instance;
    public static TrialNpcManager get() {
        if (instance == null) instance = new TrialNpcManager();
        return instance;
    }

    /** Joueur → ID du NPC Citizens */
    private final Map<UUID, Integer> playerNpc = new HashMap<>();

    /** NPC ID → Yokai */
    private final Map<Integer, YokaiRegistry> npcYokai = new HashMap<>();

    /** Joueur → task particules */
    private final Map<UUID, Integer> particleTasks = new HashMap<>();

    public void spawnTrialNpc(Player player, YokaiRegistry yokai, World world) {

        Location npcLoc = player.getLocation().clone()
                .add(player.getLocation().getDirection().normalize().multiply(3));

        npcLoc.getChunk().load();

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(
                EntityType.PLAYER,
                "§5§l✦ " + yokai.name() + " §5§l✦"
        );

        npc.spawn(npcLoc);
        npc.faceLocation(player.getLocation());

        // Sécurité / comportement
        npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, true);
        npc.data().setPersistent(NPC.Metadata.COLLIDABLE, false);

        // Item en main
        try {
            net.novauhc.dandadan.DanDaDanRole roleInst =
                    yokai.getRoleClass().getDeclaredConstructor().newInstance();

            npc.getOrAddTrait(Equipment.class).set(
                    Equipment.EquipmentSlot.HAND,
                    new ItemStack(roleInst.getIconMaterial())
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save
        playerNpc.put(player.getUniqueId(), npc.getId());
        npcYokai.put(npc.getId(), yokai);

        // Particules
        int taskId = Bukkit.getScheduler().runTaskTimer(Main.get(), new Runnable() {

            double angle = 0;

            @Override
            public void run() {

                if (!npc.isSpawned()) return;

                Location center = npc.getEntity().getLocation().clone().add(0, 2, 0);

                for (int i = 0; i < 3; i++) {
                    double a = angle + (i * Math.PI * 2 / 3);
                    double x = Math.cos(a) * 1.2;
                    double z = Math.sin(a) * 1.2;

                    new ParticleBuilder(ParticleEffect.REDSTONE)
                            .setColor(java.awt.Color.MAGENTA)
                            .setLocation(center.clone().add(x, 0, z))
                            .setAmount(2)
                            .display();
                }

                angle += 0.15;
            }

        }, 0L, 3L).getTaskId();

        particleTasks.put(player.getUniqueId(), taskId);

        LangManager.get().send(DanDaDanLang.TRIAL_NPC_SPAWNED, player,
                Map.of("%yokai%", yokai.name()));
    }

    public void removeTrialNpc(Player player) {

        UUID uuid = player.getUniqueId();
        Integer npcId = playerNpc.remove(uuid);

        if (npcId != null) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
            if (npc != null) npc.destroy();

            npcYokai.remove(npcId);
        }

        Integer taskId = particleTasks.remove(uuid);
        if (taskId != null) Bukkit.getScheduler().cancelTask(taskId);
    }

    @EventHandler
    public void onNpcClick(NPCRightClickEvent event) {

        Player player = event.getClicker();
        NPC npc = event.getNPC();

        if (!npcYokai.containsKey(npc.getId())) return;

        Integer expectedNpc = playerNpc.get(player.getUniqueId());

        if (expectedNpc == null || expectedNpc != npc.getId()) {
            LangManager.get().send(DanDaDanLang.TRIAL_NPC_NOT_YOURS, player);
            return;
        }

        YokaiRegistry yokai = npcYokai.get(npc.getId());
        if (yokai == null) return;

        new YokaiPactUi(player, yokai).open();
    }

    public void cleanupAll() {

        for (Integer npcId : npcYokai.keySet()) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(npcId);
            if (npc != null) npc.destroy();
        }

        playerNpc.clear();
        npcYokai.clear();

        particleTasks.values().forEach(Bukkit.getScheduler()::cancelTask);
        particleTasks.clear();
    }
}