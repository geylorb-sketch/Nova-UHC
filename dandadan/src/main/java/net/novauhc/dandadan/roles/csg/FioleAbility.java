package net.novauhc.dandadan.roles.csg;

import net.novaproject.novauhc.Main;
import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.uhcplayer.UHCPlayerManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLangExt3;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FioleAbility extends Ability {

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FIOLE_EFFECT_DURATION_NAME", descKey = "FIOLE_EFFECT_DURATION_DESC", type = VariableType.TIME)
    private int effectDuration = 30;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "FIOLE_COOLDOWN_NAME", descKey = "FIOLE_COOLDOWN_DESC", type = VariableType.TIME)
    private int cooldown = 60;

    @Override public String getName()       { return "Fiole"; }
    @Override public Material getMaterial() { return Material.GLASS_BOTTLE; }

    /**
     * Lookup dynamique du role parent.
     * RoleVariableProcessor clone les abilities via constructeur no-arg,
     * donc on ne peut PAS stocker une ref dans le constructeur.
     */
    private CompteSaintGermainRole getRole(Player player) {
        if (DanDaDan.get() == null) return null;
        UHCPlayer uhc = UHCPlayerManager.get().getPlayer(player);
        if (uhc == null) return null;
        DanDaDanRole r = DanDaDan.get().getRoleByUHCPlayer(uhc);
        if (r instanceof CompteSaintGermainRole csg) return csg;
        return null;
    }

    @Override
    public void onClick(org.bukkit.event.player.PlayerInteractEvent event, ItemStack item) {
        if (item == null || item.getType() != Material.GLASS_BOTTLE) return;
        Player player = event.getPlayer();
        CompteSaintGermainRole role = getRole(player);
        if (role == null) return;

        if (event.getAction().name().contains("RIGHT")) {
            role.nextFiole();
            player.sendMessage("§6[Fiole] §7Actuelle : §e" + role.getCurrentFiole().name());
        } else if (event.getAction().name().contains("LEFT")) {
            tryUse(player);
        }
    }

    @Override
    public boolean onEnable(Player player) {
        CompteSaintGermainRole role = getRole(player);
        if (role == null) return false;

        switch (role.getCurrentFiole()) {
            case JUMP_CRONE -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_FIOLE_JUMP, player);
                player.setAllowFlight(true);
                Main.get().getServer().getScheduler().runTaskLater(Main.get(),
                        () -> player.setAllowFlight(false), 20L * 300);
            }
            case COUPE_CHEVEUX -> {
                LangManager.get().send(DanDaDanLangExt3.CSG_FIOLE_COUPE, player);
            }
            case TIGRE_EAU -> LangManager.get().send(DanDaDanLangExt3.CSG_FIOLE_EAU, player);
        }
        setCooldown(420);
        return true;
    }
}
