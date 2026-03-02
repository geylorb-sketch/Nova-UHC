package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;

public enum FlowerPowerLang implements Lang {

    FLOWER_POWER_NAME(
            "FlowerPower",
            "FlowerPower"
    ),
    FLOWER_POWER_DESC(
            "Les fleurs donnent des drops aléatoires aux joueurs.",
            "Flowers give random drops to players."
    ),
    VAR_GOLDEN_APPLE_AMOUNT_NAME(
            "Quantité de Golden Apple",
            "Golden Apple Amount"
    ),
    VAR_GOLDEN_APPLE_AMOUNT_DESC(
            "Nombre de Golden Apples droppées lors d'un drop rare.",
            "Amount of Golden Apples dropped on a rare drop."
    ),
    VAR_INGOT_AMOUNT_NAME(
            "Quantité de Lingots",
            "Ingot Amount"
    ),
    VAR_INGOT_AMOUNT_DESC(
            "Nombre de lingots droppés lors d'un drop rare.",
            "Amount of ingots dropped on a rare drop."
    ),
    VAR_NORMAL_DROP_AMOUNT_NAME(
            "Quantité Drop Normal",
            "Normal Drop Amount"
    ),
    VAR_NORMAL_DROP_AMOUNT_DESC(
            "Nombre d'items droppés lors d'un drop commun.",
            "Amount of items dropped on a common drop."
    ),
    VAR_MAX_ENCHANT_LEVEL_NAME(
            "Niveau Max d'Enchantement",
            "Max Enchant Level"
    ),
    VAR_MAX_ENCHANT_LEVEL_DESC(
            "Niveau maximum des enchantements sur les livres enchantés droppés.",
            "Maximum enchantment level on dropped enchanted books."
    );

    private final String fr;
    private final String en;

    FlowerPowerLang(String fr, String en) {
        this.fr = fr;
        this.en = en;
    }

    @Override
    public String getKey() {
        return "flowerpower." + name();
    }

    @Override
    public Map<String, String> getTranslations() {
        return Map.of(
                "fr_FR", fr,
                "en_EN", en
        );
    }
}