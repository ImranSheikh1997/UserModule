package com.usermodule.utility.validator.annotations;

import com.usermodule.utility.validator.CountryIdValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {CountryIdValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryID {

    String message() default "Invalid Country Id value";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default{};
}
