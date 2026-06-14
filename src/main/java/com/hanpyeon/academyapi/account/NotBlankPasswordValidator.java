package com.hanpyeon.academyapi.account;

import com.hanpyeon.academyapi.account.model.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankPasswordValidator implements ConstraintValidator<NotBlankPassword, Password> {
    @Override
    public boolean isValid(Password password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && !password.isBlank();
    }
}
