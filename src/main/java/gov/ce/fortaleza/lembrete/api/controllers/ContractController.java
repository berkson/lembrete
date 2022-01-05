package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.services.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static gov.ce.fortaleza.lembrete.api.controllers.ContractController.CONTRACT_API_ROOT;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:52
 */
@Slf4j
@RestController
@RequestMapping(value = CONTRACT_API_ROOT)
public class ContractController {

    public static final String CONTRACT_API_ROOT = "/api/contract";
    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO newContract(@Valid @RequestBody ContractDTO contractDTO) {

        return contractService.save(contractDTO);
    }

    // TODO: criar o erro personalizado de não encontrado
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO getContract(@PathVariable Long id) {
        return contractService.findById(id)
                .orElseThrow(() -> new RuntimeException("Não Encontrado"));
    }
}
