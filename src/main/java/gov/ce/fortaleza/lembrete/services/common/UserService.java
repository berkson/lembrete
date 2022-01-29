package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;

import java.util.Optional;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 23:11
 */
public interface UserService extends CrudService<UserDTO, Long> {


    User save(User user);

    UserDTO findByCpf(String cpf);

    User findByUsername(String cpf);

    Optional<User> findByEmail(String email);

    long count();

    boolean exists(String cpf);
}
