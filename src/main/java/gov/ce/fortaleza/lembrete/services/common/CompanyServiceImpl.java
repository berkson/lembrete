package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.mappers.CompanyMapper;
import gov.ce.fortaleza.lembrete.api.models.CompanyDTO;
import gov.ce.fortaleza.lembrete.domain.Company;
import gov.ce.fortaleza.lembrete.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 23:17
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }


    @Override
    @Transactional
    public CompanyDTO save(CompanyDTO entity) {
        Company company = companyRepository
                .save(companyMapper.companyDTOToCompany(entity));
        return companyMapper.companyToCompanyDTO(company);
    }

    @Override
    public Optional<CompanyDTO> findById(Long id) {
        return Optional.ofNullable(
                companyMapper.companyToCompanyDTO(
                        companyRepository.getById(id)
                )
        );
    }

    @Override
    public List<CompanyDTO> findAll() {
        return companyRepository.findAll().stream()
                .map(companyMapper::companyToCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CompanyDTO> findByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj)
                .map(companyMapper::companyToCompanyDTO);

    }
}
