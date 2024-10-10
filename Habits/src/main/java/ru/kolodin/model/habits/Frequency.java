package ru.kolodin.model.habits;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Предопределенная периодичность привычки
 */
@RequiredArgsConstructor
@Getter
public enum Frequency {

    DAILY("Ежедневно"),
    WEEKLY("Еженедельно");

    private final String value;

}
