package net.novauhc.dandadan.roles.acrobatique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLangExt2;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AcrobatePassive extends Ability {


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATE_DOUBLE_JUMP_COOLDOWN_NAME", descKey = "ACROBATE_DOUBLE_JUMP_COOLDOWN_DESC", type = VariableType.TIME)
    private int doubleJumpCooldownMs = 2000;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ACROBATE_LAUNCH_POWER_NAME", descKey = "ACROBATE_LAUNCH_POWER_DESC", type = VariableType.DOUBLE)
    private double launchPower = 0.8;

    private long lastDoubleJump = 0;
    private boolean inAir = false;
    private boolean doubleJumpUsed = false;


    @Override public String getName()       { return "Acrobate"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        return true;
    }

    @Override
    public void onSec(Player player) {
        player.setFallDistance(0); // immunité chute
        long now = System.currentTimeMillis();
        boolean onGround = player.isOnGround();

        if (onGround) { doubleJumpUsed = false; inAir = false; }
        else inAir = true;
        UHCPlayer uhcPlayer = getUHCPlayer(player);
        AcrobatiqueSoyeuseRole role = (AcrobatiqueSoyeuseRole) DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        // Double saut toutes les 15s
        if (inAir && !doubleJumpUsed && now - lastDoubleJump >= 15000) {
            // Déclenché si le joueur "saute" en étant déjà en l'air (détection par scroll ou espace)
            // → on offre juste le boost vertical une fois par 15s si en l'air
            if (player.getVelocity().getY() < 0) {
                doubleJumpUsed = true;
                lastDoubleJump = now;
                player.setVelocity(player.getVelocity().setY(1.2));
                LangManager.get().send(DanDaDanLangExt2.ACRO_DOUBLE_JUMP, player);
            }
        }

        // Amélioration PIED : speed cumulable
        if (role != null && role.getChosen() == AcrobatiqueSoyeuseRole.Upgrade.PIED && role.getSpeedStack() > 0) {
            int amplifier = (int)(role.getSpeedStack() / 0.1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, amplifier, false, false));
        }
    }
}