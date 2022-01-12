package gov.ce.fortaleza.lembrete.validations.validators;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import gov.ce.fortaleza.lembrete.validations.annotations.SixtyMonthsVerification;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
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

    private ContractService contractService;

    @Autowired
    public void setContract(ContractService contractService) {
        this.contractService = contractService;
    }

    @Override
    public void initialize(SixtyMonthsVerification constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Transactional
    public boolean isValid(AdditiveDTO additive, ConstraintValidatorContext context) {

        Contract contract = contractService.getById(additive.getContractId());

        LocalDate initialDate = contract.getInitialDate();
        LocalDate finalDate = contract.getFinalDate();

        long timePassed = ChronoUnit.MONTHS
                .between(initialDate, finalDate);


        long total = timePassed + additive.getDeadline();


        if (total > contract.getContractType().getMaxValidity()) {
            long remain = contract.getContractType().getMaxValidity() - timePassed;
            if (context instanceof HibernateConstraintValidatorContext) {
                context.unwrap(HibernateConstraintValidatorContext.class)
                        .addMessageParameter("0", remain < 0 ? 0 : remain);
            }

            return false;
        }

        return true;
    }
}
