package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 21:12
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
