package gov.ce.fortaleza.lembrete.services.common;

import gov.ce.fortaleza.lembrete.domain.Authority;

import java.util.List;

/**
 * Created by berkson
 * Date: 13/01/2022
 * Time: 22:55
 */
public interface AuthorityService extends CrudService<Authority, String> {
    long count();

    List<Authority> findAllByUserId(long id);

    List<Authority> saveAll(Iterable<Authority> authorities);
}
