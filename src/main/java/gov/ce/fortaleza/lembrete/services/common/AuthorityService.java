package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.api.models.AuthorityDTO;

import java.util.List;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 22:55
 */
public interface AuthorityService extends CrudService<AuthorityDTO, String> {
    long count();

    List<AuthorityDTO> findAllByUserId(long id);

    List<AuthorityDTO> saveAll(Iterable<AuthorityDTO> authorities);
}
