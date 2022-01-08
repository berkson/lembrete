package gov.ce.fortaleza.lembrete.services.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.domain.Interested;
import gov.ce.fortaleza.lembrete.enums.ContractTypes;
import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.enums.TimeCode;
import gov.ce.fortaleza.lembrete.models.ContractMessage;
import gov.ce.fortaleza.lembrete.models.Email;
import gov.ce.fortaleza.lembrete.models.Message;
import gov.ce.fortaleza.lembrete.models.ScheduleDataModel;
import gov.ce.fortaleza.lembrete.quartz.jobs.SendEmailJob;
import gov.ce.fortaleza.lembrete.services.common.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CaseUtils;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 21:21
 */
@Slf4j
@Service
public class DeadlineNotifyServiceImpl implements NotifyService {

    public static final String DEADLINE_GROUP = "Contract Notification";
    private final ScheduleService scheduleService;
    private final Scheduler scheduler;

    public DeadlineNotifyServiceImpl(ScheduleService scheduleService, Scheduler scheduler) {
        this.scheduleService = scheduleService;
        this.scheduler = scheduler;
    }

    @Override
    public void verifyAndSchedule(Object o) {
        Contract contract = (Contract) o;

        if (contract.getContractType().getCode()
                .equals(ContractTypes.SERVICO_CONTINUADO.getCode()))
            removeAlertBasedOnAdditivePossibility(contract);

        setCrons(contract);
        scheduleJob(contract);
    }

    /**
     * Remove o alerta conforme o tempo que o contrato está ativo
     *
     * @param contract contrato
     */
    public void removeAlertBasedOnAdditivePossibility(Contract contract) {
        long monthsPassed = ChronoUnit.MONTHS
                .between(contract.getInitialDate(), contract.getFinalDate());
        // Contrato que não atingiu o limite de 60 meses
        if (monthsPassed < ContractTypes.SERVICO_CONTINUADO.getValidity()) {
            contract.getContractType()
                    .getAlerts()
                    .removeIf(alert -> alert.getTime() == 6
                            && alert.getTimeCode().equals(TimeCode.M));
        } else {
            contract.getContractType()
                    .getAlerts()
                    .removeIf(alert -> alert.getTime() == 2
                            && alert.getTimeCode().equals(TimeCode.M));
        }
    }

    /**
     * Método que chama o scheduler e agenda o trabalho para cada alerta..
     *
     * @param contract contrato
     */
    private void scheduleJob(Contract contract) {
        List<ScheduleDataModel> models = contract.getContractType()
                .getAlerts().stream().map(alert -> setScheduleModel(contract, alert))
                .collect(Collectors.toList());
        models.forEach(model -> {
            try {
                scheduler
                        .scheduleJob(scheduleService.createJob(model),
                                scheduleService.createCronTrigger(model));
                log.info("Job " + model.getJobName() + " agendado");
            } catch (SchedulerException e) {
                log.error("Erro ao criar job: " + model.getJobName() + " " + e.getMessage());
            }
        });
    }

    /**
     * Método que cria as crons de agendamento e as atribui ao
     * respectivo alerta
     *
     * @param contract contrato
     */
    private void setCrons(Contract contract) {
        LocalDate date = null;
        for (Alert alert : contract.getContractType().getAlerts()) {
            if (alert.getTimeCode().equals(TimeCode.D)) {
                date = contract.getFinalDate().minusDays(alert.getTime());
            } else if (alert.getTimeCode().equals(TimeCode.M)) {
                date = contract.getFinalDate().minusMonths(alert.getTime());
            }

            String cron = String.format("%d %d %d %d %s ? %s", 0, 0, 0,
                    date.getDayOfMonth(),
                    date.getMonth().getDisplayName(TextStyle.SHORT, Locale.US),
                    date.getYear());

            alert.setCron(cron);
        }
    }

    /**
     * Método que gera os modelos para criação do JobDetail e do Trigger
     * de cada alerta.
     *
     * @param contract contrato
     * @param alert    o alerta para o qual se deseja o modelo
     * @return ScheduleDataModel modelo de agendamento
     */
    private ScheduleDataModel setScheduleModel(Contract contract, Alert alert) {
        String name = contract.getContractNumber() + "_" + alert.getTime() + alert.getTimeCode();
        ScheduleDataModel model = new ScheduleDataModel();
        model.setDurable(false);
        model.setJobName(name);
        model.setJobGroup(DEADLINE_GROUP);
        model.setJobClass(SendEmailJob.class);
        model.setJobDescription(contract.getContractType().getDescription());
        model.setJobDataMap(createDataMap(contract));
        model.setTriggerName(name);
        model.setTriggerGroup(DEADLINE_GROUP);
        model.setTriggerDescription(contract.getContractType().getDescription());
        model.setStartTriggerDate(LocalDate.now());
        model.setCronExpression(alert.getCron());
        return model;
    }

    /**
     * Cria o email a ser enviado e o inclui no mapa
     * do agendamento.
     *
     * @param contract contrato
     * @return JobDataMap mapa com os dados do agendamento
     */
    private JobDataMap createDataMap(Contract contract) {
        Email email = new Email();

        Map<String, String> data = new HashMap<>();
        data.put("contractNumber", contract.getContractNumber());
        data.put("finalDate", contract.getFinalDate().toString());
        Message message = new ContractMessage(data);

        List<String> to = contract.getInterestedList().stream()
                .map(Interested::getEmail).collect(Collectors.toList());
        email.setTo(to.toArray(String[]::new));
        email.setSubject(CaseUtils.toCamelCase(contract
                .getContractType().getDescription(), true));
        email.setMessage(message);
        email.setPriority(EmailPriority.NORMAL);

        JobDataMap jobDataMap = new JobDataMap();
        try {
            jobDataMap.put("email", new ObjectMapper().writeValueAsString(email));
            log.info("Json de Email criada com sucesso: " + jobDataMap.get("email"));
        } catch (JsonProcessingException e) {
            log.error("Erro na conversão do objeto Email: " + e.getMessage());
        }

        return jobDataMap;
    }
}
