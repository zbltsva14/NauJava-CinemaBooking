package ru.zyablitseva.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.zyablitseva.NauJava.entity.Movie;
import ru.zyablitseva.NauJava.service.MovieService;

/**
 * Компонент для обработки команд, введенных в консоль.
 */
@Component
public class CommandProcessor {

    private final MovieService movieService;

    @Autowired
    public CommandProcessor(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Разбирает строку ввода и вызывает соответствующий метод сервиса.
     * @param input строка, введенная пользователем
     */
    public void processCommand(String input) {
        String[] cmd = input.trim().split("\\s+");

        if (cmd.length == 0) return;

        switch (cmd[0].toLowerCase()) {
            case "create" -> {
                if (cmd.length >= 4) {
                    Long id = Long.valueOf(cmd[1]);
                    String title = cmd[2];
                    String genre = cmd[3];
                    movieService.createMovie(id, title, genre);
                    System.out.println("Фильм успешно добавлен!");
                } else {
                    System.out.println("Ошибка: неверный формат. Используйте: create <id> <название> <жанр>");
                }
            }
            case "read" -> {
                if (cmd.length == 2) {
                    Long id = Long.valueOf(cmd[1]);
                    Movie movie = movieService.findById(id);
                    if (movie != null) {
                        System.out.println("Найден фильм: " + movie);
                    } else {
                        System.out.println("Фильм с id " + id + " не найден.");
                    }
                }
            }
            case "update" -> {
                if (cmd.length == 3) {
                    Long id = Long.valueOf(cmd[1]);
                    String newGenre = cmd[2];
                    movieService.updateGenre(id, newGenre);
                    System.out.println("Жанр обновлен!");
                } else {
                    System.out.println("Ошибка: неверный формат. Используйте: update <id> <новый_жанр>");
                }
            }
            case "delete" -> {
                if (cmd.length == 2) {
                    Long id = Long.valueOf(cmd[1]);
                    movieService.deleteById(id);
                    System.out.println("Фильм удален!");
                } else {
                    System.out.println("Ошибка: неверный формат. Используйте: delete <id>");
                }
            }
            default -> System.out.println("Неизвестная команда. Доступно: create, read, update, delete, exit");
        }
    }
}