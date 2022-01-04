package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.domain.ContractType;

import java.util.List;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 21:45
 */
public interface ContractTypeService {
    List<ContractTypeDTO> findAll();

    long count();

    ContractType save(ContractType contractType);

    List<ContractType> saveAll(List<ContractType> contractTypes);

    ContractType findByDescription(String description);
}
