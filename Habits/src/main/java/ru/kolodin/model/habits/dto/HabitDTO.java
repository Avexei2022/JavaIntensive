package ru.kolodin.model.habits.dto;

import lombok.*;
import ru.kolodin.model.habits.Frequency;
import ru.kolodin.model.page.DTO;
import ru.kolodin.model.users.dto.UserDTO;

import java.util.Date;

/**
 * Класс ДТО привычки
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitDTO extends DTO {
    /**
     * Уникальный идентификатор привычки
     */
    private Long id;

    /**
     * Название привычки
     */
    private String title;

    /**
     * Описание привычки
     */
    private String description;

    /**
     * Периодичность привычки
     */
    private Frequency frequency;

    /**
     * Пользователь/обладатель привычки
     */
    private UserDTO userDTO;

    /**
     * Дата создания привычки
     */
    private Date creationDate;
}
