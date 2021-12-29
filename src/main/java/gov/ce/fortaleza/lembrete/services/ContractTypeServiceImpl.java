package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.mappers.ContractTypeMapper;
import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.domain.ContractType;
import gov.ce.fortaleza.lembrete.repositories.ContractTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:54
 */
@Service
public class ContractTypeServiceImpl implements ContractTypeService {

    private ContractTypeRepository contractTypeRepository;
    private ContractTypeMapper contractTypeMapper;

    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository,
                                   ContractTypeMapper contractTypeMapper) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractTypeMapper = contractTypeMapper;
    }

    @Override
    public List<ContractTypeDTO> getAllContractTypes() {
        return this.contractTypeRepository
                .findAll().stream()
                .map(type -> contractTypeMapper
                        .contractTypeToContractTypeDTO(type))
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return this.contractTypeRepository.count();
    }

    @Override
    public ContractType save(ContractType contractType) {
        return this.contractTypeRepository.save(contractType);
    }

    @Override
    public List<ContractType> saveAll(List<ContractType> contractTypes) {
        return this.contractTypeRepository.saveAll(contractTypes);
    }


}
