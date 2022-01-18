package gov.ce.fortaleza.lembrete.bootstrap;

import gov.ce.fortaleza.lembrete.domain.Authority;
import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.services.common.AuthorityService;
import gov.ce.fortaleza.lembrete.services.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 22:50
 */
@Slf4j
@Component
public class BootStrapUsers implements CommandLineRunner {

    private final AuthorityService authorityService;
    private final UserService userService;

    public BootStrapUsers(AuthorityService authorityService, UserService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        if (authorityService.count() == 0)
            createBasicRoles();
        if (!userService.exists("87621193387"))
            createAdmin();
    }

    private void createBasicRoles() {
        log.info("Salvando autorizações!");
        Authority admin = new Authority("ROLE_ADMIN", "administrador do sistema");
        Authority support = new Authority("ROLE_SUPORTE", "acesso suporte");
        authorityService.saveAll(Arrays.asList(admin, support));
    }

    private void createAdmin() {
        log.info("Salvando usuário administrador");
        User admin = new User();
        admin.setCpf("87621193387");
        admin.setPassword("$2a$10$erOsX2UaNCRuo5YunF5N5uHmGPhJoDVPqUzIEWM71UiGxIPJfRlZC");
        admin.setEmail("berksonx@yahoo.com.br");
        admin.setName("berkson ximenes soares");
        admin.setAuthorities(List.of(authorityService.findById("ROLE_ADMIN").orElseThrow()));
        admin.setEnabled(true);
        userService.save(admin);
    }
}
