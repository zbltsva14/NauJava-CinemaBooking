package ru.zyablitseva.NauJava.service;

import ru.zyablitseva.NauJava.entity.User;

/**
 * Бизнес-логика для работы с пользователями.
 */
public interface UserService {

    /**
     * Получить пользователя по логину.
     */
    User findByLogin(String login);

    /**
     * Добавить нового пользователя (с шифрованием пароля).
     */
    User addUser(User user);
}