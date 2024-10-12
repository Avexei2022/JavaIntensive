package ru.kolodin.model.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Класс сообщения
 */
@Schema(description = "Класс сообщения")
@Data
public class Message {

    /**
     * Строка сообщения
     */
    @Schema(description = "Строка сообщения")
    private String message;
}
