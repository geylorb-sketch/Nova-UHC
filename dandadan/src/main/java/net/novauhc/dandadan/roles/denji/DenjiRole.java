package net.novauhc.dandadan.roles.denji;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanCamps;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanDescLang;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DenjiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_MALEDENJI_NAME", type = VariableType.ABILITY)
    private Ability maleDenjiAbility = new MaleDenjiAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_CHAINE_NAME", type = VariableType.ABILITY)
    private Ability chaineAbility = new ChaineAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_CHAINSAWMAN_NAME", type = VariableType.ABILITY)
    private Ability chainsawManAbility = new ChainsawManAbility();

    private final  SangPassive sangPassive = new SangPassive();


    public DenjiRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(sangPassive);
    }

    @Override public String getName() { return "Denji"; }
    @Override public Material getIconMaterial() { return Material.IRON_AXE; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.DENJI_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DENJI_SANG_TEXT), L(DanDaDanDescLang.DENJI_SANG_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DENJI_OUBLIE_TEXT), L(DanDaDanDescLang.DENJI_OUBLIE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DENJI_MALE_DE_TEXT), L(DanDaDanDescLang.DENJI_MALE_DE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DENJI_CHAINE_TEXT), L(DanDaDanDescLang.DENJI_CHAINE_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DENJI_CHAINSAW_TEXT), L(DanDaDanDescLang.DENJI_CHAINSAW_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.IRON_AXE).setName(LangManager.get().get(DanDaDanLang.ITEM_DENJI_5MALEDICTION)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.TRIPWIRE_HOOK).setName(LangManager.get().get(DanDaDanLang.ITEM_DENJI_7CHAINE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.DIAMOND_AXE).setName(LangManager.get().get(DanDaDanLang.ITEM_DENJI_CCHAINSAW_MAN)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
