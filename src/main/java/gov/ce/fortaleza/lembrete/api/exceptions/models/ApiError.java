package gov.ce.fortaleza.lembrete.api.exceptions.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Created by berkson
 * Date: 08/01/2022
 * Time: 10:16
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private String path;

    /**
     * @param status  Http Status
     * @param message Mensagem de erro
     * @param path    Caminho solicitado na api
     */
    public ApiError(HttpStatus status, String message,
                    String path) {
        super();
        this.status = status.value();
        this.message = message;
        this.path = path;
    }


}
