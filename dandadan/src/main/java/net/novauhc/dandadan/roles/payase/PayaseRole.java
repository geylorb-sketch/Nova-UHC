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
import net.novaproject.novauhc.utils.HoverUtils;
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

    private final PayasePassive payasePassive = new PayasePassive();


    public PayaseRole() {
        getAbilities().add(payasePassive);
    }

    @Override public String getName() { return "Payase"; }
    @Override public Material getIconMaterial() { return Material.PUMPKIN; }

    private String L(DanDaDanDescLang k) { return LangManager.get().get(k); }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(L(DanDaDanDescLang.ROLE_PREFIX) + L(DanDaDanDescLang.PAYASE_NAME));
        p.sendMessage(L(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(L(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.PAYASE_JOUR_TEXT), L(DanDaDanDescLang.PAYASE_JOUR_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.PAYASE_NUIT_TEXT), L(DanDaDanDescLang.PAYASE_NUIT_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.PAYASE_DARKNESS_TEXT), L(DanDaDanDescLang.PAYASE_DARKNESS_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.PAYASE_PERMUTATION_TEXT), L(DanDaDanDescLang.PAYASE_PERMUTATION_HOVER));
        HoverUtils.sendHoverLine(p, L(DanDaDanDescLang.PAYASE_OMBRE_TEXT), L(DanDaDanDescLang.PAYASE_OMBRE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(L(DanDaDanDescLang.SEPARATOR));
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
        if (uhcPlayer.getPlayer() != null) payasePassive.onDamage(uhcPlayer.getPlayer(), event);

    }
}
