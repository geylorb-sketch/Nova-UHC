package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;

public enum NineSlotLang implements Lang {

    START("§8[NineSlot] §fVotre inventaire est maintenant limité à la hotbar !", "§8[NineSlot] §fYour inventory is now limited to the hotbar!"),
    START_ALL("§8[NineSlot] §fTous les inventaires sont maintenant limités à 9 slots !", "§8[NineSlot] §fAll inventories are now limited to 9 slots!"),
    UNLOCK_ALL("§8[NineSlot] §fLes inventaires sont maintenant libres !", "§8[NineSlot] §fInventories are now free!"),
    ITEM_DROPPED("§8[NineSlot] §fObjet jeté : inventaire plein !", "§8[NineSlot] §fItem dropped: inventory full!"),
    CANNOT_USE_INV("§8[NineSlot] §cVous ne pouvez pas utiliser l'inventaire principal !", "§8[NineSlot] §cYou cannot use the main inventory!"),
    CANNOT_MOVE("§8[NineSlot] §cVous ne pouvez pas déplacer d'objets vers l'inventaire principal !", "§8[NineSlot] §cYou cannot move items to the main inventory!"),
    ITEM_DROPPED2("§8[NineSlot] §fObjet jeté pour faire de la place !", "§8[NineSlot] §fItem dropped to make space!"),
    ADMIN_CLEARED("§8[NineSlot] §fInventaire principal vidé par un administrateur !", "§8[NineSlot] §fMain inventory cleared by an administrator!"),
    ;

    private final Map<String, String> translations;
    NineSlotLang(String fr, String en) { this.translations = Map.of("fr_FR", fr, "en_US", en); }
    @Override public String getKey() { return "nineslot." + name(); }
    @Override public Map<String, String> getTranslations() { return translations; }
}
