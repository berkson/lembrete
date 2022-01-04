package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.mappers.ReminderMapper;
import gov.ce.fortaleza.lembrete.api.models.ReminderDTO;
import gov.ce.fortaleza.lembrete.domain.Reminder;
import gov.ce.fortaleza.lembrete.repositories.ReminderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 21:08
 */
@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;
    private final ReminderMapper reminderMapper;

    public ReminderServiceImpl(ReminderRepository reminderRepository,
                               ReminderMapper reminderMapper) {
        this.reminderRepository = reminderRepository;
        this.reminderMapper = reminderMapper;
    }

    @Override
    public ReminderDTO save(ReminderDTO entity) {
        Reminder reminder = reminderRepository
                .save(reminderMapper.reminderDTOToReminder(entity));

        return reminderMapper.reminderToReminderDTO(reminder);
    }

    @Override
    public Optional<ReminderDTO> findById(Long id) {
        return Optional.ofNullable(reminderMapper.reminderToReminderDTO(
                reminderRepository.getById(id)
        ));
    }

    @Override
    public List<ReminderDTO> findAll() {
        return reminderRepository.findAll().stream()
                .map(reminderMapper::reminderToReminderDTO)
                .collect(Collectors.toList());
    }
}
