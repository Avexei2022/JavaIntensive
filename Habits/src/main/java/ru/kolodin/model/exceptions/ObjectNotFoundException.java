package ru.kolodin.model.exceptions;

/**
 * Пользовательское исключение.
 */
public class ObjectNotFoundException extends RuntimeException {

  /**
   * Исключение при отсутствии объекта в базе данных.
   * @param message текст сообщения об ошибке.
   */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
