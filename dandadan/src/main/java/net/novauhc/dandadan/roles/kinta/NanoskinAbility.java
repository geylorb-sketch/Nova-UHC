package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.CommandAbility;
import net.novaproject.novauhc.ability.utils.AbilityVariable;
import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.utils.VariableType;
import net.novauhc.dandadan.lang.DanDaDanLang;
import net.novauhc.dandadan.lang.DanDaDanVarLang;
import org.bukkit.entity.Player;

/**
 * Nanoskin — /ddd nanoskin  (1 usage par kill)
 * Menu de sélection en chat : attaque/défense/vitesse.
 * Gadgets :
 *   jetpack    → 1ère fois sous 2❤ → envol 10 blocs (5s)
 *   epingle    → passe à travers les pouvoirs bloquants
 *   gantelets  → immunité force/résistance adverses
 */
public class NanoskinAbility extends CommandAbility {

    public enum Gadget { NONE, JETPACK, EPINGLE, GANTELETS }

    private Gadget chosen = Gadget.NONE;
    private boolean jetpackUsed = false;

    public NanoskinAbility() { setMaxUse(-1); setCooldown(0); }


    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "NANOSKIN_PROTECT_LEVEL_NAME", descKey = "NANOSKIN_PROTECT_LEVEL_DESC", type = VariableType.INTEGER)
    private int protectionLevel = 2;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "NANOSKIN_RESIST_AMP_NAME", descKey = "NANOSKIN_RESIST_AMP_DESC", type = VariableType.INTEGER)
    private int resistAmplifier = 1;

    @AbilityVariable(lang = DanDaDanVarLang.class, nameKey = "NANOSKIN_DURATION_NAME", descKey = "NANOSKIN_DURATION_DESC", type = VariableType.TIME)
    private int duration = 120;

    @Override public String getName()       { return "Nanoskin"; }
    @Override public String getCommandKey() { return "nanoskin"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) {
            LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_CHOOSE, player);
            player.sendMessage("§e/ddd nanoskin jetpack §f| §e/ddd nanoskin epingle §f| §e/ddd nanoskin gantelets");
            return false;
        }

        String choice = args[1].toLowerCase();
        switch (choice) {
            case "jetpack"   -> { chosen = Gadget.JETPACK;   jetpackUsed = false; }
            case "epingle"   -> chosen = Gadget.EPINGLE;
            case "gantelets" -> chosen = Gadget.GANTELETS;
            default -> { player.sendMessage("§c✘ Gadget inconnu."); return false; }
        }
        LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_JETPACK, player);
        return true;
    }

    @Override
    public void onSec(Player player) {
        // Jetpack : déclenché si < 2❤ et pas encore utilisé
        if (chosen == Gadget.JETPACK && !jetpackUsed && player.getHealth() <= 4.0) {
            jetpackUsed = true;
            LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_JETPACK, player);
            player.setVelocity(player.getVelocity().setY(2.0));
            // Fly temporaire 5s
            player.setAllowFlight(true);
            player.setFlying(true);
            net.novaproject.novauhc.Main.get().getServer().getScheduler().runTaskLater(
                    net.novaproject.novauhc.Main.get(), () -> {
                        player.setFlying(false);
                        player.setAllowFlight(false);
                    }, 100L);
        }
    }

    public Gadget getChosenGadget() { return chosen; }
}
