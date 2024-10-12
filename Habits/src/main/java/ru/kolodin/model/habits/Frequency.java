package ru.kolodin.model.habits;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Предопределенная периодичность привычки
 */
@Schema(description = "Предопределенная периодичность привычки")
@RequiredArgsConstructor
@Getter
public enum Frequency {

    DAILY("Ежедневно"),
    WEEKLY("Еженедельно");

    private final String value;

}
