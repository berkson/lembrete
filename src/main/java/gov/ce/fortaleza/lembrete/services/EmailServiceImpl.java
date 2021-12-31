package gov.ce.fortaleza.lembrete.services;


import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.models.Email;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String FROM = "Sistema de Controle de Ci's <appweb.amc@fortaleza.ce.gov.br>";
    private static final String ENCODE = "UTF-8";
    private static final String ERRMIME = "Erro ao criar Mensagem MIME";
    private static final String PR = "X-Priority";

    /**
     *
     * @param mailSender carteiro do java
     */
    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        super();
        this.mailSender = mailSender;
    }

    /**
     *
     * @param para destinatário do email
     * @param assunto assunto do email
     * @param mensagem descrição do email
     * @throws SendMailException
     */
    @Override
    public void enviarMsgSimples(String[] para, String assunto,
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
            throw new SendMailException(ERRMIME);
        }

        mailSender.send(msg);
    }

    /**
     *
     * @param para destinatário do email
     * @param assunto assunto do email
     * @param texto descrição do email
     * @throws SendMailException
     */
    @Override
    public void enviarMsgSimples(String para, String assunto, String texto)
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
            throw new SendMailException(ERRMIME);
        }

        mailSender.send(msg);
    }

    /**
     *
     * @param para destinatário do email
     * @param assunto assunto do email
     * @param texto descrição do email
     * @param prioridade prioridade do email
     * @throws SendMailException
     */
    @Override
    public void enviarMsgSimples(String para, String assunto, String texto,
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
            throw new SendMailException(ERRMIME);
        }

        mailSender.send(msg);

    }

    /**
     *
     * @param para destinatário do email
     * @param assunto assunto do email
     * @param texto descrição do email
     * @param prioridade prioridade do email
     * @throws SendMailException
     */
    @Override
    public void enviarMsgSimples(String[] para, String assunto,
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
            throw new SendMailException(ERRMIME);
        }

        mailSender.send(msg);

    }

    /**
     *
     * @param email email do usuário/pessoa
     * @throws SendMailException
     */
    @Override
    public void enviarMsgSimples(Email email) throws SendMailException {
        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(msg, true, ENCODE);
            helper.setTo(email.getPara());
            helper.setFrom(FROM);
            helper.setSubject(email.getAssunto());
            helper.setText(email.getMensagem(), true);
            msg.setHeader(PR, email.getPrioridade().toString());
        } catch (MessagingException e) {
            throw new SendMailException(ERRMIME);
        }

        mailSender.send(msg);

    }



}
