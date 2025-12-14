package com.fedbacknow.fedbacknow.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotallUppercaseValidator implements ConstraintValidator<NotAllUppercase, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;

        return value.chars().anyMatch(Character::isLowerCase);
    }
}
