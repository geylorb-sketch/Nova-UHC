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
import net.novaproject.novauhc.scenario.role.RoleDescription;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JosephRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_HAMONOVERDRIVE_NAME", type = VariableType.ABILITY)
    private Ability hamonOverdriveAbility = new HamonOverdriveAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_REBUFF_NAME", type = VariableType.ABILITY)
    private Ability rebuffAbility = new RebuffAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_CLACKER_NAME", type = VariableType.ABILITY)
    private Ability clackerAbility = new ClackerAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_PREDICTION_NAME", type = VariableType.ABILITY)
    private Ability predPassive = new PredictionPassive();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "JOSEPH_ABILITY_HERMIT_NAME", type = VariableType.ABILITY)
    private Ability hermitPassive = new HermitPassive();


    public JosephRole() {
        setCamp(DanDaDanCamps.SPECIAL);
    }

    @Override public String getName() { return "Joseph"; }
    @Override public Material getIconMaterial() { return Material.BOW; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.JOSEPH_NAME)
            .line(DanDaDanDescLang.CAMP_SPECIAL)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.JOSEPH_PREDICT_TEXT, DanDaDanDescLang.JOSEPH_PREDICT_HOVER)
            .hover(DanDaDanDescLang.JOSEPH_THOMSON_TEXT, DanDaDanDescLang.JOSEPH_THOMSON_HOVER)
            .hover(DanDaDanDescLang.JOSEPH_HERMIT_TEXT, DanDaDanDescLang.JOSEPH_HERMIT_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.JOSEPH_HAMON_O_TEXT, DanDaDanDescLang.JOSEPH_HAMON_O_HOVER)
            .hover(DanDaDanDescLang.JOSEPH_REBUFF_TEXT, DanDaDanDescLang.JOSEPH_REBUFF_HOVER)
            .hover(DanDaDanDescLang.JOSEPH_CLACKER_TEXT, DanDaDanDescLang.JOSEPH_CLACKER_HOVER)
            .hover(DanDaDanDescLang.JOSEPH_NIGE_TEXT, DanDaDanDescLang.JOSEPH_NIGE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
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
