package gov.ce.fortaleza.lembrete.validations.annotations;

import gov.ce.fortaleza.lembrete.validations.validators.ContractTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 18:17
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ContractTypeValidator.class)
@Documented
public @interface ContractTypeVerification {
    String message() default "{object.validation.constraint.contractTypeBlock.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
