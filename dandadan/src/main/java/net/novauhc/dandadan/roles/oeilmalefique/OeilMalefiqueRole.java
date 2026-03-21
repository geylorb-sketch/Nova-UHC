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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OeilMalefiqueRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEILMALEFIQUE_ABILITY_BALLE_NAME", type = VariableType.ABILITY)
    private Ability balleAbility = new BalleAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "OEILMALEFIQUE_ABILITY_MALEDICTIONO_NAME", type = VariableType.ABILITY)
    private Ability maledictionOAbility = new MaledictionOAbility();

    private final EnviePassive enviePassive  = new EnviePassive();


    public OeilMalefiqueRole() {
        getAbilities().add(enviePassive);
    }

    @Override public String getName() { return "L'Oeil Malefique"; }
    @Override public Material getIconMaterial() { return Material.EYE_OF_ENDER; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.OEIL_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.OEIL_ENVIE_TEXT), L(DanDaDanDescLang.OEIL_ENVIE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.OEIL_JIJI_D_TEXT), L(DanDaDanDescLang.OEIL_JIJI_D_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.OEIL_BALLE_TEXT), L(DanDaDanDescLang.OEIL_BALLE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.OEIL_MALEDICTION_O_TEXT), L(DanDaDanDescLang.OEIL_MALEDICTION_O_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.OEIL_TRANSFO_TEXT), L(DanDaDanDescLang.OEIL_TRANSFO_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
