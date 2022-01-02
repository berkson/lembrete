package gov.ce.fortaleza.lembrete.quartz.jobs;

import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.services.EmailService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
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
        try {
            emailService.enviarMsgSimples("berksonx@yahoo.com.br", "Teste", "TEstando envio");
        } catch (SendMailException e) {
            log.error("Erro de envio de email: " + e.getMessage());
            throw new JobExecutionException(e);
        }
    }

}
