package eoen.jwtroles.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import eoen.jwtroles.dtos.ErrorsDTO;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorsDTO> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        String error = "Resource not found!";
        ErrorsDTO apiError = new ErrorsDTO(error, ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<ErrorsDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ BdException.class })
    public ResponseEntity<ErrorsDTO> handleBdException(BdException ex, WebRequest request) {
        String error = "Argument not valid!";
        ErrorsDTO apiError = new ErrorsDTO(error, ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<ErrorsDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> erros = createListErrors(ex.getBindingResult());
        String error = "Argument not valid!";
        ErrorsDTO apiError = new ErrorsDTO(error, erros, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<String> createListErrors(BindingResult bindingResult) {
        List<String> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msgUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            erros.add(msgUser);
        }
        return erros;
    }
}