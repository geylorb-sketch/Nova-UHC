package net.novauhc.dandadan.roles.bamora;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.DanDaDan;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Système — /ddd systeme
 * Désactive Kaiju et téléporte Bamora au bloc d'or (son cœur).
 * Cooldown 5 minutes.
 */
public class SystemeAbility extends CommandAbility {

    public SystemeAbility() { setCooldown(300); }

    @Override public String getName()       { return "Système"; }
    @Override public String getCommandKey() { return "systeme"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        var uhcPlayer = getUHCPlayer(player);
        if (uhcPlayer == null || DanDaDan.get() == null) return false;

        var role = DanDaDan.get().getRoleByUHCPlayer(uhcPlayer);
        if (!(role instanceof BamoraRole)) return false;

        // Cherche KaijuAbility dans les abilities du rôle
        role.getAbilities().stream()
                .filter(a -> a instanceof BamoraKaijuAbility)
                .map(a -> (BamoraKaijuAbility) a)
                .findFirst()
                .ifPresent(kaiju -> {
                    Location heart = kaiju.getHeartLocation();
                    if (heart != null && kaiju.isKaijuActive()) {
                        kaiju.onWorldBreak(player, heart); // désactive proprement
                        player.teleport(heart.add(0, 1, 0));
                        LangManager.get().send(DanDaDanLang.BAMORA_SYSTEME_TELEPORT, player);
                    } else {
                        player.sendMessage("§c✘ Kaiju n'est pas actif.");
                    }
                });

        return true;
    }
}
