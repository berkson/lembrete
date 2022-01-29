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
 * @since 14/07/2020
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MessageSource messageSource;
    private final String FROM;
    private static final String ENCODE = "UTF-8";
    private final String ERR_MIME;
    private static final String PR = "X-Priority";

    /**
     * @param mailSender    carteiro do java
     * @param messageSource
     */
    public EmailServiceImpl(JavaMailSender mailSender, MessageSource messageSource) {
        super();
        this.mailSender = mailSender;
        this.messageSource = messageSource;
        this.FROM = messageSource.getMessage("email.from",
                null, LocaleContextHolder.getLocale());
        this.ERR_MIME = messageSource.getMessage("error.email.mime",
                null, LocaleContextHolder.getLocale());
    }

    /**
     * @param para     destinatário do email
     * @param assunto  assunto do email
     * @param mensagem descrição do email
     * @throws SendMailException
     */
    @Override
    public void sendSimpleMessage(String[] para, String assunto,
                                  String mensagem) throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(para);
            helper.setFrom(FROM);
            helper.setSubject(assunto);
            helper.setText(mensagem, true);
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);
    }

    /**
     * @param para    destinatário do email
     * @param assunto assunto do email
     * @param texto   descrição do email
     * @throws SendMailException
     */
    @Override
    public void sendSimpleMessage(String para, String assunto, String texto)
            throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(para);
            helper.setFrom(FROM);
            helper.setSubject(assunto);
            helper.setText(texto, true);
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);
    }

    /**
     * @param para       destinatário do email
     * @param assunto    assunto do email
     * @param texto      descrição do email
     * @param prioridade prioridade do email
     * @throws SendMailException
     */
    @Override
    public void sendSimpleMessage(String para, String assunto, String texto,
                                  EmailPriority prioridade) throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(para);
            helper.setFrom(FROM);
            helper.setSubject(assunto);
            helper.setText(texto, true);
            msg.setHeader(PR, prioridade.getPrioridade());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }

    /**
     * @param para       destinatário do email
     * @param assunto    assunto do email
     * @param texto      descrição do email
     * @param prioridade prioridade do email
     * @throws SendMailException
     */
    @Override
    public void sendSimpleMessage(String[] para, String assunto,
                                  String texto, EmailPriority prioridade) throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(para);
            helper.setFrom(FROM);
            helper.setSubject(assunto);
            helper.setText(texto, true);
            msg.setHeader(PR, prioridade.getPrioridade());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }

    /**
     * @param email email do usuário/pessoa
     * @throws SendMailException
     */
    @Override
    public void sendSimpleMessage(Email email) throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(email.getTo());
            helper.setFrom(FROM);
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessage().generateText(), true);
            msg.setHeader(PR, email.getPriority().toString());
        } catch (MessagingException e) {
            throw new SendMailException(ERR_MIME);
        }

        mailSender.send(msg);

    }


}
