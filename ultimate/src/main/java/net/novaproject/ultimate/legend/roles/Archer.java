package net.novaproject.ultimate.legend.roles;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.novauhc.lang.special.LegendLang;
import net.novaproject.ultimate.legend.LegendRole;
import net.novaproject.ultimate.legend.roles.abilities.ArcherBowPassive;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;


public class Archer extends LegendRole {

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "ARCHER_BOW_POWER_NAME", descKey = "ARCHER_BOW_POWER_DESC", type = VariableType.INTEGER)
    private int bowPower = 2;

    @RoleVariable(lang = ScenarioVarLang.class, nameKey = "ARCHER_ABILITY_NAME", type = VariableType.ABILITY)
    private Ability bowPassive;

    public Archer() {
        this.bowPassive = new ArcherBowPassive();
    }

    @Override public int getId() { return 3; }
    @Override public String getName() { return "Archer"; }
    @Override public Material getIconMaterial() { return Material.BOW; }
    @Override public ItemCreator getItem() { return new ItemCreator(Material.BOW); }
    @Override public Lang getDescriptionLang() { return LegendLang.ROLE_DESC_ARCHER; }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        super.onGive(uhcPlayer);
        Player p = uhcPlayer.getPlayer(); if (p == null) return;
        p.getInventory().addItem(new ItemCreator(Material.BOW)
                .addEnchantment(Enchantment.ARROW_DAMAGE, bowPower)
                .addEnchantment(Enchantment.ARROW_INFINITE, 1)
                .setName("§6§lArc de l'Archer").setUnbreakable(true).getItemstack());
        p.getInventory().addItem(new ItemCreator(Material.ARROW).setAmount(64).getItemstack());
    }
}
