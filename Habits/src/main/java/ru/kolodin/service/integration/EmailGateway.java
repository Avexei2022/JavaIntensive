package ru.kolodin.service.integration;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Интерфейс интеграции с почтой.
 * В данном варианте - уведомление пользователей в виде записи информации в файл с именем пользователя.
 */
@MessagingGateway(defaultRequestChannel = "messageInputChannel")
public interface EmailGateway {

    /**
     * Запись необходимой информации в файл.
     * @param filename имя файла - имя пользователя.
     * @param object уведомление.
     */
    void  writeToFile(@Header(FileHeaders.FILENAME) String filename, Object object);
}
