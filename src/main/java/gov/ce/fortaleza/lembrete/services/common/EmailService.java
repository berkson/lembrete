package gov.ce.fortaleza.lembrete.services.common;


import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.models.Email;

/**
 * @author Berkson Ximenes
 * @since 21/02/2020
 */
public interface EmailService {

    /**
     * @param para     para quem é o email
     * @param assunto  assunto do emaiç
     * @param mensagem descrição do email
     * @throws SendMailException Erro ao enviar email
     */
    void enviarMsgSimples(String para, String assunto, String mensagem)
            throws SendMailException;

    /**
     * @param para       para quem é o email
     * @param assunto    assunto do email
     * @param mensagem   descrição do email
     * @param prioridade prioridade do email
     */
    void enviarMsgSimples(String para, String assunto, String mensagem, EmailPriority prioridade)
            throws SendMailException;

    /**
     * @param para     para quem é o email
     * @param assunto  assunto do emaiç
     * @param mensagem descrição do email
     * @throws SendMailException
     */
    void enviarMsgSimples(String[] para, String assunto, String mensagem) throws SendMailException;

    /**
     * @param para       para quem é o email
     * @param assunto    assunto do emaiç
     * @param mensagem   descrição do email
     * @param prioridade prioridade do email
     * @throws SendMailException
     */
    void enviarMsgSimples(String[] para, String assunto, String mensagem, EmailPriority prioridade)
            throws SendMailException;

    /**
     * @param email email do usuário/pessoa
     * @throws SendMailException
     */
    void enviarMsgSimples(Email email) throws SendMailException;
}
