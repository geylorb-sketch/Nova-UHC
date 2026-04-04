package net.novaproject.novauhc.scenario.random;

import lombok.Getter;
import lombok.Setter;
import net.novaproject.novauhc.scenario.Scenario;
import net.novaproject.novauhc.utils.VariableSerializer;
import net.novaproject.novauhc.utils.VariableType;
import org.bson.Document;

/**
 * Base class for random game events.
 *
 * Subclasses set default timing/chance in their no-arg constructor:
 * <pre>
 *   public class MyEvent extends RandomGameEvent&lt;MyScenario&gt; {
 *       public MyEvent() {
 *           setMinGameTime(90 * 60);  // 90 min
 *           setMaxGameTime(120 * 60); // 120 min
 *           setChance(0.8);           // 80%
 *       }
 *       {@literal @}Override public void execute() { ... }
 *   }
 * </pre>
 *
 * Do NOT re-declare the fields from this class — that would shadow them
 * and break VariableSerializer which walks the class hierarchy.
 */
@Getter
@Setter
public abstract class RandomGameEvent<S extends Scenario> {

    @RandomEventVariable(lang = RandomEventLang.class, nameKey = "ENABLED_NAME", descKey = "ENABLED_DESC", type = VariableType.BOOLEAN)
    private boolean enabled = true;

    @RandomEventVariable(lang = RandomEventLang.class, nameKey = "CHANCE_NAME", descKey = "CHANCE_DESC", type = VariableType.PERCENTAGE)
    private double chance = 1.0;

    @RandomEventVariable(lang = RandomEventLang.class, nameKey = "MIN_TIME_NAME", descKey = "MIN_TIME_DESC", type = VariableType.TIME)
    private int minGameTime = 0;

    @RandomEventVariable(lang = RandomEventLang.class, nameKey = "MAX_TIME_NAME", descKey = "MAX_TIME_DESC", type = VariableType.TIME)
    private int maxGameTime = 3600;

    @RandomEventVariable(lang = RandomEventLang.class, nameKey = "REPEATING_NAME", descKey = "REPEATING_DESC", type = VariableType.BOOLEAN)
    private boolean repeating = false;

    private S scenario;

    /** Execute the event logic. Called when the event fires. */
    public abstract void execute();

    /** Display name used in the config UI. Override to customize. */
    public String getName() {
        return getClass().getSimpleName();
    }

    public Document toDoc() {
        return VariableSerializer.toDoc(this, RandomEventVariable.class, RandomEventVariable::type, true);
    }

    public void fromDoc(Document doc) {
        VariableSerializer.fromDoc(this, doc, RandomEventVariable.class, RandomEventVariable::type, true);
    }
}
