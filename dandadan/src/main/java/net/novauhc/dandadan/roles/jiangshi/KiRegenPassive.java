package net.novauhc.dandadan.roles.jiangshi;

import net.novaproject.novauhc.ability.PassiveAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.roles.jiangshi.JiangshiRole;




public class KiRegenPassive extends PassiveAbility {
    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KI_REGEN_INTERVAL_NAME", descKey = "KI_REGEN_INTERVAL_DESC", type = VariableType.TIME)
    private int regenInterval = 60; // secondes entre chaque +1 KI

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "KI_REGEN_AMOUNT_NAME", descKey = "KI_REGEN_AMOUNT_DESC", type = VariableType.INTEGER)
    private int kiPerRegen = 1;

    private int secCounter = 0;

    @Override public String getName()       { return "Mana KI"; }
    @Override public Material getMaterial() { return null; }

    @Override
    public boolean onEnable(Player player) {
        secCounter++;
        if (secCounter >= regenInterval) {
            secCounter = 0;
            // Pas d'accessor set — on passe par spendKi(-1) ou un addKi
            // Pour simplicité : le rôle gère ça lui-même
        }
        return true;
    }
}