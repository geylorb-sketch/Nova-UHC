// TODO: renommer en <NomScénario>UHC et ajuster le package
package net.novaproject.myscenario;

import net.novaproject.novauhc.lang.LangManager;
import net.novaproject.novauhc.scenario.ScenarioVariable;
import net.novaproject.novauhc.scenario.random.RandomGameEvent;
import net.novaproject.novauhc.scenario.random.RandomEventScheduler;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.scenario.role.camps.Camps;
import net.novaproject.novauhc.utils.ItemCreator;
import net.novaproject.novauhc.utils.VariableType;
import net.novaproject.myscenario.lang.MyScenarioDescLang;
import net.novaproject.myscenario.lang.MyScenarioLang;
import net.novaproject.myscenario.lang.MyScenarioVarLang;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MyScenarioUHC extends ScenarioRole<MyRole> {

    private static MyScenarioUHC instance;
    public static MyScenarioUHC get() { return instance; }

    // ── Paramètres configurables du scénario ─────────────────────────
    // TODO: ajouter un @ScenarioVariable par réglage global (radius, durée, etc.)
    @ScenarioVariable(
        lang    = MyScenarioVarLang.class,
        nameKey = "MY_SCENARIO_PARAM_NAME",
        descKey = "MY_SCENARIO_PARAM_DESC",
        type    = VariableType.INTEGER
    )
    private int myGlobalParam = 10; // TODO

    // ── Événements aléatoires (optionnel) ────────────────────────────
    // TODO: décommenter et créer la classe si le scénario a des événements aléatoires
    // @ScenarioVariable(lang = MyScenarioVarLang.class, nameKey = "MY_EVENT_NAME", type = VariableType.RANDOM_EVENT)
    // private RandomGameEvent<?> myEvent = new MyRandomEvent();
    // private final RandomEventScheduler eventScheduler = new RandomEventScheduler();

    // ── Constructeur ─────────────────────────────────────────────────
    public MyScenarioUHC() {
        instance = this;

        // TODO: addRole(XxxRole.class); pour chaque rôle du scénario
        // Ordre par camp recommandé : PASSIF → NOCIF → HYBRIDE → BOSS
        // addRole(RoleARole.class);
        // addRole(RoleBRole.class);
    }

    // ── Identité ─────────────────────────────────────────────────────

    @Override
    public String getName() {
        return "TODO: NomScénario"; // TODO
    }

    @Override
    public String getDescription(Player p) {
        return LangManager.get().get(MyScenarioDescLang.DESCRIPTION, p);
    }

    @Override
    public ItemCreator getItem() {
        return new ItemCreator(Material.NETHER_STAR); // TODO: material représentatif
    }

    @Override
    public Camps[] getCamps() {
        return MyCamps.values();
    }

    // ── Lifecycle ────────────────────────────────────────────────────

    @Override
    public void onGameStart() {
        // OBLIGATOIRE : enregistrer les 3 enums Lang
        LangManager.get().register(MyScenarioLang.values());
        LangManager.get().register(MyScenarioVarLang.values());
        LangManager.get().register(MyScenarioDescLang.values());

        // TODO: démarrer les managers/schedulers ici si nécessaire
        // eventScheduler.register(myEvent);
        // eventScheduler.start(this, Main.get());
    }

    @Override
    public void onStop() {
        // TODO: arrêter les managers/schedulers ici
        // eventScheduler.stop();
    }

    // ── Getters pour les @ScenarioVariable ───────────────────────────
    // TODO: ajouter un getter par @ScenarioVariable (lu dynamiquement, jamais mis en cache)
    public int getMyGlobalParam() { return myGlobalParam; }
}
