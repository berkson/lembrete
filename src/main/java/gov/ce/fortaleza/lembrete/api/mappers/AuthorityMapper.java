package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.AuthorityDTO;
import gov.ce.fortaleza.lembrete.domain.Authority;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 20/01/2022
 * Time: 19:40
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorityMapper {
    AuthorityMapper INSTANCE = Mappers.getMapper(AuthorityMapper.class);

    Authority authorityDTOToAuthority(AuthorityDTO authorityDTO);

    AuthorityDTO authorityToAuthorityDTO(Authority authority);
}
