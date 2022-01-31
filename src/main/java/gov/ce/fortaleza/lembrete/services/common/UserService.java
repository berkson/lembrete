package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.CodeVerifyDTO;
import gov.ce.fortaleza.lembrete.api.models.UserDTO;
import gov.ce.fortaleza.lembrete.domain.User;
import gov.ce.fortaleza.lembrete.exceptions.InvalidRecoveryCodeException;

import java.util.Map;
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

    /**
     * Verifica se o código de verificação está correto.
     *
     * @param codeVerifyDTO objeto de verificação de código
     * @return Map com a chave isValid e o valor sendo boolean.
     * @throws InvalidRecoveryCodeException caso o código no banco seja nulo
     */
    Map<String, Boolean> verifyCode(CodeVerifyDTO codeVerifyDTO) throws InvalidRecoveryCodeException;

    void changePassword(CodeVerifyDTO codeVerifyDTO);
}
