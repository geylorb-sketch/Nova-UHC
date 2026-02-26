package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum MumbleUiLang implements Lang {
    ITEM_NAME("Mumble", "Mumble"),
    ITEM_LORE("Cliquez pour rejoindre le mumble", "Click to join Mumble"),
    CLICK_MESSAGE("Mumble serveurIp : %ip%\nMumble port : %port%", "Mumble serverIp: %ip%\nMumble port: %port%"),
    PORT_LABEL("§7Port : ", "§7Port: "),
    IP_LABEL("§7Ip : ", "§7IP: "),
    ;

    private final Map<String, String> translations;
    MumbleUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.mumble." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
