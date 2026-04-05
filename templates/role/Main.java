// TODO: ajuster le package (ex: net.novaproject.myscenario)
package net.novaproject.myscenario;

import net.novaproject.novauhc.ability.AbilityManager;
import net.novaproject.novauhc.scenario.ScenarioManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

// TODO: renommer en 'Main' (déjà le bon nom), ou en 'MyModulePlugin' si conflit
public class Main extends JavaPlugin {

    private static Main instance;

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Délai d'1 tick pour éviter la race condition avec NovaUHC (core)
        new BukkitRunnable() {
            @Override
            public void run() {
                // 1. Enregistrer le scénario
                ScenarioManager.get().addScenario(new MyScenarioUHC()); // TODO

                // 2. Enregistrer chaque ability (OBLIGATOIRE sinon cooldowns absents)
                AbilityManager a = AbilityManager.get();

                // TODO: décommenter / dupliquer pour chaque ability du module
                // Camp PASSIF
                // a.registerAbility(new RoleNamePassiveAbility());

                // Camp NOCIF
                // a.registerAbility(new OtherRoleAbility());

                // Camp BOSS
                // a.registerAbility(new BossAbility());
            }
        }.runTaskLater(this, 1L);
    }
}
