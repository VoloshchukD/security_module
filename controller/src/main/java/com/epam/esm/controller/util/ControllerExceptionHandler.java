package com.epam.esm.controller.util;

import com.epam.esm.entity.dto.ErrorData;
import com.epam.esm.service.exception.DataNotFoundException;
import com.epam.esm.service.exception.ForbiddenRequestException;
import com.epam.esm.service.exception.IllegalPageNumberException;
import com.epam.esm.service.exception.ParameterNotPresentException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.util.ExceptionMessageHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ParameterNotPresentException.class)
    protected ResponseEntity<Object> handleParameterNotPresentException(ParameterNotPresentException exception,
                                                                        WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleException(exception, request, status);
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException exception,
                                                                 WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleException(exception, request, status);
    }

    @ExceptionHandler(ForbiddenRequestException.class)
    protected ResponseEntity<Object> handleForbiddenRequestException(ForbiddenRequestException exception,
                                                                 WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return handleException(exception, request, status);
    }

    @ExceptionHandler(IllegalPageNumberException.class)
    protected ResponseEntity<Object> handleDataNotFoundException(IllegalPageNumberException exception,
                                                                 WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String messageName = ExceptionMessageHandler.getMessageForLocale(exception.getMessage(),
                request.getLocale());
        log.error(messageName, exception);
        ErrorData errorData = new ErrorData(messageName, String.valueOf(status.value()));
        return new ResponseEntity<>(errorData, status);
    }

    private ResponseEntity<Object> handleException(ServiceException exception, WebRequest request,
                                                   HttpStatus httpStatus) {
        String messageName = ExceptionMessageHandler.getMessageForLocale(exception.getMessageName(),
                request.getLocale());
        log.error(messageName, exception);
        ErrorData errorData = new ErrorData(messageName, httpStatus.value() + exception.getEntityCode());
        return new ResponseEntity<>(errorData, httpStatus);
    }

}
