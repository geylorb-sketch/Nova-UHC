package net.novauhc.dandadan.roles.devilman;

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

public class DevilmanRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_MALED_NAME", type = VariableType.ABILITY)
    private Ability maleDAbility = new MaleDAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_CROC_NAME", type = VariableType.ABILITY)
    private Ability crocAbility = new CrocAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_CHALEUR_NAME", type = VariableType.ABILITY)
    private Ability chaleurAbility = new ChaleurAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_FLAMME_NAME", type = VariableType.ABILITY)
    private Ability flammePassive = new FlammePassive();


    public DevilmanRole() {
    }

    @Override public String getName() { return "Devilman"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_ROD; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.DEVILMAN_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.DEVILMAN_FLAMME_D_TEXT, DanDaDanDescLang.DEVILMAN_FLAMME_D_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.DEVILMAN_MALE_D_TEXT, DanDaDanDescLang.DEVILMAN_MALE_D_HOVER)
            .hover(DanDaDanDescLang.DEVILMAN_CROC_TEXT, DanDaDanDescLang.DEVILMAN_CROC_HOVER)
            .hover(DanDaDanDescLang.DEVILMAN_CHALEUR_TEXT, DanDaDanDescLang.DEVILMAN_CHALEUR_HOVER)
            .hover(DanDaDanDescLang.DEVILMAN_CRYBABY_TEXT, DanDaDanDescLang.DEVILMAN_CRYBABY_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.BLAZE_ROD).setName(LangManager.get().get(DanDaDanLang.ITEM_DEVILMAN_5MALEDICTION)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.IRON_SWORD).setName(LangManager.get().get(DanDaDanLang.ITEM_DEVILMAN_CCROC)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.MAGMA_CREAM).setName(LangManager.get().get(DanDaDanLang.ITEM_DEVILMAN_6CHALEUR)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

}
