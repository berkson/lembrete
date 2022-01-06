package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.services.CompanyService;
import gov.ce.fortaleza.lembrete.services.ContractService;
import gov.ce.fortaleza.lembrete.services.InterestedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    private final CompanyService companyService;
    private final InterestedService interestedService;

    public ContractController(ContractService contractService,
                              CompanyService companyService, InterestedService interestedService) {
        this.contractService = contractService;
        this.companyService = companyService;
        this.interestedService = interestedService;
    }

    //TODO: Verificar possibilidade de reduzir esse método
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO newContract(@RequestBody ContractDTO contractDTO) {

        companyService.findByCnpj(contractDTO.getCompany().getCnpj())
                .ifPresent(contractDTO::setCompany);

        contractDTO.getInterestedList().forEach(interestedDTO -> {
            interestedService
                    .findByCpf(interestedDTO.getCpf())
                    .ifPresent(interested -> interestedDTO.setId(interested.getId()));
            if (interestedDTO.isNew())
                interestedDTO.setId(interestedService.save(interestedDTO).getId());
        });

        if (contractDTO.getCompany().isNew())
            contractDTO.setCompany(companyService.save(contractDTO.getCompany()));

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
