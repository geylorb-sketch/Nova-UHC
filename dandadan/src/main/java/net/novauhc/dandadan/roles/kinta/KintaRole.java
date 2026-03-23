package net.novauhc.dandadan.roles.kinta;

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

public class KintaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_GREAT_NAME", type = VariableType.ABILITY)
    private Ability great = new GreatKintaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_DEDALE_NAME", type = VariableType.ABILITY)
    private Ability dedale = new DedaleAchaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_NANOSKIN_NAME", type = VariableType.ABILITY)
    private Ability nanoskin = new NanoskinCommand();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_LUNETTE_NAME", type = VariableType.ABILITY)
    private Ability lunettePassive = new LunettePassive();

    public KintaRole() {
    }

    @Override public String getName()           { return "Kinta"; }
    @Override public Material getIconMaterial() { return Material.GOLD_HELMET; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.KINTA_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.KINTA_LUNETTE_TEXT, DanDaDanDescLang.KINTA_LUNETTE_HOVER)
            .hover(DanDaDanDescLang.KINTA_NANOSKIN_TEXT, DanDaDanDescLang.KINTA_NANOSKIN_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.KINTA_GREAT_TEXT, DanDaDanDescLang.KINTA_GREAT_HOVER)
            .hover(DanDaDanDescLang.KINTA_DEDALE_TEXT, DanDaDanDescLang.KINTA_DEDALE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.GOLD_HELMET).setName(LangManager.get().get(DanDaDanLang.ITEM_KINTA_GREAT)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        if (nanoskin instanceof NanoskinCommand nc) nc.addUse();
    }
}
