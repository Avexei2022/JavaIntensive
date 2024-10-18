package ru.kolodin.service.integration;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.message.MessageContextForSendingToUser;
import ru.kolodin.service.db.habit.HabitDbService;
import ru.kolodin.service.db.habitstatus.HabitStatusDbService;
import ru.kolodin.service.db.user.UserDbService;

import java.util.Date;
import java.util.List;

/**
 * Сервис сообщение/напоминаний по электронной почте
 */
@Service
@AllArgsConstructor
public class EmailService {

    private final EmailGateway emailGateway;
    private final UserDbService userDbService;
    private final HabitDbService habitDbService;
    private final HabitStatusDbService habitStatusDbService;

    /**
     * Рассылка уведомлений пользователям в 09.00 и 21.00 о статусе привычек на текущую дату
     */
    @Scheduled(cron = "0 0 9,21 * * *")
    private void sendMessage() {
        Date today = new Date();
        userDbService.findAll().forEach(user -> {
            habitDbService.getAllByUserEmail(user.getEmail()).forEach(habit -> {
                MessageContextForSendingToUser messageContextForSendingToUser = new MessageContextForSendingToUser();
                messageContextForSendingToUser.setUserName(user.getUsername());
                messageContextForSendingToUser.setDate(today);
                messageContextForSendingToUser.setHabitStatuses(
                        habitStatusDbService
                                .getAllByHabitIdAndDateBetween(habit.getId(), today, today)
                );
                String fileName = user.getEmail().replace("@", "_").concat(".txt");
                emailGateway.writeToFile(fileName, messageContextForSendingToUser);
            });
        });
    }
}
