package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.domain.Alert;
import gov.ce.fortaleza.lembrete.enums.TimeCode;

import java.util.List;

/**
 * Created by berkson
 * Date: 29/12/2021
 * Time: 20:48
 */
public interface AlertService {
    Alert findAlert(int time, TimeCode timeCode);

    List<Alert> saveAll(List<Alert> alerts);

    Alert save(Alert alert);

    long count();
}
