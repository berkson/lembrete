package gov.ce.fortaleza.lembrete.quartz.jobs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.models.Email;
import gov.ce.fortaleza.lembrete.services.common.EmailService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by berkson
 * Date: 30/12/2021
 * Time: 23:31
 */
@DisallowConcurrentExecution
@Slf4j
public class SendEmailJob extends QuartzJobBean {

    private EmailService emailService;


    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected void executeInternal(@NonNull JobExecutionContext context)
            throws JobExecutionException {

        log.info("Trabalho: " + context.getJobDetail().getKey().getName() +
                " Iniciado: " + context.getFireTime());

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        try {
            Email email = new ObjectMapper().readValue(jobDataMap.get("email").toString(), Email.class);
            emailService.sendSimpleMessage(email);
        } catch (SendMailException e) {
            log.error("Erro de envio de email: " + e.getMessage());
            throw new JobExecutionException(e);
        } catch (JsonProcessingException e) {
            log.error("Erro ao recuperar Json da classe Email: " + e.getMessage());
            throw new JobExecutionException(e);
        }

        log.info("Próximo Trabalho Programado para: " + context.getNextFireTime());
    }

}
