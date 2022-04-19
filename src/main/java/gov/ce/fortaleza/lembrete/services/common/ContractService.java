package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.AdditiveDTO;
import gov.ce.fortaleza.lembrete.api.models.ContractDTO;
import gov.ce.fortaleza.lembrete.domain.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 22:21
 */
public interface ContractService extends CrudService<ContractDTO, Long> {

    Contract getById(long id);

    boolean contractExists(String number);

    /**
     * Realiza a aditivação do contrato
     *
     * @param additiveDTO Dto de aditivo
     * @return Dto de contrato
     */
    ContractDTO add(AdditiveDTO additiveDTO);

    Page<ContractDTO> findAll(Pageable pageable);

    List<ContractDTO> findAllByContractNumber(int offset, int quantityPerPage, String direction);
}
