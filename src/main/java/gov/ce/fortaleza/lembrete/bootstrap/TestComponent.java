package gov.ce.fortaleza.lembrete.bootstrap;

import gov.ce.fortaleza.lembrete.models.ScheduleDataModel;
import gov.ce.fortaleza.lembrete.quartz.jobs.SendEmailJob;
import gov.ce.fortaleza.lembrete.services.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    @Scheduled(cron = "0 40 14 2 JAN ?")
    public void scheduleEmail() {
        ScheduleDataModel data = new ScheduleDataModel(
                SendEmailJob.class, false, "TesteJob", "Teste", "primeiro teste",
                "TesteTrigger", "Teste", "trigger de teste", LocalDate.now(),
                "0 41 14 2 JAN ? 2022");
        JobDetail jobDetail = scheduleService.createJobWithoutDataMap(data);
        Trigger trigger = scheduleService.createCronTrigger(data);
        try {
            this.scheduler.scheduleJob(jobDetail, trigger);
            log.info("Agendado!");
        } catch (SchedulerException e) {
            log.error("Erro ao criar o job: " + e.getMessage());
        }
    }

}
