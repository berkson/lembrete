package gov.ce.fortaleza.lembrete.bootstrap;

import gov.ce.fortaleza.lembrete.domain.ContractType;
import gov.ce.fortaleza.lembrete.services.ContractTypeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by berkson
 * Date: 30/11/2021
 * Time: 20:42
 */
@Component
public class Bootstrap implements CommandLineRunner {

    private final ContractTypeService contractTypeService;

    public Bootstrap(ContractTypeService contractTypeService) {
        this.contractTypeService = contractTypeService;
    }

    @Override
    public void run(String... args) {
        if (this.contractTypeService.count() == 0) {
            createContractTypes();
        }
    }

    private void createContractTypes() {
        List<ContractType> contractTypes = Arrays
                .asList(ContractType.builder()
                                .description("Aquisição de bens").maxValidity(12).build(),
                        ContractType.builder()
                                .description("Serviços de prestação continuada")
                                .maxValidity(60).build(),
                        ContractType.builder()
                                .description("Contrato Excepcional")
                                .maxValidity(12).build(),
                        ContractType.builder()
                                .description("Contrato Emergencial")
                                .maxValidity(6).build(),
                        ContractType.builder()
                                .description("Convênio")
                                .maxValidity(60).build(),
                        ContractType.builder()
                                .description("Acordo de Cooperação")
                                .maxValidity(60).build(),
                        ContractType.builder()
                                .description("Termos de credenciamento")
                                .maxValidity(60).build());
        this.contractTypeService.saveAll(contractTypes);
    }
}
