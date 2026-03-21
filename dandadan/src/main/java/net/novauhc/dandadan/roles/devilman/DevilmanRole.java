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
import net.novaproject.novauhc.utils.HoverUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DevilmanRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_MALED_NAME", type = VariableType.ABILITY)
    private Ability maleDAbility = new MaleDAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_CROC_NAME", type = VariableType.ABILITY)
    private Ability crocAbility = new CrocAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "DEVILMAN_ABILITY_CHALEUR_NAME", type = VariableType.ABILITY)
    private Ability chaleurAbility = new ChaleurAbility();

    private final  FlammePassive flammePassive = new FlammePassive(null);


    public DevilmanRole() {
        getAbilities().add(flammePassive);
    }

    @Override public String getName() { return "Devilman"; }
    @Override public Material getIconMaterial() { return Material.BLAZE_ROD; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.DEVILMAN_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DEVILMAN_FLAMME_D_TEXT), L(DanDaDanDescLang.DEVILMAN_FLAMME_D_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DEVILMAN_MALE_D_TEXT), L(DanDaDanDescLang.DEVILMAN_MALE_D_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DEVILMAN_CROC_TEXT), L(DanDaDanDescLang.DEVILMAN_CROC_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DEVILMAN_CHALEUR_TEXT), L(DanDaDanDescLang.DEVILMAN_CHALEUR_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.DEVILMAN_CRYBABY_TEXT), L(DanDaDanDescLang.DEVILMAN_CRYBABY_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
