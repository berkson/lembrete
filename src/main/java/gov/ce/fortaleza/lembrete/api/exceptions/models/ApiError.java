package gov.ce.fortaleza.lembrete.api.exceptions.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Created by berkson
 * Date: 08/01/2022
 * Time: 10:16
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String path;

    public ApiError(LocalDateTime timestamp, HttpStatus status, String message,
                    String path) {
        super();
        this.timestamp = timestamp;
        this.status = status.value();
        this.message = message;
        this.path = path;
    }


}
