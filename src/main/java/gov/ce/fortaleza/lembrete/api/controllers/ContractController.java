package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.exceptions.CustomDataIntegrityException;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import gov.ce.fortaleza.lembrete.services.errors.HandleErrorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Objects;

import static gov.ce.fortaleza.lembrete.api.controllers.ContractController.CONTRACT_API_ROOT;

/**
 * Created by Berkson Ximenes
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
    public static final String CONTRACT_NUMBER_PARAM = "contractNumber";
    private final ContractService contractService;
    @Qualifier(value = "handleContractErrorsImpl")
    private final HandleErrorsService handleErrorsService;
    @Value("${pagination.properties.quantity_per_page}")
    private int quantityPerPage;

    public ContractController(ContractService contractService, HandleErrorsService handleErrorsService) {
        this.contractService = contractService;
        this.handleErrorsService = handleErrorsService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPORTE', 'ROLE_CADCONTRACT') || hasAnyAuthority('SIGECON_CADCONTRACT')")
    @PostMapping(value = "/new")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO newContract(@Valid @RequestBody ContractDTO contractDTO) throws CustomDataIntegrityException {

        ContractDTO contract = null;
        try {
            contract = contractService.save(contractDTO);
        } catch (DataIntegrityViolationException e) {
            handleErrorsService.handleDataIntegrityErros(e);
        }

        log.info("Contrato id: " + Objects.requireNonNull(contract).getId() + " Salvo!");
        return contract;
    }

    @IsUser
    @PostMapping(value = "/check")
    public ResponseEntity<Object> checkContract(@RequestBody String number) {
        return new ResponseEntity<>(contractService.contractExists(number), HttpStatus.OK);
    }

    @IsUser
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO getContract(@PathVariable @Min(1) Long id) {
        return contractService.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPORTE', 'ROLE_CADADITIVO') || hasAnyAuthority('SIGECON_CADADITIVO')")
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public ContractDTO add(@Valid @RequestBody AdditiveDTO additiveDTO) {
        return contractService.add(additiveDTO);
    }

    //@IsUser
    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    @IsUser
    public Page<ContractDTO> getContracts(@RequestParam(value = "pag", defaultValue = "0") int pag,
                                          @RequestParam(value = "ord", defaultValue = ContractController.CONTRACT_NUMBER_PARAM) String ord,
                                          @RequestParam(value = "dir", defaultValue = "DESC") String dir) {

        PageRequest pageRequest;

        if (ord.equals(ContractController.CONTRACT_NUMBER_PARAM)) {
            pageRequest = PageRequest.of(pag, this.quantityPerPage);
            return contractService
                    .findAllByContractNumber(pageRequest, dir.toUpperCase());
        }

        pageRequest = PageRequest.of(pag, this.quantityPerPage, Sort.Direction.valueOf(dir.toUpperCase()), ord);

        return contractService.findAll(pageRequest);
    }
}
