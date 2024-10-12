package ru.kolodin.model.habitstatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Предопределенный статус исполнения привычки
 */
@Schema(description = "Предопределенный статус исполнения привычки")
@RequiredArgsConstructor
@Getter
public enum Status {
    WAITING("В ожидании"),
    COMPLETED("Выполнена");

    private final String value;
}
