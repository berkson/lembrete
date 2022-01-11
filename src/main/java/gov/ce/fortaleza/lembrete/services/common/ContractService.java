package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.domain.Contract;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:21
 */
public interface ContractService extends CrudService<ContractDTO, Long> {

    Contract getById(long id);

    /**
     * Realiza a aditivação do contrato
     *
     * @param additiveDTO Dto de aditivo
     * @return Dto de contrato
     */
    ContractDTO add(AdditiveDTO additiveDTO);
}
