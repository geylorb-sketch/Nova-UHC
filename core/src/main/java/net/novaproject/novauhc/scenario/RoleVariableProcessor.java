package net.novaproject.novauhc.scenario;

import net.novaproject.novauhc.ability.Ability;
import net.novaproject.novauhc.scenario.role.Role;
import net.novaproject.novauhc.scenario.role.RoleVariable;
import net.novaproject.novauhc.scenario.role.ScenarioRole;
import net.novaproject.novauhc.uhcplayer.UHCPlayer;

import java.lang.reflect.Field;

public class RoleVariableProcessor {

    public static void process(Object role, UHCPlayer player, ScenarioRole scenarioRole) {
        for (Field field : role.getClass().getDeclaredFields()) {
            RoleVariable variable = field.getAnnotation(RoleVariable.class);
            if (variable == null) continue;

            field.setAccessible(true);

            try {
                switch (variable.type()) {
                    case ABILITY -> handleAbility(field, role, player, scenarioRole);
                    case INTEGER, TIME -> {
                        Object value = field.get(role);
                        if (value instanceof Number n) field.set(role, n.intValue());
                    }
                    case DOUBLE, PERCENTAGE -> {
                        Object value = field.get(role);
                        if (value instanceof Number n) field.set(role, n.doubleValue());
                    }
                    case BOOLEAN -> {
                        Object value = field.get(role);
                        if (value instanceof Boolean b) field.set(role, b);
                    }
                    default -> { /* STRING and other types: leave as-is */ }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleAbility(Field field, Object role, UHCPlayer player, ScenarioRole scenarioRole)
            throws Exception {

        if (!Ability.class.isAssignableFrom(field.getType())) {
            throw new IllegalStateException(
                    "@RoleVariable(type=ABILITY) doit être Ability sur le champ " + field.getName());
        }

        Ability base = (Ability) field.get(role);
        if (base == null || !base.active()) return;

        Ability instance = base.clone();
        Role playerRole = scenarioRole.getRoleByUHCPlayer(player);
        instance.setOwner(playerRole.getOwner());
        playerRole.getAbilities().add(instance);
    }
}
