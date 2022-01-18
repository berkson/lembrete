package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractTypesDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.ContractTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

import static gov.ce.fortaleza.lembrete.api.controllers.ContractTypeController.CONTRACT_TYPE_API;


/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:52
 */
@Slf4j
@RestController
@RequestMapping(value = CONTRACT_TYPE_API)
@Validated
public class ContractTypeController {

    public static final String CONTRACT_TYPE_API = "/api/contract-type";
    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }


    @IsUser
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractTypeDTO getContractType(@PathVariable @Min(1) Long id) {
        return contractTypeService.findById(id);
    }

    @IsUser
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ContractTypesDTO getTypes() {
        return new ContractTypesDTO(contractTypeService.findAll());
    }
}
