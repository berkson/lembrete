package gov.ce.fortaleza.lembrete.validations.validators;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.validations.annotations.SixtyMonthsVerification;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by berkson
 * Date: 10/01/2022
 * Time: 18:21
 */
public class SixtyMonthsValidator implements ConstraintValidator<SixtyMonthsVerification, AdditiveDTO> {

    @Override
    public void initialize(SixtyMonthsVerification constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(AdditiveDTO value, ConstraintValidatorContext context) {

        LocalDate initialDate = value.getContract().getInitialDate();
        LocalDate finalDate = value.getContract().getFinalDate();

        long timePassed = ChronoUnit.MONTHS
                .between(initialDate, finalDate);


        long total = timePassed + value.getDeadline();


        if (total > value.getContract().getContractType().getMaxValidity()) {
            if (context instanceof HibernateConstraintValidatorContext) {
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addMessageParameter("0", value.getContract().getContractType()
                                .getMaxValidity() - timePassed);
            }

            return false;
        }

        return true;
    }
}
