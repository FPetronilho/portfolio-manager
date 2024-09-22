package com.portfolio.portfoliomanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BooleanValidator.class)
public @interface ValidBoolean {
    String message() default "Invalid boolean value: it must be either 'true' or 'false'.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
