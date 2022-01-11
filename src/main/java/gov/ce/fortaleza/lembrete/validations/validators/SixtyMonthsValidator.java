package gov.ce.fortaleza.lembrete.validations.validators;

import gov.ce.fortaleza.lembrete.api.models.AditivoDTO;
import gov.ce.fortaleza.lembrete.validations.annotations.SixtyMonthsVerification;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 18:21
 */
public class SixtyMonthsValidator implements ConstraintValidator<SixtyMonthsVerification, AditivoDTO> {

    @Override
    public void initialize(SixtyMonthsVerification constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AditivoDTO value, ConstraintValidatorContext context) {
        LocalDate initialDate = value.getContract().getInitialDate();
        LocalDate finalDate = value.getContract().getFinalDate();

        int timePassed = Period
                .between(initialDate, finalDate).getMonths();

        context.unwrap(HibernateConstraintValidatorContext.class)
                .addMessageParameter("max", value.getContract().getContractType()
                        .getMaxValidity() - timePassed);

        int total = timePassed + value.getDeadline();

        return total <= value.getContract().getContractType().getMaxValidity();

    }
}
