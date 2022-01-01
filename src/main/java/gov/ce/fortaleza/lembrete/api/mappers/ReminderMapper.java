package gov.ce.fortaleza.lembrete.api.mappers;

import gov.ce.fortaleza.lembrete.api.models.ReminderDTO;
import gov.ce.fortaleza.lembrete.domain.Reminder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 21:08
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ReminderMapper {

    ReminderMapper INSTANCE = Mappers.getMapper(ReminderMapper.class);

    ReminderDTO reminderToReminderDTO(Reminder reminder);

    Reminder reminderDTOToReminder(ReminderDTO reminderDTO);
}
