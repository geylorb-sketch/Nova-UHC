package net.novaproject.ultimate.superheros;

import net.novaproject.novauhc.UHCManager;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioManager;
import net.novaproject.novauhc.scenario.normal.GoldenHead;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcteam.UHCTeam;
import net.novaproject.novauhc.uhcteam.UHCTeamManager;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.UHCUtils;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Random;

public class SuperHeros extends Scenario {

    private final HashMap<Player, Integer> superHeros = new HashMap<>();

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_SPEED_NAME", descKey = "SUPERHEROS_VAR_SPEED_DESC", type = VariableType.BOOLEAN)
    private boolean enableSpeed = true;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_DAMAGE_NAME", descKey = "SUPERHEROS_VAR_DAMAGE_DESC", type = VariableType.BOOLEAN)
    private boolean enableDamage = true;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_RESISTANCE_NAME", descKey = "SUPERHEROS_VAR_RESISTANCE_DESC", type = VariableType.BOOLEAN)
    private boolean enableResistance = true;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_JUMP_NAME", descKey = "SUPERHEROS_VAR_JUMP_DESC", type = VariableType.BOOLEAN)
    private boolean enableJump = true;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_EXTRA_HEALTH_NAME", descKey = "SUPERHEROS_VAR_EXTRA_HEALTH_DESC", type = VariableType.BOOLEAN)
    private boolean enableExtraHealth = true;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_SPEED_AMPLIFIER_NAME", descKey = "SUPERHEROS_VAR_SPEED_AMPLIFIER_DESC", type = VariableType.INTEGER)
    private int speedAmplifier = 1;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_DAMAGE_AMPLIFIER_NAME", descKey = "SUPERHEROS_VAR_DAMAGE_AMPLIFIER_DESC", type = VariableType.INTEGER)
    private int damageAmplifier = 0;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_RESISTANCE_AMPLIFIER_NAME", descKey = "SUPERHEROS_VAR_RESISTANCE_AMPLIFIER_DESC", type = VariableType.INTEGER)
    private int resistanceAmplifier = 1;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_JUMP_AMPLIFIER_NAME", descKey = "SUPERHEROS_VAR_JUMP_AMPLIFIER_DESC", type = VariableType.INTEGER)
    private int jumpAmplifier = 3;

    @ScenarioVariable(nameKey = "SUPERHEROS_VAR_FIRE_RESISTANCE_AMPLIFIER_NAME", descKey = "SUPERHEROS_VAR_FIRE_RESISTANCE_AMPLIFIER_DESC", type = VariableType.INTEGER)
    private int fireResistanceAmplifier = 1;

    @ScenarioVariable(
            nameKey = "SUPERHEROS_VAR_ABSORPTION_HEALTH_NAME",
            descKey = "SUPERHEROS_VAR_ABSORPTION_HEALTH_DESC",
            type = VariableType.INTEGER
    )
    private int absorptionExtraHealth = 1;

    @ScenarioVariable(
            nameKey = "SUPERHEROS_VAR_REGENERATION_HEALTH_NAME",
            descKey = "SUPERHEROS_VAR_REGENERATION_HEALTH_DESC",
            type = VariableType.INTEGER
    )
    private int regenerationExtraHealth = 1;

    @ScenarioVariable(
            nameKey = "SUPERHEROS_VAR_REGENERATION_DURATION_NAME",
            descKey = "SUPERHEROS_VAR_REGENERATION_DURATION_DESC",
            type = VariableType.INTEGER
    )
    private int regenerationDurationExtraHeath = 1;

    @ScenarioVariable(
            nameKey = "SUPERHEROS_VAR_ABSORPTION_DURATION_NAME",
            descKey = "SUPERHEROS_VAR_ABSORPTION_DURATION_DESC",
            type = VariableType.INTEGER
    )
    private int absorptionDurationExtraHealth = 1;

    @Override
    public String getName() {
        return "SuperHeros";
    }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.SUPER_HEROS, player);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.NETHER_STAR);
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
    public void onStart(Player player) {
        superHeros.clear();
        Random ran = new Random();
        int aleatoire = ran.nextInt(5);
        superHeros.put(player, aleatoire);

        if (aleatoire == 4 && enableExtraHealth) {
            player.setMaxHealth(40);
            player.setHealth(player.getMaxHealth());
        }
    }

    @Override
    public void onSec(Player player) {
        if (!superHeros.containsKey(player)) return;

        int type = superHeros.get(player);

        int speedLvl = Math.max(0, speedAmplifier);
        int damageLvl = Math.max(0, damageAmplifier);
        int resistanceLvl = Math.max(0, resistanceAmplifier);
        int jumpLvl = Math.max(0, jumpAmplifier);
        int fireResistLvl = Math.max(0, fireResistanceAmplifier);

        PotionEffect[] effects = switch (type) {
            case 0 -> enableSpeed && speedLvl > 0
                    ? new PotionEffect[]{new PotionEffect(PotionEffectType.SPEED, 80, speedLvl, false, false)}
                    : new PotionEffect[]{};
            case 1 -> enableDamage && damageLvl > 0
                    ? new PotionEffect[]{new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 80, damageLvl, false, false)}
                    : new PotionEffect[]{};
            case 2 -> enableResistance && resistanceLvl > 0
                    ? new PotionEffect[]{new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 80, resistanceLvl, false, false)}
                    : new PotionEffect[]{};
            case 3 -> enableJump && jumpLvl > 0
                    ? new PotionEffect[]{
                    new PotionEffect(PotionEffectType.JUMP, 80, jumpLvl, false, false),
                    new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, fireResistLvl, false, false)
            } : new PotionEffect[]{};
            default -> new PotionEffect[]{};
        };

        if (effects.length > 0) {
            UHCUtils.applyInfiniteEffects(effects, player);
        }
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public void onPlayerTakeDamage(Entity entity, EntityDamageEvent event) {
        if (!(entity instanceof Player player)) return;
        if (!superHeros.containsKey(player)) return;

        int type = superHeros.get(player);
        if (type == 3 && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        }
    }

    @Override
    public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
        if(item.getType() != Material.GOLDEN_APPLE ) return;
        if(!(superHeros.get(player) == 4)) return;
        if(ScenarioManager.get().getScenario(GoldenHead.class).isActive()){
            for (PotionEffect e : player.getActivePotionEffects()) {
                if (e.getType().equals(PotionEffectType.REGENERATION) || e.getType().equals(PotionEffectType.ABSORPTION))
                    player.removePotionEffect(e.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*absorptionDurationExtraHealth*2, absorptionExtraHealth*2, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20* regenerationDurationExtraHeath*2, regenerationExtraHealth*2, false, true));
            return;
        }
        for (PotionEffect e : player.getActivePotionEffects()) {
            if (e.getType().equals(PotionEffectType.REGENERATION) || e.getType().equals(PotionEffectType.ABSORPTION))
                player.removePotionEffect(e.getType());
        }
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*absorptionDurationExtraHealth, absorptionExtraHealth, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20* regenerationDurationExtraHeath, regenerationExtraHealth, false, true));
    }
}