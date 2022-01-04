package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.CompanyDTO;
import gov.ce.fortaleza.lembrete.domain.Company;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 20:30
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompanyMapper {
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDTOToCompany(CompanyDTO companyDTO);
}
