package ru.kolodin.model.message;

import lombok.Data;
import ru.kolodin.model.habitstatus.HabitStatus;

import java.util.Date;
import java.util.List;

/**
 * Данные для формирования сообщения пользователю
 */
@Data
public class MessageContextForSendingToUser {
    private String userName;
    private Date date;
    private List<HabitStatus> habitStatuses;
}
