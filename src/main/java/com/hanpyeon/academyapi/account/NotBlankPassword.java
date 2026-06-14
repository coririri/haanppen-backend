package com.hanpyeon.academyapi.account;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = {NotBlankPasswordValidator.class})
public @interface NotBlankPassword {
    String message() default "비밀번호를 입력해주세요";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
