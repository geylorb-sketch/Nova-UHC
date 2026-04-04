package net.novaproject.novauhc.lang.special;

import net.novaproject.novauhc.lang.Lang;

import java.util.Map;


public enum FKLang implements Lang {

    

    WELCOME_FK(
            "§6§l⚔ FK §r§fBienvenue ! Protégez votre base et éliminez les équipes adverses !",
            "§6§l⚔ FK §r§fWelcome! Protect your base and eliminate the enemy teams!"
    ),
    DISABLE_ACTION(
            "§c✘ Cette action est désactivée pour le moment.",
            "§c✘ This action is currently disabled."
    ),

    
    ANNONCE_NEW_EPISODE(
            "§6§l⚔ FK §r§eNouvel épisode ! Episode : %episode%",
            "§6§l⚔ FK §r§eNew episode! Episode : %episode%"
    ),
    ANNONCE_NETHER(
            "§6§l⚔ FK §r§cLe Nether est désormais accessible !",
            "§6§l⚔ FK §r§cThe Nether is now accessible!"
    ),
    ANNONCE_END(
            "§6§l⚔ FK §r§5L'End est désormais accessible !",
            "§6§l⚔ FK §r§5The End is now accessible!"
    ),
    ANNONCE_ASSAUT(
            "§6§l⚔ FK §r§4§lL'ASSAUT EST LANCÉ ! §r§fVous pouvez désormais attaquer les bases ennemies !",
            "§6§l⚔ FK §r§4§lASSAULT HAS BEGUN! §r§fYou can now attack enemy bases!"
    ),

    
    CAPTURE_START(
            "§6§l⚔ FK §r§aCapture de la base de §e%enemy_team% §aen cours !",
            "§6§l⚔ FK §r§aCapturing §e%enemy_team%§a's base!"
    ),
    CAPTURE_WARNING(
            "§6§l⚔ FK §r§c⚠ Votre base est attaquée ! Défendez-la !",
            "§6§l⚔ FK §r§c⚠ Your base is under attack! Defend it!"
    ),
    CAPTURE_PROGRESS(
            "§6§l⚔ FK §r§aCapture en cours... §e%remaining_time%s §arestantes.",
            "§6§l⚔ FK §r§aCapture in progress... §e%remaining_time%s §aremaining."
    ),
    CAPTURE_PROGRESS_WARNING(
            "§6§l⚔ FK §r§c⚠ Base attaquée ! §e%remaining_time%s §cavant la capture !",
            "§6§l⚔ FK §r§c⚠ Base under attack! §e%remaining_time%s §cbefore capture!"
    ),
    CAPTURE_FAIL(
            "§6§l⚔ FK §r§cCapture annulée ! Tous les membres doivent rester près du coffre.",
            "§6§l⚔ FK §r§cCapture cancelled! All members must stay near the chest."
    ),

    
    TEAM_ELIMINATED(
            "§6§l⚔ FK §r§c☠ Votre équipe a été éliminée !",
            "§6§l⚔ FK §r§c☠ Your team has been eliminated!"
    ),
    TEAM_CAPTURED(
            "§6§l⚔ FK §r§a✓ Vous avez éliminé l'équipe §e%enemy_team% §a!",
            "§6§l⚔ FK §r§a✓ You eliminated team §e%enemy_team%§a!"
    ),
    TEAM_ELIMINATION_BROADCAST(
            "§6§l⚔ FK §r§fL'équipe §e%enemy_team% §fa été éliminée par §e%capturing_team% §f!",
            "§6§l⚔ FK §r§fTeam §e%enemy_team% §fhas been eliminated by §e%capturing_team%§f!"
    ),

    
    TNT_DISABLED(
            "§c✘ La TNT est désactivée avant l'assaut.",
            "§c✘ TNT is disabled before the assault."
    ),
    BUILD_DENIED(
            "§c✘ Vous ne pouvez pas construire dans cette base.",
            "§c✘ You cannot build in this base."
    ),

    ;

    private final Map<String, String> translations;

    FKLang(String fr, String en) {
        this.translations = Map.of("fr_FR", fr, "en_EN", en);
    }

    @Override
    public String getKey() { return "fk." + name(); }

    @Override
    public Map<String, String> getTranslations() { return translations; }
}