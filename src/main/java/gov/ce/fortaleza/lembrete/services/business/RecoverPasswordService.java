package gov.ce.fortaleza.lembrete.services.business;

import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.models.Message;
import gov.ce.fortaleza.lembrete.models.RecoverPasswordMessage;
import gov.ce.fortaleza.lembrete.services.common.EmailService;
import gov.ce.fortaleza.lembrete.services.common.UserService;
import gov.ce.fortaleza.lembrete.utils.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Created by berkson
 * Date: 28/01/2022
 * Time: 18:40
 */
@Slf4j
@Service
public class RecoverPasswordService {
    private final EmailService es;
    private final UserService userService;
    private final MessageSource messageSource;

    public RecoverPasswordService(EmailService es, UserService userService,
                                  MessageSource messageSource) {
        this.es = es;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    public void sendCode(User user) throws SendMailException {
        String code = CodeGenerator.generate();
        Message message = new RecoverPasswordMessage(Map.of("code", code));
        es.sendSimpleMessage(user.getEmail(),
                messageSource.getMessage("email.title", null, LocaleContextHolder.getLocale()),
                message.generateText());
        user.setRecoveryCode(code);
        userService.save(user);
    }

}
