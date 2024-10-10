package ru.kolodin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Основной класс приложения.
 * Точка входа.
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class HabitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HabitsApplication.class, args);
    }
}
