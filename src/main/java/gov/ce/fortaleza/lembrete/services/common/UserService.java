package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 23:11
 */
public interface UserService extends CrudService<UserDTO, Long> {


    UserDTO findByCpf(String cpf);

    User findByUsername(String cpf);

    long count();

    boolean exists(String cpf);
}
