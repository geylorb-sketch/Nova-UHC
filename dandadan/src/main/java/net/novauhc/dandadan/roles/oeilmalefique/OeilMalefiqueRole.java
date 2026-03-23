package net.novauhc.dandadan.roles.oeilmalefique;

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

public class OeilMalefiqueRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEILMALEFIQUE_ABILITY_BALLE_NAME", type = VariableType.ABILITY)
    private Ability balleAbility = new BalleAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEILMALEFIQUE_ABILITY_MALEDICTIONO_NAME", type = VariableType.ABILITY)
    private Ability maledictionOAbility = new MaledictionOAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEILMALEFIQUE_ABILITY_ENVIE_NAME", type = VariableType.ABILITY)
    private Ability enviePassive = new EnviePassive();


    public OeilMalefiqueRole() {
    }

    @Override public String getName() { return "L'Oeil Malefique"; }
    @Override public Material getIconMaterial() { return Material.EYE_OF_ENDER; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.OEIL_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.OEIL_ENVIE_TEXT, DanDaDanDescLang.OEIL_ENVIE_HOVER)
            .hover(DanDaDanDescLang.OEIL_JIJI_D_TEXT, DanDaDanDescLang.OEIL_JIJI_D_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.OEIL_BALLE_TEXT, DanDaDanDescLang.OEIL_BALLE_HOVER)
            .hover(DanDaDanDescLang.OEIL_MALEDICTION_O_TEXT, DanDaDanDescLang.OEIL_MALEDICTION_O_HOVER)
            .hover(DanDaDanDescLang.OEIL_TRANSFO_TEXT, DanDaDanDescLang.OEIL_TRANSFO_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.EYE_OF_ENDER).setName(LangManager.get().get(DanDaDanLang.ITEM_OEILMALEFIQUE_5BALLE_DE_RANCUNE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.FERMENTED_SPIDER_EYE).setName(LangManager.get().get(DanDaDanLang.ITEM_OEILMALEFIQUE_CMALEDICTION)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
