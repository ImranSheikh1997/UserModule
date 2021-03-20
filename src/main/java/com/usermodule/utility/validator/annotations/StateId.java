package com.usermodule.utility.validator.annotations;

import com.usermodule.utility.validator.StateIdValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {StateIdValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface StateId {
    String message() default "Invalid Country Id value";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};
}
