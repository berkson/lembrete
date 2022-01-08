package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.services.common.ContractTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.ce.fortaleza.lembrete.api.controllers.ContractTypeController.CONTRACT_TYPE_API;


/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:52
 */
@Slf4j
@RestController
@RequestMapping(value = CONTRACT_TYPE_API)
public class ContractTypeController {

    public static final String CONTRACT_TYPE_API = "/api/contract-type";
    private final ContractTypeService contractTypeService;

    public ContractTypeController(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractTypeDTO getContractType(@PathVariable Long id) {
        return contractTypeService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContractTypeDTO> getTypes() {
        return contractTypeService.findAll();
    }
}
