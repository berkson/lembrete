package gov.ce.fortaleza.lembrete.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.models.ContractMessage;
import gov.ce.fortaleza.lembrete.models.Email;
import gov.ce.fortaleza.lembrete.models.Message;
import gov.ce.fortaleza.lembrete.models.ScheduleDataModel;
import gov.ce.fortaleza.lembrete.quartz.jobs.SendEmailJob;
import gov.ce.fortaleza.lembrete.services.common.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 18:26
 */

// TODO: Deletar após conclusão
@Component
@EnableScheduling
@Slf4j
public class TestComponent {

    private final ScheduleService scheduleService;
    private final Scheduler scheduler;

    public TestComponent(ScheduleService scheduleService, Scheduler scheduler) {
        this.scheduleService = scheduleService;
        this.scheduler = scheduler;
    }

    @Scheduled(cron = "0 11 00 8 JAN ?")
    public void scheduleEmail() {
        ScheduleDataModel data = new ScheduleDataModel(
                SendEmailJob.class, false, "TesteJob", "Teste", "primeiro teste",
                "TesteTrigger", "Teste", "trigger de teste", LocalDate.now(),
                "0 12 00 8 JAN ? 2022");
        data.setJobDataMap(createDataMap());
        JobDetail jobDetail = scheduleService.createJob(data);
        Trigger trigger = scheduleService.createCronTrigger(data);
        try {
            this.scheduler.scheduleJob(jobDetail, trigger);
            log.info("Agendado!");
        } catch (SchedulerException e) {
            log.error("Erro ao criar o job: " + e.getMessage());
        }
    }

    private JobDataMap createDataMap() {
        Email email = new Email();

        Map<String, String> data = new HashMap<>();
        data.put("contractNumber", "12345");
        data.put("finalDate", "07/01/2022");
        Message message = new ContractMessage(data);

        List<String> to = List.of("berksonx@yahoo.com.br");
        email.setTo(to.toArray(String[]::new));
        email.setSubject("Teste de envio");
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
