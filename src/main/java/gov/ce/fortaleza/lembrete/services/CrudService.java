package gov.ce.fortaleza.lembrete.services;

import java.util.List;
import java.util.Optional;

/**
 * Created by berkson
 * Date: 03/01/2022
 * Time: 20:58
 */
public interface CrudService<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();
}
