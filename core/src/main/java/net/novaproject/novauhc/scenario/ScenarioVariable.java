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

    /**
     * La classe Lang enum contenant les traductions.
     * Ex: SoulBrotherLang.class, AcidRainLang.class
     */
    Class<? extends Lang> lang() default ScenarioVarLang.class;

    /**
     * Nom de la constante enum pour le NOM de la variable.
     * Ex: "VAR_REUNION_TIME_NAME"
     */
    String nameKey();

    /**
     * Nom de la constante enum pour la DESCRIPTION.
     * Ex: "VAR_REUNION_TIME_DESC"
     */
    String descKey() default "";

    VariableType type();
}