package gov.ce.fortaleza.lembrete.services.security;

import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.repositories.UserRepository;
import gov.ce.fortaleza.lembrete.services.common.AuthorityService;
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

    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final AuthorityService authorityService;

    public LembreteUserDetailsService(UserRepository userRepository, MessageSource messageSource, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.messageSource = messageSource;
        this.authorityService = authorityService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Buscando usuário: " + username);
        try {
            User user = userRepository.findByCpf(username);
            user.setAuthorities(authorityService.findAllByUserId(user.getId()));
            return user;
        } catch (Exception e) {
            throw new UsernameNotFoundException(messageSource
                    .getMessage("error.missing.user", null, Locale.getDefault()));
        }

    }
}
