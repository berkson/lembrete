package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.ContractTypeDTO;
import gov.ce.fortaleza.lembrete.domain.ContractType;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 28/12/2021
 * Time: 22:47
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContractTypeMapper {
    ContractTypeMapper INSTANCE = Mappers.getMapper(ContractTypeMapper.class);

    ContractTypeDTO contractTypeToContractTypeDTO(ContractType contractType);

    ContractType contractTypeDTOToContractType(ContractTypeDTO contractTypeDTO);
}
