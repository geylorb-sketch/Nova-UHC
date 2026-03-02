package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum BloodCycleLang implements Lang {

    TAKE_DAMAGE(
        "Ahhh ! Force à toi mauvais minerais",
        "Ouch! You mined the wrong ore!"
    ),
    DIAMOND(
        "Vous ne devez pas miner §b§lDiamant§f. Changement du cycle dans §6§l%blood_timer%§f secondes",
        "You must not mine §b§lDiamond§f. Cycle changes in §6§l%blood_timer%§f seconds"
    ),
    GOLD(
        "Vous ne devez pas miner §6§lOr",
        "You must not mine §6§lGold"
    ),
    IRON(
        "Vous ne devez pas miner §7§lFer",
        "You must not mine §7§lIron"
    ),
    COAL(
        "Vous ne devez pas miner §8§lCharbon",
        "You must not mine §8§lCoal"
    ),
    LAPIS(
        "Vous ne devez pas miner §9§lLapis",
        "You must not mine §9§lLapis"
    ),
    REDSTONE(
        "Vous ne devez pas miner §c§lRedstone",
        "You must not mine §c§lRedstone"
    )
    ;

    private final Map<String, String> translations;

    BloodCycleLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_US", en);
    }

    @Override
    public String getKey() { return "bloodcycle." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
