package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.ContractTypeMapper;
import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.domain.ContractType;
import gov.ce.fortaleza.lembrete.repositories.ContractTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:54
 */
@Service
public class ContractTypeServiceImpl implements ContractTypeService {

    private final ContractTypeRepository contractTypeRepository;
    private final ContractTypeMapper contractTypeMapper;

    public ContractTypeServiceImpl(ContractTypeRepository contractTypeRepository,
                                   ContractTypeMapper contractTypeMapper) {
        this.contractTypeRepository = contractTypeRepository;
        this.contractTypeMapper = contractTypeMapper;
    }

    @Override
    public List<ContractTypeDTO> findAll() {
        return this.contractTypeRepository
                .findAll().stream()
                .sorted(Comparator.naturalOrder())
                .map(contractTypeMapper::contractTypeToContractTypeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return this.contractTypeRepository.count();
    }

    @Override
    public ContractTypeDTO save(ContractTypeDTO contractTypeDTO) {
        ContractType contractType = contractTypeRepository
                .save(contractTypeMapper.contractTypeDTOToContractType(contractTypeDTO));

        return contractTypeMapper.contractTypeToContractTypeDTO(contractType);
    }

    @Override
    public List<ContractType> saveAll(List<ContractType> contractTypes) {
        return this.contractTypeRepository.saveAll(contractTypes);
    }

    @Override
    public ContractTypeDTO findByDescription(String description) {
        return contractTypeMapper
                .contractTypeToContractTypeDTO(contractTypeRepository
                        .findContractTypeByDescription(description));
    }

    @Override
    @Transactional
    public ContractTypeDTO findById(long id) {
        return contractTypeMapper.contractTypeToContractTypeDTO(
                contractTypeRepository.getById(id)
        );
    }

    @Override
    @Transactional
    public ContractType getById(long id) {
        return contractTypeRepository.getById(id);
    }


}
