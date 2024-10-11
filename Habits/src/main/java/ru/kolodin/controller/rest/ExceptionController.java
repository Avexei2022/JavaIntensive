package ru.kolodin.controller.rest;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kolodin.model.exceptions.AnyReasonException;
import ru.kolodin.model.exceptions.ExceptionBody;
import ru.kolodin.model.exceptions.ObjectNotFoundException;
import ru.kolodin.model.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;

/**
 * REST-Контроллер обработки исключений.
 */
@RestControllerAdvice
@SecurityScheme(  name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
public class ExceptionController {

    /**
     * Исключение при отсутствии пользователя в базе данных
     * @param e объект исключения.
     * @return тело/обертка исключения.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody UserNotFound(ObjectNotFoundException e){
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        exceptionBody.setDateTime(LocalDateTime.now());
        return exceptionBody;
    }

    /**
     * Исключение при отсутствии доступа к ресурсу
     * @param e объект исключения.
     * @return тело/обертка исключения.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ExceptionBody resourceNotFound(ResourceNotFoundException e){
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        exceptionBody.setDateTime(LocalDateTime.now());
        return exceptionBody;
    }

    /**
     * Исключение при какой-либо ошибке.
     * @param e объект исключения.
     * @return тело/обертка исключения.
     */
    @ExceptionHandler(AnyReasonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody anyReason(AnyReasonException e){
        ExceptionBody exceptionBody = new ExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        exceptionBody.setDateTime(LocalDateTime.now());
        return exceptionBody;
    }
}
