// TODO: remplacer 'MyScenario' par le vrai nom du scénario partout dans ce fichier
// TODO: si le scénario est dans le core → garder ce package
//       si c'est dans un module externe → adapter (ex: net.novaproject.myscenario)
package net.novaproject.novauhc.scenario.normal;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.lang.scenario.ScenarioDescLang;
import net.novaproject.novauhc.lang.special.ScenarioVarLang;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MyScenario extends Scenario {

    // ── Paramètres configurables ─────────────────────────────────────
    // TODO: ajouter un @ScenarioVariable par paramètre configurable en jeu
    // Types disponibles : TIME, INTEGER, DOUBLE, BOOLEAN, PERCENTAGE
    @ScenarioVariable(
        lang    = ScenarioVarLang.class,
        nameKey = "MY_PARAM_NAME",  // TODO: clé dans ScenarioVarLang (ou ton propre Lang)
        descKey = "MY_PARAM_DESC",
        type    = VariableType.INTEGER
    )
    private int myParam = 30; // TODO: valeur par défaut

    // ── Identité ─────────────────────────────────────────────────────

    @Override
    public String getName() {
        return "TODO: NomScénario"; // TODO
    }

    @Override
    public String getDescription(Player player) {
        // TODO: utiliser la bonne clé Lang pour la description affichée dans l'UI
        return LangManager.get().get(ScenarioDescLang.TODO_KEY, player);
    }

    @Override
    public ItemCreator getItem() {
        // TODO: choisir un Material représentatif du scénario
        return new ItemCreator(Material.DIAMOND);
    }

    // ── Hooks d'événement ────────────────────────────────────────────
    // Supprimer les méthodes non utilisées.
    // Hooks disponibles : onBreak, onPlace, onEntityDeath, onDeath, onKill,
    //   onConsume, onSec, onGameStart, onStart, onCraft, onDrop,
    //   onPlayerInteract, onMove, onBow, onHit, onDamage, onChatSpeak,
    //   onPickUp, noFood, onPortal, onFurnaceBurn, onPlayerInteractEntity

    @Override
    public void onDeath(UHCPlayer uhcPlayer, PlayerDeathEvent event) {
        // TODO: logique à la mort d'un joueur
    }

    // TODO: ajouter d'autres hooks selon la mécanique du scénario
}
