package net.novaproject.ultimate.king;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.lang.CommonLang;

import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.special.KingLang;
import net.novaproject.novauhc.scenario.Scenario;

import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class King extends Scenario {

    
    
    

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_KING_MAX_HEALTH_NAME",
            descKey = "KING_VAR_KING_MAX_HEALTH_DESC",
            type = VariableType.INTEGER)
    private int kingMaxHealth = 40;

    
    
    

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_SPEED_ENABLED_NAME",
            descKey = "KING_VAR_SPEED_ENABLED_DESC",
            type = VariableType.BOOLEAN)
    private boolean speedEnabled = true;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_SPEED_LEVEL_NAME",
            descKey = "KING_VAR_SPEED_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int speedLevel = 1;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_STRENGTH_ENABLED_NAME",
            descKey = "KING_VAR_STRENGTH_ENABLED_DESC",
            type = VariableType.BOOLEAN)
    private boolean strengthEnabled = true;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_STRENGTH_LEVEL_NAME",
            descKey = "KING_VAR_STRENGTH_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int strengthLevel = 1;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_RESISTANCE_ENABLED_NAME",
            descKey = "KING_VAR_RESISTANCE_ENABLED_DESC",
            type = VariableType.BOOLEAN)
    private boolean resistanceEnabled = true;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_RESISTANCE_LEVEL_NAME",
            descKey = "KING_VAR_RESISTANCE_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int resistanceLevel = 1;


    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_POISON_ENABLED_NAME",
            descKey = "KING_VAR_POISON_ENABLED_DESC",
            type = VariableType.BOOLEAN)
    private boolean poisonEnabled = true;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_POISON_DURATION_NAME",
            descKey = "KING_VAR_POISON_DURATION_DESC",
            type = VariableType.TIME)
    private int poisonDuration = 120;

    @ScenarioVariable(lang = ScenarioVarLang.class,
            nameKey = "KING_VAR_POISON_LEVEL_NAME",
            descKey = "KING_VAR_POISON_LEVEL_DESC",
            type = VariableType.INTEGER)
    private int poisonLevel = 1;

    
    
    

    private final Set<UUID> kings = new HashSet<>();

    
    
    

    @Override
    public String getName() {
        return "King";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.KING, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.GOLD_BLOCK);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    
    
    

    @Override
    public void toggleActive() {
        super.toggleActive();
        if (isActive()) {
            UHCManager.get().setTeam_size(2);
        } else {
            kings.clear();
            UHCTeamManager.get().deleteTeams();
        }
    }

    @Override
    public void onTeamUpdate() {
        if (UHCManager.get().getTeam_size() == 1) {
            UHCManager.get().setTeam_size(2);
            LangManager.get().sendAll(CommonLang.TEAM_REDFINIED_AUTO);
        }
    }

    
    
    

    @Override
    public void onGameStart() {
        kings.clear();

        for (UHCTeam team : UHCTeamManager.get().getTeams()) {
            List<UHCPlayer> players = team.getPlayers();
            if (players.isEmpty()) continue;

            UHCPlayer king = players.get(ThreadLocalRandom.current().nextInt(players.size()));
            Player kingPlayer = king.getPlayer();
            if (kingPlayer == null) continue;

            kings.add(kingPlayer.getUniqueId());

            int health = Math.max(kingMaxHealth, 2); 
            kingPlayer.setMaxHealth(health);
            kingPlayer.setHealth(health);

            LangManager.get().send(KingLang.YOU_ARE_KING, kingPlayer);

            for (UHCPlayer member : players) {
                Player mp = member.getPlayer();
                if (mp != null && !mp.getUniqueId().equals(kingPlayer.getUniqueId())) {
                    String msg = LangManager.get().get(KingLang.TEAM_KING_ANNOUNCE, mp)
                            .replace("%king%", kingPlayer.getName());
                    mp.sendMessage(msg);
                }
            }
        }
    }

    
    
    

    @Override
    public void scatter(UHCPlayer uhcPlayer, Location location, HashMap<UHCTeam, Location> teamloc) {
        if (UHCManager.get().getTeam_size() > 1) {
            UHCTeamManager.get().scatterTeam(uhcPlayer, teamloc);
        } else {
            Player player = uhcPlayer.getPlayer();
            if (player != null) {
                player.teleport(location);
            }
        }
    }

    
    
    

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        Player player = uhcPlayer.getPlayer();
        if (player == null) return;

        if (!kings.remove(player.getUniqueId())) return;
        if (!uhcPlayer.getTeam().isPresent()) return;

        for (UHCPlayer member : uhcPlayer.getTeam().get().getPlayers()) {
            Player mp = member.getPlayer();
            if (mp == null || !mp.isOnline() || !member.isPlaying()) continue;

            if (poisonEnabled && poisonLevel > 0 && poisonDuration > 0) {
                mp.addPotionEffect(new PotionEffect(
                        PotionEffectType.POISON,
                        poisonDuration * 20,
                        poisonLevel - 1,
                        false, false
                ));
            }
            LangManager.get().send(KingLang.KING_DIED, mp);
        }
    }

    
    
    

    @Override
    public void onSec(Player p) {
        if (p == null || !kings.contains(p.getUniqueId())) return;

        List<PotionEffect> effects = new ArrayList<>();

        if (speedEnabled && speedLevel > 0) {
            effects.add(new PotionEffect(PotionEffectType.SPEED, 80, speedLevel - 1, false, false));
        }
        if (strengthEnabled && strengthLevel > 0) {
            effects.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, strengthLevel - 1, false, false));
        }
        if (resistanceEnabled && resistanceLevel > 0) {
            effects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, resistanceLevel - 1, false, false));
        }

        if (!effects.isEmpty()) {
            UHCUtils.applyInfiniteEffects(effects.toArray(new PotionEffect[0]), p);
        }
    }

    public boolean isKing(Player player) {
        return player != null && kings.contains(player.getUniqueId());
    }
}