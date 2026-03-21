package net.novauhc.dandadan.roles.joseph;

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

public class JosephRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_HAMONOVERDRIVE_NAME", type = VariableType.ABILITY)
    private Ability hamonOverdriveAbility = new HamonOverdriveAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_REBUFF_NAME", type = VariableType.ABILITY)
    private Ability rebuffAbility = new RebuffAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_CLACKER_NAME", type = VariableType.ABILITY)
    private Ability clackerAbility = new ClackerAbility();

    private final PredictionPassive predPassive  = new PredictionPassive();
    private final  HermitPassive  hermitPassive = new HermitPassive();


    public JosephRole() {
        setCamp(DanDaDanCamps.SPECIAL);
        getAbilities().add(predPassive);
        getAbilities().add(hermitPassive);
    }

    @Override public String getName() { return "Joseph"; }
    @Override public Material getIconMaterial() { return Material.BOW; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.JOSEPH_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_SPECIAL));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_PREDICT_TEXT), L(DanDaDanDescLang.JOSEPH_PREDICT_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_THOMSON_TEXT), L(DanDaDanDescLang.JOSEPH_THOMSON_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_HERMIT_TEXT), L(DanDaDanDescLang.JOSEPH_HERMIT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_HAMON_O_TEXT), L(DanDaDanDescLang.JOSEPH_HAMON_O_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_REBUFF_TEXT), L(DanDaDanDescLang.JOSEPH_REBUFF_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_CLACKER_TEXT), L(DanDaDanDescLang.JOSEPH_CLACKER_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.JOSEPH_NIGE_TEXT), L(DanDaDanDescLang.JOSEPH_NIGE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.BLAZE_POWDER).setName(LangManager.get().get(DanDaDanLang.ITEM_JOSEPH_EHAMON_OVERDRIVE)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_JOSEPH_CREBUFF)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.SNOW_BALL).setName(LangManager.get().get(DanDaDanLang.ITEM_JOSEPH_6CLACKER)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
