package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.template.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.DanDaDanRole;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.entity.Player;

/**
 * Systeme — Commande Bamora (/ddd systeme)
 * Desactive Kaiju et TP au bloc d'or. CD 5min.
 */
public class SystemeCommand extends CommandAbility {

    public SystemeCommand() {
        setMaxUse(-1);
        setCooldown(300);
    }

    @Override public String getName()       { return "Systeme"; }
    @Override public String getCommandKey() { return "systeme"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        DanDaDanRole role = DanDaDan.get().getRoleByUHCPlayer(
                net.novaproject.novauhc.uhcplayer.UHCPlayerManager.get().getPlayer(player));
        if (!(role instanceof BamoraRole bamora)) return false;

        KaijuAbility kaiju = bamora.getKaijuAbility();
        if (kaiju == null || !kaiju.isActive() || kaiju.getHeartLocation() == null) {
            LangManager.get().send(DanDaDanLang.BAMORA_SYSTEME_INACTIVE, player);
            return false;
        }

        kaiju.deactivate();
        player.teleport(kaiju.getHeartLocation().clone().add(0, 1, 0));
        LangManager.get().send(DanDaDanLang.BAMORA_SYSTEME_TP, player);
        return true;
    }
}
