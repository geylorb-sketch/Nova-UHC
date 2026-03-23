package net.novauhc.dandadan.roles.jiji;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JijiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIJI_ABILITY_SPIRALES_NAME", type = VariableType.ABILITY)
    private Ability spiralesAbility = new SpiralesAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JIJI_ABILITY_ADAPT_NAME", type = VariableType.ABILITY)
    private Ability adaptPassive = new AdaptPassive();


    public JijiRole() {
    }

    @Override public String getName() { return "Jiji"; }
    @Override public Material getIconMaterial() { return Material.FIREWORK; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.JIJI_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.JIJI_ADAPT_TEXT, DanDaDanDescLang.JIJI_ADAPT_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.JIJI_SPIRALES_TEXT, DanDaDanDescLang.JIJI_SPIRALES_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.FIREWORK).setName(LangManager.get().get(DanDaDanLang.ITEM_JIJI_DSPIRALES)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
