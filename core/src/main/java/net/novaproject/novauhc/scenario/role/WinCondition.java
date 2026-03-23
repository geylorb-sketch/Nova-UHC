package net.novaproject.novauhc.scenario.role;

import net.novaproject.novauhc.uhcplayer.UHCPlayer;

import java.util.Map;

/**
 * Condition de victoire injectable dans un {@link ScenarioRole}.
 * Par défaut, ScenarioRole vérifie qu'un seul camp reste en vie.
 * Injecter un WinCondition permet de définir des conditions custom (timer, objectif, asymétrique, etc.)
 */
@FunctionalInterface
public interface WinCondition<T extends Role> {
    boolean isWin(Map<UHCPlayer, T> playerRoles);
}
