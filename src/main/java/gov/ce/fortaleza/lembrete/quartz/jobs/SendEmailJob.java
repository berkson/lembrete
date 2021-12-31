package gov.ce.fortaleza.lembrete.quartz.jobs;

import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by berkson
 * Date: 30/12/2021
 * Time: 23:31
 */
@DisallowConcurrentExecution
@Slf4j
public class SendEmailJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            emailService.enviarMsgSimples("berksonx@yahoo.com.br", "Teste", "TEstando envio");
        } catch (SendMailException e) {
            log.error("Erro de envio de email: " + e.getMessage());
            throw new JobExecutionException(e);
        }
    }
}
