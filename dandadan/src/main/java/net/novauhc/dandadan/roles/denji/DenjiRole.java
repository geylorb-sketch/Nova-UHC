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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DenjiRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_MALEDENJI_NAME", type = VariableType.ABILITY)
    private Ability maleDenjiAbility = new MaleDenjiAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_CHAINE_NAME", type = VariableType.ABILITY)
    private Ability chaineAbility = new ChaineAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_CHAINSAWMAN_NAME", type = VariableType.ABILITY)
    private Ability chainsawManAbility = new ChainsawManAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DENJI_ABILITY_SANG_NAME", type = VariableType.ABILITY)
    private Ability sangPassive = new SangPassive();


    public DenjiRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Denji"; }
    @Override public Material getIconMaterial() { return Material.IRON_AXE; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.DENJI_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.DENJI_SANG_TEXT, DanDaDanDescLang.DENJI_SANG_HOVER)
            .hover(DanDaDanDescLang.DENJI_OUBLIE_TEXT, DanDaDanDescLang.DENJI_OUBLIE_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.DENJI_MALE_DE_TEXT, DanDaDanDescLang.DENJI_MALE_DE_HOVER)
            .hover(DanDaDanDescLang.DENJI_CHAINE_TEXT, DanDaDanDescLang.DENJI_CHAINE_HOVER)
            .hover(DanDaDanDescLang.DENJI_CHAINSAW_TEXT, DanDaDanDescLang.DENJI_CHAINSAW_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
