package gov.ce.fortaleza.lembrete.api.exceptions;

import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiError;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiErrors;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status,
            WebRequest request) {

        String method = Optional.ofNullable(((ServletWebRequest) request).getHttpMethod())
                .map(Enum::name).orElse("");

        ApiError apiError = new ApiError(LocalDateTime.now(),
                status, "Método de requisição " + method + " não suportado",
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder().error(apiError).build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                status, "Nenhum serviço encontrado para requisição '" +
                ex.getHttpMethod() + "' na url", ex.getRequestURL());

        return new ResponseEntity<>(ApiErrors.builder().error(apiError).build(),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> map = new HashMap<>();
        int count = 1;

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String value = "Valor " + violation.getInvalidValue() + " " + violation.getMessage();
            map.put("restriction_" + count, value);
            count++;
        }

        ApiError apiError = new ValidationError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Erro(s) de restrição", map,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return ApiErrors.builder().error(apiError).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Não encontrado",
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

}
