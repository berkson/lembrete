package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.InterestedDTO;
import gov.ce.fortaleza.lembrete.domain.Interested;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:34
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InterestedMapper {
    InterestedMapper INSTANCE = Mappers.getMapper(InterestedMapper.class);

    InterestedDTO interestedToInterestedDTO(Interested interested);

    Interested interestedDTOToInterested(InterestedDTO interestedDTO);

}
