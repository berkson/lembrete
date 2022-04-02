package gov.ce.fortaleza.lembrete.services.initialize;

import gov.ce.fortaleza.lembrete.utils.MessagesUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by berkson
 * Date: 02/04/2022
 * Time: 11:54
 */
@Component
public class InitStaticMessageSource {
    private final MessageSource messageSource;


    public InitStaticMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        MessagesUtil.setMessageSource(this.messageSource);
    }
}
