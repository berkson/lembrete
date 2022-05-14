package gov.ce.fortaleza.lembrete.services.security;

import gov.ce.fortaleza.lembrete.services.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 21:07
 */
@Slf4j
@Service("userDetailsService")
public class LembreteUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final MessageSource messageSource;

    public LembreteUserDetailsService(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //log.info("Buscando usuário: " + username);
        try {
            return userService.findByUsername(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException(messageSource
                    .getMessage("error.missing.user", null, Locale.getDefault()));
        }

    }
}
