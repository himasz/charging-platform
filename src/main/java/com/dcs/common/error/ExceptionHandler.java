package com.dcs.common.error;

import com.dcs.common.error.exception.DCSException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.dcs.common.Constants.*;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DCSException.class)
    public final ResponseEntity<DCSError> handleServiceException(DCSException exception) {
        DCSErrorCode errorCode = exception.getErrorCode();
        String detailedMessage = exception.getMessage();
        return new ResponseEntity<>(errorCode.response(detailedMessage), errorCode.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders httpHeaders, HttpStatus httpStatus, WebRequest request) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .toList();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CODE, DCSErrorCode.NOT_FOUND_BODY_FIELD.getCode());
        body.put(MESSAGE, DCSErrorCode.NOT_FOUND_BODY_FIELD.name());
        body.put(DESCRIPTION, DCSErrorCode.NOT_FOUND_BODY_FIELD.getMessage()+ " : " + errors);
        return new ResponseEntity<>(body, DCSErrorCode.NOT_FOUND_BODY_FIELD.getStatus());
    }
}
