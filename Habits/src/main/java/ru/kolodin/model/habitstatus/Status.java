package ru.kolodin.model.habitstatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Предопределенный статус исполнения привычки
 */
@RequiredArgsConstructor
@Getter
public enum Status {
    WAITING("В ожидании"),
    COMPLETED("Выполнена");

    private final String value;
}
