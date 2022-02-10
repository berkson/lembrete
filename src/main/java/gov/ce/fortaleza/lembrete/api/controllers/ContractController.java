package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
@PropertySource(value = "classpath:general.properties")
public class ContractController {

    public static final String CONTRACT_API_ROOT = "/api/contract";
    private final ContractService contractService;
    @Value("${pagination.properties.quantity_per_page}")
    private int quantityPerPage;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_SUPORTE", "ROLE_CADCONTRACT"})
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO newContract(@Valid @RequestBody ContractDTO contractDTO) {

        ContractDTO contract = contractService.save(contractDTO);
        log.info("Contrato id: " + contract.getId() + " Salvo!");
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
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO add(@Valid @RequestBody AdditiveDTO additiveDTO) {
        return contractService.add(additiveDTO);
    }

    @IsUser
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContractDTO> getContracts(@RequestParam(value = "pag", defaultValue = "0") int pag,
                                          @RequestParam(value = "ord", defaultValue = "contractNumber") String ord,
                                          @RequestParam(value = "dir", defaultValue = "DESC") String dir) {
        PageRequest pageRequest = PageRequest.of(pag, this.quantityPerPage, Sort.Direction.valueOf(dir));
        return contractService.findAll(pageRequest);
    }
}
