package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import static gov.ce.fortaleza.lembrete.api.controllers.ContractController.CONTRACT_API_ROOT;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:52
 */
@Slf4j
@RestController
@RequestMapping(value = CONTRACT_API_ROOT)
@Validated
public class ContractController {

    public static final String CONTRACT_API_ROOT = "/api/contract";
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPORTE", "ROLE_CADCONTRACT"})
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO newContract(@Valid @RequestBody ContractDTO contractDTO) {

        ContractDTO contract = contractService.save(contractDTO);
        log.info("Contrato id: " + contract.getId() + "Salvo!");
        return contract;
    }

    @IsUser
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO getContract(@PathVariable @Min(1) Long id) {
        return contractService.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPORTE", "ROLE_CADADITIVO"})
    @PostMapping("/add")
    public ContractDTO add(@Valid @RequestBody AdditiveDTO additiveDTO) {
        return contractService.add(additiveDTO);
    }
}
