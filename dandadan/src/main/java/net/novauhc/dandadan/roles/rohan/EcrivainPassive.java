package net.novauhc.dandadan.roles.rohan;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EcrivainPassive extends Ability {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "ECRIVAIN_RANGE_NAME", descKey = "ECRIVAIN_RANGE_DESC", type = VariableType.INTEGER)
    private int spyRange = 15; // portée de lecture de l'inventaire

    @Override public String getName()       { return "Écrivain"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        return true;
    }

}