package net.novauhc.dandadan.roles.oeilmalefique;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EnvieMeurtrPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ENVIE_MEURTR_TP_RANGE_NAME", descKey = "ENVIE_MEURTR_TP_RANGE_DESC", type = VariableType.INTEGER)
    private int tpRange = 50; // blocs max pour le TP sur le lieu du meurtre

    private static String lastKillInfo = null;
    @Override public String getName()       { return "Envie de meurtre"; }
    @Override public Material getMaterial() { return null; }
    @Override public boolean onEnable(Player player) {
        if (lastKillInfo != null) {
            // Propose un TP
            player.sendMessage("§c[Œil] " + lastKillInfo + " — §fTP possible ! /ddd tp_kill");
            lastKillInfo = null;
        }
        return true;
    }
    public static void markKill(String killer, String victim) {
        lastKillInfo = "§7" + killer + " §ca tué §7" + victim;
    }
}