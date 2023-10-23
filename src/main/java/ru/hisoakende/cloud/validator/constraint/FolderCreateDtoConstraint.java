package ru.hisoakende.cloud.validator.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.hisoakende.cloud.validator.FolderCreateDtoValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FolderCreateDtoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FolderCreateDtoConstraint {
    String message() default "Invalid folder";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}