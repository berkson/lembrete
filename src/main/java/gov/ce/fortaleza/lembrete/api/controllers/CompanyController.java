package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.CompanyDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.CompanyService;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

/**
 * Created by berkson
 * Date: 07/04/2022
 * Time: 22:23
 */
@RestController
@Validated
@RequestMapping(value = CompanyController.COMPANY_API_ROOT)
public class CompanyController {
    public static final String COMPANY_API_ROOT = "/api/company";

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(value = "/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    @IsUser
    public CompanyDTO getCompany(@PathVariable @CNPJ String cnpj) {
        return companyService.findByCnpj(cnpj).orElseThrow(EntityNotFoundException::new);
    }
}
