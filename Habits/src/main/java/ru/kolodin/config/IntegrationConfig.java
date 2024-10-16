package ru.kolodin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;
import ru.kolodin.model.habitstatus.HabitStatus;
import ru.kolodin.model.message.MessageContextForSendingToUser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Конфигуратор интеграции.
 */
@Configuration
public class IntegrationConfig {


    /**
     * Входной канал сообщений.
     * @return - новый экземпляр канала
     */
    @Bean
    public MessageChannel messageInputChannel() {
        return new DirectChannel();
    }

    /**
     * Выходной канал (в данном случае запись в файл).
     * @return - новый экземпляр канала.
     */
    @Bean
    public MessageChannel fileWriterChannel() {
        return new DirectChannel();
    }

    /**
     * Преобразователь данных объекта в текст сообщения.
     * @return преобразователь.
     */
    @Bean
    @Transformer(inputChannel = "messageInputChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<MessageContextForSendingToUser, String> messageTransformer() {
        return context -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\tУважаемый ")
                    .append(context.getUserName())
                    .append("!\n\n")
                    .append("\tНаправляем список Ваших привычек на ")
                    .append(context.getDate())
                    .append(".\n");
            context.getHabitStatuses().forEach(
                    habitStatus ->
                        stringBuilder.append("Привычка: ")
                                .append(habitStatus.getHabit().getTitle())
                                .append(", Периодичность: ")
                                .append(habitStatus.getHabit().getFrequency().getValue())
                                .append(", Статус: ")
                                .append(habitStatus.getStatus().getValue())
                                .append(".\n")
            );
          return stringBuilder.toString();
        };
    }

    /**
     * Сервис выходного канала.
     * В данном случае сервис записи в файл.
     * @return новый сервис.
     */
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("messages/"));
        handler.setExpectReply(true);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }
}
