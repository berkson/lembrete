package gov.ce.fortaleza.lembrete.api.exceptions;

import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiError;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ApiErrors;
import gov.ce.fortaleza.lembrete.api.exceptions.models.ValidationError;
import gov.ce.fortaleza.lembrete.exceptions.InvalidRecoveryCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
import java.util.ArrayList;
import java.util.List;
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
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request) {

        List<String> validationErrors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            validationErrors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        String message = messageSource.getMessage("error.validation",
                null, LocaleContextHolder.getLocale());

        ApiError apiError = new ValidationError(
                status, message, validationErrors,
                ((ServletWebRequest) request).getRequest().getRequestURI());


        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            @NonNull HttpRequestMethodNotSupportedException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatus status,
            @NonNull WebRequest request) {

        String method = Optional.ofNullable(((ServletWebRequest) request).getHttpMethod())
                .map(Enum::name).orElse("");

        ApiError apiError = new ApiError(
                status, messageSource.getMessage("error.request.method",
                List.of(method).toArray(), LocaleContextHolder.getLocale()),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder().error(apiError).build(),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   @NonNull HttpHeaders headers,
                                                                   @NonNull HttpStatus status,
                                                                   @NonNull WebRequest request) {
        ApiError apiError = new ApiError(
                status, messageSource.getMessage(
                "error.handler.out",
                List.of(ex.getHttpMethod(), ex.getRequestURL()).toArray(),
                LocaleContextHolder.getLocale()),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder().error(apiError).build(),
                HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> list = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String restriction = "Valor: " + violation.getInvalidValue() + " " + violation.getMessage();
            list.add(restriction);
        }

        ApiError apiError = new ValidationError(
                HttpStatus.BAD_REQUEST, messageSource.getMessage(
                "error.restriction", null, LocaleContextHolder.getLocale()), list,
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return ApiErrors.builder().error(apiError).build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, messageSource.getMessage("error.not.found",
                null, LocaleContextHolder.getLocale()),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex,
                                                        WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRecoveryCodeException.class)
    public ResponseEntity<Object> handleIllegalArgument(InvalidRecoveryCodeException ex,
                                                        WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull HttpMessageNotReadableException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, @NonNull WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, messageSource
                .getMessage("error.conversion", null, LocaleContextHolder.getLocale()),
                ((ServletWebRequest) request).getRequest().getRequestURI());

        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, messageSource
                .getMessage("error.missing.parameter",
                        List.of(ex.getParameterName()).toArray(),
                        request.getLocale()),
                ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(ApiErrors.builder()
                .error(apiError).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthenticationException.class, AccessDeniedException.class})
    public ResponseEntity<Object> handleAuthentication(Exception ex, WebRequest request) {

        String message = ex.getLocalizedMessage();

        if (ex instanceof InsufficientAuthenticationException) {
            message = messageSource.getMessage("InsufficientAuthenticationException.fullAuth",
                    null, LocaleContextHolder.getLocale());
        }

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED,
                message,
                ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(ApiErrors.builder().error(apiError)
                .build(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI());
        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

}
