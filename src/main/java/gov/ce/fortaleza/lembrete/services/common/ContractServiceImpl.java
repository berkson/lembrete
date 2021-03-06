package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.ContractMapper;
import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.domain.Contract;
import gov.ce.fortaleza.lembrete.domain.Interested;
import gov.ce.fortaleza.lembrete.repositories.CompanyRepository;
import gov.ce.fortaleza.lembrete.repositories.ContractRepository;
import gov.ce.fortaleza.lembrete.repositories.ContractTypeRepository;
import gov.ce.fortaleza.lembrete.repositories.InterestedRepository;
import gov.ce.fortaleza.lembrete.services.business.NotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CompanyRepository companyRepository;
    private final InterestedRepository interestedRepository;
    private final NotifyService notifyService;
    private final ContractTypeRepository contractTypeRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               ContractMapper contractMapper,
                               CompanyRepository companyRepository,
                               InterestedRepository interestedRepository,
                               @Qualifier("deadlineNotifyServiceImpl") NotifyService notifyService,
                               ContractTypeRepository contractTypeRepository) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
        this.companyRepository = companyRepository;
        this.interestedRepository = interestedRepository;
        this.notifyService = notifyService;
        this.contractTypeRepository = contractTypeRepository;
    }


    @Override
    @Transactional
    public ContractDTO save(ContractDTO entity) {

        Contract contract = contractMapper.contractDTOToContract(entity);

        companyRepository.findByCnpj(contract.getCompany().getCnpj())
                .ifPresentOrElse(contract::setCompany,
                        () -> contract.setCompany(companyRepository.save(contract.getCompany())));

        contract.getInterestedList().forEach(interested -> interestedRepository
                .findByCpf(interested.getCpf())
                .ifPresentOrElse(encontrado -> interested.setId(encontrado.getId()),
                        () -> {
                            interested.getPhones().forEach(phone -> phone.setInterested(interested));
                            Interested savedInterested = interestedRepository.save(interested);
                            interested.setId(savedInterested.getId());
                        }));

        if (contract.getContractType().getCode() == null) {
            contract.setContractType(contractTypeRepository
                    .getById(contract.getContractType().getId()));
        }

        return this.saveOrUpdateAndSchedule(contract);
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

    @Override
    @Transactional
    public Contract getById(long id) {
        return contractRepository.getById(id);
    }

    @Override
    public boolean contractExists(String number) {
        return contractRepository.existsContractByContractNumber(number);
    }

    @Override
    @Transactional
    public ContractDTO add(AdditiveDTO additiveDTO) {

        Contract contract = contractRepository.getById(additiveDTO.getContractId());

        contract.setFinalDate(contract
                .getFinalDate()
                .plusMonths(additiveDTO.getDeadline()));

        return this.saveOrUpdateAndSchedule(contract);
    }

    @Override
    @Transactional
    public Page<ContractDTO> findAll(Pageable pageable) {
        Page<Contract> contracts = contractRepository.findAll(pageable);
        return contracts.map(contractMapper::contractToContractDTO);
    }

    @Override
    @Transactional
    public Page<ContractDTO> findAllByContractNumber(PageRequest pageRequest, String dir) {
        Page<Contract> contracts = contractRepository.findAllByContractNumber(pageRequest, dir);
        return contracts.map(contractMapper::contractToContractDTO);
    }

    private ContractDTO saveOrUpdateAndSchedule(Contract contract) {
        Contract savedContract = contractRepository.save(contract);


        notifyService.verifyAndSchedule(savedContract);

        return contractMapper.contractToContractDTO(savedContract);
    }

}
