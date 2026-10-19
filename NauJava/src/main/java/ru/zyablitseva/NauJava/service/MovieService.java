package ru.zyablitseva.NauJava.service;

import ru.zyablitseva.NauJava.entity.Movie;

/**
 * Интерфейс, описывающий бизнес-логику для работы с фильмами.
 */
public interface MovieService {

    void createMovie(Long id, String title, String genre);

    Movie findById(Long id);

    void deleteById(Long id);

    void updateGenre(Long id, String newGenre);
}