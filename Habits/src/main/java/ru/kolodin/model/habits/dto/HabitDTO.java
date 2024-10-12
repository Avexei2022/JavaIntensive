package ru.kolodin.model.habits.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.page.DTO;
import ru.kolodin.model.users.dto.UserDTO;

import java.util.Date;

/**
 * Класс ДТО привычки
 */
@Schema(description = "Класс ДТО привычки")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitDTO extends DTO {
    /**
     * Уникальный идентификатор привычки
     */
    @Schema(description = "Уникальный идентификатор привычки")
    private Long id;

    /**
     * Название привычки
     */
    @Schema(description = "Название привычки")
    private String title;

    /**
     * Описание привычки
     */
    @Schema(description = "Описание привычки")
    private String description;

    /**
     * Периодичность привычки
     */
    @Schema(description = "Периодичность привычки")
    private Frequency frequency;

    /**
     * Пользователь/обладатель привычки
     */
    @Schema(description = "Пользователь/обладатель привычки")
    private UserDTO userDTO;

    /**
     * Дата создания привычки
     */
    @Schema(description = "Дата создания привычки")
    private Date creationDate;
}
