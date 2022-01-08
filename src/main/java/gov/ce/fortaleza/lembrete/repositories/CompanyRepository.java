package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 23:15
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCnpj(String cnpj);
}
