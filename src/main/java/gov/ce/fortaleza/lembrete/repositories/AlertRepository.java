package gov.ce.fortaleza.lembrete.repositories;

import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.enums.TimeCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 20:47
 */
public interface AlertRepository extends JpaRepository<Alert, Long> {
    Alert findAlertByTimeAndTimeCode(Integer time, TimeCode timeCode);
}
