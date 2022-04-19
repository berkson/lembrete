package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Contract;

import java.util.List;

/**
 * Created by berkson
 * Date: 18/04/2022
 * Time: 23:49
 */
public interface ContractRepositoryCustom {
    List<Contract> findAllByContractNumber(int offset, int quantityPerPage, String dir);
}
