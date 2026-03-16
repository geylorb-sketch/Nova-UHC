package net.novauhc.dandadan.roles.jotaro;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DetectivePassive extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DETECTIVE_RANGE_NAME", descKey = "DETECTIVE_RANGE_DESC", type = VariableType.INTEGER)
    private int detectRange = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "DETECTIVE_CHECK_INTERVAL_NAME", descKey = "DETECTIVE_CHECK_INTERVAL_DESC", type = VariableType.TIME)
    private int checkIntervalSec = 10;

    @Override public String getName()       { return "Detective"; }
    @Override public Material getMaterial() { return null; }

    @Override public boolean onEnable(Player player) { return true; }

    @Override public void onSec(Player player) {
        if (DanDaDan.get() == null) return;
        UHCPlayerManager.get().getPlayingOnlineUHCPlayers().forEach(up -> {
            var role = DanDaDan.get().getRoleByUHCPlayer(up);
            if (role != null && up.getPlayer() != null && !up.getPlayer().equals(player)) {
                // ScoreboardManager ou ActionBar pour afficher — on envoie juste en chat 1x
            }
        });
    }
}