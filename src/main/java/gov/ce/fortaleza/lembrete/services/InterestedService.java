package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.models.InterestedDTO;

import java.util.Optional;

/**
 * Created by berkson
 * Date: 05/01/2022
 * Time: 23:27
 */
public interface InterestedService extends CrudService<InterestedDTO, Long> {
    Optional<InterestedDTO> findByCpf(String cpf);
}
