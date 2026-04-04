package net.novaproject.novauhc.lang.scenario;

import net.novaproject.novauhc.lang.Lang;
import java.util.Map;


public enum ParkourMasterLang implements Lang {

    PARKOUR_SPAWNED(
        "§a[ParkourMaster] §fUn parcours est apparu près de vous ! Complétez-le pour une récompense !",
        "§a[ParkourMaster] §fA parkour appeared near you! Complete it for a reward!"
    ),
    PARKOUR_BROADCAST(
        "§a[ParkourMaster] §fUn parcours est apparu près de %player% !",
        "§a[ParkourMaster] §fA parkour appeared near %player%!"
    ),
    CHECKPOINT_REACHED(
        "§a[ParkourMaster] §fCheckpoint %current%/%total% atteint !",
        "§a[ParkourMaster] §fCheckpoint %current%/%total% reached!"
    ),
    PARKOUR_COMPLETED(
        "§a§l[ParkourMaster] §fParcours complété ! Récompense : %reward%",
        "§a§l[ParkourMaster] §fParkour completed! Reward: %reward%"
    ),
    PARKOUR_FAILED(
        "§c[ParkourMaster] §fVous avez échoué au parcours ! Réessayez la prochaine fois.",
        "§c[ParkourMaster] §fYou failed the parkour! Try again next time."
    ),
    PARKOUR_EXPIRED(
        "§c[ParkourMaster] §fLe parcours a expiré !",
        "§c[ParkourMaster] §fThe parkour expired!"
    ),
    INVENTORY_FULL(
        "§a[ParkourMaster] §fInventaire plein ! Récompense jetée au sol.",
        "§a[ParkourMaster] §fInventory full! Reward dropped."
    ),

    
    PARKOUR_SPAWNED_PERSONAL(
        "§a[ParkourMaster] §fUn parcours est apparu près de vous ! Complétez-le pour une récompense !",
        "§a[ParkourMaster] §fA parkour course appeared near you! Complete it for a reward!"
    ),
    PARKOUR_SPAWNED_BROADCAST(
        "§a[ParkourMaster] §fUn parcours est apparu près de %player% !",
        "§a[ParkourMaster] §fA parkour course appeared near %player%!"
    ),
    PARKOUR_COMPLETED_BROADCAST(
        "§a[ParkourMaster] §f%player% a complété un parcours !",
        "§a[ParkourMaster] §f%player% completed a parkour course!"
    ),
    INVENTORY_FULL_DROP(
        "§a[ParkourMaster] §fInventaire plein ! Récompense jetée au sol.",
        "§a[ParkourMaster] §fInventory full! Reward dropped on the ground."
    ),
    ;

    private final Map<String, String> translations;

    ParkourMasterLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "parkourmaster." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}
