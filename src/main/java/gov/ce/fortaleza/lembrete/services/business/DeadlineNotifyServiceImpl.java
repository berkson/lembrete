package gov.ce.fortaleza.lembrete.services.business;

import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.domain.Interested;
import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.enums.TimeCode;
import gov.ce.fortaleza.lembrete.models.ContractMessage;
import gov.ce.fortaleza.lembrete.models.Email;
import gov.ce.fortaleza.lembrete.models.Message;
import gov.ce.fortaleza.lembrete.models.ScheduleDataModel;
import gov.ce.fortaleza.lembrete.quartz.jobs.SendEmailJob;
import gov.ce.fortaleza.lembrete.services.common.ContractService;
import gov.ce.fortaleza.lembrete.services.common.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CaseUtils;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
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
    private final ContractService contractService;

    public DeadlineNotifyServiceImpl(ScheduleService scheduleService, Scheduler scheduler,
                                     ContractService contractService) {
        this.scheduleService = scheduleService;
        this.scheduler = scheduler;
        this.contractService = contractService;
    }

    @Override
    public void verifyAndSchedule(ContractDTO contractDTO) {
        Contract contract = contractService.getById(contractDTO.getId());
        setCrons(contract);
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
                log.error("Erro ao criar job: " + model.getJobName() + e.getMessage());
            }
        });
    }

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
        jobDataMap.put("email", email);

        return jobDataMap;
    }
}
