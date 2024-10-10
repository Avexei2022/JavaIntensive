package ru.kolodin.model.exceptions;

/**
 * Пользовательское исключение.
 */
public class AnyReasonException extends RuntimeException {
    /**
     * Исключение по какой-либо причине.
     * @param message текст сообщения об ошибке.
     */
    public AnyReasonException(String message) {
        super(message);
    }
}
