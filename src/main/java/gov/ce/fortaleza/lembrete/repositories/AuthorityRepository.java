package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 22:52
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    @Query(value = "SELECT a.authority_cod, a.description FROM authorities a\n" +
            "JOIN permissions p ON p.authority_cod = a.authority_cod \n" +
            "JOIN users u ON u.user_id = p.user_id \n" +
            "WHERE u.user_id = ?1", nativeQuery = true)
    Iterable<Authority> findAuthoritiesByUserId(long id);
}
