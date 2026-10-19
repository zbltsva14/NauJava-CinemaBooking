package ru.zyablitseva.NauJava.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.zyablitseva.NauJava.console.CommandProcessor;

import java.util.Scanner;

/**
 * Класс конфигурации Spring.
 */
@Configuration
public class AppConfig {

    @Autowired
    private CommandProcessor commandProcessor;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void init() {
        System.out.println("Приложение: " + appName);
        System.out.println("Версия: " + appVersion);
    }

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("\nСистема кинотеатра запущена");
                System.out.println("Введите команду ('exit' для выхода):");
                System.out.println("Доступные команды:");
                System.out.println("  create <id> <название> <жанр>");
                System.out.println("  read <id>");
                System.out.println("  update <id> <новый_жанр>");
                System.out.println("  delete <id>");

                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine();

                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Выход из программы...");
                        break;
                    }

                    try {
                        commandProcessor.processCommand(input);
                    } catch (Exception e) {
                        System.out.println("Ошибка при выполнении команды: " + e.getMessage());
                    }
                }
            }
        };
    }
}