package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.PhoneDTO;
import gov.ce.fortaleza.lembrete.domain.Phone;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:53
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PhoneMapper {
    PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    PhoneDTO phoneToPhoneDTO(Phone phone);

    Phone phoneDTOToPhone(PhoneDTO phoneDTO);
}
