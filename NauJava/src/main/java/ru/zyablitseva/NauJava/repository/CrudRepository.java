package ru.zyablitseva.NauJava.repository;

/**
 * Шаблонный интерфейс, описывающий базовые CRUD-операции.
 * @param <T>  тип сущности, с которой работает репозиторий
 * @param <ID> тип уникального идентификатора сущности
 */

public interface CrudRepository<T, ID> {

    void create(T entity);

    T read(ID id);

    void update(T entity);

    void delete(ID id);
}