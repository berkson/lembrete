package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.enums.TimeCode;
import gov.ce.fortaleza.lembrete.repositories.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 20:56
 */
@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;

    public AlertServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Alert findAlert(int time, TimeCode timeCode) {
        return this.alertRepository.findAlertByTimeAndTimeCode(time, timeCode);
    }

    @Override
    public List<Alert> saveAll(List<Alert> alerts) {
        return this.alertRepository.saveAll(alerts);
    }

    @Override
    public Alert save(Alert alert) {
        return this.alertRepository.save(alert);
    }

    @Override
    public long count() {
        return this.alertRepository.count();
    }
}
