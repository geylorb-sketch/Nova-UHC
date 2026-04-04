package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum BeatTheSantaLang implements Lang {

    WARNING_LUTIN(
            "§c§lAttention ! Vous devez gagner seul, pas en équipe avec les autres lutins.",
            "§c§lWarning! You must win alone, not by teaming with the other elves."
    ),

    WARNING_SANTA(
            "§c§lAttention ! Vous devez gagner seul, les lutins essaieront de vous tuer en premier avant de se battre entre eux.",
            "§c§lWarning! You must win alone. The elves will try to kill you first before fighting each other."
    ),

    WARNING_SANTA_DEATH(
            "§eLe Père Noël est mort ! Les lutins doivent maintenant s'entretuer pour gagner !",
            "§eSanta is dead! The elves must now fight each other to win!"
    );

    private final Map<String, String> translations;

    BeatTheSantaLang(String fr, String en) {
        this.translations = Map.of(
                "fr_FR", fr,
                "en_EN", en
        );
    }

    @Override
    public String getKey() {
        return "beatthesanta." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return translations;
    }
}