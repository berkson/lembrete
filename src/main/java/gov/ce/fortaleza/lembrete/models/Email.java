package gov.ce.fortaleza.lembrete.models;


import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
public class Email implements Serializable {

    private static final long serialVersionUID = -911575682568098101L;
    private String[] para;
    private String assunto;
    private String mensagem;
    private EmailPriority prioridade;

    public Email() {
    }

    public Email(String[] para, String assunto, String mensagem) {
        super();
        this.para = para;
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public Email(String[] para, String assunto, String mensagem, EmailPriority prioridade) {
        super();
        this.para = para;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.prioridade = prioridade;
    }

    public EmailPriority getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(EmailPriority prioridade) {
        this.prioridade = prioridade;
    }

    public String[] getPara() {
        return para;
    }

    public void setPara(String[] para) {
        this.para = para;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
