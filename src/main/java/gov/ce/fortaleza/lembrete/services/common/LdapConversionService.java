package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.AuthorityDTO;
import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Berkson Ximenes
 * Date: 14/05/2022
 */
@Service
@Slf4j
public class LdapConversionService {
    private AuthorityService authorityService;

    public LdapConversionService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public UserDTO getUserDetails(Authentication authentication) {

        log.info("Logando via ActiveDirectory, Domínio: {}", ((LdapUserDetailsImpl) authentication.getPrincipal()).getDn());

        UserDTO userDTO = this.mountNamesAndEmail(authentication);
        userDTO.setAuthorities(this.setAuthorities(authentication));

        return userDTO;
    }

    private UserDTO mountNamesAndEmail(Authentication authentication) {
        final String dn = ((LdapUserDetailsImpl) authentication.getPrincipal()).getDn();
        String[] breakDC = dn.split("DC=");
        String username = ((LdapUserDetailsImpl) authentication.getPrincipal()).getUsername();
        String name = breakDC[0].substring(3, breakDC[0].indexOf(","));
        StringBuilder emailBuilder = new StringBuilder(username + "@");
        for (int i = 1; i < breakDC.length; i++) {
            if (i == breakDC.length - 1) {
                emailBuilder.append(breakDC[i].toLowerCase().replace(",", ""));
                break;
            }
            emailBuilder.append(breakDC[i].toLowerCase().replace(",", "."));
        }
        UserDTO userDTO = UserDTO.builder().name(((LdapUserDetailsImpl) authentication.getPrincipal()).getDn())
                .username(username)
                .name(name)
                .email(emailBuilder.toString())
                .build();
        return userDTO;
    }

    private List<AuthorityDTO> setAuthorities(Authentication authentication) {
        List<AuthorityDTO> auth = new ArrayList<>();
        List<AuthorityDTO> dbAuth = authorityService.findAll();
        dbAuth.forEach(role -> authentication.getAuthorities().forEach(granted -> {
            if(role.getAuthority().substring(5).equalsIgnoreCase(granted.getAuthority())){
                auth.add(role);
            }
        }));
        return auth;
    }
}
