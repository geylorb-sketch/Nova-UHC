package net.novauhc.dandadan.roles.kashimoto;

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

public class KashimotoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_FLAMME_NAME", type = VariableType.ABILITY)
    private Ability flamme = new FlammeGlaceAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_ESPRIT_NAME", type = VariableType.ABILITY)
    private Ability esprit = new EspritProtecteurAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_APHOOM_NAME", type = VariableType.ABILITY)
    private Ability aphoom = new AphoomZhahAbility();

    private final ProtecteurPassive protecteurPassive = new ProtecteurPassive();

    public KashimotoRole() {
        getAbilities().add(protecteurPassive);
    }

    @Override public String getName()           { return "Kashimoto"; }
    @Override public Material getIconMaterial() { return Material.ICE; }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.KASHIMOTO_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KASHIMOTO_PROTECTEUR_TEXT), LangManager.get().get(DanDaDanDescLang.KASHIMOTO_PROTECTEUR_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KASHIMOTO_FLAMME_TEXT), LangManager.get().get(DanDaDanDescLang.KASHIMOTO_FLAMME_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KASHIMOTO_ESPRIT_TEXT), LangManager.get().get(DanDaDanDescLang.KASHIMOTO_ESPRIT_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KASHIMOTO_APHOOM_TEXT), LangManager.get().get(DanDaDanDescLang.KASHIMOTO_APHOOM_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.ICE).setName(LangManager.get().get(DanDaDanLang.ITEM_KASHIMOTO_FLAMME)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SKULL_ITEM).setName(LangManager.get().get(DanDaDanLang.ITEM_KASHIMOTO_ESPRIT)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.PACKED_ICE).setName(LangManager.get().get(DanDaDanLang.ITEM_KASHIMOTO_APHOOM)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }
}
