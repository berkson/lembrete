package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:20
 */
public interface ContractRepository extends JpaRepository<Contract, Long> {
    boolean existsContractByContractNumber(String number);
}
