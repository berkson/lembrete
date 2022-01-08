package gov.ce.fortaleza.lembrete.api.exceptions;

import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiError;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiErrors;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 22:41
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        log.info("Status code: " + status.value() + " description: " + status.getReasonPhrase());
        Map<String, Object> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            validationErrors.put(fieldName, message);
        });

        ApiError apiError = new ValidationError(LocalDateTime.now(),
                status, "Erro de validação", validationErrors,
                ((ServletWebRequest) request).getRequest().getRequestURI());


        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors constraintViolationException(ConstraintViolationException ex, WebRequest request) {

        String message = ex.getMessage().substring(ex.getMessage().indexOf(" ") + 1);

        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, message,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return ApiErrors.builder().error(apiError).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException ex,
                                                          WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Não encontrado",
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }


}
