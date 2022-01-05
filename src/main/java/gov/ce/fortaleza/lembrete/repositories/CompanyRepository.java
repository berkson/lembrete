package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 23:15
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCnpj(String cnpj);
}
