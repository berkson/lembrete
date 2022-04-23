package gov.ce.fortaleza.lembrete.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ce.fortaleza.lembrete.api.exceptions.RestExceptionHandler;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiError;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiErrors;
import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.enums.ContractTypes;
import gov.ce.fortaleza.lembrete.repositories.ContractTypeRepository;
import gov.ce.fortaleza.lembrete.services.common.ContractTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by berkson
 * Date: 11/01/2022
 * Time: 17:15
 */
class ContractTypeControllerTest {
    static final String NOT_FOUND = "error.not.found";

    @Mock
    ContractTypeService contractTypeService;

    @Mock
    ContractTypeRepository contractTypeRepository;

    @InjectMocks
    ContractTypeController contractTypeController;
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    @InjectMocks
    RestExceptionHandler restExceptionHandler = new RestExceptionHandler(messageSource);

    MockMvc mockMvc;

    ContractTypeDTO contractTypeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageSource.setDefaultLocale(Locale.getDefault());
        Properties properties = new Properties();
        properties.setProperty(ContractTypeControllerTest.NOT_FOUND, "Não encontrado");
        messageSource.setCommonMessages(properties);
        mockMvc = MockMvcBuilders.standaloneSetup(contractTypeController)
                .setControllerAdvice(restExceptionHandler).build();
        contractTypeDTO = new ContractTypeDTO(1L,
                ContractTypes.SERVICO_CONTINUADO.getCode(),
                ContractTypes.SERVICO_CONTINUADO.getDescription(),
                ContractTypes.SERVICO_CONTINUADO.getValidity());
    }

    @Test
    void getContractType() throws Exception {
        when(contractTypeService.findById(anyLong())).thenReturn(contractTypeDTO);
        //when
        ContractTypeDTO contract = contractTypeService.findById(1L);

        //then
        assertNotNull(contract);
        assertEquals(ContractTypes.SERVICO_CONTINUADO.getCode(), contract.getCode());
        mockMvc.perform(get(ContractTypeController.CONTRACT_TYPE_API + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description",
                        equalTo(ContractTypes.SERVICO_CONTINUADO.getDescription())));
    }

    @Test
    void getContractTypeNotFound() throws Exception {
        //given
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Não encontrado",
                ContractTypeController.CONTRACT_TYPE_API + "/1");
        when(contractTypeService.findById(anyLong())).thenThrow(new EntityNotFoundException());

        //when
        assertThrows(EntityNotFoundException.class, () -> {
            ContractTypeDTO contract = contractTypeService.findById(1L);
        });

        //then
        mockMvc.perform(get(ContractTypeController.CONTRACT_TYPE_API + "/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(new ObjectMapper().readValue(result.getResponse()
                                        .getContentAsString(StandardCharsets.UTF_8),
                                ApiErrors.class).getErrors().get(0).getMessage(),
                        apiError.getMessage()));
    }

    @Test
    void getTypes() throws Exception {
        //given
        List<ContractTypeDTO> dtoList = Arrays.asList(contractTypeDTO, new ContractTypeDTO(2L,
                ContractTypes.AQUISICAO_BENS.getCode(),
                ContractTypes.AQUISICAO_BENS.getDescription(),
                ContractTypes.AQUISICAO_BENS.getValidity()));

        when(contractTypeService.findAll()).thenReturn(dtoList);
        //when
        List<ContractTypeDTO> contractTypeDTOS = contractTypeService.findAll();

        //then
        assertNotNull(contractTypeDTOS);
        mockMvc.perform(get(ContractTypeController.CONTRACT_TYPE_API)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contract_types", hasSize(2)));
    }
}