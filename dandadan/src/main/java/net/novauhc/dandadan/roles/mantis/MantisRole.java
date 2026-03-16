package net.novauhc.dandadan.roles.mantis;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import net.novauhc.dandadan.lang.DanDaDanVarLangExt4;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class MantisRole extends DanDaDanRole {

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_BOXE_MAX_TIME_NAME", descKey = "MANTIS_BOXE_MAX_TIME_DESC", type = VariableType.TIME)
    private int boxeMaxTime = 90;

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_BOXE_NAME", type = VariableType.ABILITY)
    private Ability boxe = new BoxeAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_UPPERCUT_NAME", type = VariableType.ABILITY)
    private Ability uppercut = new UppercutAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_JETWATER_NAME", type = VariableType.ABILITY)
    private Ability jetWater = new JetWaterAbility();

    @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_ABILITY_CRABE_NAME", type = VariableType.ABILITY)
    private Ability crabe = new CrabeAbility();

    // Passif Business Man → constructeur ✅
        @RoleVariable(lang = DanDaDanVarLang.class, nameKey = "MANTIS_PASSIVE_BUSINESS_NAME", type = VariableType.ABILITY)
    private Ability businessManPassive = new BusinessManPassive();


    @RoleVariable(lang = DanDaDanVarLangExt4.class, nameKey = "MANTIS_ABILITY_ESPACE_VIDE_NAME", type = VariableType.ABILITY)
    private Ability espaceVideMantis = new EspaceVideMantisAbility();
public MantisRole() {
    }

    @Override public int getId()                { return 10; }
    @Override public String getName()           { return "M. Mantis"; }
    @Override public Material getIconMaterial() { return Material.SHEARS; }

    @Override
    public String getDescription(Player player) {
        return LangManager.get().get(DanDaDanLang.MANTIS_DESC, player);
    }

    @Override
    public void onKill(UHCPlayer killer, UHCPlayer victim) {
        super.onKill(killer, victim);
        Player bp = killer.getPlayer();
        if (bp != null) LangManager.get().send(DanDaDanLang.MANTIS_KILL_BONUS, bp);
    }

    @Override
    public void onConsume(Player player, ItemStack item, PlayerItemConsumeEvent event) {
        super.onConsume(player, item, event);
        if (item.getType() != Material.GOLDEN_APPLE) return;
        // Passif Business Man : 2% de ne pas consommer la pomme
        if (ThreadLocalRandom.current().nextDouble() < 0.02) {
            event.setCancelled(true);
            player.sendMessage("§e[Mantis] §7Pomme conservée ! (Business Man)");
        }
    }

    public int getBoxeMaxTime() { return boxeMaxTime; }
}
