package gov.ce.fortaleza.lembrete.api.controllers;

import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.security.annotations.IsUser;
import gov.ce.fortaleza.lembrete.services.common.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
