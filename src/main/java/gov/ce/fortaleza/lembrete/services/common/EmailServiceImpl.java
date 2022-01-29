package gov.ce.fortaleza.lembrete.services.common;


import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.models.Email;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Berkson Ximenes
 * @since 29/01/2022
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final String FROM;
    private static final String ENCODE = "UTF-8";
    private final String ERR_MIME;
    private static final String PR = "X-Priority";

    /**
     * @param mailSender    carteiro do java
     * @param messageSource mensagens i18n
     */
    public EmailServiceImpl(JavaMailSender mailSender, MessageSource messageSource) {
        super();
        this.mailSender = mailSender;
        this.FROM = messageSource.getMessage("email.from",
                null, LocaleContextHolder.getLocale());
        this.ERR_MIME = messageSource.getMessage("error.email.mime",
                null, LocaleContextHolder.getLocale());
    }


    private MimeMessage setArguments(String[] to, String subject, String body)
            throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            if (to.length == 1)
                helper.setTo(to[0]);
            else
                helper.setTo(to);
            helper.setFrom(FROM);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        return msg;
    }

    private String[] generateArray(String to) {
        String[] array = new String[1];
        array[0] = to;
        return array;
    }

    /**
     * @param para     destinatário do endereço eletrônico
     * @param assunto  assunto do endereço eletrônico
     * @param mensagem descrição do endereço eletrônico
     * @throws SendMailException Exceção ao enviar endereço eletrônico
     */
    @Override
    public void sendSimpleMessage(String[] para, String assunto,
                                  String mensagem) throws SendMailException {

        mailSender.send(setArguments(para, assunto, mensagem));
    }

    /**
     * @param para    destinatário do endereço eletrônico
     * @param assunto assunto do endereço eletrônico
     * @param texto   descrição do endereço eletrônico
     * @throws SendMailException Exceção ao enviar endereço eletrônico
     */
    @Override
    public void sendSimpleMessage(String para, String assunto, String texto)
            throws SendMailException {

        mailSender.send(setArguments(generateArray(para), assunto, texto));
    }

    /**
     * @param para       destinatário do endereço eletrônico
     * @param assunto    assunto do endereço eletrônico
     * @param texto      descrição do endereço eletrônico
     * @param prioridade prioridade do endereço eletrônico
     * @throws SendMailException exceção ao enviar endereço eletrônico
     */
    @Override
    public void sendSimpleMessage(String para, String assunto, String texto,
                                  EmailPriority prioridade) throws SendMailException {
        MimeMessage msg = setArguments(generateArray(para), assunto, texto);
        try {
            msg.setHeader(PR, prioridade.getPrioridade());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }

    /**
     * @param para       destinatário do endereço eletrônico
     * @param assunto    assunto do endereço eletrônico
     * @param texto      descrição do endereço eletrônico
     * @param prioridade prioridade do endereço eletrônico
     * @throws SendMailException exceção ao enviar endereço eletrônico
     */
    @Override
    public void sendSimpleMessage(String[] para, String assunto,
                                  String texto, EmailPriority prioridade) throws SendMailException {
        MimeMessage msg = setArguments(para, assunto, texto);
        try {
            msg.setHeader(PR, prioridade.getPrioridade());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }

    /**
     * @param email email do usuário/pessoa
     * @throws SendMailException Exceção ao enviar endereço eletrônico
     */
    @Override
    public void sendSimpleMessage(Email email) throws SendMailException {
        MimeMessage msg = setArguments(email.getTo(), email.getSubject(),
                email.getMessage().generateText());
        try {
            msg.setHeader(PR, email.getPriority().toString());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }


}
