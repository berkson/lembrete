package gov.ce.fortaleza.lembrete.validations.annotations;

import gov.ce.fortaleza.lembrete.validations.validators.SixtyMonthsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 18:17
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SixtyMonthsValidator.class)
@Documented
public @interface SixtyMonthsVerification {
    String message() default "{object.validation.constraint.sixtyMonths.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
