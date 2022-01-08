package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Interested;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by berkson
 * Date: 05/01/2022
 * Time: 23:26
 */
public interface InterestedRepository extends JpaRepository<Interested, Long> {
    Optional<Interested> findByCpf(String cpf);
}
