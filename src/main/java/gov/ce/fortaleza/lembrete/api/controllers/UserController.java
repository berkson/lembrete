package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static gov.ce.fortaleza.lembrete.api.controllers.UserController.USER_ROOT;

/**
 * Created by berkson
 * Date: 20/01/2022
 * Time: 08:27
 */
@Slf4j
@RestController
@RequestMapping(value = USER_ROOT)
public class UserController {
    public static final String USER_ROOT = "/api/user";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @IsUser
    public UserDTO getUser(Principal principal) {
        return userService.findByCpf(principal.getName());
    }

    @RequestMapping(value = "/csrf", method = RequestMethod.HEAD)
    @ResponseStatus(HttpStatus.OK)
    public void head() {
    }

    @PostMapping(value = "/logout")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }
}
