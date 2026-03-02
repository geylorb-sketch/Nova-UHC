package net.novaproject.novauhc.scenario;

import net.novaproject.novauhc.lang.Lang;
import net.novaproject.novauhc.lang.lang.ScenarioVarLang;
import net.novaproject.novauhc.utils.VariableType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ScenarioVariable {


    Class<? extends Lang> lang() default ScenarioVarLang.class;


    String nameKey();


    String descKey() default "";

    VariableType type();
}