package net.novauhc.dandadan.roles.kinta;

import net.novaproject.novauhc.ability.template.CommandAbility;
import net.novaproject.novauhc.lang.LangManager;
import net.novauhc.dandadan.lang.DanDaDanLang;
import org.bukkit.entity.Player;

/**
 * Nanoskin — Commande Kinta (/ddd nanoskin)
 * 1 usage par kill. Menu avec 3 gadgets : Jetpack, Epingle, Gantelets.
 */
public class NanoskinCommand extends CommandAbility {

    public enum Gadget { NONE, JETPACK, EPINGLE, GANTELETS }
    private Gadget activeGadget = Gadget.NONE;

    public NanoskinCommand() {
        setMaxUse(0); // Augmente via onKill
        setCooldown(0);
    }

    @Override public String getName()       { return "Nanoskin"; }
    @Override public String getCommandKey() { return "nanoskin"; }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length < 2) {
            LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_MENU, player);
            return false;
        }
        switch (args[1].toLowerCase()) {
            case "jetpack"   -> { activeGadget = Gadget.JETPACK;   LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_JETPACK, player); }
            case "epingle"   -> { activeGadget = Gadget.EPINGLE;   LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_EPINGLE, player); }
            case "gantelets"  -> { activeGadget = Gadget.GANTELETS; LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_GANTELETS, player); }
            default          -> { LangManager.get().send(DanDaDanLang.KINTA_NANOSKIN_MENU, player); return false; }
        }
        return true;
    }

    public Gadget getActiveGadget() { return activeGadget; }
    public void addUse()            { setMaxUse(getMaxUse() + 1); }
}
