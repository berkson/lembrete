package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.InterestedDTO;
import gov.ce.fortaleza.lembrete.services.common.InterestedService;
import org.hibernate.validator.constraints.br.CPF;
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
@RequestMapping(value = InterestedController.INTERESTED_API_ROOT)
public class InterestedController {
    public static final String INTERESTED_API_ROOT = "/api/interested";

    private final InterestedService interestedService;

    public InterestedController(InterestedService interestedService) {
        this.interestedService = interestedService;
    }

    @GetMapping(value = "/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public InterestedDTO getInterested(@PathVariable @CPF String cpf) {
        return interestedService.findByCpf(cpf).orElseThrow(EntityNotFoundException::new);
    }
}
