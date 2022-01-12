package gov.ce.fortaleza.lembrete.api.exceptions;

import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiError;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiErrors;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by berkson
 * Date: 04/01/2022
 * Time: 22:41
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        List<String> validationErrors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            validationErrors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String message = messageSource.getMessage("error.validation",
                null, Locale.getDefault());

        ApiError apiError = new ValidationError(LocalDateTime.now(),
                status, message, validationErrors,
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
        List<String> list = new ArrayList<>();
        int count = 1;

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String restriction = "Valor " + violation.getInvalidValue() + " " + violation.getMessage();
            list.add(restriction);
            count++;
        }

        ApiError apiError = new ValidationError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, "Erro(s) de restrição", list,
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex,
                                                        WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, ex.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST, messageSource
                .getMessage("error.conversion", null, Locale.getDefault()),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
