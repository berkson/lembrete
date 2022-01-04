package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 20:56
 */
public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
