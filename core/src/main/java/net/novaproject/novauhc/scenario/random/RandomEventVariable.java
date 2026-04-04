package net.novaproject.novauhc.scenario.random;

import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.utils.VariableType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RandomEventVariable {

    Class<? extends Lang> lang();

    String nameKey();

    String descKey() default "";

    VariableType type();
}
