package gov.ce.fortaleza.lembrete.services.business;

import gov.ce.fortaleza.lembrete.api.models.ContractDTO;

/**
 * Created by berkson
 * Date: 06/01/2022
 * Time: 21:05
 */
public interface NotifyService {
    void verifyAndSchedule(ContractDTO contract);
}
