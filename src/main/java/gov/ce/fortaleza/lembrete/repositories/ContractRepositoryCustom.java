package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by berkson
 * Date: 18/04/2022
 * Time: 23:49
 */
public interface ContractRepositoryCustom {
    Page<Contract> findAllByContractNumber(PageRequest pageRequest, String dir);
}
