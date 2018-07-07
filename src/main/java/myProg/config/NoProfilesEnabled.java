package myProg.config;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * Indicates that a component is eligible for registration when NO one
 * {@linkplain #value specified profiles} are active.
 *
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(NoProfilesEnabledCondition.class)
public @interface NoProfilesEnabled {
    String[] value();
}
