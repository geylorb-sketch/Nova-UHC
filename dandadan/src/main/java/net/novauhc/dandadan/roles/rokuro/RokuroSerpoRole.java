package net.novauhc.dandadan.roles.rokuro;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class RokuroSerpoRole extends DanDaDanRole {

    public enum Forme { SERUPO, SEIJIN }
    private Forme currentForme = Forme.SERUPO;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_FORME_SERPO_NAME", type = VariableType.ABILITY)
    private Ability formeSerpoAbility = new FormeSerpoAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ORGUE_NAME", type = VariableType.ABILITY)
    private Ability orgueAbility = new OrgueSesSensAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ZONE_INCROYABLE_NAME", type = VariableType.ABILITY)
    private Ability zoneIncroyable = new ZoneIncroyableAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_ABILITY_ZONE_INTOUCHABLE_NAME", type = VariableType.ABILITY)
    private Ability zoneIntouchable = new ZoneIntouchableAbility();

    // Passifs des deux formes + /ddd forme → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_PASSIVE_SERUPO_NAME", type = VariableType.ABILITY)
    private Ability serupoPassive = new SerupoPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_PASSIVE_SEIJIN_NAME", type = VariableType.ABILITY)
    private Ability seijinPassive = new SeijinPassive();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "ROKURO_CMD_FORME_NAME", type = VariableType.ABILITY)
    private Ability formeCommand = new RokuroFormeCommand();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "ROKURO_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideRokuro = new EspaceVideRokuroAbility();
public RokuroSerpoRole() {
    }

    @Override public int getId()                { return 9; }
    @Override public String getName()           { return "Rokuro Serpo"; }
    @Override public Material getIconMaterial() { return Material.EXP_BOTTLE; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.ROKURO_DESC, player);
    }

    public Forme getCurrentForme() { return currentForme; }

    public void switchForme(Player player) {
        currentForme = (currentForme == Forme.SERUPO) ? Forme.SEIJIN : Forme.SERUPO;
        // Retire tous les effets et applique ceux de la nouvelle forme
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        player.setMaxHealth(player.getMaxHealth() - 4); // retire les +2❤ de Serupo si existants

        if (currentForme == Forme.SERUPO) {
            LangManager.get().send(DanDaDanLang.ROKURO_SWITCH_SERUPO, player);
        } else {
            LangManager.get().send(DanDaDanLang.ROKURO_SWITCH_SEIJIN, player);
        }
    }
}
