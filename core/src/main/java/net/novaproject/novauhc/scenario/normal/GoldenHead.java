package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.GoldenHeadLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.lang.lang.ScenarioDescLang;

public class GoldenHead extends Scenario {
    private ShapedRecipe recipe;

    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_ABSORPTION_DURATION_GOLDEN_HEAD_NAME", descKey = "GOLDENHEAD_VAR_ABSORPTION_DURATION_GOLDEN_HEAD_DESC", type = VariableType.TIME)
    private int absorptionDurationGoldenHead = 20 * 60 * 2;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_ABSORPTION_AMPLIFIER_GOLDEN_HEAD_NAME", descKey = "GOLDENHEAD_VAR_ABSORPTION_AMPLIFIER_GOLDEN_HEAD_DESC", type = VariableType.INTEGER)
    private int absorptionAmplifierGoldenHead = 1;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_REGENERATION_DURATION_GOLDEN_HEAD_NAME", descKey = "GOLDENHEAD_VAR_REGENERATION_DURATION_GOLDEN_HEAD_DESC", type = VariableType.TIME)
    private int regenerationDurationGoldenHead = 20 * 6;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_REGENERATION_AMPLIFIER_GOLDEN_HEAD_NAME", descKey = "GOLDENHEAD_VAR_REGENERATION_AMPLIFIER_GOLDEN_HEAD_DESC", type = VariableType.INTEGER)
    private int regenerationAmplifierGoldenHead = 1;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_ABSORPTION_DURATION_APPLE_NAME", descKey = "GOLDENHEAD_VAR_ABSORPTION_DURATION_APPLE_DESC", type = VariableType.TIME)
    private int absorptionDurationApple = 20 * 60 * 2;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_ABSORPTION_AMPLIFIER_APPLE_NAME", descKey = "GOLDENHEAD_VAR_ABSORPTION_AMPLIFIER_APPLE_DESC", type = VariableType.INTEGER)
    private int absorptionAmplifierApple = 0;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_REGENERATION_DURATION_APPLE_NAME", descKey = "GOLDENHEAD_VAR_REGENERATION_DURATION_APPLE_DESC", type = VariableType.TIME)
    private int regenerationDurationApple = 20 * 4;
    @ScenarioVariable(lang = ScenarioVarLang.class, nameKey = "GOLDENHEAD_VAR_REGENERATION_AMPLIFIER_APPLE_NAME", descKey = "GOLDENHEAD_VAR_REGENERATION_AMPLIFIER_APPLE_DESC", type = VariableType.INTEGER)
    private int regenerationAmplifierApple = 1;

    private String t(GoldenHeadLang key) { return LangManager.get().get(key); }
    private String t(GoldenHeadLang key, Map<String,Object> p) { return LangManager.get().get(key, p); }

    @Override public String getName() { return "Golden Head"; }
    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(ScenarioDescLang.GOLDEN_HEAD, player);
    }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.GOLDEN_APPLE); }

    @Override
    public void toggleActive() {
        super.toggleActive();
        ShapedRecipe goldenHead = new ShapedRecipe(new ItemCreator(Material.GOLDEN_APPLE)
                .setName(t(GoldenHeadLang.ITEM_NAME)).getItemstack());
        goldenHead.shape("GGG", "GHG", "GGG");
        goldenHead.setIngredient('G', Material.GOLD_INGOT);
        goldenHead.setIngredient('H', new MaterialData(Material.SKULL_ITEM, (byte) 3));
        recipe = goldenHead;
        if (isActive()) Bukkit.addRecipe(goldenHead);
    }

    @Override
    public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
        if (item.getType() == Material.GOLDEN_APPLE && item.hasItemMeta()
                && item.getItemMeta().hasDisplayName()
                && item.getItemMeta().getDisplayName().equals(t(GoldenHeadLang.ITEM_NAME))) {
            for (PotionEffect e : player.getActivePotionEffects()) {
                if (e.getType().equals(PotionEffectType.REGENERATION) || e.getType().equals(PotionEffectType.ABSORPTION))
                    player.removePotionEffect(e.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, absorptionDurationGoldenHead, absorptionAmplifierGoldenHead, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenerationDurationGoldenHead, regenerationAmplifierGoldenHead, false, true));
        } else if (item.getType() == Material.GOLDEN_APPLE) {
            for (PotionEffect e : player.getActivePotionEffects()) {
                if (e.getType().equals(PotionEffectType.REGENERATION) || e.getType().equals(PotionEffectType.ABSORPTION))
                    player.removePotionEffect(e.getType());
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, absorptionDurationApple, absorptionAmplifierApple, false, true));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, regenerationDurationApple, regenerationAmplifierApple, false, true));
        }
    }

    @Override
    public void onDeath(UHCPlayer uhcPlayer, UHCPlayer killer, PlayerDeathEvent event) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(uhcPlayer.getPlayer().getName());
        meta.setDisplayName(t(GoldenHeadLang.SKULL_NAME, Map.of("%player%", uhcPlayer.getPlayer().getName())));
        skull.setItemMeta(meta);
        uhcPlayer.getPlayer().getWorld().dropItemNaturally(uhcPlayer.getPlayer().getLocation(), skull);
    }
}
