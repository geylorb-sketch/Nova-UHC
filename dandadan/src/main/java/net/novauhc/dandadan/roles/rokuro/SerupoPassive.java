package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.rokuro.RokuroSerpoRole;

public class SerupoPassive extends PassiveAbility {

    @AbilityVariable(
            lang = DanDaDanVarLang.class,
            nameKey = "SERUPO_MAX_HEALTH_NAME",
            descKey = "SERUPO_MAX_HEALTH_DESC",
            type = VariableType.DOUBLE
    )
    private double serupoMaxHealth = 24.0; // +2❤

    @Override
    public String getName() {
        return "Serupo";
    }

    @Override
    public Material getMaterial() {
        return null; // éviter null
    }

    private boolean isSerupoForm(Player player) {
        Object role = DanDaDan.get().getRoleByUHCPlayer(getUHCPlayer(player));

        if (!(role instanceof RokuroSerpoRole rokuroRole)) {
            return false;
        }

        return rokuroRole.getCurrentForme() == RokuroSerpoRole.Forme.SERUPO;
    }

    @Override
    public boolean onEnable(Player player) {

        if (!isSerupoForm(player)) {
            return false;
        }

        player.addPotionEffect(new PotionEffect(
                PotionEffectType.SPEED,
                40,
                0,
                false,
                false
        ));

        if (player.getMaxHealth() < serupoMaxHealth) {
            player.setMaxHealth(serupoMaxHealth);
        }

        return true;
    }
}