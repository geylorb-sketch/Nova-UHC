package net.novaproject.novauhc.scenario.random;

import net.novaproject.novauhc.scenario.Scenario;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Manages and schedules a collection of {@link RandomGameEvent}s.
 *
 * Usage in a Scenario:
 * <pre>
 *   private final RandomEventScheduler events = new RandomEventScheduler();
 *
 *   {@literal @}Override public void onGameStart() {
 *       events.register(new MyEvent(), new OtherEvent());
 *       events.start(this, Main.get());
 *   }
 *
 *   {@literal @}Override public void onStop() {
 *       events.stop();
 *   }
 * </pre>
 */
public class RandomEventScheduler {

    private final List<RandomGameEvent<?>> events = new ArrayList<>();
    private final List<Integer> taskIds = new ArrayList<>();
    private final Random random = new Random();
    private volatile boolean running = false;

    public void register(RandomGameEvent<?>... events) {
        this.events.addAll(Arrays.asList(events));
    }

    public List<RandomGameEvent<?>> getEvents() {
        return events;
    }

    /**
     * Starts scheduling all registered enabled events.
     * Each event picks a random moment in [minGameTime, maxGameTime] (in seconds),
     * then rolls its chance. If the roll succeeds, execute() is called.
     * Repeating events re-schedule themselves after firing.
     */
    @SuppressWarnings("unchecked")
    public <S extends Scenario> void start(S scenario, JavaPlugin plugin) {
        running = true;
        for (RandomGameEvent<?> event : events) {
            if (!event.isEnabled()) continue;
            ((RandomGameEvent<S>) event).setScenario(scenario);
            scheduleOne(event, plugin);
        }
    }

    private void scheduleOne(RandomGameEvent<?> event, JavaPlugin plugin) {
        int min = event.getMinGameTime();
        int max = event.getMaxGameTime();
        int range = Math.max(0, max - min);
        long delayTicks = (long) (min + (range > 0 ? random.nextInt(range + 1) : 0)) * 20L;

        int id = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (Math.random() < event.getChance()) {
                event.execute();
            }
            if (event.isRepeating() && event.isEnabled() && running) {
                scheduleOne(event, plugin);
            }
        }, delayTicks).getTaskId();

        taskIds.add(id);
    }

    public void stop() {
        running = false;
        taskIds.forEach(Bukkit.getScheduler()::cancelTask);
        taskIds.clear();
    }
}
