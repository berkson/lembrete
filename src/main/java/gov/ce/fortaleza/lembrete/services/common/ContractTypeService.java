package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.domain.ContractType;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 21:45
 */
public interface ContractTypeService {
    List<ContractTypeDTO> findAll();

    long count();

    ContractTypeDTO save(ContractTypeDTO contractTypeDTO);

    List<ContractType> saveAll(List<ContractType> contractTypes);

    ContractTypeDTO findByDescription(String description);

    @Transactional
    ContractTypeDTO findById(long id);

    @Transactional
    ContractType getById(long id);
}
