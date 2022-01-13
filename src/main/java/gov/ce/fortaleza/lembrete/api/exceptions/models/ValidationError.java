package gov.ce.fortaleza.lembrete.api.exceptions.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Created by berkson
 * Date: 08/01/2022
 * Time: 12:42
 */
@Getter
@Setter
@NoArgsConstructor
public class ValidationError extends ApiError {

    private List<String> details;

    public ValidationError(HttpStatus status,
                           String message, List<String> details, String path) {
        super(status, message, path);
        this.details = details;
    }

}
