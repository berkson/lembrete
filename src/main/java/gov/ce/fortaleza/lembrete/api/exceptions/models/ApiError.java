package gov.ce.fortaleza.lembrete.api.exceptions.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by berkson
 * Date: 08/01/2022
 * Time: 10:16
 */
@Getter
@Setter
public class ApiError {
    private LocalDateTime dateTime;
    private HttpStatus status;
    private String message;
    private Map<String, Object> details;
    private String path;

    public ApiError(LocalDateTime dateTime, HttpStatus status, String message,
                    Map<String, Object> details, String path) {
        super();
        this.dateTime = dateTime;
        this.status = status;
        this.message = message;
        this.details = details;
        this.path = path;
    }

//    public ApiError(LocalDateTime dateTime, HttpStatus status, String message,
//                    String error, String path) {
//        super();
//        this.dateTime = dateTime;
//        this.status = status;
//        this.message = message;
//        this.path = path;
//        details = Arrays.asList(error);
//    }
}
