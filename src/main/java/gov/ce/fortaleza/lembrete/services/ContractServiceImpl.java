package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.mappers.ContractMapper;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.repositories.ContractRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:22
 */
@Slf4j
@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractRepository contractRepository,
                               ContractMapper contractMapper, CompanyService companyService) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }


    @Override
    @Transactional
    public ContractDTO save(ContractDTO entity) {

        Contract contract = contractMapper.contractDTOToContract(entity);
        contractRepository.save(contract);
        return contractMapper.contractToContractDTO(contract);
    }

    @Override
    @Transactional
    public Optional<ContractDTO> findById(Long id) {
        return Optional.ofNullable(
                contractMapper
                        .contractToContractDTO(contractRepository.getById(id))
        );
    }

    @Override
    public List<ContractDTO> findAll() {
        return contractRepository.findAll().stream()
                .map(contractMapper::contractToContractDTO)
                .collect(Collectors.toList());
    }
}
