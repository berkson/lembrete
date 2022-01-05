package gov.ce.fortaleza.lembrete.services;

import gov.ce.fortaleza.lembrete.api.models.CompanyDTO;

import java.util.Optional;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 23:16
 */
public interface CompanyService extends CrudService<CompanyDTO, Long>{
    Optional<CompanyDTO> findByCnpj(String cnpj);
}
