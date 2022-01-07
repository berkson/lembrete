package gov.ce.fortaleza.lembrete.bootstrap;

import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.domain.ContractType;
import gov.ce.fortaleza.lembrete.enums.ContractTypes;
import gov.ce.fortaleza.lembrete.enums.TimeCode;
import gov.ce.fortaleza.lembrete.services.common.AlertService;
import gov.ce.fortaleza.lembrete.services.common.ContractTypeService;
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
    private final AlertService alertService;

    public Bootstrap(ContractTypeService contractTypeService, AlertService alertService) {
        this.contractTypeService = contractTypeService;
        this.alertService = alertService;
    }

    @Override
    public void run(String... args) {
        List<Alert> alerts = null;
        if (this.alertService.count() == 0) alerts = this.createAlertTypes();
        if (this.contractTypeService.count() == 0) this.createContractTypes(alerts);
    }

    private void createContractTypes(List<Alert> alerts) {
        List<ContractType> contractTypes = Arrays
                .asList(ContractType.builder()
                                .description(ContractTypes.AQUISICAO_BENS.getType())
                                .maxValidity(ContractTypes.AQUISICAO_BENS.getValidity())
                                .code(ContractTypes.AQUISICAO_BENS.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(0), alerts.get(3)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.SERVICO_CONTINUADO.getType())
                                .maxValidity(ContractTypes.SERVICO_CONTINUADO.getValidity())
                                .code(ContractTypes.SERVICO_CONTINUADO.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(2), alerts.get(3)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.EXCEPCIONAL.getType())
                                .maxValidity(ContractTypes.EXCEPCIONAL.getValidity())
                                .code(ContractTypes.EXCEPCIONAL.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(1), alerts.get(3)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.EMERGENCIAL.getType())
                                .maxValidity(ContractTypes.EMERGENCIAL.getValidity())
                                .code(ContractTypes.EMERGENCIAL.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(1)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.CONVENIO.getType())
                                .maxValidity(ContractTypes.CONVENIO.getValidity())
                                .code(ContractTypes.CONVENIO.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(2)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.COOP.getType())
                                .maxValidity(ContractTypes.COOP.getValidity())
                                .code(ContractTypes.COOP.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(2)
                                ))
                                .build(),
                        ContractType.builder()
                                .description(ContractTypes.CREDENCIA.getType())
                                .maxValidity(ContractTypes.CREDENCIA.getValidity())
                                .code(ContractTypes.CREDENCIA.getCode())
                                .alerts(Arrays.asList(
                                        alerts.get(2)
                                ))
                                .build());
        this.contractTypeService.saveAll(contractTypes);
    }

    private List<Alert> createAlertTypes() {
        List<Alert> alerts = Arrays
                .asList(Alert.builder().time(30).timeCode(TimeCode.D).build(),
                        Alert.builder().time(60).timeCode(TimeCode.D).build(),
                        Alert.builder().time(2).timeCode(TimeCode.M).build(),
                        Alert.builder().time(6).timeCode(TimeCode.M).build()
                );
        return this.alertService.saveAll(alerts);
    }
}
