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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KashimotoRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_FLAMME_NAME", type = VariableType.ABILITY)
    private Ability flamme = new FlammeGlaceAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_ESPRIT_NAME", type = VariableType.ABILITY)
    private Ability esprit = new EspritProtecteurAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_APHOOM_NAME", type = VariableType.ABILITY)
    private Ability aphoom = new AphoomZhahAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KASHIMOTO_ABILITY_PROTECTEUR_NAME", type = VariableType.ABILITY)
    private Ability protecteurPassive = new ProtecteurPassive();

    public KashimotoRole() {
    }

    @Override public String getName()           { return "Kashimoto"; }
    @Override public Material getIconMaterial() { return Material.ICE; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.KASHIMOTO_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.KASHIMOTO_PROTECTEUR_TEXT, DanDaDanDescLang.KASHIMOTO_PROTECTEUR_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.KASHIMOTO_FLAMME_TEXT, DanDaDanDescLang.KASHIMOTO_FLAMME_HOVER)
            .hover(DanDaDanDescLang.KASHIMOTO_ESPRIT_TEXT, DanDaDanDescLang.KASHIMOTO_ESPRIT_HOVER)
            .hover(DanDaDanDescLang.KASHIMOTO_APHOOM_TEXT, DanDaDanDescLang.KASHIMOTO_APHOOM_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
