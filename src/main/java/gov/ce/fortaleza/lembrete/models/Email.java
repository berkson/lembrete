package gov.ce.fortaleza.lembrete.models;


import gov.ce.fortaleza.lembrete.enums.EmailPriority;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
@Getter
@Setter
@NoArgsConstructor
public class Email implements Serializable {

    private static final long serialVersionUID = -911575682568098101L;
    private String[] to;
    private String subject;
    private Message message;
    private EmailPriority priority;

    public Email(String[] to, String subject, Message message) {
        super();
        this.to = to;
        this.subject = subject;
        this.message = message;
    }

    public Email(String[] to, String subject, Message message, EmailPriority priority) {
        super();
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.priority = priority;
    }
}
