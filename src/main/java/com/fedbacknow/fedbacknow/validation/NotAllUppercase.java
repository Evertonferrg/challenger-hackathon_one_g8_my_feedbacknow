package com.fedbacknow.fedbacknow.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotallUppercaseValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface  NotAllUppercase {

    String message() default "Amensagem não pode conter apenas letras maiúsculas ";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
