package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.models.ScheduleDataModel;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.stereotype.Service;

import java.sql.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author Berkson Soares
 * @since 31/12/2021
 */
@Service
@Slf4j
public class ScheduleService {

    /**
     * Cria um detalhe de trabalho
     *
     * @return detalhes do trabalho serem executados/gravados para posterior
     * execução.
     */
    public JobDetail createJob(ScheduleDataModel data) {
        log.info("JobDetail: " + data.getJobName() + " Group: " + data.getJobGroup());

        return newJob().ofType(data.getJobClass()).storeDurably(data.isDurable())
                .withIdentity(data.getJobName(), data.getJobGroup())
                .withDescription(data.getJobDescription())
                .usingJobData(data.getJobDataMap())
                .build();
    }

    /**
     * Cria um detalhe de trabalho com nome baseado em enum e sem mapeamento de
     * dados.
     * @return detalhes do trabalho serem executados/gravados para posterior
     * execução.
     */
    public JobDetail createJobWithoutDataMap(ScheduleDataModel data) {
        log.info("JobDetail: " + data.getJobName() + " Group: " + data.getJobGroup());

        return newJob().ofType(data.getJobClass()).storeDurably(data.isDurable())
                .withIdentity(data.getJobName(), data.getJobGroup())
                .withDescription(data.getJobDescription())
                .build();
    }


    /**
     * Cria um CronTrigger que é um "trigger" mais avançado e com vários agendamentos
     * possíveis através de uma cronExpression
     * @return um gatilho cron.
     */
    public CronTrigger createCronTrigger(ScheduleDataModel data) {

        return newTrigger().withIdentity(data.getTriggerName(), data.getTriggerGroup())
                .withDescription(data.getTriggerDescription())
                .startAt(Date.valueOf(data.getStartTriggerDate()))
                .withSchedule(CronScheduleBuilder.cronSchedule(data.getCronExpression()))
                .build();
    }


}
