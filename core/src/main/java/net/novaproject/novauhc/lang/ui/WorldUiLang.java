package net.novaproject.novauhc.lang.ui;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum WorldUiLang implements Lang {

    ORE_NAME("§8┃ §fBoost des §6§lMinerais", "§8┃ §fOre §6§lBoost"),
    ORE_DESC1("  §8┃ §fPermet de configurer les", "  §8┃ §fConfigure"),
    ORE_DESC2("  §8┃ §fparamètres des boosts des §2minerais§r.", "  §8┃ §fore §2boost§r parameters."),
    ARENA_NAME("§8┃ §fRecréer l'%main_color%Arena", "§8┃ §fRecreate %main_color%Arena"),
    ARENA_DESC1("  §8┃ §fPermet de recréer l'arène.", "  §8┃ §fRecreate the arena."),
    ARENA_DESC2("  §8┃ §c§lAttention, cela effacera l'arène existante.", "  §8┃ §c§lWarning: this will delete the existing arena."),
    PREGEN_NAME("§8┃ §fRedémarrer la%main_color% Prégénération", "§8┃ §fRestart%main_color% Pregeneration"),
    PREGEN_DESC("  §8┃ §fPermet de redémarrer la prégénération.", "  §8┃ §fRestart the pregeneration."),
    TP_LOBBY_NAME("§8┃ §fTéléportation au §a§llobby", "§8┃ §fTeleport to §a§llobby"),
    TP_ARENA_NAME("§8┃ §fTéléportation au monde %main_color%§lArena", "§8┃ §fTeleport to %main_color%§lArena"),
    TP_LOBBY_DESC("  §8┃ §fPermet de téléporter au §a§llobby.", "  §8┃ §fTeleport to §a§llobby."),
    TP_ARENA_DESC("  §8┃ §fPermet de téléporter au monde %main_color%§lArena.", "  §8┃ §fTeleport to %main_color%§lArena."),
    CENTER_NAME("§8┃ §fChanger le %main_color%Type de Centre", "§8┃ §fChange %main_color%Center Type"),
    CENTER_DESC1("  §8┃ §fPermet de changer le type de centre", "  §8┃ §fChange the center type"),
    CENTER_DESC2("  §8┃ §fde l'%main_color%arène.", "  §8┃ §fof the %main_color%arena."),
    ;

    private final Map<String, String> translations;
    WorldUiLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "ui.world." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
