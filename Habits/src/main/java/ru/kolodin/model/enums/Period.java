package ru.kolodin.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Продолжительность периодов в днях.
 */
@Schema(description = "Продолжительность периодов в днях")
@RequiredArgsConstructor
@Getter
public enum Period {
    NOW(0),
    DAY(1),
    WEEK(7),
    MONTH(30);

    private final int value;

}
