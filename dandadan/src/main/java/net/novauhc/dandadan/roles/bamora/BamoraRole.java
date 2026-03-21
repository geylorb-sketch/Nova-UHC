package net.novauhc.dandadan.roles.bamora;

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

public class BamoraRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_KAIJU_NAME", type = VariableType.ABILITY)
    private Ability kaiju = new KaijuAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_INVIS_NAME", type = VariableType.ABILITY)
    private Ability invisibilite = new InvisibiliteAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_SYSTEME_NAME", type = VariableType.ABILITY)
    private Ability systeme = new SystemeCommand();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "BAMORA_ABILITY_VILLE_NAME", type = VariableType.ABILITY)
    private Ability ville = new VilleAbility();

    private final ProjectilePassive projectilePassive = new ProjectilePassive();

    public BamoraRole() {
        getAbilities().add(projectilePassive);
    }

    @Override
    public String getName() {
        return "Bamora";
    }

    @Override
    public Material getIconMaterial() {
        return Material.GOLD_BLOCK;
    }

    public KaijuAbility getKaijuAbility() {
        return (KaijuAbility) kaiju;
    }

    @Override
    public void sendDescription(Player p) {
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_INFO));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.ROLE_PREFIX) + LangManager.get().get(DanDaDanDescLang.BAMORA_NAME));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.CAMP_YOKAI));
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.OBJECTIVE));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_PASSIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.BAMORA_PROJECTILE_TEXT), LangManager.get().get(DanDaDanDescLang.BAMORA_PROJECTILE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ACTIFS));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.BAMORA_KAIJU_TEXT), LangManager.get().get(DanDaDanDescLang.BAMORA_KAIJU_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.BAMORA_INVIS_TEXT), LangManager.get().get(DanDaDanDescLang.BAMORA_INVIS_HOVER));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.BAMORA_SYSTEME_TEXT), LangManager.get().get(DanDaDanDescLang.BAMORA_SYSTEME_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SECTION_ESPACE));
        HoverUtils.sendHoverLine(p, LangManager.get().get(DanDaDanDescLang.BAMORA_VILLE_TEXT), LangManager.get().get(DanDaDanDescLang.BAMORA_VILLE_HOVER));
        p.sendMessage(" ");
        p.sendMessage(LangManager.get().get(DanDaDanDescLang.SEPARATOR));
    }

    @Override
    public void onGive(UHCPlayer uhcPlayer) {
        Player player = uhcPlayer.getPlayer();
        if (player != null) {
            player.getInventory().addItem(new ItemCreator(Material.GOLD_BLOCK).setName(LangManager.get().get(DanDaDanLang.ITEM_BAMORA_KAIJU)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.GLASS).setName(LangManager.get().get(DanDaDanLang.ITEM_BAMORA_INVIS)).getItemstack());
            player.getInventory().addItem(new ItemCreator(Material.ENDER_PEARL).setName(LangManager.get().get(DanDaDanLang.ITEM_BAMORA_VILLE)).getItemstack());
        }
        super.onGive(uhcPlayer);
    }


    @Override
    public void onHit(Entity entity, Entity dammager, EntityDamageByEntityEvent event) {
        if (!(entity instanceof Player victim)) return;
        if (!(event.getDamager() instanceof Player attacker)) return;
        if (victim.getPlayer() == null) return;
        projectilePassive.onHit(attacker, victim.getPlayer());
        if (invisibilite instanceof InvisibiliteAbility ia){
            ia.onDealtHit(attacker);
            ia.onTookHit(attacker);
        }


    }

}
