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

    private ScheduleService scheduleService;
    private Scheduler scheduler;

    public TestComponent(ScheduleService scheduleService, Scheduler scheduler) {
        this.scheduleService = scheduleService;
        this.scheduler = scheduler;
    }

    @Scheduled(cron = "0 07 20 31 DEC ?")
    public void scheduleEmail() {
        ScheduleDataModel data = new ScheduleDataModel(
                SendEmailJob.class, false, "TesteJob", "Teste", "primeiro teste",
                "TesteTrigger", "Teste", "trigger de teste", LocalDate.now(),
                "0 08 20 31 DEC ? 2021");
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
