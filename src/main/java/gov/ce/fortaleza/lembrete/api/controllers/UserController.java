package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.exceptions.SendMailException;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.business.RecoverPasswordService;
import gov.ce.fortaleza.lembrete.services.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.Locale;

import static gov.ce.fortaleza.lembrete.api.controllers.UserController.USER_ROOT;

/**
 * Created by berkson
 * Date: 20/01/2022
 * Time: 08:27
 */
@Slf4j
@RestController
@RequestMapping(value = USER_ROOT)
@Validated
public class UserController {
    public static final String USER_ROOT = "/api/user";

    private final UserService userService;
    private final RecoverPasswordService recoverPasswordService;
    private final MessageSource messageSource;
    private static final Locale locale = LocaleContextHolder.getLocale();

    public UserController(UserService userService, RecoverPasswordService recoverPasswordService,
                          MessageSource messageSource) {
        this.userService = userService;
        this.recoverPasswordService = recoverPasswordService;
        this.messageSource = messageSource;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @IsUser
    public UserDTO getUser(Principal principal) {
        return userService.findByCpf(principal.getName());
    }

    @PostMapping(value = "/passrecover")
    @ResponseStatus(HttpStatus.OK)
    public String recoverPassword(@RequestBody @Email String email) throws SendMailException {
        User user = userService.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        try {
            recoverPasswordService.sendCode(user);
            return messageSource.getMessage("email.send", null,
                    locale);
        } catch (Exception e) {
            throw new SendMailException("email.not.send");
        }
    }
}

//TODO: o serviço de alteração da senha.