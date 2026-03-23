package net.novauhc.dandadan.roles.payase;

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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PayaseRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_DARKNESS_NAME", type = VariableType.ABILITY)
    private Ability darkness = new DarknessFormAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_PERMUTATION_NAME", type = VariableType.ABILITY)
    private Ability permutation = new PermutationAbility();
    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_OMBRE_NAME", type = VariableType.ABILITY)
    private Ability ombre = new OmbreAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "PAYASE_ABILITY_PAYASE_NAME", type = VariableType.ABILITY)
    private Ability payasePassive = new PayasePassive();


    public PayaseRole() {
    }

    @Override public String getName() { return "Payase"; }
    @Override public Material getIconMaterial() { return Material.PUMPKIN; }

    @Override
    public void sendDescription(Player p) {
        RoleDescription.of(p)
            .separator(DanDaDanDescLang.SEPARATOR)
            .space()
            .line(DanDaDanDescLang.SECTION_INFO)
            .line(DanDaDanDescLang.ROLE_PREFIX, DanDaDanDescLang.PAYASE_NAME)
            .line(DanDaDanDescLang.CAMP_YOKAI)
            .line(DanDaDanDescLang.OBJECTIVE)
            .space()
            .line(DanDaDanDescLang.SECTION_PASSIFS)
            .hover(DanDaDanDescLang.PAYASE_JOUR_TEXT, DanDaDanDescLang.PAYASE_JOUR_HOVER)
            .hover(DanDaDanDescLang.PAYASE_NUIT_TEXT, DanDaDanDescLang.PAYASE_NUIT_HOVER)
            .space()
            .line(DanDaDanDescLang.SECTION_ACTIFS)
            .hover(DanDaDanDescLang.PAYASE_DARKNESS_TEXT, DanDaDanDescLang.PAYASE_DARKNESS_HOVER)
            .hover(DanDaDanDescLang.PAYASE_PERMUTATION_TEXT, DanDaDanDescLang.PAYASE_PERMUTATION_HOVER)
            .hover(DanDaDanDescLang.PAYASE_OMBRE_TEXT, DanDaDanDescLang.PAYASE_OMBRE_HOVER)
            .space()
            .separator(DanDaDanDescLang.SEPARATOR)
            .send();
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.INK_SACK).setName(LangManager.get().get(DanDaDanLang.ITEM_PAYASE_8DARKNESS_FORM)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.EYE_OF_ENDER).setName(LangManager.get().get(DanDaDanLang.ITEM_PAYASE_5PERMUTATION)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.OBSIDIAN).setName(LangManager.get().get(DanDaDanLang.ITEM_PAYASE_0OMBRE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }

    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        super.onHit(entity, dammager, event);
        if (!(entity instanceof Player uhcPlayer)) return;
        if (uhcPlayer.getPlayer() != null && payasePassive instanceof PayasePassive pp) pp.onDamage(uhcPlayer.getPlayer(), event);

    }
}
