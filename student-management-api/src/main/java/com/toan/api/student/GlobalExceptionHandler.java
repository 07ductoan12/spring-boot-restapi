package com.toan.api.student;

import java.sql.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;


/**
 * GlobalExceptionHandler
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGenericException(HttpServletRequest request, Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date(System.currentTimeMillis()));
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.addError(ex.getMessage());
        error.setPath(request.getServletPath());

        LOGGER.error(ex.getMessage(), ex);

        return error;
    }
}
