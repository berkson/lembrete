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
     * @param para     para quem é o endereço eletrônico
     * @param assunto  assunto do endereço eletrônico
     * @param mensagem descrição do endereço eletrônico
     * @throws SendMailException Erro ao enviar email
     */
    void sendSimpleMessage(String para, String assunto, String mensagem)
            throws SendMailException;

    /**
     * @param para       para quem é o endereço eletrônico
     * @param assunto    assunto do endereço eletrônico
     * @param mensagem   descrição do endereço eletrônico
     * @param prioridade prioridade do endereço eletrônico
     */
    void sendSimpleMessage(String para, String assunto, String mensagem, EmailPriority prioridade)
            throws SendMailException;

    /**
     * @param para     para quem é o endereço eletrônico
     * @param assunto  assunto do endereço eletrônico
     * @param mensagem descrição do endereço eletrônico
     * @throws SendMailException Erro ao enviar email
     */
    void sendSimpleMessage(String[] para, String assunto, String mensagem) throws SendMailException;

    /**
     * @param para       para quem é o endereço eletrônico
     * @param assunto    assunto do endereço eletrônico
     * @param mensagem   descrição do endereço eletrônico
     * @param prioridade prioridade do endereço eletrônico
     * @throws SendMailException Erro ao enviar email
     */
    void sendSimpleMessage(String[] para, String assunto, String mensagem, EmailPriority prioridade)
            throws SendMailException;

    /**
     * @param email email do usuário/pessoa
     * @throws SendMailException Erro ao enviar email
     */
    void sendSimpleMessage(Email email) throws SendMailException;
}
