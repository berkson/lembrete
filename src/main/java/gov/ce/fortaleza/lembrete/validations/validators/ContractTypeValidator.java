package gov.ce.fortaleza.lembrete.validations.validators;

import gov.ce.fortaleza.lembrete.enums.ContractTypes;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import gov.ce.fortaleza.lembrete.validations.annotations.ContractTypeVerification;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by berkson
 * Date: 11/01/2022
 * Time: 23:56
 */
public class ContractTypeValidator implements ConstraintValidator<ContractTypeVerification, Long> {

    private ContractService contractService;

    @Autowired
    public void setContract(ContractService contractService) {
        this.contractService = contractService;
    }


    @Override
    public void initialize(ContractTypeVerification constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    @Transactional
    public boolean isValid(Long id, ConstraintValidatorContext context) {

        String type = contractService.getById(id)
                .getContractType().getCode();

        return type.equals(ContractTypes.SERVICO_CONTINUADO.getCode());
    }
}
