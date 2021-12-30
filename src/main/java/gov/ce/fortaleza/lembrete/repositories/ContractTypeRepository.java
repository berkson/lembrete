package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.ContractType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 21:40
 */
public interface ContractTypeRepository extends JpaRepository<ContractType, Long> {
    ContractType findContractTypeByDescription(String description);
}
