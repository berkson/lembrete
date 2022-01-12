package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.exceptions.RestExceptionHandler;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.enums.ContractTypes;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by berkson
 * Date: 11/01/2022
 * Time: 21:28
 */
// TODO: Finish this tests
class ContractControllerTest {

    private static final long ID = 1L;

    @Mock
    ContractService contractService;

    @InjectMocks
    ContractController contractController;

    @InjectMocks
    RestExceptionHandler restExceptionHandler;

    MockMvc mockMvc;

    ContractDTO contractDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contractController)
                .setControllerAdvice(restExceptionHandler).build();
        contractDTO = new ContractDTO();
        ContractTypeDTO contractTypeDTO = new ContractTypeDTO(ID, ContractTypes.COOP.getCode(),
                ContractTypes.COOP.getDescription(), ContractTypes.COOP.getValidity());
        contractDTO.setId(ID);
        contractDTO.setContractType(contractTypeDTO);
    }

    @Test
    void newContract() {
    }

    @Test
    void getContract() throws Exception {
        when(contractService.findById(anyLong())).thenReturn(Optional.ofNullable(contractDTO));
        //when
        ContractDTO contract = contractService.findById(ID).get();

        assertNotNull(contract);
        assertEquals(ContractTypes.COOP.getCode(), contractDTO.getContractType().getCode());
        mockMvc.perform(get(ContractController.CONTRACT_API_ROOT + "/" + ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo((int) ID)));
    }

    @Test
    void add() {
    }
}