package net.novaproject.novauhc.utils;

import net.novaproject.novauhc.scenario.random.RandomGameEvent;
import org.bson.Document;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.function.Function;

public class VariableSerializer {

    /**
     * Sérialise tous les champs annotés d'un objet vers un Document MongoDB.
     *
     * @param obj            L'objet à sérialiser
     * @param annotationType L'annotation qui marque les champs (AbilityVariable, RoleVariable, ScenarioVariable)
     * @param getType        Fonction qui lit le VariableType depuis l'annotation
     * @param walkHierarchy  true = remonte toute la hiérarchie de classes (pour Ability), false = seulement la classe déclarée
     */
    public static <A extends Annotation> Document toDoc(
            Object obj,
            Class<A> annotationType,
            Function<A, VariableType> getType,
            boolean walkHierarchy
    ) {
        Document doc = new Document();
        Class<?> clazz = obj.getClass();

        do {
            for (Field field : clazz.getDeclaredFields()) {
                A annotation = field.getAnnotation(annotationType);
                if (annotation == null) continue;

                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value == null) continue;

                    VariableType type = getType.apply(annotation);
                    switch (type) {
                        case TIME -> {
                            if (value instanceof Integer i) doc.append(field.getName(), i);
                        }
                        case PERCENTAGE -> {
                            if (value instanceof Double d) doc.append(field.getName(), d);
                            else if (value instanceof Integer i) doc.append(field.getName(), i);
                        }
                        case RANDOM_EVENT -> {
                            if (value instanceof RandomGameEvent<?> event)
                                doc.append(field.getName(), event.toDoc());
                        }
                        default -> doc.append(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = walkHierarchy ? clazz.getSuperclass() : null;
        } while (clazz != null && clazz != Object.class);

        return doc;
    }

    /**
     * Désérialise un Document MongoDB dans les champs annotés d'un objet.
     */
    public static <A extends Annotation> void fromDoc(
            Object obj,
            Document doc,
            Class<A> annotationType,
            Function<A, VariableType> getType,
            boolean walkHierarchy
    ) {
        if (doc == null) return;
        Class<?> clazz = obj.getClass();

        do {
            for (Field field : clazz.getDeclaredFields()) {
                A annotation = field.getAnnotation(annotationType);
                if (annotation == null) continue;
                if (!doc.containsKey(field.getName())) continue;

                field.setAccessible(true);
                try {
                    Object value = doc.get(field.getName());
                    VariableType type = getType.apply(annotation);

                    switch (type) {
                        case TIME -> {
                            if (value instanceof Number n) field.set(obj, n.intValue());
                        }
                        case PERCENTAGE -> {
                            if (value instanceof Number n) {
                                if (field.getType() == double.class || field.getType() == Double.class)
                                    field.set(obj, n.doubleValue());
                                else if (field.getType() == int.class || field.getType() == Integer.class)
                                    field.set(obj, n.intValue());
                            }
                        }
                        case RANDOM_EVENT -> {
                            if (value instanceof Document subDoc) {
                                Object existing = field.get(obj);
                                if (existing instanceof RandomGameEvent<?> event)
                                    event.fromDoc(subDoc);
                            }
                        }
                        default -> {
                            if (value instanceof Number n) {
                                if (field.getType() == int.class || field.getType() == Integer.class)
                                    field.set(obj, n.intValue());
                                else if (field.getType() == double.class || field.getType() == Double.class)
                                    field.set(obj, n.doubleValue());
                                else if (field.getType() == float.class || field.getType() == Float.class)
                                    field.set(obj, n.floatValue());
                                else if (field.getType() == long.class || field.getType() == Long.class)
                                    field.set(obj, n.longValue());
                                else field.set(obj, value);
                            } else {
                                field.set(obj, value);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = walkHierarchy ? clazz.getSuperclass() : null;
        } while (clazz != null && clazz != Object.class);
    }
}
