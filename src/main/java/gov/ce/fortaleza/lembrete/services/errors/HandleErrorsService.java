package gov.ce.fortaleza.lembrete.services.errors;

import gov.ce.fortaleza.lembrete.exceptions.CustomDataIntegrityException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by berkson
 * Date: 02/04/2022
 * Time: 10:24
 */
public interface HandleErrorsService {
    void handleDataIntegrityErros(DataIntegrityViolationException e) throws CustomDataIntegrityException;
}
