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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KintaRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_GREAT_NAME", type = VariableType.ABILITY)
    private Ability great = new GreatKintaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_DEDALE_NAME", type = VariableType.ABILITY)
    private Ability dedale = new DedaleAchaAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "KINTA_ABILITY_NANOSKIN_NAME", type = VariableType.ABILITY)
    private Ability nanoskin = new NanoskinCommand();

    private final LunettePassive lunettePassive = new LunettePassive();

    public KintaRole() {
        getAbilities().add(lunettePassive);
    }

    @Override public String getName()           { return "Kinta"; }
    @Override public Material getIconMaterial() { return Material.GOLD_HELMET; }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.KINTA_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KINTA_LUNETTE_TEXT), LangManager.get().get(DanDaDanDescLang.KINTA_LUNETTE_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KINTA_NANOSKIN_TEXT), LangManager.get().get(DanDaDanDescLang.KINTA_NANOSKIN_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KINTA_GREAT_TEXT), LangManager.get().get(DanDaDanDescLang.KINTA_GREAT_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.KINTA_DEDALE_TEXT), LangManager.get().get(DanDaDanDescLang.KINTA_DEDALE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
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
